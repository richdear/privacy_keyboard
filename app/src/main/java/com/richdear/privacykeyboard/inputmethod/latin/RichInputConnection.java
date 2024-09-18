

package com.richdear.privacykeyboard.inputmethod.latin;

import android.inputmethodservice.InputMethodService;
import android.os.SystemClock;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;

import com.richdear.privacykeyboard.inputmethod.latin.common.Constants;
import com.richdear.privacykeyboard.inputmethod.latin.common.StringUtils;
import com.richdear.privacykeyboard.inputmethod.latin.common.UnicodeSurrogate;
import com.richdear.privacykeyboard.inputmethod.latin.settings.SpacingAndPunctuations;
import com.richdear.privacykeyboard.inputmethod.latin.utils.CapsModeUtils;
import com.richdear.privacykeyboard.inputmethod.latin.utils.DebugLogUtils;


public final class RichInputConnection {
  private static final String TAG = "RichInputConnection";
  private static final boolean DBG = false;
  private static final boolean DEBUG_PREVIOUS_TEXT = false;
  private static final boolean DEBUG_BATCH_NESTING = false;
  private static final int INVALID_CURSOR_POSITION = -1;


  private static final long SLOW_INPUT_CONNECTION_ON_FULL_RELOAD_MS = 1000;

  private static final long SLOW_INPUT_CONNECTION_ON_PARTIAL_RELOAD_MS = 200;

  private static final int OPERATION_GET_TEXT_BEFORE_CURSOR = 0;
  private static final int OPERATION_GET_TEXT_AFTER_CURSOR = 1;
  private static final int OPERATION_RELOAD_TEXT_CACHE = 3;
  private static final String[] OPERATION_NAMES = new String[]{
      "GET_TEXT_BEFORE_CURSOR",
      "GET_TEXT_AFTER_CURSOR",
      "GET_WORD_RANGE_AT_CURSOR",
      "RELOAD_TEXT_CACHE"};


  private int mExpectedSelStart = INVALID_CURSOR_POSITION; // in chars, not code points

  private int mExpectedSelEnd = INVALID_CURSOR_POSITION; // in chars, not code points

  private final StringBuilder mCommittedTextBeforeComposingText = new StringBuilder();

  private final StringBuilder mComposingText = new StringBuilder();


  private final SpannableStringBuilder mTempObjectForCommitText = new SpannableStringBuilder();

  private final InputMethodService mParent;
  private InputConnection mIC;
  private E2EEInputConnection mOtherIC;
  private int mNestLevel;

  private boolean shouldUseOtherIC;

  public RichInputConnection(final InputMethodService parent) {
    mParent = parent;
    mIC = null;
    mNestLevel = 0;
  }

  public boolean isConnected() {
    return getIC() != null;
  }

  private void checkConsistencyForDebug() {
    final ExtractedTextRequest r = new ExtractedTextRequest();
    r.hintMaxChars = 0;
    r.hintMaxLines = 0;
    r.token = 1;
    r.flags = 0;
    final ExtractedText et = getIC().getExtractedText(r, 0);
    final CharSequence beforeCursor = getTextBeforeCursor(Constants.EDITOR_CONTENTS_CACHE_SIZE,
        0);
    final StringBuilder internal = new StringBuilder(mCommittedTextBeforeComposingText)
        .append(mComposingText);
    if (null == et || null == beforeCursor) return;
    final int actualLength = Math.min(beforeCursor.length(), internal.length());
    if (internal.length() > actualLength) {
      internal.delete(0, internal.length() - actualLength);
    }
    final String reference = (beforeCursor.length() <= actualLength) ? beforeCursor.toString()
        : beforeCursor.subSequence(beforeCursor.length() - actualLength,
        beforeCursor.length()).toString();
    if (et.selectionStart != mExpectedSelStart
        || !(reference.equals(internal.toString()))) {
      final String context = "Expected selection start = " + mExpectedSelStart
          + "\nActual selection start = " + et.selectionStart
          + "\nExpected text = " + internal.length() + " " + internal
          + "\nActual text = " + reference.length() + " " + reference;
      ((LatinIME) mParent).debugDumpStateAndCrashWithException(context);
    } else {
      Log.e(TAG, DebugLogUtils.getStackTrace(2));
      Log.e(TAG, "Exp <> Actual : " + mExpectedSelStart + " <> " + et.selectionStart);
    }
  }

