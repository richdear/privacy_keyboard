

package com.richdear.privacykeyboard.inputmethod.latin.utils;

import android.util.Log;

import com.richdear.privacykeyboard.inputmethod.latin.define.DebugFlags;


public final class DebugLogUtils {
  private final static String TAG = DebugLogUtils.class.getSimpleName();
  private final static boolean sDBG = DebugFlags.DEBUG_ENABLED;


  public static String s(final Object o) {
    return null == o ? "null" : o.toString();
  }


  public static String getStackTrace() {
    return getStackTrace(Integer.MAX_VALUE - 1);
  }


  public static String getStackTrace(final int limit) {
    final StringBuilder sb = new StringBuilder();
    try {
      throw new RuntimeException();
    } catch (final RuntimeException e) {
      final StackTraceElement[] frames = e.getStackTrace();
      // Start at 1 because the first frame is here and we don't care about it
      for (int j = 1; j < frames.length && j < limit + 1; ++j) {
        sb.append(frames[j].toString() + "\n");
      }
    }
    return sb.toString();
  }


  public static void l(final Object... args) {
    if (!sDBG) return;
    final StringBuilder sb = new StringBuilder();
    for (final Object o : args) {
      sb.append(s(o));
      sb.append(" ");
    }
    Log.e(TAG, sb.toString());
  }


  public static void r(final Object... args) {
    if (!sDBG) return;
    final StringBuilder sb = new StringBuilder("\u001B[31m");
    for (final Object o : args) {
      sb.append(s(o));
      sb.append(" ");
    }
    sb.append("\u001B[0m");
    Log.e(TAG, sb.toString());
  }
}
