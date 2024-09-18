

package com.richdear.privacykeyboard.inputmethod.keyboard.internal;

import android.os.Message;
import android.view.ViewConfiguration;

import com.richdear.privacykeyboard.inputmethod.keyboard.Key;
import com.richdear.privacykeyboard.inputmethod.keyboard.PointerTracker;
import com.richdear.privacykeyboard.inputmethod.latin.common.Constants;
import com.richdear.privacykeyboard.inputmethod.latin.utils.LeakGuardHandlerWrapper;

public final class TimerHandler extends LeakGuardHandlerWrapper<DrawingProxy>
    implements TimerProxy {
  private static final int MSG_TYPING_STATE_EXPIRED = 0;
  private static final int MSG_REPEAT_KEY = 1;
  private static final int MSG_LONGPRESS_KEY = 2;
  private static final int MSG_LONGPRESS_SHIFT_KEY = 3;
  private static final int MSG_DOUBLE_TAP_SHIFT_KEY = 4;
  private static final int MSG_UPDATE_BATCH_INPUT = 5;
  private static final int MSG_DISMISS_KEY_PREVIEW = 6;
  private static final int MSG_DISMISS_GESTURE_FLOATING_PREVIEW_TEXT = 7;

  private final int mIgnoreAltCodeKeyTimeout;

  public TimerHandler(final DrawingProxy ownerInstance, final int ignoreAltCodeKeyTimeout) {
    super(ownerInstance);
    mIgnoreAltCodeKeyTimeout = ignoreAltCodeKeyTimeout;
  }

  @Override
  public void handleMessage(final Message msg) {
    final DrawingProxy drawingProxy = getOwnerInstance();
    if (drawingProxy == null) {
      return;
    }
    switch (msg.what) {
      case MSG_REPEAT_KEY:
        final PointerTracker tracker1 = (PointerTracker) msg.obj;
        tracker1.onKeyRepeat(msg.arg1 , msg.arg2 );
        break;
      case MSG_LONGPRESS_KEY:
      case MSG_LONGPRESS_SHIFT_KEY:
        cancelLongPressTimers();
        final PointerTracker tracker2 = (PointerTracker) msg.obj;
        tracker2.onLongPressed();
        break;
      case MSG_DISMISS_KEY_PREVIEW:
        drawingProxy.onKeyReleased((Key) msg.obj, false );
        break;
    }
  }

  @Override
  public void startKeyRepeatTimerOf(final PointerTracker tracker, final int repeatCount,
                                    final int delay) {
    final Key key = tracker.getKey();
    if (key == null || delay == 0) {
      return;
    }
    sendMessageDelayed(
        obtainMessage(MSG_REPEAT_KEY, key.getCode(), repeatCount, tracker), delay);
  }

  private void cancelKeyRepeatTimerOf(final PointerTracker tracker) {
    removeMessages(MSG_REPEAT_KEY, tracker);
  }

  public void cancelKeyRepeatTimers() {
    removeMessages(MSG_REPEAT_KEY);
  }

  // TODO: Suppress layout changes in key repeat mode
  public boolean isInKeyRepeat() {
    return hasMessages(MSG_REPEAT_KEY);
  }

  @Override
  public void startLongPressTimerOf(final PointerTracker tracker, final int delay) {
    final Key key = tracker.getKey();
    if (key == null) {
      return;
    }
    // Use a separate message id for long pressing shift key, because long press shift key
    // timers should be canceled when other key is pressed.
    final int messageId = (key.getCode() == Constants.CODE_SHIFT)
        ? MSG_LONGPRESS_SHIFT_KEY : MSG_LONGPRESS_KEY;
    sendMessageDelayed(obtainMessage(messageId, tracker), delay);
  }

  @Override
  public void cancelLongPressTimersOf(final PointerTracker tracker) {
    removeMessages(MSG_LONGPRESS_KEY, tracker);
    removeMessages(MSG_LONGPRESS_SHIFT_KEY, tracker);
  }

  @Override
  public void cancelLongPressShiftKeyTimer() {
    removeMessages(MSG_LONGPRESS_SHIFT_KEY);
  }

  public void cancelLongPressTimers() {
    removeMessages(MSG_LONGPRESS_KEY);
    removeMessages(MSG_LONGPRESS_SHIFT_KEY);
  }

  @Override
  public void startTypingStateTimer(final Key typedKey) {
    if (typedKey.isModifier() || typedKey.altCodeWhileTyping()) {
      return;
    }

    final boolean isTyping = isTypingState();
    removeMessages(MSG_TYPING_STATE_EXPIRED);
    final DrawingProxy drawingProxy = getOwnerInstance();
    if (drawingProxy == null) {
      return;
    }

    // When user hits the space or the enter key, just cancel the while-typing timer.
    final int typedCode = typedKey.getCode();
    if (typedCode == Constants.CODE_SPACE || typedCode == Constants.CODE_ENTER) {
      if (isTyping) {
        drawingProxy.startWhileTypingAnimation(DrawingProxy.FADE_IN);
      }
      return;
    }

    sendMessageDelayed(
        obtainMessage(MSG_TYPING_STATE_EXPIRED), mIgnoreAltCodeKeyTimeout);
    if (isTyping) {
      return;
    }
    drawingProxy.startWhileTypingAnimation(DrawingProxy.FADE_OUT);
  }

  @Override
  public boolean isTypingState() {
    return hasMessages(MSG_TYPING_STATE_EXPIRED);
  }

  @Override
  public void startDoubleTapShiftKeyTimer() {
    sendMessageDelayed(obtainMessage(MSG_DOUBLE_TAP_SHIFT_KEY),
        ViewConfiguration.getDoubleTapTimeout());
  }

  @Override
  public void cancelDoubleTapShiftKeyTimer() {
    removeMessages(MSG_DOUBLE_TAP_SHIFT_KEY);
  }

  @Override
  public boolean isInDoubleTapShiftKeyTimeout() {
    return hasMessages(MSG_DOUBLE_TAP_SHIFT_KEY);
  }

  @Override
  public void cancelKeyTimersOf(final PointerTracker tracker) {
    cancelKeyRepeatTimerOf(tracker);
    cancelLongPressTimersOf(tracker);
  }

  public void cancelAllKeyTimers() {
    cancelKeyRepeatTimers();
    cancelLongPressTimers();
  }

  @Override
  public void cancelUpdateBatchInputTimer(final PointerTracker tracker) {
    removeMessages(MSG_UPDATE_BATCH_INPUT, tracker);
  }

  @Override
  public void cancelAllUpdateBatchInputTimers() {
    removeMessages(MSG_UPDATE_BATCH_INPUT);
  }

  public void postDismissKeyPreview(final Key key, final long delay) {
    sendMessageDelayed(obtainMessage(MSG_DISMISS_KEY_PREVIEW, key), delay);
  }

  public void cancelAllMessages() {
    cancelAllKeyTimers();
    cancelAllUpdateBatchInputTimers();
    removeMessages(MSG_DISMISS_KEY_PREVIEW);
    removeMessages(MSG_DISMISS_GESTURE_FLOATING_PREVIEW_TEXT);
  }
}
