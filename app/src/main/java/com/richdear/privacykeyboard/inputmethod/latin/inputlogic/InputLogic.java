

package com.richdear.privacykeyboard.inputmethod.latin.inputlogic;

import android.os.SystemClock;
import android.text.TextUtils;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

import com.richdear.privacykeyboard.inputmethod.event.Event;
import com.richdear.privacykeyboard.inputmethod.event.InputTransaction;
import com.richdear.privacykeyboard.inputmethod.latin.LatinIME;
import com.richdear.privacykeyboard.inputmethod.latin.RichInputConnection;
import com.richdear.privacykeyboard.inputmethod.latin.common.Constants;
import com.richdear.privacykeyboard.inputmethod.latin.common.StringUtils;
import com.richdear.privacykeyboard.inputmethod.latin.settings.SettingsValues;
import com.richdear.privacykeyboard.inputmethod.latin.utils.InputTypeUtils;
import com.richdear.privacykeyboard.inputmethod.latin.utils.RecapitalizeStatus;
import com.richdear.privacykeyboard.inputmethod.latin.utils.SubtypeLocaleUtils;

import java.util.TreeSet;


public final class InputLogic {
  // TODO : Remove this member when we can.
  final LatinIME mLatinIME;

  // This has package visibility so it can be accessed from InputLogicHandler.
  public final RichInputConnection mConnection;
  private final RecapitalizeStatus mRecapitalizeStatus = new RecapitalizeStatus();

  public final TreeSet<Long> mCurrentlyPressedHardwareKeys = new TreeSet<>();


  public InputLogic(final LatinIME latinIME) {
    mLatinIME = latinIME;
    mConnection = new RichInputConnection(latinIME);
    // TODO handle better
    mLatinIME.setRichInputConnection(mConnection);
  }


  public void startInput() {
    mRecapitalizeStatus.disable(); // Do not perform recapitalize until the cursor is moved once
    mCurrentlyPressedHardwareKeys.clear();
  }


  public void onSubtypeChanged() {
    startInput();
  }


  public InputTransaction onTextInput(final SettingsValues settingsValues, final Event event) {
    final String rawText = event.getTextToCommit().toString();
    final InputTransaction inputTransaction = new InputTransaction(settingsValues);
    mConnection.beginBatchEdit();
    final String text = performSpecificTldProcessingOnTextInput(rawText);
    mConnection.commitText(text, 1);
    mConnection.endBatchEdit();
    // Space state must be updated before calling updateShiftState
    inputTransaction.requireShiftUpdate(InputTransaction.SHIFT_UPDATE_NOW);
    return inputTransaction;
  }


  public boolean onUpdateSelection(final int newSelStart, final int newSelEnd) {
    resetEntireInputState(newSelStart, newSelEnd);

    // The cursor has been moved : we now accept to perform recapitalization
    mRecapitalizeStatus.enable();
    // Stop the last recapitalization, if started.
    mRecapitalizeStatus.stop();
    return true;
  }


  public InputTransaction onCodeInput(final SettingsValues settingsValues, final Event event) {
    final InputTransaction inputTransaction = new InputTransaction(settingsValues);
    mConnection.beginBatchEdit();

    Event currentEvent = event;
    while (null != currentEvent) {
      if (currentEvent.isConsumed()) {
        handleConsumedEvent(currentEvent);
      } else if (currentEvent.isFunctionalKeyEvent()) {
        handleFunctionalEvent(currentEvent, inputTransaction);
      } else {
        handleNonFunctionalEvent(currentEvent, inputTransaction);
      }
      currentEvent = currentEvent.mNextEvent;
    }
    mConnection.endBatchEdit();
    return inputTransaction;
  }


  private void handleConsumedEvent(final Event event) {
    // A consumed event may have text to commit and an update to the composing state, so
    // we evaluate both. With some combiners, it's possible than an event contains both
    // and we enter both of the following if clauses.
    final CharSequence textToCommit = event.getTextToCommit();
    if (!TextUtils.isEmpty(textToCommit)) {
      mConnection.commitText(textToCommit, 1);
    }
  }


