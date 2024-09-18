

package com.richdear.privacykeyboard.inputmethod.latin.common;


public final class UnicodeSurrogate {
  private static final char LOW_SURROGATE_MIN = '\uD800';
  private static final char LOW_SURROGATE_MAX = '\uDBFF';
  private static final char HIGH_SURROGATE_MIN = '\uDC00';
  private static final char HIGH_SURROGATE_MAX = '\uDFFF';

  public static boolean isLowSurrogate(final char c) {
    return c >= LOW_SURROGATE_MIN && c <= LOW_SURROGATE_MAX;
  }

  public static boolean isHighSurrogate(final char c) {
    return c >= HIGH_SURROGATE_MIN && c <= HIGH_SURROGATE_MAX;
  }
}
