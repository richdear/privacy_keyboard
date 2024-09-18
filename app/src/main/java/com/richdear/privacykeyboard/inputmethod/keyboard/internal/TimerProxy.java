

package com.richdear.privacykeyboard.inputmethod.keyboard.internal;

import com.richdear.privacykeyboard.inputmethod.keyboard.Key;
import com.richdear.privacykeyboard.inputmethod.keyboard.PointerTracker;

public interface TimerProxy {

  void startTypingStateTimer(Key typedKey);


  boolean isTypingState();


  void startKeyRepeatTimerOf(PointerTracker tracker, int repeatCount, int delay);


  void startLongPressTimerOf(PointerTracker tracker, int delay);


  void cancelLongPressTimersOf(PointerTracker tracker);


  void cancelLongPressShiftKeyTimer();


  void cancelKeyTimersOf(PointerTracker tracker);


  void startDoubleTapShiftKeyTimer();


  void cancelDoubleTapShiftKeyTimer();


  boolean isInDoubleTapShiftKeyTimeout();


  void cancelUpdateBatchInputTimer(PointerTracker tracker);


  void cancelAllUpdateBatchInputTimers();
}