  private void handleFunctionalEvent(final Event event, final InputTransaction inputTransaction) {
    switch (event.mKeyCode) {
      case Constants.CODE_DELETE:
        handleBackspaceEvent(event, inputTransaction);
        // Backspace is a functional key, but it affects the contents of the editor.
        break;
      case Constants.CODE_SHIFT:
        performRecapitalization(inputTransaction.mSettingsValues);
        inputTransaction.requireShiftUpdate(InputTransaction.SHIFT_UPDATE_NOW);
        break;
      case Constants.CODE_CAPSLOCK:
        // Note: Changing keyboard to shift lock state is handled in
        // {@link KeyboardSwitcher#onEvent(Event)}.
        break;
      case Constants.CODE_SYMBOL_SHIFT:
        // Note: Calling back to the keyboard on the symbol Shift key is handled in
        // {@link #onPressKey(int,int,boolean)} and {@link #onReleaseKey(int,boolean)}.
        break;
      case Constants.CODE_SWITCH_ALPHA_SYMBOL:
        // Note: Calling back to the keyboard on symbol key is handled in
        // {@link #onPressKey(int,int,boolean)} and {@link #onReleaseKey(int,boolean)}.
        break;
      case Constants.CODE_SETTINGS:
        onSettingsKeyPressed();
        break;
      case Constants.CODE_ACTION_NEXT:
        performEditorAction(EditorInfo.IME_ACTION_NEXT);
        break;
      case Constants.CODE_ACTION_PREVIOUS:
        performEditorAction(EditorInfo.IME_ACTION_PREVIOUS);
        break;
      case Constants.CODE_LANGUAGE_SWITCH:
        handleLanguageSwitchKey();
        break;
      case Constants.CODE_SHIFT_ENTER:
        final Event tmpEvent = Event.createSoftwareKeypressEvent(Constants.CODE_ENTER,
            event.mKeyCode, event.mX, event.mY, event.isKeyRepeat());
        handleNonSpecialCharacterEvent(tmpEvent, inputTransaction);
        // Shift + Enter is treated as a functional key but it results in adding a new
        // line, so that does affect the contents of the editor.
        break;
      default:
        throw new RuntimeException("Unknown key code : " + event.mKeyCode);
    }
  }


  private void handleNonFunctionalEvent(final Event event,
                                        final InputTransaction inputTransaction) {
    switch (event.mCodePoint) {
      case Constants.CODE_ENTER:
        final EditorInfo editorInfo = getCurrentInputEditorInfo();
        final int imeOptionsActionId =
            InputTypeUtils.getImeOptionsActionIdFromEditorInfo(editorInfo);
        if (InputTypeUtils.IME_ACTION_CUSTOM_LABEL == imeOptionsActionId) {
          // Either we have an actionLabel and we should performEditorAction with
          // actionId regardless of its value.
          performEditorAction(editorInfo.actionId);
        } else if (EditorInfo.IME_ACTION_NONE != imeOptionsActionId) {
          // We didn't have an actionLabel, but we had another action to execute.
          // EditorInfo.IME_ACTION_NONE explicitly means no action. In contrast,
          // EditorInfo.IME_ACTION_UNSPECIFIED is the default value for an action, so it
          // means there should be an action and the app didn't bother to set a specific
          // code for it - presumably it only handles one. It does not have to be treated
          // in any specific way: anything that is not IME_ACTION_NONE should be sent to
          // performEditorAction.
          performEditorAction(imeOptionsActionId);
        } else {
          // No action label, and the action from imeOptions is NONE: this is a regular
          // enter key that should input a carriage return.
          handleNonSpecialCharacterEvent(event, inputTransaction);
        }
        break;
      default:
        handleNonSpecialCharacterEvent(event, inputTransaction);
        break;
    }
  }