  public void beginBatchEdit() {
    if (++mNestLevel == 1) {
      if (isConnected()) {
        getIC().beginBatchEdit();
      }
    } else {
      if (DBG) {
        throw new RuntimeException("Nest level too deep");
      }
      Log.e(TAG, "Nest level too deep : " + mNestLevel);
    }
    if (DEBUG_BATCH_NESTING) checkBatchEdit();
    if (DEBUG_PREVIOUS_TEXT) checkConsistencyForDebug();
  }

  public void endBatchEdit() {
    if (mNestLevel <= 0) Log.e(TAG, "Batch edit not in progress!"); // TODO: exception instead
    if (--mNestLevel == 0 && isConnected()) {
      getIC().endBatchEdit();
    }
    if (DEBUG_PREVIOUS_TEXT) checkConsistencyForDebug();
  }


  public boolean resetCachesUponCursorMoveAndReturnSuccess(final int newSelStart,
                                                           final int newSelEnd) {
    mExpectedSelStart = newSelStart;
    mExpectedSelEnd = newSelEnd;
    mComposingText.setLength(0);
    final boolean didReloadTextSuccessfully = reloadTextCache();
    if (!didReloadTextSuccessfully) {
      Log.d(TAG, "Will try to retrieve text later.");
      return false;
    }
    return true;
  }


  private boolean reloadTextCache() {
    mCommittedTextBeforeComposingText.setLength(0);
    // Call upon the inputconnection directly since our own method is using the cache, and
    // we want to refresh it.
    final CharSequence textBeforeCursor = getTextBeforeCursorAndDetectLaggyConnection(
        OPERATION_RELOAD_TEXT_CACHE,
        SLOW_INPUT_CONNECTION_ON_FULL_RELOAD_MS,
        Constants.EDITOR_CONTENTS_CACHE_SIZE,
        0 );
    if (null == textBeforeCursor) {
      // For some reason the app thinks we are not connected to it. This looks like a
      // framework bug... Fall back to ground state and return false.
      mExpectedSelStart = INVALID_CURSOR_POSITION;
      mExpectedSelEnd = INVALID_CURSOR_POSITION;
      Log.e(TAG, "Unable to connect to the editor to retrieve text.");
      return false;
    }
    mCommittedTextBeforeComposingText.append(textBeforeCursor);
    return true;
  }

  private void checkBatchEdit() {
    if (mNestLevel != 1) {
      // TODO: exception instead
      Log.e(TAG, "Batch edit level incorrect : " + mNestLevel);
      Log.e(TAG, DebugLogUtils.getStackTrace(4));
    }
  }

  public void finishComposingText() {
    if (DEBUG_BATCH_NESTING) checkBatchEdit();
    if (DEBUG_PREVIOUS_TEXT) checkConsistencyForDebug();
    // TODO: this is not correct! The cursor is not necessarily after the composing text.
    // In the practice right now this is only called when input ends so it will be reset so
    // it works, but it's wrong and should be fixed.
    mCommittedTextBeforeComposingText.append(mComposingText);
    mComposingText.setLength(0);
    if (isConnected()) {
      getIC().finishComposingText();
    }
  }


