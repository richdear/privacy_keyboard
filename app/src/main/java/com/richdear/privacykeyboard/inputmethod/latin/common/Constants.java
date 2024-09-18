

package com.richdear.privacykeyboard.inputmethod.latin.common;

public final class Constants {

  public static final class Color {
    
    public final static int ALPHA_OPAQUE = 255;
  }

  public static final class TextUtils {
    
    // TODO: Straighten this out. It's bizarre to have to use android.text.TextUtils.CAP_MODE_*
    // except for OFF that is in Constants.TextUtils.
    public static final int CAP_MODE_OFF = 0;

    private TextUtils() {
      // This utility class is not publicly instantiable.
    }
  }

  public static final int NOT_A_CODE = -1;
  public static final int NOT_A_CURSOR_POSITION = -1;
  // TODO: replace the following constants with state in InputTransaction?
  public static final int NOT_A_COORDINATE = -1;

  // A hint on how many characters to cache from the TextView. A good value of this is given by
  // how many characters we need to be able to almost always find the caps mode.
  public static final int EDITOR_CONTENTS_CACHE_SIZE = 1024;
  // How many characters we accept for the recapitalization functionality. This needs to be
  // large enough for all reasonable purposes, but avoid purposeful attacks. 100k sounds about
  // right for this.
  public static final int MAX_CHARACTERS_FOR_RECAPITALIZATION = 1024 * 100;

  public static boolean isValidCoordinate(final int coordinate) {
    // Detect {@link NOT_A_COORDINATE}, {@link SUGGESTION_STRIP_COORDINATE},
    // and {@link SPELL_CHECKER_COORDINATE}.
    return coordinate >= 0;
  }

  
  // The code to show input method picker.
  public static final int CUSTOM_CODE_SHOW_INPUT_METHOD_PICKER = 1;

  
  public static final int CODE_ENTER = '\n';
  public static final int CODE_TAB = '\t';
  public static final int CODE_SPACE = ' ';
  public static final int CODE_PERIOD = '.';
  public static final int CODE_COMMA = ',';
  public static final int CODE_SINGLE_QUOTE = '\'';
  public static final int CODE_DOUBLE_QUOTE = '"';
  public static final int CODE_BACKSLASH = '\\';
  public static final int CODE_VERTICAL_BAR = '|';
  public static final int CODE_PERCENT = '%';
  public static final int CODE_INVERTED_QUESTION_MARK = 0xBF; // ¿
  public static final int CODE_INVERTED_EXCLAMATION_MARK = 0xA1; // ¡

  
  public static final int CODE_SHIFT = -1;
  public static final int CODE_CAPSLOCK = -2;
  public static final int CODE_SWITCH_ALPHA_SYMBOL = -3;
  public static final int CODE_OUTPUT_TEXT = -4;
  public static final int CODE_DELETE = -5;
  public static final int CODE_SETTINGS = -6;
  public static final int CODE_ACTION_NEXT = -8;
  public static final int CODE_ACTION_PREVIOUS = -9;
  public static final int CODE_LANGUAGE_SWITCH = -10;
  public static final int CODE_SHIFT_ENTER = -11;
  public static final int CODE_SYMBOL_SHIFT = -12;
  // Code value representing the code is not specified.
  public static final int CODE_UNSPECIFIED = -13;

  public static boolean isLetterCode(final int code) {
    return code >= CODE_SPACE;
  }

  public static String printableCode(final int code) {
    switch (code) {
      case CODE_SHIFT:
        return "shift";
      case CODE_CAPSLOCK:
        return "capslock";
      case CODE_SWITCH_ALPHA_SYMBOL:
        return "symbol";
      case CODE_OUTPUT_TEXT:
        return "text";
      case CODE_DELETE:
        return "delete";
      case CODE_SETTINGS:
        return "settings";
      case CODE_ACTION_NEXT:
        return "actionNext";
      case CODE_ACTION_PREVIOUS:
        return "actionPrevious";
      case CODE_LANGUAGE_SWITCH:
        return "languageSwitch";
      case CODE_SHIFT_ENTER:
        return "shiftEnter";
      case CODE_UNSPECIFIED:
        return "unspec";
      case CODE_TAB:
        return "tab";
      case CODE_ENTER:
        return "enter";
      case CODE_SPACE:
        return "space";
      default:
        if (code < CODE_SPACE) return String.format("\\u%02X", code);
        if (code < 0x100) return String.format("%c", code);
        if (code < 0x10000) return String.format("\\u%04X", code);
        return String.format("\\U%05X", code);
    }
  }

  
  public static final int SCREEN_METRICS_LARGE_TABLET = 2;
  public static final int SCREEN_METRICS_SMALL_TABLET = 3;

  private Constants() {
    // This utility class is not publicly instantiable.
  }
}