  private void handleNonSpecialCharacterEvent(final Event event,
                                              final InputTransaction inputTransaction) {
    final int codePoint = event.mCodePoint;
    if (inputTransaction.mSettingsValues.isWordSeparator(codePoint)
        || Character.getType(codePoint) == Character.OTHER_SYMBOL) {
      handleSeparatorEvent(event, inputTransaction);
    } else {
      handleNonSeparatorEvent(event);
    }
  }


  private void handleNonSeparatorEvent(final Event event) {
    mConnection.commitText(StringUtils.newSingleCodePointString(event.mCodePoint), 1);
  }


  private void handleSeparatorEvent(final Event event, final InputTransaction inputTransaction) {
    mConnection.commitText(StringUtils.newSingleCodePointString(event.mCodePoint), 1);

    inputTransaction.requireShiftUpdate(InputTransaction.SHIFT_UPDATE_NOW);
  }


  private void handleBackspaceEvent(final Event event, final InputTransaction inputTransaction) {
    // In many cases after backspace, we need to update the shift state. Normally we need
    // to do this right away to avoid the shift state being out of date in case the user types
    // backspace then some other character very fast. However, in the case of backspace key
    // repeat, this can lead to flashiness when the cursor flies over positions where the
    // shift state should be updated, so if this is a key repeat, we update after a small delay.
    // Then again, even in the case of a key repeat, if the cursor is at start of text, it
    // can't go any further back, so we can update right away even if it's a key repeat.
    final int shiftUpdateKind =
        event.isKeyRepeat() && mConnection.getExpectedSelectionStart() > 0
            ? InputTransaction.SHIFT_UPDATE_LATER : InputTransaction.SHIFT_UPDATE_NOW;
    inputTransaction.requireShiftUpdate(shiftUpdateKind);

    // No cancelling of commit/double space/swap: we have a regular backspace.
    // We should backspace one char and restart suggestion if at the end of a word.
    if (mConnection.hasSelection()) {
      // If there is a selection, remove it.
      // We also need to unlearn the selected text.
      final int numCharsDeleted = mConnection.getExpectedSelectionEnd()
          - mConnection.getExpectedSelectionStart();
      mConnection.setSelection(mConnection.getExpectedSelectionEnd(),
          mConnection.getExpectedSelectionEnd());
      mConnection.deleteTextBeforeCursor(numCharsDeleted);
    } else {
      // There is no selection, just delete one character.
      if (inputTransaction.mSettingsValues.mInputAttributes.isTypeNull()
          || Constants.NOT_A_CURSOR_POSITION
          == mConnection.getExpectedSelectionEnd()) {
        // There are three possible reasons to send a key event: either the field has
        // type TYPE_NULL, in which case the keyboard should send events, or we are
        // running in backward compatibility mode, or we don't know the cursor position.
        // Before Jelly bean, the keyboard would simulate a hardware keyboard event on
        // pressing enter or delete. This is bad for many reasons (there are race
        // conditions with commits) but some applications are relying on this behavior
        // so we continue to support it for older apps, so we retain this behavior if
        // the app has target SDK < JellyBean.
        // As for the case where we don't know the cursor position, it can happen
        // because of bugs in the framework. But the framework should know, so the next
        // best thing is to leave it to whatever it thinks is best.
        sendDownUpKeyEvent(KeyEvent.KEYCODE_DEL);
      } else {
        final int codePointBeforeCursor = mConnection.getCodePointBeforeCursor();
        if (codePointBeforeCursor == Constants.NOT_A_CODE) {
          // HACK for backward compatibility with broken apps that haven't realized
          // yet that hardware keyboards are not the only way of inputting text.
          // Nothing to delete before the cursor. We should not do anything, but many
          // broken apps expect something to happen in this case so that they can
          // catch it and have their broken interface react. If you need the keyboard
          // to do this, you're doing it wrong -- please fix your app.
          mConnection.deleteTextBeforeCursor(1);
          // TODO: Add a new StatsUtils method onBackspaceWhenNoText()
          return;
        }
        final int lengthToDelete =
            Character.isSupplementaryCodePoint(codePointBeforeCursor) ? 2 : 1;
        mConnection.deleteTextBeforeCursor(lengthToDelete);
      }
    }
  }