  public void commitText(final CharSequence text, final int newCursorPosition) {
    RichInputMethodManager.getInstance().resetSubtypeCycleOrder();
    if (DEBUG_BATCH_NESTING) checkBatchEdit();
    if (DEBUG_PREVIOUS_TEXT) checkConsistencyForDebug();
    mCommittedTextBeforeComposingText.append(text);
    // TODO: the following is exceedingly error-prone. Right now when the cursor is in the
    // middle of the composing word mComposingText only holds the part of the composing text
    // that is before the cursor, so this actually works, but it's terribly confusing. Fix this.
    if (hasCursorPosition()) {
      mExpectedSelStart += text.length() - mComposingText.length();
      mExpectedSelEnd = mExpectedSelStart;
    }
    mComposingText.setLength(0);
    if (isConnected()) {
      mTempObjectForCommitText.clear();
      mTempObjectForCommitText.append(text);
      final CharacterStyle[] spans = mTempObjectForCommitText.getSpans(
          0, text.length(), CharacterStyle.class);
      for (final CharacterStyle span : spans) {
        final int spanStart = mTempObjectForCommitText.getSpanStart(span);
        final int spanEnd = mTempObjectForCommitText.getSpanEnd(span);
        final int spanFlags = mTempObjectForCommitText.getSpanFlags(span);
        // We have to adjust the end of the span to include an additional character.
        // This is to avoid splitting a unicode surrogate pair.
        // See com.richdear.privacykeyboard.inputmethod.latin.common.Constants.UnicodeSurrogate
        // See https://b.corp.google.com/issues/19255233
        if (0 < spanEnd && spanEnd < mTempObjectForCommitText.length()) {
          final char spanEndChar = mTempObjectForCommitText.charAt(spanEnd - 1);
          final char nextChar = mTempObjectForCommitText.charAt(spanEnd);
          if (UnicodeSurrogate.isLowSurrogate(spanEndChar)
              && UnicodeSurrogate.isHighSurrogate(nextChar)) {
            mTempObjectForCommitText.setSpan(span, spanStart, spanEnd + 1, spanFlags);
          }
        }
      }
      getIC().commitText(mTempObjectForCommitText, newCursorPosition);
    }
  }

  public CharSequence getSelectedText(final int flags) {
    return isConnected() ? getIC().getSelectedText(flags) : null;
  }

  public boolean canDeleteCharacters() {
    return mExpectedSelStart > 0;
  }


  public int getCursorCapsMode(final int inputType, final SpacingAndPunctuations spacingAndPunctuations) {
    if (!isConnected()) {
      return Constants.TextUtils.CAP_MODE_OFF;
    }
    if (!TextUtils.isEmpty(mComposingText)) {
      // We have some composing text - we should be in MODE_CHARACTERS only.
      return TextUtils.CAP_MODE_CHARACTERS & inputType;
    }
    // TODO: this will generally work, but there may be cases where the buffer contains SOME
    // information but not enough to determine the caps mode accurately. This may happen after
    // heavy pressing of delete, for example DEFAULT_TEXT_CACHE_SIZE - 5 times or so.
    // getCapsMode should be updated to be able to return a "not enough info" result so that
    // we can get more context only when needed.
    if (TextUtils.isEmpty(mCommittedTextBeforeComposingText) && 0 != mExpectedSelStart) {
      if (!reloadTextCache()) {
        Log.w(TAG, "Unable to connect to the editor. "
            + "Setting caps mode without knowing text.");
      }
    }
    // This never calls InputConnection#getCapsMode - in fact, it's a static method that
    // never blocks or initiates IPC.
    // TODO: don't call #toString() here. Instead, all accesses to
    // mCommittedTextBeforeComposingText should be done on the main thread.
    return CapsModeUtils.getCapsMode(mCommittedTextBeforeComposingText.toString(), inputType,
        spacingAndPunctuations);
  }

  public int getCodePointBeforeCursor() {
    final int length = mCommittedTextBeforeComposingText.length();
    if (length < 1) return Constants.NOT_A_CODE;
    return Character.codePointBefore(mCommittedTextBeforeComposingText, length);
  }

  public CharSequence getTextBeforeCursor(final int n, final int flags) {
    final int cachedLength =
        mCommittedTextBeforeComposingText.length() + mComposingText.length();
    // If we have enough characters to satisfy the request, or if we have all characters in
    // the text field, then we can return the cached version right away.
    // However, if we don't have an expected cursor position, then we should always
    // go fetch the cache again (as it happens, INVALID_CURSOR_POSITION < 0, so we need to
    // test for this explicitly)
    if (INVALID_CURSOR_POSITION != mExpectedSelStart
        && (cachedLength >= n || cachedLength >= mExpectedSelStart)) {
      final StringBuilder s = new StringBuilder(mCommittedTextBeforeComposingText);
      // We call #toString() here to create a temporary object.
      // In some situations, this method is called on a worker thread, and it's possible
      // the main thread touches the contents of mComposingText while this worker thread
      // is suspended, because mComposingText is a StringBuilder. This may lead to crashes,
      // so we call #toString() on it. That will result in the return value being strictly
      // speaking wrong, but since this is used for basing bigram probability off, and
      // it's only going to matter for one getSuggestions call, it's fine in the practice.
      s.append(mComposingText);
      if (s.length() > n) {
        s.delete(0, s.length() - n);
      }
      return s;
    }
    return getTextBeforeCursorAndDetectLaggyConnection(
        OPERATION_GET_TEXT_BEFORE_CURSOR,
        SLOW_INPUT_CONNECTION_ON_PARTIAL_RELOAD_MS,
        n, flags);
  }

