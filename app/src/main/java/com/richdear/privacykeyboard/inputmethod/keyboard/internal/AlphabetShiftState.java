

package com.richdear.privacykeyboard.inputmethod.keyboard.internal;

import android.util.Log;

public final class AlphabetShiftState {
  private static final String TAG = AlphabetShiftState.class.getSimpleName();
  private static final boolean DEBUG = false;

  private static final int UNSHIFTED = 0;
  private static final int MANUAL_SHIFTED = 1;
  private static final int AUTOMATIC_SHIFTED = 2;
  private static final int SHIFT_LOCKED = 3;
  private static final int SHIFT_LOCK_SHIFTED = 4;

  private int mState = UNSHIFTED;

  public void setShifted(boolean newShiftState) {
    final int oldState = mState;
    if (newShiftState) {
      switch (oldState) {
        case UNSHIFTED:
          mState = MANUAL_SHIFTED;
          break;
        case SHIFT_LOCKED:
          mState = SHIFT_LOCK_SHIFTED;
          break;
      }
    } else {
      switch (oldState) {
        case MANUAL_SHIFTED:
        case AUTOMATIC_SHIFTED:
          mState = UNSHIFTED;
          break;
        case SHIFT_LOCK_SHIFTED:
          mState = SHIFT_LOCKED;
          break;
      }
    }
    if (DEBUG)
      Log.d(TAG, "setShifted(" + newShiftState + "): " + toString(oldState) + " > " + this);
  }

  public void setShiftLocked(boolean newShiftLockState) {
    final int oldState = mState;
    if (newShiftLockState) {
      switch (oldState) {
        case UNSHIFTED:
        case MANUAL_SHIFTED:
        case AUTOMATIC_SHIFTED:
          mState = SHIFT_LOCKED;
          break;
      }
    } else {
      mState = UNSHIFTED;
    }
    if (DEBUG)
      Log.d(TAG, "setShiftLocked(" + newShiftLockState + "): " + toString(oldState)
          + " > " + this);
  }

  public void setAutomaticShifted() {
    mState = AUTOMATIC_SHIFTED;
  }

  public boolean isShiftedOrShiftLocked() {
    return mState != UNSHIFTED;
  }

  public boolean isShiftLocked() {
    return mState == SHIFT_LOCKED || mState == SHIFT_LOCK_SHIFTED;
  }

  public boolean isShiftLockShifted() {
    return mState == SHIFT_LOCK_SHIFTED;
  }

  public boolean isAutomaticShifted() {
    return mState == AUTOMATIC_SHIFTED;
  }

  public boolean isManualShifted() {
    return mState == MANUAL_SHIFTED || mState == SHIFT_LOCK_SHIFTED;
  }

  @Override
  public String toString() {
    return toString(mState);
  }

  private static String toString(int state) {
    switch (state) {
      case UNSHIFTED:
        return "UNSHIFTED";
      case MANUAL_SHIFTED:
        return "MANUAL_SHIFTED";
      case AUTOMATIC_SHIFTED:
        return "AUTOMATIC_SHIFTED";
      case SHIFT_LOCKED:
        return "SHIFT_LOCKED";
      case SHIFT_LOCK_SHIFTED:
        return "SHIFT_LOCK_SHIFTED";
      default:
        return "UNKNOWN";
    }
  }
}