  private void handleLanguageSwitchKey() {
    mLatinIME.switchToNextSubtype();
  }


  private void performRecapitalization(final SettingsValues settingsValues) {
    if (!mConnection.hasSelection() || !mRecapitalizeStatus.mIsEnabled()) {
      return; // No selection or recapitalize is disabled for now
    }
    final int selectionStart = mConnection.getExpectedSelectionStart();
    final int selectionEnd = mConnection.getExpectedSelectionEnd();
    final int numCharsSelected = selectionEnd - selectionStart;
    if (numCharsSelected > Constants.MAX_CHARACTERS_FOR_RECAPITALIZATION) {
      // We bail out if we have too many characters for performance reasons. We don't want
      // to suck possibly multiple-megabyte data.
      return;
    }
    // If we have a recapitalize in progress, use it; otherwise, start a new one.
    if (!mRecapitalizeStatus.isStarted()
        || !mRecapitalizeStatus.isSetAt(selectionStart, selectionEnd)) {
      final CharSequence selectedText =
          mConnection.getSelectedText(0 );
      if (TextUtils.isEmpty(selectedText)) return; // Race condition with the input connection
      mRecapitalizeStatus.start(selectionStart, selectionEnd, selectedText.toString(), mLatinIME.getCurrentLayoutLocale());
      // We trim leading and trailing whitespace.
      mRecapitalizeStatus.trim();
    }
    mConnection.finishComposingText();
    mRecapitalizeStatus.rotate();
    mConnection.setSelection(selectionEnd, selectionEnd);
    mConnection.deleteTextBeforeCursor(numCharsSelected);
    mConnection.commitText(mRecapitalizeStatus.getRecapitalizedString(), 0);
    mConnection.setSelection(mRecapitalizeStatus.getNewCursorStart(),
        mRecapitalizeStatus.getNewCursorEnd());
  }


  public int getCurrentAutoCapsState(final SettingsValues settingsValues,
                                     final String layoutSetName) {
    if (!settingsValues.mAutoCap || !layoutUsesAutoCaps(layoutSetName)) {
      return Constants.TextUtils.CAP_MODE_OFF;
    }

    final EditorInfo ei = getCurrentInputEditorInfo();
    if (ei == null) return Constants.TextUtils.CAP_MODE_OFF;
    final int inputType = ei.inputType;
    // Warning: this depends on mSpaceState, which may not be the most current value. If
    // mSpaceState gets updated later, whoever called this may need to be told about it.
    return mConnection.getCursorCapsMode(inputType, settingsValues.mSpacingAndPunctuations);
  }

  private boolean layoutUsesAutoCaps(final String layoutSetName) {
    switch (layoutSetName) {
      case SubtypeLocaleUtils.LAYOUT_ARABIC:
      case SubtypeLocaleUtils.LAYOUT_BENGALI:
      case SubtypeLocaleUtils.LAYOUT_BENGALI_AKKHOR:
      case SubtypeLocaleUtils.LAYOUT_FARSI:
      case SubtypeLocaleUtils.LAYOUT_GEORGIAN:
      case SubtypeLocaleUtils.LAYOUT_HEBREW:
      case SubtypeLocaleUtils.LAYOUT_HINDI:
      case SubtypeLocaleUtils.LAYOUT_HINDI_COMPACT:
      case SubtypeLocaleUtils.LAYOUT_KANNADA:
      case SubtypeLocaleUtils.LAYOUT_KHMER:
      case SubtypeLocaleUtils.LAYOUT_LAO:
      case SubtypeLocaleUtils.LAYOUT_MALAYALAM:
      case SubtypeLocaleUtils.LAYOUT_MARATHI:
      case SubtypeLocaleUtils.LAYOUT_NEPALI_ROMANIZED:
      case SubtypeLocaleUtils.LAYOUT_NEPALI_TRADITIONAL:
      case SubtypeLocaleUtils.LAYOUT_TAMIL:
      case SubtypeLocaleUtils.LAYOUT_TELUGU:
      case SubtypeLocaleUtils.LAYOUT_THAI:
      case SubtypeLocaleUtils.LAYOUT_URDU:
        return false;
      default:
        return true;
    }
  }