  public CharSequence getTextAfterCursor(final int n, final int flags) {
    return getTextAfterCursorAndDetectLaggyConnection(
        OPERATION_GET_TEXT_AFTER_CURSOR,
        SLOW_INPUT_CONNECTION_ON_PARTIAL_RELOAD_MS,
        n, flags);
  }

  private CharSequence getTextBeforeCursorAndDetectLaggyConnection(
      final int operation, final long timeout, final int n, final int flags) {
    if (!isConnected()) {
      return null;
    }
    final long startTime = SystemClock.uptimeMillis();
    final CharSequence result = getIC().getTextBeforeCursor(n, flags);
    detectLaggyConnection(operation, timeout, startTime);
    return result;
  }

  private CharSequence getTextAfterCursorAndDetectLaggyConnection(
      final int operation, final long timeout, final int n, final int flags) {
    if (!isConnected()) {
      return null;
    }
    final long startTime = SystemClock.uptimeMillis();
    final CharSequence result = getIC().getTextAfterCursor(n, flags);
    detectLaggyConnection(operation, timeout, startTime);
    return result;
  }

  private void detectLaggyConnection(final int operation, final long timeout, final long startTime) {
    final long duration = SystemClock.uptimeMillis() - startTime;
    if (duration >= timeout) {
      final String operationName = OPERATION_NAMES[operation];
      Log.w(TAG, "Slow InputConnection: " + operationName + " took " + duration + " ms.");
    }
  }

  public void deleteTextBeforeCursor(final int beforeLength) {
    if (DEBUG_BATCH_NESTING) checkBatchEdit();
    // TODO: the following is incorrect if the cursor is not immediately after the composition.
    // Right now we never come here in this case because we reset the composing state before we
    // come here in this case, but we need to fix this.
    final int remainingChars = mComposingText.length() - beforeLength;
    if (remainingChars >= 0) {
      mComposingText.setLength(remainingChars);
    } else {
      mComposingText.setLength(0);
      // Never cut under 0
      final int len = Math.max(mCommittedTextBeforeComposingText.length()
          + remainingChars, 0);
      mCommittedTextBeforeComposingText.setLength(len);
    }
    if (mExpectedSelStart > beforeLength) {
      mExpectedSelStart -= beforeLength;
      mExpectedSelEnd -= beforeLength;
    } else {
      // There are fewer characters before the cursor in the buffer than we are being asked to
      // delete. Only delete what is there, and update the end with the amount deleted.
      mExpectedSelEnd -= mExpectedSelStart;
      mExpectedSelStart = 0;
    }
    if (isConnected()) {
      getIC().deleteSurroundingText(beforeLength, 0);
    }
    if (DEBUG_PREVIOUS_TEXT) checkConsistencyForDebug();
  }

  public void performEditorAction(final int actionId) {
    if (isConnected()) {
      getIC().performEditorAction(actionId);
    }
  }