  public int getCurrentRecapitalizeState() {
    if (!mRecapitalizeStatus.isStarted()
        || !mRecapitalizeStatus.isSetAt(mConnection.getExpectedSelectionStart(),
        mConnection.getExpectedSelectionEnd())) {
      // Not recapitalizing at the moment
      return RecapitalizeStatus.NOT_A_RECAPITALIZE_MODE;
    }
    return mRecapitalizeStatus.getCurrentMode();
  }


  private EditorInfo getCurrentInputEditorInfo() {
    return mLatinIME.getCurrentInputEditorInfo();
  }


  private void performEditorAction(final int actionId) {
    mConnection.performEditorAction(actionId);
  }


  private String performSpecificTldProcessingOnTextInput(final String text) {
    if (text.length() <= 1 || text.charAt(0) != Constants.CODE_PERIOD
        || !Character.isLetter(text.charAt(1))) {
      // Not a tld: do nothing.
      return text;
    }
    final int codePointBeforeCursor = mConnection.getCodePointBeforeCursor();
    // If no code point, #getCodePointBeforeCursor returns NOT_A_CODE_POINT.
    if (Constants.CODE_PERIOD == codePointBeforeCursor) {
      return text.substring(1);
    }
    return text;
  }


  private void onSettingsKeyPressed() {
    mLatinIME.launchSettings();
  }


  // TODO: how is this different from startInput ?!
  private void resetEntireInputState(final int newSelStart, final int newSelEnd) {
    mConnection.resetCachesUponCursorMoveAndReturnSuccess(newSelStart, newSelEnd);
  }


  public void sendDownUpKeyEvent(final int keyCode) {
    final long eventTime = SystemClock.uptimeMillis();
    mConnection.sendKeyEvent(new KeyEvent(eventTime, eventTime,
        KeyEvent.ACTION_DOWN, keyCode, 0, 0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0,
        KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE));
    mConnection.sendKeyEvent(new KeyEvent(SystemClock.uptimeMillis(), eventTime,
        KeyEvent.ACTION_UP, keyCode, 0, 0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0,
        KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE));
  }


  // TODO: replace these two parameters with an InputTransaction
  private void sendKeyCodePoint(final int codePoint) {
    // TODO: Remove this special handling of digit letters.
    // For backward compatibility. See {@link InputMethodService#sendKeyChar(char)}.
    if (codePoint >= '0' && codePoint <= '9') {
      sendDownUpKeyEvent(codePoint - '0' + KeyEvent.KEYCODE_0);
      return;
    }

    mConnection.commitText(StringUtils.newSingleCodePointString(codePoint), 1);
  }


  public boolean retryResetCachesAndReturnSuccess(final boolean tryResumeSuggestions,
                                                  final int remainingTries, final LatinIME.UIHandler handler) {
    if (!mConnection.resetCachesUponCursorMoveAndReturnSuccess(
        mConnection.getExpectedSelectionStart(), mConnection.getExpectedSelectionEnd())) {
      if (0 < remainingTries) {
        handler.postResetCaches(tryResumeSuggestions, remainingTries - 1);
        return false;
      }
      // If remainingTries is 0, we should stop waiting for new tries, however we'll still
      // return true as we need to perform other tasks (for example, loading the keyboard).
    }
    return true;
  }
}