  public void sendKeyEvent(final KeyEvent keyEvent) {
    if (DEBUG_BATCH_NESTING) checkBatchEdit();
    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
      if (DEBUG_PREVIOUS_TEXT) checkConsistencyForDebug();
      // This method is only called for enter or backspace when speaking to old applications
      // (target SDK <= 15 (Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)), or for digits.
      // When talking to new applications we never use this method because it's inherently
      // racy and has unpredictable results, but for backward compatibility we continue
      // sending the key events for only Enter and Backspace because some applications
      // mistakenly catch them to do some stuff.
      switch (keyEvent.getKeyCode()) {
        case KeyEvent.KEYCODE_ENTER:
          mCommittedTextBeforeComposingText.append("\n");
          if (hasCursorPosition()) {
            mExpectedSelStart += 1;
            mExpectedSelEnd = mExpectedSelStart;
          }
          break;
        case KeyEvent.KEYCODE_DEL:
          if (0 == mComposingText.length()) {
            if (mCommittedTextBeforeComposingText.length() > 0) {
              mCommittedTextBeforeComposingText.delete(
                  mCommittedTextBeforeComposingText.length() - 1,
                  mCommittedTextBeforeComposingText.length());
            }
          } else {
            mComposingText.delete(mComposingText.length() - 1, mComposingText.length());
          }

          if (mExpectedSelStart > 0 && mExpectedSelStart == mExpectedSelEnd) {
            // TODO: Handle surrogate pairs.
            mExpectedSelStart -= 1;
          }
          mExpectedSelEnd = mExpectedSelStart;
          break;
        case KeyEvent.KEYCODE_UNKNOWN:
          if (null != keyEvent.getCharacters()) {
            mCommittedTextBeforeComposingText.append(keyEvent.getCharacters());
            if (hasCursorPosition()) {
              mExpectedSelStart += keyEvent.getCharacters().length();
              mExpectedSelEnd = mExpectedSelStart;
            }
          }
          break;
        default:
          final String text = StringUtils.newSingleCodePointString(keyEvent.getUnicodeChar());
          mCommittedTextBeforeComposingText.append(text);
          if (hasCursorPosition()) {
            mExpectedSelStart += text.length();
            mExpectedSelEnd = mExpectedSelStart;
          }
          break;
      }
    }
    if (isConnected()) {
      getIC().sendKeyEvent(keyEvent);
    }
  }


  public void setSelection(int start, int end) {
    if (DEBUG_BATCH_NESTING) checkBatchEdit();
    if (DEBUG_PREVIOUS_TEXT) checkConsistencyForDebug();
    if (start < 0 || end < 0) {
      return;
    }
    if (mExpectedSelStart == start && mExpectedSelEnd == end) {
      return;
    }

    mExpectedSelStart = start;
    mExpectedSelEnd = end;
    if (isConnected()) {
      final boolean isIcValid = getIC().setSelection(start, end);
      if (!isIcValid) {
        return;
      }
    }
    reloadTextCache();
  }

  public int getExpectedSelectionStart() {
    return mExpectedSelStart;
  }

  public int getExpectedSelectionEnd() {
    return mExpectedSelEnd;
  }


  public boolean hasSelection() {
    return mExpectedSelEnd != mExpectedSelStart;
  }

  public boolean hasCursorPosition() {
    return mExpectedSelStart != INVALID_CURSOR_POSITION && mExpectedSelEnd != INVALID_CURSOR_POSITION;
  }


  public int getUnicodeSteps(int chars, boolean rightSidePointer) {
    int steps = 0;
    if (chars < 0) {
      CharSequence charsBeforeCursor = rightSidePointer && hasSelection() ?
          getSelectedText(0) :
          getTextBeforeCursor(-chars * 2, 0);
      if (charsBeforeCursor != null) {
        for (int i = charsBeforeCursor.length() - 1; i >= 0 && chars < 0; i--, chars++, steps--) {
          if (Character.isSurrogate(charsBeforeCursor.charAt(i))) {
            steps--;
            i--;
          }
        }
      }
    } else if (chars > 0) {
      CharSequence charsAfterCursor = !rightSidePointer && hasSelection() ?
          getSelectedText(0) :
          getTextAfterCursor(chars * 2, 0);
      if (charsAfterCursor != null) {
        for (int i = 0; i < charsAfterCursor.length() && chars > 0; i++, chars--, steps++) {
          if (Character.isSurrogate(charsAfterCursor.charAt(i))) {
            steps++;
            i++;
          }
        }
      }
    }
    return steps;
  }

  // TODO
  private InputConnection getIC() {
    if (!shouldUseOtherIC) mIC = mParent.getCurrentInputConnection();
    return shouldUseOtherIC ? mOtherIC : mIC;
  }

  // TODO
  public void setShouldUseOtherIC(boolean shouldUseOtherIC) {
    this.shouldUseOtherIC = shouldUseOtherIC;
  }

  // TODO
  public void setOtherIC(TextView textView) {
    if (textView == null) return;
    mOtherIC = new E2EEInputConnection(textView);
  }
}
