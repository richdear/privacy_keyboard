

package com.richdear.privacykeyboard.inputmethod.keyboard.internal;

import java.util.HashMap;
import java.util.Locale;

public final class KeyboardTextsTable {
  // Name to index map.
  private static final HashMap<String, Integer> sNameToIndexesMap = new HashMap<>();
  // Locale to texts table map.
  private static final HashMap<String, String[]> sLocaleToTextsTableMap = new HashMap<>();

  public static String getText(final String name, final String[] textsTable) {
    final Integer indexObj = sNameToIndexesMap.get(name);
    if (indexObj == null) {
      throw new RuntimeException("Unknown text name=" + name);
    }
    final int index = indexObj;
    final String text = (index < textsTable.length) ? textsTable[index] : null;
    if (text != null) {
      return text;
    }
    // Sanity check.
    if (index >= 0 && index < TEXTS_DEFAULT.length) {
      return TEXTS_DEFAULT[index];
    }
    // Throw exception for debugging purpose.
    throw new RuntimeException("Illegal index=" + index + " for name=" + name);
  }

  public static String[] getTextsTable(final Locale locale) {
    final String localeKey = locale.toString();
    if (sLocaleToTextsTableMap.containsKey(localeKey)) {
      return sLocaleToTextsTableMap.get(localeKey);
    }
    final String languageKey = locale.getLanguage();
    if (sLocaleToTextsTableMap.containsKey(languageKey)) {
      return sLocaleToTextsTableMap.get(languageKey);
    }
    return TEXTS_DEFAULT;
  }

  private static final String[] NAMES = {
      //   "name",
       "morekeys_a",
       "morekeys_o",
       "morekeys_e",
       "morekeys_u",
       "keylabel_to_alpha",
       "morekeys_i",
       "morekeys_n",
       "morekeys_c",
       "double_quotes",
       "morekeys_s",
       "single_quotes",
       "keyspec_currency",
       "morekeys_y",
       "morekeys_z",
       "morekeys_d",
       "morekeys_t",
       "morekeys_l",
       "morekeys_g",
       "single_angle_quotes",
       "double_angle_quotes",
       "morekeys_r",
       "morekeys_k",
       "morekeys_cyrillic_ie",
       "keyspec_nordic_row1_11",
       "keyspec_nordic_row2_10",
       "keyspec_nordic_row2_11",
       "morekeys_nordic_row2_10",
       "keyspec_east_slavic_row1_9",
       "keyspec_east_slavic_row2_2",
       "keyspec_east_slavic_row2_11",
       "keyspec_east_slavic_row3_5",
       "morekeys_cyrillic_soft_sign",
       "keyspec_symbols_1",
       "keyspec_symbols_2",
       "keyspec_symbols_3",
       "keyspec_symbols_4",
       "keyspec_symbols_5",
       "keyspec_symbols_6",
       "keyspec_symbols_7",
       "keyspec_symbols_8",
       "keyspec_symbols_9",
       "keyspec_symbols_0",
       "keylabel_to_symbol",
       "additional_morekeys_symbols_1",
       "additional_morekeys_symbols_2",
       "additional_morekeys_symbols_3",
       "additional_morekeys_symbols_4",
       "additional_morekeys_symbols_5",
       "additional_morekeys_symbols_6",
       "additional_morekeys_symbols_7",
       "additional_morekeys_symbols_8",
       "additional_morekeys_symbols_9",
       "additional_morekeys_symbols_0",
       "morekeys_tablet_period",
       "morekeys_nordic_row2_11",
       "morekeys_punctuation",
       "keyspec_tablet_comma",
       "keyspec_period",
       "morekeys_period",
       "keyspec_tablet_period",
       "keyspec_swiss_row1_11",
       "keyspec_swiss_row2_10",
       "keyspec_swiss_row2_11",
       "morekeys_swiss_row1_11",
       "morekeys_swiss_row2_10",
       "morekeys_swiss_row2_11",
       "morekeys_star",
       "keyspec_left_parenthesis",
       "keyspec_right_parenthesis",
       "keyspec_left_square_bracket",
       "keyspec_right_square_bracket",
       "keyspec_left_curly_bracket",
       "keyspec_right_curly_bracket",
       "keyspec_less_than",
       "keyspec_greater_than",
       "keyspec_less_than_equal",
       "keyspec_greater_than_equal",
       "keyspec_left_double_angle_quote",
       "keyspec_right_double_angle_quote",
       "keyspec_left_single_angle_quote",
       "keyspec_right_single_angle_quote",
       "keyspec_comma",
       "morekeys_tablet_comma",
       "keyhintlabel_period",
       "morekeys_question",
       "morekeys_h",
       "morekeys_w",
       "morekeys_east_slavic_row2_2",
       "morekeys_cyrillic_u",
       "morekeys_cyrillic_en",
       "morekeys_cyrillic_ghe",
       "morekeys_cyrillic_o",
       "morekeys_cyrillic_i",
       "keyspec_south_slavic_row1_6",
       "keyspec_south_slavic_row2_11",
       "keyspec_south_slavic_row3_8",
       "morekeys_tablet_punctuation",
       "keyspec_spanish_row2_10",
       "morekeys_bullet",
       "morekeys_left_parenthesis",
       "morekeys_right_parenthesis",
       "morekeys_arabic_diacritics",
       "keyhintlabel_tablet_comma",
       "keyhintlabel_tablet_period",
       "keyspec_symbols_question",
       "keyspec_symbols_semicolon",
       "keyspec_symbols_percent",
       "morekeys_symbols_semicolon",
       "morekeys_symbols_percent",
       "label_pause_key",
       "label_wait_key",
       "morekeys_v",
       "morekeys_j",
       "morekeys_q",
       "morekeys_x",
       "keyspec_q",
       "keyspec_w",
       "keyspec_y",
       "keyspec_x",
       "morekeys_east_slavic_row2_11",
       "morekeys_cyrillic_ka",
       "morekeys_cyrillic_a",
       "morekeys_currency_dollar",
       "morekeys_plus",
       "morekeys_less_than",
       "morekeys_greater_than",
       "morekeys_exclamation",
       "morekeys_currency_generic",
       "morekeys_symbols_1",
       "morekeys_symbols_2",
       "morekeys_symbols_3",
       "morekeys_symbols_4",
       "morekeys_symbols_5",
       "morekeys_symbols_6",
       "morekeys_symbols_7",
       "morekeys_symbols_8",
       "morekeys_symbols_9",
       "morekeys_symbols_0",
       "morekeys_am_pm",
       "keyspec_settings",
       "keyspec_action_next",
       "keyspec_action_previous",
       "keylabel_to_more_symbol",
       "keylabel_tablet_to_more_symbol",
       "keylabel_to_phone_numeric",
       "keylabel_to_phone_symbols",
       "keylabel_time_am",
       "keylabel_time_pm",
       "keyspecs_left_parenthesis_more_keys",
       "keyspecs_right_parenthesis_more_keys",
       "single_laqm_raqm",
       "single_raqm_laqm",
       "double_laqm_raqm",
       "double_raqm_laqm",
       "single_lqm_rqm",
       "single_9qm_lqm",
       "single_9qm_rqm",
       "single_rqm_9qm",
       "double_lqm_rqm",
       "double_9qm_lqm",
       "double_9qm_rqm",
       "double_rqm_9qm",
       "morekeys_single_quote",
       "morekeys_double_quote",
       "morekeys_tablet_double_quote",
  };

  private static final String EMPTY = "";


  private static final String[] TEXTS_DEFAULT = {

      EMPTY, EMPTY, EMPTY, EMPTY,

      // Label for "switch to alphabetic" key.
       "ABC",

      EMPTY, EMPTY, EMPTY,

       "!text/double_lqm_rqm",
       EMPTY,
       "!text/single_lqm_rqm",
       "$",

      EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,

       "!text/single_laqm_raqm",
       "!text/double_laqm_raqm",

      EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,

       "1",
       "2",
       "3",
       "4",
       "5",
       "6",
       "7",
       "8",
       "9",
       "0",
      // Label for "switch to symbols" key.
       "?123",

      EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,

       "!text/morekeys_tablet_punctuation",
       EMPTY,
       "!autoColumnOrder!8,\\,,?,!,#,!text/keyspec_right_parenthesis,!text/keyspec_left_parenthesis,/,;,',@,:,-,\",+,\\%,&",
       ",",
      // Period key
       ".",
       "!text/morekeys_punctuation",
       ".",

      EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,

      // U+2020: "†" DAGGER
      // U+2021: "‡" DOUBLE DAGGER
      // U+2605: "★" BLACK STAR
       "\u2020,\u2021,\u2605",
      // The all letters need to be mirrored are found at
      // http://www.unicode.org/Public/6.1.0/ucd/BidiMirroring.txt
      // U+2039: "‹" SINGLE LEFT-POINTING ANGLE QUOTATION MARK
      // U+203A: "›" SINGLE RIGHT-POINTING ANGLE QUOTATION MARK
      // U+2264: "≤" LESS-THAN OR EQUAL TO
      // U+2265: "≥" GREATER-THAN EQUAL TO
      // U+00AB: "«" LEFT-POINTING DOUBLE ANGLE QUOTATION MARK
      // U+00BB: "»" RIGHT-POINTING DOUBLE ANGLE QUOTATION MARK
       "(",
       ")",
       "[",
       "]",
       "{",
       "}",
       "<",
       ">",
       "\u2264",
       "\u2265",
       "\u00AB",
       "\u00BB",
       "\u2039",
       "\u203A",
      // Comma key
       ",",
       EMPTY,
       EMPTY,
      // U+00BF: "¿" INVERTED QUESTION MARK
       "\u00BF",

      EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,

       "!autoColumnOrder!7,\\,,',#,!text/keyspec_right_parenthesis,!text/keyspec_left_parenthesis,/,;,@,:,-,\",+,\\%,&",
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
       "\u00F1",
      // U+266A: "♪" EIGHTH NOTE
      // U+2665: "♥" BLACK HEART SUIT
      // U+2660: "♠" BLACK SPADE SUIT
      // U+2666: "♦" BLACK DIAMOND SUIT
      // U+2663: "♣" BLACK CLUB SUIT
       "\u266A,\u2665,\u2660,\u2666,\u2663",
       "!fixedColumnOrder!3,!text/keyspecs_left_parenthesis_more_keys",
       "!fixedColumnOrder!3,!text/keyspecs_right_parenthesis_more_keys",

      EMPTY, EMPTY, EMPTY,

       "?",
       ";",
       "%",
       EMPTY,
      // U+2030: "‰" PER MILLE SIGN
       "\u2030",
       "!string/label_pause_key",
       "!string/label_wait_key",

      EMPTY, EMPTY, EMPTY, EMPTY,

       "q",
       "w",
       "y",
       "x",

      EMPTY, EMPTY, EMPTY,

      // U+00A2: "¢" CENT SIGN
      // U+00A3: "£" POUND SIGN
      // U+20AC: "€" EURO SIGN
      // U+00A5: "¥" YEN SIGN
      // U+20B1: "₱" PESO SIGN
       "\u00A2,\u00A3,\u20AC,\u00A5,\u20B1",
      // U+00B1: "±" PLUS-MINUS SIGN
       "\u00B1",
       "!fixedColumnOrder!3,!text/keyspec_left_single_angle_quote,!text/keyspec_less_than_equal,!text/keyspec_left_double_angle_quote",
       "!fixedColumnOrder!3,!text/keyspec_right_single_angle_quote,!text/keyspec_greater_than_equal,!text/keyspec_right_double_angle_quote",
      // U+00A1: "¡" INVERTED EXCLAMATION MARK
       "\u00A1",
       "$,\u00A2,\u20AC,\u00A3,\u00A5,\u20B1",
      // U+00B9: "¹" SUPERSCRIPT ONE
      // U+00BD: "½" VULGAR FRACTION ONE HALF
      // U+2153: "⅓" VULGAR FRACTION ONE THIRD
      // U+00BC: "¼" VULGAR FRACTION ONE QUARTER
      // U+215B: "⅛" VULGAR FRACTION ONE EIGHTH
       "\u00B9,\u00BD,\u2153,\u00BC,\u215B",
      // U+00B2: "²" SUPERSCRIPT TWO
      // U+2154: "⅔" VULGAR FRACTION TWO THIRDS
       "\u00B2,\u2154",
      // U+00B3: "³" SUPERSCRIPT THREE
      // U+00BE: "¾" VULGAR FRACTION THREE QUARTERS
      // U+215C: "⅜" VULGAR FRACTION THREE EIGHTHS
       "\u00B3,\u00BE,\u215C",
      // U+2074: "⁴" SUPERSCRIPT FOUR
       "\u2074",
      // U+215D: "⅝" VULGAR FRACTION FIVE EIGHTHS
       "\u215D",
       EMPTY,
      // U+215E: "⅞" VULGAR FRACTION SEVEN EIGHTHS
       "\u215E",
       EMPTY,
       EMPTY,
      // U+207F: "ⁿ" SUPERSCRIPT LATIN SMALL LETTER N
      // U+2205: "∅" EMPTY SET
       "\u207F,\u2205",
       "!fixedColumnOrder!2,!hasLabels!,!text/keylabel_time_am,!text/keylabel_time_pm",
       "!icon/settings_key|!code/key_settings",
       "!code/key_action_next",
       "!code/key_action_previous",
      // Label for "switch to more symbol" modifier key ("= \ <"). Must be short to fit on key!
       "= \\\\ <",
      // Label for "switch to more symbol" modifier key on tablets.  Must be short to fit on key!
       "~ [ <",
      // Label for "switch to phone numeric" key.  Must be short to fit on key!
       "123",
      // Label for "switch to phone symbols" key.  Must be short to fit on key!
      // U+FF0A: "＊" FULLWIDTH ASTERISK
      // U+FF03: "＃" FULLWIDTH NUMBER SIGN
       "\uFF0A\uFF03",
      // Key label for "ante meridiem"
       "AM",
      // Key label for "post meridiem"
       "PM",
       "!text/keyspec_less_than,!text/keyspec_left_curly_bracket,!text/keyspec_left_square_bracket",
       "!text/keyspec_greater_than,!text/keyspec_right_curly_bracket,!text/keyspec_right_square_bracket",
      // The following characters don't need BIDI mirroring.
      // U+2018: "‘" LEFT SINGLE QUOTATION MARK
      // U+2019: "’" RIGHT SINGLE QUOTATION MARK
      // U+201A: "‚" SINGLE LOW-9 QUOTATION MARK
      // U+201C: "“" LEFT DOUBLE QUOTATION MARK
      // U+201D: "”" RIGHT DOUBLE QUOTATION MARK
      // U+201E: "„" DOUBLE LOW-9 QUOTATION MARK
      // Abbreviations are:
      // laqm: LEFT-POINTING ANGLE QUOTATION MARK
      // raqm: RIGHT-POINTING ANGLE QUOTATION MARK
      // lqm: LEFT QUOTATION MARK
      // rqm: RIGHT QUOTATION MARK
      // 9qm: LOW-9 QUOTATION MARK
      // The following each quotation mark pair consist of
      // <opening quotation mark>, <closing quotation mark>
      // and is named after (single|double)_<opening quotation mark>_<closing quotation mark>.
       "!text/keyspec_left_single_angle_quote,!text/keyspec_right_single_angle_quote",
       "!text/keyspec_right_single_angle_quote,!text/keyspec_left_single_angle_quote",
       "!text/keyspec_left_double_angle_quote,!text/keyspec_right_double_angle_quote",
       "!text/keyspec_right_double_angle_quote,!text/keyspec_left_double_angle_quote",
      // The following each quotation mark triplet consists of
      // <another quotation mark>, <opening quotation mark>, <closing quotation mark>
      // and is named after (single|double)_<opening quotation mark>_<closing quotation mark>.
       "\u201A,\u2018,\u2019",
       "\u2019,\u201A,\u2018",
       "\u2018,\u201A,\u2019",
       "\u2018,\u2019,\u201A",
       "\u201E,\u201C,\u201D",
       "\u201D,\u201E,\u201C",
       "\u201C,\u201E,\u201D",
       "\u201C,\u201D,\u201E",
       "!fixedColumnOrder!5,!text/single_quotes,!text/single_angle_quotes",
       "!fixedColumnOrder!5,!text/double_quotes,!text/double_angle_quotes",
       "!fixedColumnOrder!6,!text/double_quotes,!text/single_quotes,!text/double_angle_quotes,!text/single_angle_quotes",
  };


  private static final String[] TEXTS_af = {
      // This is the same as Dutch except more keys of y and demoting vowels with diaeresis.
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
       "\u00E1,\u00E2,\u00E4,\u00E0,\u00E6,\u00E3,\u00E5,\u0101",
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
       "\u00F3,\u00F4,\u00F6,\u00F2,\u00F5,\u0153,\u00F8,\u014D",
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+0119: "ę" LATIN SMALL LETTER E WITH OGONEK
      // U+0117: "ė" LATIN SMALL LETTER E WITH DOT ABOVE
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
       "\u00E9,\u00E8,\u00EA,\u00EB,\u0119,\u0117,\u0113",
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00FA,\u00FB,\u00FC,\u00F9,\u016B",
       null,
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
      // U+0133: "ĳ" LATIN SMALL LIGATURE IJ
       "\u00ED,\u00EC,\u00EF,\u00EE,\u012F,\u012B,\u0133",
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
      // U+0144: "ń" LATIN SMALL LETTER N WITH ACUTE
       "\u00F1,\u0144",

      null, null, null, null, null,

      // U+00FD: "ý" LATIN SMALL LETTER Y WITH ACUTE
      // U+0133: "ĳ" LATIN SMALL LIGATURE IJ
       "\u00FD,\u0133",
  };


  private static final String[] TEXTS_ar = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0623: "أ" ARABIC LETTER ALEF WITH HAMZA ABOVE
      // U+200C: ZERO WIDTH NON-JOINER
      // U+0628: "ب" ARABIC LETTER BEH
      // U+062C: "ج" ARABIC LETTER JEEM
       "\u0623\u200C\u0628\u200C\u062C",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null,

      // U+0661: "١" ARABIC-INDIC DIGIT ONE
       "\u0661",
      // U+0662: "٢" ARABIC-INDIC DIGIT TWO
       "\u0662",
      // U+0663: "٣" ARABIC-INDIC DIGIT THREE
       "\u0663",
      // U+0664: "٤" ARABIC-INDIC DIGIT FOUR
       "\u0664",
      // U+0665: "٥" ARABIC-INDIC DIGIT FIVE
       "\u0665",
      // U+0666: "٦" ARABIC-INDIC DIGIT SIX
       "\u0666",
      // U+0667: "٧" ARABIC-INDIC DIGIT SEVEN
       "\u0667",
      // U+0668: "٨" ARABIC-INDIC DIGIT EIGHT
       "\u0668",
      // U+0669: "٩" ARABIC-INDIC DIGIT NINE
       "\u0669",
      // U+0660: "٠" ARABIC-INDIC DIGIT ZERO
       "\u0660",
      // Label for "switch to symbols" key.
      // U+061F: "؟" ARABIC QUESTION MARK
       "\u0663\u0662\u0661\u061F",
       "1",
       "2",
       "3",
       "4",
       "5",
       "6",
       "7",
       "8",
       "9",
      // U+066B: "٫" ARABIC DECIMAL SEPARATOR
      // U+066C: "٬" ARABIC THOUSANDS SEPARATOR
       "0,\u066B,\u066C",
       "!text/morekeys_arabic_diacritics",
       null,
       null,
      // U+061F: "؟" ARABIC QUESTION MARK
      // U+060C: "،" ARABIC COMMA
      // U+061B: "؛" ARABIC SEMICOLON
       "\u060C",
       null,
       "!text/morekeys_arabic_diacritics",

      null, null, null, null, null, null, null,

      // U+2605: "★" BLACK STAR
      // U+066D: "٭" ARABIC FIVE POINTED STAR
       "\u2605,\u066D",
      // U+2264: "≤" LESS-THAN OR EQUAL TO
      // U+2265: "≥" GREATER-THAN EQUAL TO
      // U+00AB: "«" LEFT-POINTING DOUBLE ANGLE QUOTATION MARK
      // U+00BB: "»" RIGHT-POINTING DOUBLE ANGLE QUOTATION MARK
      // U+2039: "‹" SINGLE LEFT-POINTING ANGLE QUOTATION MARK
      // U+203A: "›" SINGLE RIGHT-POINTING ANGLE QUOTATION MARK
       "(|)",
       ")|(",
       "[|]",
       "]|[",
       "{|}",
       "}|{",
       "<|>",
       ">|<",
       "\u2264|\u2265",
       "\u2265|\u2264",
       "\u00AB|\u00BB",
       "\u00BB|\u00AB",
       "\u2039|\u203A",
       "\u203A|\u2039",
      // U+060C: "،" ARABIC COMMA
       "\u060C",
       "!fixedColumnOrder!4,:,!,\u061F,\u061B,-,\",'",
      // U+0651: "ّ" ARABIC SHADDA
       "\u0651",
      // U+00BF: "¿" INVERTED QUESTION MARK
       "?,\u00BF",

      null, null, null, null, null, null, null, null, null, null, null, null, null,

      // U+266A: "♪" EIGHTH NOTE
       "\u266A",
      // The all letters need to be mirrored are found at
      // http://www.unicode.org/Public/6.1.0/ucd/BidiMirroring.txt
      // U+FD3E: "﴾" ORNATE LEFT PARENTHESIS
      // U+FD3F: "﴿" ORNATE RIGHT PARENTHESIS
       "!fixedColumnOrder!4,\uFD3E|\uFD3F,!text/keyspecs_left_parenthesis_more_keys",
       "!fixedColumnOrder!4,\uFD3F|\uFD3E,!text/keyspecs_right_parenthesis_more_keys",
      // U+0655: "ٕ" ARABIC HAMZA BELOW
      // U+0654: "ٔ" ARABIC HAMZA ABOVE
      // U+0652: "ْ" ARABIC SUKUN
      // U+064D: "ٍ" ARABIC KASRATAN
      // U+064C: "ٌ" ARABIC DAMMATAN
      // U+064B: "ً" ARABIC FATHATAN
      // U+0651: "ّ" ARABIC SHADDA
      // U+0656: "ٖ" ARABIC SUBSCRIPT ALEF
      // U+0670: "ٰ" ARABIC LETTER SUPERSCRIPT ALEF
      // U+0653: "ٓ" ARABIC MADDAH ABOVE
      // U+0650: "ِ" ARABIC KASRA
      // U+064F: "ُ" ARABIC DAMMA
      // U+064E: "َ" ARABIC FATHA
      // U+0640: "ـ" ARABIC TATWEEL
      // In order to make Tatweel easily distinguishable from other punctuations, we use consecutive Tatweels only for its displayed label.
      // Note: The space character is needed as a preceding letter to draw Arabic diacritics characters correctly.
       "!fixedColumnOrder!7, \u0655|\u0655, \u0654|\u0654, \u0652|\u0652, \u064D|\u064D, \u064C|\u064C, \u064B|\u064B, \u0651|\u0651, \u0656|\u0656, \u0670|\u0670, \u0653|\u0653, \u0650|\u0650, \u064F|\u064F, \u064E|\u064E,\u0640\u0640\u0640|\u0640",
       "\u061F",
       "\u0651",
       "\u061F",
       "\u061B",
      // U+066A: "٪" ARABIC PERCENT SIGN
       "\u066A",
       ";",
      // U+2030: "‰" PER MILLE SIGN
       "\\%,\u2030",
  };


  private static final String[] TEXTS_az_AZ = {
      // This is the same as Turkish
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
       "\u00E2,\u00E4,\u00E1",
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
       "\u00F6,\u00F4,\u0153,\u00F2,\u00F3,\u00F5,\u00F8,\u014D",
      // U+0259: "ə" LATIN SMALL LETTER SCHWA
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
       "\u0259,\u00E9",
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00FC,\u00FB,\u00F9,\u00FA,\u016B",
       null,
      // U+0131: "ı" LATIN SMALL LETTER DOTLESS I
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
       "\u0131,\u00EE,\u00EF,\u00EC,\u00ED,\u012F,\u012B",
      // U+0148: "ň" LATIN SMALL LETTER N WITH CARON
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
       "\u0148,\u00F1",
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
       "\u00E7,\u0107,\u010D",
       null,
      // U+015F: "ş" LATIN SMALL LETTER S WITH CEDILLA
      // U+00DF: "ß" LATIN SMALL LETTER SHARP S
      // U+015B: "ś" LATIN SMALL LETTER S WITH ACUTE
      // U+0161: "š" LATIN SMALL LETTER S WITH CARON
       "\u015F,\u00DF,\u015B,\u0161",
       null,
       null,
      // U+00FD: "ý" LATIN SMALL LETTER Y WITH ACUTE
       "\u00FD",
      // U+017E: "ž" LATIN SMALL LETTER Z WITH CARON
       "\u017E",

      null, null, null,

      // U+011F: "ğ" LATIN SMALL LETTER G WITH BREVE
       "\u011F",
  };


  private static final String[] TEXTS_be_BY = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0410: "А" CYRILLIC CAPITAL LETTER A
      // U+0411: "Б" CYRILLIC CAPITAL LETTER BE
      // U+0412: "В" CYRILLIC CAPITAL LETTER VE
       "\u0410\u0411\u0412",

      null, null, null,

       "!text/double_9qm_lqm",
       null,
       "!text/single_9qm_lqm",

      null, null, null, null, null, null, null, null, null, null, null,

      // U+0451: "ё" CYRILLIC SMALL LETTER IO
       "\u0451",

      null, null, null, null,

      // U+045E: "ў" CYRILLIC SMALL LETTER SHORT U
       "\u045E",
      // U+044B: "ы" CYRILLIC SMALL LETTER YERU
       "\u044B",
      // U+044D: "э" CYRILLIC SMALL LETTER E
       "\u044D",
      // U+0456: "і" CYRILLIC SMALL LETTER BYELORUSSIAN-UKRAINIAN I
       "\u0456",
      // U+044A: "ъ" CYRILLIC SMALL LETTER HARD SIGN
       "\u044A",
  };


  private static final String[] TEXTS_bg = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0410: "А" CYRILLIC CAPITAL LETTER A
      // U+0411: "Б" CYRILLIC CAPITAL LETTER BE
      // U+0412: "В" CYRILLIC CAPITAL LETTER VE
       "\u0410\u0411\u0412",

      null, null, null,

      // single_quotes of Bulgarian is default single_quotes_right_left.
       "!text/double_9qm_lqm",
  };


  private static final String[] TEXTS_bn_BD = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0995: "क" BENGALI LETTER KA
      // U+0996: "ख" BENGALI LETTER KHA
      // U+0997: "ग" BENGALI LETTER GA
       "\u0995\u0996\u0997",

      null, null, null, null, null, null,

      // U+09F3: "৳" BENGALI RUPEE SIGN
       "\u09F3",
  };


  private static final String[] TEXTS_bn_IN = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0995: "क" BENGALI LETTER KA
      // U+0996: "ख" BENGALI LETTER KHA
      // U+0997: "ग" BENGALI LETTER GA
       "\u0995\u0996\u0997",

      null, null, null, null, null, null,

      // U+20B9: "₹" INDIAN RUPEE SIGN
       "\u20B9",
  };


  private static final String[] TEXTS_ca = {
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+0105: "ą" LATIN SMALL LETTER A WITH OGONEK
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
      // U+00AA: "ª" FEMININE ORDINAL INDICATOR
       "\u00E0,\u00E1,\u00E4,\u00E2,\u00E3,\u00E5,\u0105,\u00E6,\u0101,\u00AA",
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
      // U+00BA: "º" MASCULINE ORDINAL INDICATOR
       "\u00F2,\u00F3,\u00F6,\u00F4,\u00F5,\u00F8,\u0153,\u014D,\u00BA",
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+0119: "ę" LATIN SMALL LETTER E WITH OGONEK
      // U+0117: "ė" LATIN SMALL LETTER E WITH DOT ABOVE
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
       "\u00E8,\u00E9,\u00EB,\u00EA,\u0119,\u0117,\u0113",
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00FA,\u00FC,\u00F9,\u00FB,\u016B",
       null,
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
       "\u00ED,\u00EF,\u00EC,\u00EE,\u012F,\u012B",
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
      // U+0144: "ń" LATIN SMALL LETTER N WITH ACUTE
       "\u00F1,\u0144",
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
       "\u00E7,\u0107,\u010D",

      null, null, null, null, null, null, null, null,

      // U+00B7: "·" MIDDLE DOT
      // U+0142: "ł" LATIN SMALL LETTER L WITH STROKE
       "l\u00B7l,\u0142",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null,

      // U+00B7: "·" MIDDLE DOT
       "!autoColumnOrder!9,\\,,?,!,\u00B7,#,),(,/,;,',@,:,-,\",+,\\%,&",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null,

       "!autoColumnOrder!8,\\,,',\u00B7,#,),(,/,;,@,:,-,\",+,\\%,&",
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
       "\u00E7",
  };


  private static final String[] TEXTS_cs = {
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
       "\u00E1,\u00E0,\u00E2,\u00E4,\u00E6,\u00E3,\u00E5,\u0101",
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
       "\u00F3,\u00F6,\u00F4,\u00F2,\u00F5,\u0153,\u00F8,\u014D",
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+011B: "ě" LATIN SMALL LETTER E WITH CARON
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+0119: "ę" LATIN SMALL LETTER E WITH OGONEK
      // U+0117: "ė" LATIN SMALL LETTER E WITH DOT ABOVE
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
       "\u00E9,\u011B,\u00E8,\u00EA,\u00EB,\u0119,\u0117,\u0113",
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+016F: "ů" LATIN SMALL LETTER U WITH RING ABOVE
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00FA,\u016F,\u00FB,\u00FC,\u00F9,\u016B",
       null,
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
       "\u00ED,\u00EE,\u00EF,\u00EC,\u012F,\u012B",
      // U+0148: "ň" LATIN SMALL LETTER N WITH CARON
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
      // U+0144: "ń" LATIN SMALL LETTER N WITH ACUTE
       "\u0148,\u00F1,\u0144",
      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
       "\u010D,\u00E7,\u0107",
       "!text/double_9qm_lqm",
      // U+0161: "š" LATIN SMALL LETTER S WITH CARON
      // U+00DF: "ß" LATIN SMALL LETTER SHARP S
      // U+015B: "ś" LATIN SMALL LETTER S WITH ACUTE
       "\u0161,\u00DF,\u015B",
       "!text/single_9qm_lqm",
       null,
      // U+00FD: "ý" LATIN SMALL LETTER Y WITH ACUTE
      // U+00FF: "ÿ" LATIN SMALL LETTER Y WITH DIAERESIS
       "\u00FD,\u00FF",
      // U+017E: "ž" LATIN SMALL LETTER Z WITH CARON
      // U+017A: "ź" LATIN SMALL LETTER Z WITH ACUTE
      // U+017C: "ż" LATIN SMALL LETTER Z WITH DOT ABOVE
       "\u017E,\u017A,\u017C",
      // U+010F: "ď" LATIN SMALL LETTER D WITH CARON
       "\u010F",
      // U+0165: "ť" LATIN SMALL LETTER T WITH CARON
       "\u0165",
       null,
       null,
       "!text/single_raqm_laqm",
       "!text/double_raqm_laqm",
      // U+0159: "ř" LATIN SMALL LETTER R WITH CARON
       "\u0159",
  };


  private static final String[] TEXTS_da = {
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
       "\u00E5,\u00E6,\u00E1,\u00E4,\u00E0,\u00E2,\u00E3,\u0101",
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
       "\u00F8,\u00F6,\u00F3,\u00F4,\u00F2,\u00F5,\u0153,\u014D",
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
       "\u00E9,\u00EB",
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00FA,\u00FC,\u00FB,\u00F9,\u016B",
       null,
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
       "\u00ED,\u00EF",
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
      // U+0144: "ń" LATIN SMALL LETTER N WITH ACUTE
       "\u00F1,\u0144",
       null,
       "!text/double_9qm_lqm",
      // U+00DF: "ß" LATIN SMALL LETTER SHARP S
      // U+015B: "ś" LATIN SMALL LETTER S WITH ACUTE
      // U+0161: "š" LATIN SMALL LETTER S WITH CARON
       "\u00DF,\u015B,\u0161",
       "!text/single_9qm_lqm",
       null,
      // U+00FD: "ý" LATIN SMALL LETTER Y WITH ACUTE
      // U+00FF: "ÿ" LATIN SMALL LETTER Y WITH DIAERESIS
       "\u00FD,\u00FF",
       null,
      // U+00F0: "ð" LATIN SMALL LETTER ETH
       "\u00F0",
       null,
      // U+0142: "ł" LATIN SMALL LETTER L WITH STROKE
       "\u0142",
       null,
       "!text/single_raqm_laqm",
       "!text/double_raqm_laqm",

      null, null, null,

      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
       "\u00E5",
      // U+00E6: "æ" LATIN SMALL LETTER AE
       "\u00E6",
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
       "\u00F8",
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
       "\u00E4",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null,

      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
       "\u00F6",
  };


  private static final String[] TEXTS_de = {
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
       "\u00E4,%,\u00E2,\u00E0,\u00E1,\u00E6,\u00E3,\u00E5,\u0101",
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
       "\u00F6,%,\u00F4,\u00F2,\u00F3,\u00F5,\u0153,\u00F8,\u014D",
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+0117: "ė" LATIN SMALL LETTER E WITH DOT ABOVE
       "\u00E9,\u00E8,\u00EA,\u00EB,\u0117",
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00FC,%,\u00FB,\u00F9,\u00FA,\u016B",
       null,
       null,
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
      // U+0144: "ń" LATIN SMALL LETTER N WITH ACUTE
       "\u00F1,\u0144",
       null,
       "!text/double_9qm_lqm",
      // U+00DF: "ß" LATIN SMALL LETTER SHARP S
      // U+015B: "ś" LATIN SMALL LETTER S WITH ACUTE
      // U+0161: "š" LATIN SMALL LETTER S WITH CARON
       "\u00DF,%,\u015B,\u0161",
       "!text/single_9qm_lqm",

      null, null, null, null, null, null, null,

       "!text/single_raqm_laqm",
       "!text/double_raqm_laqm",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null,

      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
       "\u00FC",
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
       "\u00F6",
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
       "\u00E4",
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
       "\u00E8",
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
       "\u00E9",
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
       "\u00E0",
  };


  private static final String[] TEXTS_el = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0391: "Α" GREEK CAPITAL LETTER ALPHA
      // U+0392: "Β" GREEK CAPITAL LETTER BETA
      // U+0393: "Γ" GREEK CAPITAL LETTER GAMMA
       "\u0391\u0392\u0393",
  };


  private static final String[] TEXTS_en = {
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
       "\u00E0,\u00E1,\u00E2,\u00E4,\u00E6,\u00E3,\u00E5,\u0101",
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
       "\u00F3,\u00F4,\u00F6,\u00F2,\u0153,\u00F8,\u014D,\u00F5",
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
       "\u00E9,\u00E8,\u00EA,\u00EB,\u0113",
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00FA,\u00FB,\u00FC,\u00F9,\u016B",
       null,
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
       "\u00ED,\u00EE,\u00EF,\u012B,\u00EC",
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
       "\u00F1",
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
       "\u00E7",
       null,
      // U+00DF: "ß" LATIN SMALL LETTER SHARP S
       "\u00DF",
  };


  private static final String[] TEXTS_eo = {
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
      // U+0103: "ă" LATIN SMALL LETTER A WITH BREVE
      // U+0105: "ą" LATIN SMALL LETTER A WITH OGONEK
      // U+00AA: "ª" FEMININE ORDINAL INDICATOR
       "\u00E1,\u00E0,\u00E2,\u00E4,\u00E6,\u00E3,\u00E5,\u0101,\u0103,\u0105,\u00AA",
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
      // U+0151: "ő" LATIN SMALL LETTER O WITH DOUBLE ACUTE
      // U+00BA: "º" MASCULINE ORDINAL INDICATOR
       "\u00F3,\u00F6,\u00F4,\u00F2,\u00F5,\u0153,\u00F8,\u014D,\u0151,\u00BA",
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+011B: "ě" LATIN SMALL LETTER E WITH CARON
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+0119: "ę" LATIN SMALL LETTER E WITH OGONEK
      // U+0117: "ė" LATIN SMALL LETTER E WITH DOT ABOVE
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
       "\u00E9,\u011B,\u00E8,\u00EA,\u00EB,\u0119,\u0117,\u0113",
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+016F: "ů" LATIN SMALL LETTER U WITH RING ABOVE
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
      // U+0169: "ũ" LATIN SMALL LETTER U WITH TILDE
      // U+0171: "ű" LATIN SMALL LETTER U WITH DOUBLE ACUTE
      // U+0173: "ų" LATIN SMALL LETTER U WITH OGONEK
      // U+00B5: "µ" MICRO SIGN
       "\u00FA,\u016F,\u00FB,\u00FC,\u00F9,\u016B,\u0169,\u0171,\u0173,\u00B5",
       null,
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+0129: "ĩ" LATIN SMALL LETTER I WITH TILDE
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
      // U+0131: "ı" LATIN SMALL LETTER DOTLESS I
      // U+0133: "ĳ" LATIN SMALL LIGATURE IJ
       "\u00ED,\u00EE,\u00EF,\u0129,\u00EC,\u012F,\u012B,\u0131,\u0133",
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
      // U+0144: "ń" LATIN SMALL LETTER N WITH ACUTE
      // U+0146: "ņ" LATIN SMALL LETTER N WITH CEDILLA
      // U+0148: "ň" LATIN SMALL LETTER N WITH CARON
      // U+0149: "ŉ" LATIN SMALL LETTER N PRECEDED BY APOSTROPHE
      // U+014B: "ŋ" LATIN SMALL LETTER ENG
       "\u00F1,\u0144,\u0146,\u0148,\u0149,\u014B",
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
      // U+010B: "ċ" LATIN SMALL LETTER C WITH DOT ABOVE
       "\u0107,\u010D,\u00E7,\u010B",
       null,
      // U+00DF: "ß" LATIN SMALL LETTER SHARP S
      // U+0161: "š" LATIN SMALL LETTER S WITH CARON
      // U+015B: "ś" LATIN SMALL LETTER S WITH ACUTE
      // U+0219: "ș" LATIN SMALL LETTER S WITH COMMA BELOW
      // U+015F: "ş" LATIN SMALL LETTER S WITH CEDILLA
       "\u00DF,\u0161,\u015B,\u0219,\u015F",
       null,
       null,
      // U+00FD: "ý" LATIN SMALL LETTER Y WITH ACUTE
      // U+0177: "ŷ" LATIN SMALL LETTER Y WITH CIRCUMFLEX
      // U+00FF: "ÿ" LATIN SMALL LETTER Y WITH DIAERESIS
      // U+00FE: "þ" LATIN SMALL LETTER THORN
       "y,\u00FD,\u0177,\u00FF,\u00FE",
      // U+017A: "ź" LATIN SMALL LETTER Z WITH ACUTE
      // U+017C: "ż" LATIN SMALL LETTER Z WITH DOT ABOVE
      // U+017E: "ž" LATIN SMALL LETTER Z WITH CARON
       "\u017A,\u017C,\u017E",
      // U+00F0: "ð" LATIN SMALL LETTER ETH
      // U+010F: "ď" LATIN SMALL LETTER D WITH CARON
      // U+0111: "đ" LATIN SMALL LETTER D WITH STROKE
       "\u00F0,\u010F,\u0111",
      // U+0165: "ť" LATIN SMALL LETTER T WITH CARON
      // U+021B: "ț" LATIN SMALL LETTER T WITH COMMA BELOW
      // U+0163: "ţ" LATIN SMALL LETTER T WITH CEDILLA
      // U+0167: "ŧ" LATIN SMALL LETTER T WITH STROKE
       "\u0165,\u021B,\u0163,\u0167",
      // U+013A: "ĺ" LATIN SMALL LETTER L WITH ACUTE
      // U+013C: "ļ" LATIN SMALL LETTER L WITH CEDILLA
      // U+013E: "ľ" LATIN SMALL LETTER L WITH CARON
      // U+0140: "ŀ" LATIN SMALL LETTER L WITH MIDDLE DOT
      // U+0142: "ł" LATIN SMALL LETTER L WITH STROKE
       "\u013A,\u013C,\u013E,\u0140,\u0142",
      // U+011F: "ğ" LATIN SMALL LETTER G WITH BREVE
      // U+0121: "ġ" LATIN SMALL LETTER G WITH DOT ABOVE
      // U+0123: "ģ" LATIN SMALL LETTER G WITH CEDILLA
       "\u011F,\u0121,\u0123",
       null,
       null,
      // U+0159: "ř" LATIN SMALL LETTER R WITH CARON
      // U+0155: "ŕ" LATIN SMALL LETTER R WITH ACUTE
      // U+0157: "ŗ" LATIN SMALL LETTER R WITH CEDILLA
       "\u0159,\u0155,\u0157",
      // U+0137: "ķ" LATIN SMALL LETTER K WITH CEDILLA
      // U+0138: "ĸ" LATIN SMALL LETTER KRA
       "\u0137,\u0138",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null,

      // U+0125: "ĥ" LATIN SMALL LETTER H WITH CIRCUMFLEX
      // U+0127: "ħ" LATIN SMALL LETTER H WITH STROKE
       "\u0125,\u0127",
      // U+0175: "ŵ" LATIN SMALL LETTER W WITH CIRCUMFLEX
       "w,\u0175",

      null, null, null, null, null, null, null, null, null, null, null,

      // U+0135: "ĵ" LATIN SMALL LETTER J WITH CIRCUMFLEX
       "\u0135",

      null, null, null, null, null, null, null, null, null,
      null, null, null, null,

      // U+0175: "ŵ" LATIN SMALL LETTER W WITH CIRCUMFLEX
       "w,\u0175",
       null,
       "q",
       "x",
      // U+015D: "ŝ" LATIN SMALL LETTER S WITH CIRCUMFLEX
       "\u015D",
      // U+011D: "ĝ" LATIN SMALL LETTER G WITH CIRCUMFLEX
       "\u011D",
      // U+016D: "ŭ" LATIN SMALL LETTER U WITH BREVE
       "\u016D",
      // U+0109: "ĉ" LATIN SMALL LETTER C WITH CIRCUMFLEX
       "\u0109",
  };


  private static final String[] TEXTS_es = {
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+0105: "ą" LATIN SMALL LETTER A WITH OGONEK
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
      // U+00AA: "ª" FEMININE ORDINAL INDICATOR
       "\u00E1,\u00E0,\u00E4,\u00E2,\u00E3,\u00E5,\u0105,\u00E6,\u0101,\u00AA",
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
      // U+00BA: "º" MASCULINE ORDINAL INDICATOR
       "\u00F3,\u00F2,\u00F6,\u00F4,\u00F5,\u00F8,\u0153,\u014D,\u00BA",
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+0119: "ę" LATIN SMALL LETTER E WITH OGONEK
      // U+0117: "ė" LATIN SMALL LETTER E WITH DOT ABOVE
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
       "\u00E9,\u00E8,\u00EB,\u00EA,\u0119,\u0117,\u0113",
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00FA,\u00FC,\u00F9,\u00FB,\u016B",
       null,
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
       "\u00ED,\u00EF,\u00EC,\u00EE,\u012F,\u012B",
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
      // U+0144: "ń" LATIN SMALL LETTER N WITH ACUTE
       "\u00F1,\u0144",
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
       "\u00E7,\u0107,\u010D",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null,

      // U+00A1: "¡" INVERTED EXCLAMATION MARK
      // U+00BF: "¿" INVERTED QUESTION MARK
       "!autoColumnOrder!9,\\,,?,!,#,),(,/,;,\u00A1,',@,:,-,\",+,\\%,&,\u00BF",
  };


  private static final String[] TEXTS_et_EE = {
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+0105: "ą" LATIN SMALL LETTER A WITH OGONEK
       "\u00E4,\u0101,\u00E0,\u00E1,\u00E2,\u00E3,\u00E5,\u00E6,\u0105",
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+0151: "ő" LATIN SMALL LETTER O WITH DOUBLE ACUTE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
       "\u00F6,\u00F5,\u00F2,\u00F3,\u00F4,\u0153,\u0151,\u00F8",
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+0117: "ė" LATIN SMALL LETTER E WITH DOT ABOVE
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+0119: "ę" LATIN SMALL LETTER E WITH OGONEK
      // U+011B: "ě" LATIN SMALL LETTER E WITH CARON
       "\u0113,\u00E8,\u0117,\u00E9,\u00EA,\u00EB,\u0119,\u011B",
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
      // U+0173: "ų" LATIN SMALL LETTER U WITH OGONEK
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+016F: "ů" LATIN SMALL LETTER U WITH RING ABOVE
      // U+0171: "ű" LATIN SMALL LETTER U WITH DOUBLE ACUTE
       "\u00FC,\u016B,\u0173,\u00F9,\u00FA,\u00FB,\u016F,\u0171",
       null,
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+0131: "ı" LATIN SMALL LETTER DOTLESS I
       "\u012B,\u00EC,\u012F,\u00ED,\u00EE,\u00EF,\u0131",
      // U+0146: "ņ" LATIN SMALL LETTER N WITH CEDILLA
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
      // U+0144: "ń" LATIN SMALL LETTER N WITH ACUTE
       "\u0146,\u00F1,\u0144",
      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
       "\u010D,\u00E7,\u0107",
       "!text/double_9qm_lqm",
      // U+0161: "š" LATIN SMALL LETTER S WITH CARON
      // U+00DF: "ß" LATIN SMALL LETTER SHARP S
      // U+015B: "ś" LATIN SMALL LETTER S WITH ACUTE
      // U+015F: "ş" LATIN SMALL LETTER S WITH CEDILLA
       "\u0161,\u00DF,\u015B,\u015F",
       "!text/single_9qm_lqm",
       null,
      // U+00FD: "ý" LATIN SMALL LETTER Y WITH ACUTE
      // U+00FF: "ÿ" LATIN SMALL LETTER Y WITH DIAERESIS
       "\u00FD,\u00FF",
      // U+017E: "ž" LATIN SMALL LETTER Z WITH CARON
      // U+017C: "ż" LATIN SMALL LETTER Z WITH DOT ABOVE
      // U+017A: "ź" LATIN SMALL LETTER Z WITH ACUTE
       "\u017E,\u017C,\u017A",
      // U+010F: "ď" LATIN SMALL LETTER D WITH CARON
       "\u010F",
      // U+0163: "ţ" LATIN SMALL LETTER T WITH CEDILLA
      // U+0165: "ť" LATIN SMALL LETTER T WITH CARON
       "\u0163,\u0165",
      // U+013C: "ļ" LATIN SMALL LETTER L WITH CEDILLA
      // U+0142: "ł" LATIN SMALL LETTER L WITH STROKE
      // U+013A: "ĺ" LATIN SMALL LETTER L WITH ACUTE
      // U+013E: "ľ" LATIN SMALL LETTER L WITH CARON
       "\u013C,\u0142,\u013A,\u013E",
      // U+0123: "ģ" LATIN SMALL LETTER G WITH CEDILLA
      // U+011F: "ğ" LATIN SMALL LETTER G WITH BREVE
       "\u0123,\u011F",
       null,
       null,
      // U+0157: "ŗ" LATIN SMALL LETTER R WITH CEDILLA
      // U+0159: "ř" LATIN SMALL LETTER R WITH CARON
      // U+0155: "ŕ" LATIN SMALL LETTER R WITH ACUTE
       "\u0157,\u0159,\u0155",
      // U+0137: "ķ" LATIN SMALL LETTER K WITH CEDILLA
       "\u0137",
       null,
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
       "\u00FC",
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
       "\u00F6",
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
       "\u00E4",
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
       "\u00F5",
  };


  private static final String[] TEXTS_eu_ES = {
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+0105: "ą" LATIN SMALL LETTER A WITH OGONEK
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
      // U+00AA: "ª" FEMININE ORDINAL INDICATOR
       "\u00E1,\u00E0,\u00E4,\u00E2,\u00E3,\u00E5,\u0105,\u00E6,\u0101,\u00AA",
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
      // U+00BA: "º" MASCULINE ORDINAL INDICATOR
       "\u00F3,\u00F2,\u00F6,\u00F4,\u00F5,\u00F8,\u0153,\u014D,\u00BA",
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+0119: "ę" LATIN SMALL LETTER E WITH OGONEK
      // U+0117: "ė" LATIN SMALL LETTER E WITH DOT ABOVE
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
       "\u00E9,\u00E8,\u00EB,\u00EA,\u0119,\u0117,\u0113",
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00FA,\u00FC,\u00F9,\u00FB,\u016B",
       null,
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
       "\u00ED,\u00EF,\u00EC,\u00EE,\u012F,\u012B",
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
      // U+0144: "ń" LATIN SMALL LETTER N WITH ACUTE
       "\u00F1,\u0144",
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
       "\u00E7,\u0107,\u010D",
  };


  private static final String[] TEXTS_fa = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0627: "ا" ARABIC LETTER ALEF
      // U+200C: ZERO WIDTH NON-JOINER
      // U+0628: "ب" ARABIC LETTER BEH
      // U+067E: "پ" ARABIC LETTER PEH
       "\u0627\u200C\u0628\u200C\u067E",

      null, null, null, null, null, null,

      // U+FDFC: "﷼" RIAL SIGN
       "\uFDFC",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null,

      // U+06F1: "۱" EXTENDED ARABIC-INDIC DIGIT ONE
       "\u06F1",
      // U+06F2: "۲" EXTENDED ARABIC-INDIC DIGIT TWO
       "\u06F2",
      // U+06F3: "۳" EXTENDED ARABIC-INDIC DIGIT THREE
       "\u06F3",
      // U+06F4: "۴" EXTENDED ARABIC-INDIC DIGIT FOUR
       "\u06F4",
      // U+06F5: "۵" EXTENDED ARABIC-INDIC DIGIT FIVE
       "\u06F5",
      // U+06F6: "۶" EXTENDED ARABIC-INDIC DIGIT SIX
       "\u06F6",
      // U+06F7: "۷" EXTENDED ARABIC-INDIC DIGIT SEVEN
       "\u06F7",
      // U+06F8: "۸" EXTENDED ARABIC-INDIC DIGIT EIGHT
       "\u06F8",
      // U+06F9: "۹" EXTENDED ARABIC-INDIC DIGIT NINE
       "\u06F9",
      // U+06F0: "۰" EXTENDED ARABIC-INDIC DIGIT ZERO
       "\u06F0",
      // Label for "switch to symbols" key.
      // U+061F: "؟" ARABIC QUESTION MARK
       "\u06F3\u06F2\u06F1\u061F",
       "1",
       "2",
       "3",
       "4",
       "5",
       "6",
       "7",
       "8",
       "9",
      // U+066B: "٫" ARABIC DECIMAL SEPARATOR
      // U+066C: "٬" ARABIC THOUSANDS SEPARATOR
       "0,\u066B,\u066C",
       "!text/morekeys_arabic_diacritics",
       null,
       null,
      // U+060C: "،" ARABIC COMMA
      // U+061B: "؛" ARABIC SEMICOLON
      // U+061F: "؟" ARABIC QUESTION MARK
      // U+00AB: "«" LEFT-POINTING DOUBLE ANGLE QUOTATION MARK
      // U+00BB: "»" RIGHT-POINTING DOUBLE ANGLE QUOTATION MARK
       "\u060C",
       null,
       "!text/morekeys_arabic_diacritics",

      null, null, null, null, null, null, null,

      // U+2605: "★" BLACK STAR
      // U+066D: "٭" ARABIC FIVE POINTED STAR
       "\u2605,\u066D",
       "(|)",
       ")|(",
       "[|]",
       "]|[",
       "{|}",
       "}|{",
       "<|>",
       ">|<",
       "\u2264|\u2265",
       "\u2265|\u2264",
       "\u00AB|\u00BB",
       "\u00BB|\u00AB",
       "\u2039|\u203A",
       "\u203A|\u2039",
      // U+060C: "،" ARABIC COMMA
       "\u060C",
       "!fixedColumnOrder!4,:,!,\u061F,\u061B,-,!text/keyspec_left_double_angle_quote,!text/keyspec_right_double_angle_quote",
      // U+064B: "ً" ARABIC FATHATAN
       "\u064B",
      // U+00BF: "¿" INVERTED QUESTION MARK
       "?,\u00BF",

      null, null, null, null, null, null, null, null, null, null, null, null, null,

      // U+266A: "♪" EIGHTH NOTE
       "\u266A",
      // The all letters need to be mirrored are found at
      // http://www.unicode.org/Public/6.1.0/ucd/BidiMirroring.txt
      // U+FD3E: "﴾" ORNATE LEFT PARENTHESIS
      // U+FD3F: "﴿" ORNATE RIGHT PARENTHESIS
       "!fixedColumnOrder!4,\uFD3E|\uFD3F,!text/keyspecs_left_parenthesis_more_keys",
       "!fixedColumnOrder!4,\uFD3F|\uFD3E,!text/keyspecs_right_parenthesis_more_keys",
      // U+0655: "ٕ" ARABIC HAMZA BELOW
      // U+0652: "ْ" ARABIC SUKUN
      // U+0651: "ّ" ARABIC SHADDA
      // U+064C: "ٌ" ARABIC DAMMATAN
      // U+064D: "ٍ" ARABIC KASRATAN
      // U+064B: "ً" ARABIC FATHATAN
      // U+0654: "ٔ" ARABIC HAMZA ABOVE
      // U+0656: "ٖ" ARABIC SUBSCRIPT ALEF
      // U+0670: "ٰ" ARABIC LETTER SUPERSCRIPT ALEF
      // U+0653: "ٓ" ARABIC MADDAH ABOVE
      // U+064F: "ُ" ARABIC DAMMA
      // U+0650: "ِ" ARABIC KASRA
      // U+064E: "َ" ARABIC FATHA
      // U+0640: "ـ" ARABIC TATWEEL
      // In order to make Tatweel easily distinguishable from other punctuations, we use consecutive Tatweels only for its displayed label.
      // Note: The space character is needed as a preceding letter to draw Arabic diacritics characters correctly.
       "!fixedColumnOrder!7, \u0655|\u0655, \u0652|\u0652, \u0651|\u0651, \u064C|\u064C, \u064D|\u064D, \u064B|\u064B, \u0654|\u0654, \u0656|\u0656, \u0670|\u0670, \u0653|\u0653, \u064F|\u064F, \u0650|\u0650, \u064E|\u064E,\u0640\u0640\u0640|\u0640",
       "\u061F",
       "\u064B",
       "\u061F",
       "\u061B",
      // U+066A: "٪" ARABIC PERCENT SIGN
       "\u066A",
       ";",
      // U+2030: "‰" PER MILLE SIGN
       "\\%,\u2030",
      null, null, null, null, null, null, null, null, null,

      // U+2264: "≤" LESS-THAN OR EQUAL TO
      // U+2265: "≥" GREATER-THAN EQUAL TO
      // U+00AB: "«" LEFT-POINTING DOUBLE ANGLE QUOTATION MARK
      // U+00BB: "»" RIGHT-POINTING DOUBLE ANGLE QUOTATION MARK
      // U+2039: "‹" SINGLE LEFT-POINTING ANGLE QUOTATION MARK
      // U+203A: "›" SINGLE RIGHT-POINTING ANGLE QUOTATION MARK
       "!fixedColumnOrder!3,!text/keyspec_left_single_angle_quote,!text/keyspec_less_than_equal,!text/keyspec_less_than",
       "!fixedColumnOrder!3,!text/keyspec_right_single_angle_quote,!text/keyspec_greater_than_equal,!text/keyspec_greater_than",
  };


  private static final String[] TEXTS_fi = {
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
       "\u00E4,\u00E5,\u00E6,\u00E0,\u00E1,\u00E2,\u00E3,\u0101",
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
       "\u00F6,\u00F8,\u00F4,\u00F2,\u00F3,\u00F5,\u0153,\u014D",
       null,
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
       "\u00FC",

      null, null, null, null, null,

      // U+0161: "š" LATIN SMALL LETTER S WITH CARON
      // U+00DF: "ß" LATIN SMALL LETTER SHARP S
      // U+015B: "ś" LATIN SMALL LETTER S WITH ACUTE
       "\u0161,\u00DF,\u015B",

      null, null, null,

      // U+017E: "ž" LATIN SMALL LETTER Z WITH CARON
      // U+017A: "ź" LATIN SMALL LETTER Z WITH ACUTE
      // U+017C: "ż" LATIN SMALL LETTER Z WITH DOT ABOVE
       "\u017E,\u017A,\u017C",

      null, null, null, null, null, null, null, null, null,

      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
       "\u00E5",
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
       "\u00F6",
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
       "\u00E4",
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
       "\u00F8",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null,

      // U+00E6: "æ" LATIN SMALL LETTER AE
       "\u00E6",
  };


  private static final String[] TEXTS_fr = {
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
      // U+00AA: "ª" FEMININE ORDINAL INDICATOR
       "\u00E0,\u00E2,%,\u00E6,\u00E1,\u00E4,\u00E3,\u00E5,\u0101,\u00AA",
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
      // U+00BA: "º" MASCULINE ORDINAL INDICATOR
       "\u00F4,\u0153,%,\u00F6,\u00F2,\u00F3,\u00F5,\u00F8,\u014D,\u00BA",
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+0119: "ę" LATIN SMALL LETTER E WITH OGONEK
      // U+0117: "ė" LATIN SMALL LETTER E WITH DOT ABOVE
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
       "\u00E9,\u00E8,\u00EA,\u00EB,%,\u0119,\u0117,\u0113",
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00F9,\u00FB,%,\u00FC,\u00FA,\u016B",
       null,
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
       "\u00EE,%,\u00EF,\u00EC,\u00ED,\u012F,\u012B",
       null,
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
       "\u00E7,%,\u0107,\u010D",

      null, null, null, null,

      // U+00FF: "ÿ" LATIN SMALL LETTER Y WITH DIAERESIS
       "%,\u00FF",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null,

      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
       "\u00E8",
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
       "\u00E9",
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
       "\u00E0",
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
       "\u00FC",
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
       "\u00F6",
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
       "\u00E4",
  };


  private static final String[] TEXTS_gl_ES = {
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+0105: "ą" LATIN SMALL LETTER A WITH OGONEK
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
      // U+00AA: "ª" FEMININE ORDINAL INDICATOR
       "\u00E1,\u00E0,\u00E4,\u00E2,\u00E3,\u00E5,\u0105,\u00E6,\u0101,\u00AA",
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
      // U+00BA: "º" MASCULINE ORDINAL INDICATOR
       "\u00F3,\u00F2,\u00F6,\u00F4,\u00F5,\u00F8,\u0153,\u014D,\u00BA",
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+0119: "ę" LATIN SMALL LETTER E WITH OGONEK
      // U+0117: "ė" LATIN SMALL LETTER E WITH DOT ABOVE
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
       "\u00E9,\u00E8,\u00EB,\u00EA,\u0119,\u0117,\u0113",
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00FA,\u00FC,\u00F9,\u00FB,\u016B",
       null,
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
       "\u00ED,\u00EF,\u00EC,\u00EE,\u012F,\u012B",
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
      // U+0144: "ń" LATIN SMALL LETTER N WITH ACUTE
       "\u00F1,\u0144",
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
       "\u00E7,\u0107,\u010D",
  };


  private static final String[] TEXTS_hi = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0915: "क" DEVANAGARI LETTER KA
      // U+0916: "ख" DEVANAGARI LETTER KHA
      // U+0917: "ग" DEVANAGARI LETTER GA
       "\u0915\u0916\u0917",

      null, null, null, null, null, null,

      // U+20B9: "₹" INDIAN RUPEE SIGN
       "\u20B9",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null,

      // U+0967: "१" DEVANAGARI DIGIT ONE
       "\u0967",
      // U+0968: "२" DEVANAGARI DIGIT TWO
       "\u0968",
      // U+0969: "३" DEVANAGARI DIGIT THREE
       "\u0969",
      // U+096A: "४" DEVANAGARI DIGIT FOUR
       "\u096A",
      // U+096B: "५" DEVANAGARI DIGIT FIVE
       "\u096B",
      // U+096C: "६" DEVANAGARI DIGIT SIX
       "\u096C",
      // U+096D: "७" DEVANAGARI DIGIT SEVEN
       "\u096D",
      // U+096E: "८" DEVANAGARI DIGIT EIGHT
       "\u096E",
      // U+096F: "९" DEVANAGARI DIGIT NINE
       "\u096F",
      // U+0966: "०" DEVANAGARI DIGIT ZERO
       "\u0966",
      // Label for "switch to symbols" key.
       "?\u0967\u0968\u0969",
       "1",
       "2",
       "3",
       "4",
       "5",
       "6",
       "7",
       "8",
       "9",
       "0",
       "!autoColumnOrder!8,\\,,.,',#,),(,/,;,@,:,-,\",+,\\%,&",

      null, null, null,

      // U+0964: "।" DEVANAGARI DANDA
       "\u0964",
       "!autoColumnOrder!9,\\,,.,?,!,#,),(,/,;,',@,:,-,\",+,\\%,&",
       "\u0964",
  };


  private static final String[] TEXTS_hi_ZZ = {

      null, null, null, null, null, null, null, null, null, null, null,

      // U+20B9: "₹" INDIAN RUPEE SIGN
       "\u20B9"
  };


  private static final String[] TEXTS_hr = {

      null, null, null, null, null, null,

      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
      // U+0144: "ń" LATIN SMALL LETTER N WITH ACUTE
       "\u00F1,\u0144",
      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
       "\u010D,\u0107,\u00E7",
       "!text/double_9qm_rqm",
      // U+0161: "š" LATIN SMALL LETTER S WITH CARON
      // U+015B: "ś" LATIN SMALL LETTER S WITH ACUTE
      // U+00DF: "ß" LATIN SMALL LETTER SHARP S
       "\u0161,\u015B,\u00DF",
       "!text/single_9qm_rqm",
       null,
       null,
      // U+017E: "ž" LATIN SMALL LETTER Z WITH CARON
      // U+017A: "ź" LATIN SMALL LETTER Z WITH ACUTE
      // U+017C: "ż" LATIN SMALL LETTER Z WITH DOT ABOVE
       "\u017E,\u017A,\u017C",
      // U+0111: "đ" LATIN SMALL LETTER D WITH STROKE
       "\u0111",

      null, null, null,

       "!text/single_raqm_laqm",
       "!text/double_raqm_laqm",
  };


  private static final String[] TEXTS_hu = {
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
       "\u00E1,\u00E0,\u00E2,\u00E4,\u00E6,\u00E3,\u00E5,\u0101",
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+0151: "ő" LATIN SMALL LETTER O WITH DOUBLE ACUTE
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
       "\u00F3,\u00F6,\u0151,\u00F4,\u00F2,\u00F5,\u0153,\u00F8,\u014D",
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+0119: "ę" LATIN SMALL LETTER E WITH OGONEK
      // U+0117: "ė" LATIN SMALL LETTER E WITH DOT ABOVE
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
       "\u00E9,\u00E8,\u00EA,\u00EB,\u0119,\u0117,\u0113",
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+0171: "ű" LATIN SMALL LETTER U WITH DOUBLE ACUTE
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00FA,\u00FC,\u0171,\u00FB,\u00F9,\u016B",
       null,
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
       "\u00ED,\u00EE,\u00EF,\u00EC,\u012F,\u012B",
       null,
       null,
       "!text/double_9qm_rqm",
       null,
       "!text/single_9qm_rqm",

      null, null, null, null, null, null, null,

       "!text/single_raqm_laqm",
       "!text/double_raqm_laqm",
  };


  private static final String[] TEXTS_hy_AM = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0531: "Ա" ARMENIAN CAPITAL LETTER AYB
      // U+0532: "Բ" ARMENIAN CAPITAL LETTER BEN
      // U+0533: "Գ" ARMENIAN CAPITAL LETTER GIM
       "\u0531\u0532\u0533",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null,

       "!text/morekeys_punctuation",
       null,
      // U+055E: "՞" ARMENIAN QUESTION MARK
      // U+055C: "՜" ARMENIAN EXCLAMATION MARK
      // U+055A: "՚" ARMENIAN APOSTROPHE
      // U+0559: "ՙ" ARMENIAN MODIFIER LETTER LEFT HALF RING
      // U+055D: "՝" ARMENIAN COMMA
      // U+055B: "՛" ARMENIAN EMPHASIS MARK
      // U+058A: "֊" ARMENIAN HYPHEN
      // U+00BB: "»" RIGHT-POINTING DOUBLE ANGLE QUOTATION MARK
      // U+00AB: "«" LEFT-POINTING DOUBLE ANGLE QUOTATION MARK
      // U+055F: "՟" ARMENIAN ABBREVIATION MARK
       "!autoColumnOrder!8,\\,,\u055E,\u055C,.,\u055A,\u0559,?,!,\u055D,\u055B,\u058A,\u00BB,\u00AB,\u055F,;,:",
       "\u055D",
      // U+0589: "։" ARMENIAN FULL STOP
       "\u0589",
       null,
       "\u0589",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null,

      // U+058F: "֏" ARMENIAN DRAM SIGN
      // TODO: Enable this when we have glyph for the following letter
      // <string name="keyspec_currency">&#x058F;</string>
      //
      // U+055D: "՝" ARMENIAN COMMA
       "\u055D",
       null,
       null,
      // U+055E: "՞" ARMENIAN QUESTION MARK
      // U+00BF: "¿" INVERTED QUESTION MARK
       "\u055E,\u00BF",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null,

      // U+055C: "՜" ARMENIAN EXCLAMATION MARK
      // U+00A1: "¡" INVERTED EXCLAMATION MARK
       "\u055C,\u00A1",
  };


  private static final String[] TEXTS_is = {
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
       "\u00E1,\u00E4,\u00E6,\u00E5,\u00E0,\u00E2,\u00E3,\u0101",
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
       "\u00F3,\u00F6,\u00F4,\u00F2,\u00F5,\u0153,\u00F8,\u014D",
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+0119: "ę" LATIN SMALL LETTER E WITH OGONEK
      // U+0117: "ė" LATIN SMALL LETTER E WITH DOT ABOVE
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
       "\u00E9,\u00EB,\u00E8,\u00EA,\u0119,\u0117,\u0113",
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00FA,\u00FC,\u00FB,\u00F9,\u016B",
       null,
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
       "\u00ED,\u00EF,\u00EE,\u00EC,\u012F,\u012B",
       null,
       null,
       "!text/double_9qm_lqm",
       null,
       "!text/single_9qm_lqm",
       null,
      // U+00FD: "ý" LATIN SMALL LETTER Y WITH ACUTE
      // U+00FF: "ÿ" LATIN SMALL LETTER Y WITH DIAERESIS
       "\u00FD,\u00FF",
       null,
      // U+00F0: "ð" LATIN SMALL LETTER ETH
       "\u00F0",
      // U+00FE: "þ" LATIN SMALL LETTER THORN
       "\u00FE",
  };


  private static final String[] TEXTS_it = {
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
      // U+00AA: "ª" FEMININE ORDINAL INDICATOR
       "\u00E0,\u00E1,\u00E2,\u00E4,\u00E6,\u00E3,\u00E5,\u0101,\u00AA",
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
      // U+00BA: "º" MASCULINE ORDINAL INDICATOR
       "\u00F2,\u00F3,\u00F4,\u00F6,\u00F5,\u0153,\u00F8,\u014D,\u00BA",
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+0119: "ę" LATIN SMALL LETTER E WITH OGONEK
      // U+0117: "ė" LATIN SMALL LETTER E WITH DOT ABOVE
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
      // U+0259: "ə" LATIN SMALL LETTER SCHWA
       "\u00E8,\u00E9,\u00EA,\u00EB,\u0119,\u0117,\u0113,\u0259",
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00F9,\u00FA,\u00FB,\u00FC,\u016B",
       null,
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
       "\u00EC,\u00ED,\u00EE,\u00EF,\u012F,\u012B",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null,

      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
       "\u00FC",
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
       "\u00F6",
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
       "\u00E4",
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
       "\u00E8",
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
       "\u00E9",
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
       "\u00E0",
  };


  private static final String[] TEXTS_iw = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+05D0: "א" HEBREW LETTER ALEF
      // U+05D1: "ב" HEBREW LETTER BET
      // U+05D2: "ג" HEBREW LETTER GIMEL
       "\u05D0\u05D1\u05D2",

      null, null, null,

       "!text/double_rqm_9qm",
       null,
       "!text/single_rqm_9qm",
      // U+20AA: "₪" NEW SHEQEL SIGN
       "\u20AA",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null,

      // U+2605: "★" BLACK STAR
       "\u2605",
      // The all letters need to be mirrored are found at
      // http://www.unicode.org/Public/6.1.0/ucd/BidiMirroring.txt
      // U+2264: "≤" LESS-THAN OR EQUAL TO
      // U+2265: "≥" GREATER-THAN EQUAL TO
      // U+00AB: "«" LEFT-POINTING DOUBLE ANGLE QUOTATION MARK
      // U+00BB: "»" RIGHT-POINTING DOUBLE ANGLE QUOTATION MARK
      // U+2039: "‹" SINGLE LEFT-POINTING ANGLE QUOTATION MARK
      // U+203A: "›" SINGLE RIGHT-POINTING ANGLE QUOTATION MARK
       "(|)",
       ")|(",
       "[|]",
       "]|[",
       "{|}",
       "}|{",
       "<|>",
       ">|<",
       "\u2264|\u2265",
       "\u2265|\u2264",
       "\u00AB|\u00BB",
       "\u00BB|\u00AB",
       "\u2039|\u203A",
       "\u203A|\u2039",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null,

      // U+00B1: "±" PLUS-MINUS SIGN
      // U+FB29: "﬩" HEBREW LETTER ALTERNATIVE PLUS SIGN
       "\u00B1,\uFB29",
  };


  private static final String[] TEXTS_ka_GE = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+10D0: "ა" GEORGIAN LETTER AN
      // U+10D1: "ბ" GEORGIAN LETTER BAN
      // U+10D2: "გ" GEORGIAN LETTER GAN
       "\u10D0\u10D1\u10D2",

      null, null, null,

       "!text/double_9qm_lqm",
       null,
       "!text/single_9qm_lqm",
  };


  private static final String[] TEXTS_kk = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0410: "А" CYRILLIC CAPITAL LETTER A
      // U+0411: "Б" CYRILLIC CAPITAL LETTER BE
      // U+0412: "В" CYRILLIC CAPITAL LETTER VE
       "\u0410\u0411\u0412",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null,

      // U+0451: "ё" CYRILLIC SMALL LETTER IO
       "\u0451",

      null, null, null, null,

      // U+0449: "щ" CYRILLIC SMALL LETTER SHCHA
       "\u0449",
      // U+044B: "ы" CYRILLIC SMALL LETTER YERU
       "\u044B",
      // U+044D: "э" CYRILLIC SMALL LETTER E
       "\u044D",
      // U+0438: "и" CYRILLIC SMALL LETTER I
       "\u0438",
      // U+044A: "ъ" CYRILLIC SMALL LETTER HARD SIGN
       "\u044A",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null,

      // U+0456: "і" CYRILLIC SMALL LETTER BYELORUSSIAN-UKRAINIAN I
       "\u0456",
      // U+04AF: "ү" CYRILLIC SMALL LETTER STRAIGHT U
      // U+04B1: "ұ" CYRILLIC SMALL LETTER STRAIGHT U WITH STROKE
       "\u04AF,\u04B1",
      // U+04A3: "ң" CYRILLIC SMALL LETTER EN WITH DESCENDER
       "\u04A3",
      // U+0493: "ғ" CYRILLIC SMALL LETTER GHE WITH STROKE
       "\u0493",
      // U+04E9: "ө" CYRILLIC SMALL LETTER BARRED O
       "\u04E9",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null,

      // U+04BB: "һ" CYRILLIC SMALL LETTER SHHA
       "\u04BB",
      // U+049B: "қ" CYRILLIC SMALL LETTER KA WITH DESCENDER
       "\u049B",
      // U+04D9: "ә" CYRILLIC SMALL LETTER SCHWA
       "\u04D9",
  };


  private static final String[] TEXTS_km_KH = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+1780: "ក" KHMER LETTER KA
      // U+1781: "ខ" KHMER LETTER KHA
      // U+1782: "គ" KHMER LETTER KO
       "\u1780\u1781\u1782",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null,

      // U+17DB: "៛" KHMER CURRENCY SYMBOL RIEL
       "\u17DB,\u00A2,\u00A3,\u20AC,\u00A5,\u20B1",
  };


  private static final String[] TEXTS_kn_IN = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0C85: "ಅ" KANNADA LETTER A
      // U+0C86: "ಆ" KANNADA LETTER AA
      // U+0C87: "ಇ" KANNADA LETTER I
       "\u0C85\u0C86\u0C87",

      null, null, null, null, null, null,

      // U+20B9: "₹" INDIAN RUPEE SIGN
       "\u20B9",
  };


  private static final String[] TEXTS_ky = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0410: "А" CYRILLIC CAPITAL LETTER A
      // U+0411: "Б" CYRILLIC CAPITAL LETTER BE
      // U+0412: "В" CYRILLIC CAPITAL LETTER VE
       "\u0410\u0411\u0412",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null,

      // U+0451: "ё" CYRILLIC SMALL LETTER IO
       "\u0451",

      null, null, null, null,

      // U+0449: "щ" CYRILLIC SMALL LETTER SHCHA
       "\u0449",
      // U+044B: "ы" CYRILLIC SMALL LETTER YERU
       "\u044B",
      // U+044D: "э" CYRILLIC SMALL LETTER E
       "\u044D",
      // U+0438: "и" CYRILLIC SMALL LETTER I
       "\u0438",
      // U+044A: "ъ" CYRILLIC SMALL LETTER HARD SIGN
       "\u044A",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null,

      // U+04AF: "ү" CYRILLIC SMALL LETTER STRAIGHT U
       "\u04AF",
      // U+04A3: "ң" CYRILLIC SMALL LETTER EN WITH DESCENDER
       "\u04A3",
       null,
      // U+04E9: "ө" CYRILLIC SMALL LETTER BARRED O
       "\u04E9",
  };


  private static final String[] TEXTS_lo_LA = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0E81: "ກ" LAO LETTER KO
      // U+0E82: "ຂ" LAO LETTER KHO SUNG
      // U+0E84: "ຄ" LAO LETTER KHO TAM
       "\u0E81\u0E82\u0E84",

      null, null, null, null, null, null,

      // U+20AD: "₭" KIP SIGN
       "\u20AD",
  };


  private static final String[] TEXTS_lt = {
      // U+0105: "ą" LATIN SMALL LETTER A WITH OGONEK
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+00E6: "æ" LATIN SMALL LETTER AE
       "\u0105,\u00E4,\u0101,\u00E0,\u00E1,\u00E2,\u00E3,\u00E5,\u00E6",
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+0151: "ő" LATIN SMALL LETTER O WITH DOUBLE ACUTE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
       "\u00F6,\u00F5,\u00F2,\u00F3,\u00F4,\u0153,\u0151,\u00F8",
      // U+0117: "ė" LATIN SMALL LETTER E WITH DOT ABOVE
      // U+0119: "ę" LATIN SMALL LETTER E WITH OGONEK
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+011B: "ě" LATIN SMALL LETTER E WITH CARON
       "\u0117,\u0119,\u0113,\u00E8,\u00E9,\u00EA,\u00EB,\u011B",
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
      // U+0173: "ų" LATIN SMALL LETTER U WITH OGONEK
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+016F: "ů" LATIN SMALL LETTER U WITH RING ABOVE
      // U+0171: "ű" LATIN SMALL LETTER U WITH DOUBLE ACUTE
       "\u016B,\u0173,\u00FC,\u016B,\u00F9,\u00FA,\u00FB,\u016F,\u0171",
       null,
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+0131: "ı" LATIN SMALL LETTER DOTLESS I
       "\u012F,\u012B,\u00EC,\u00ED,\u00EE,\u00EF,\u0131",
      // U+0146: "ņ" LATIN SMALL LETTER N WITH CEDILLA
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
      // U+0144: "ń" LATIN SMALL LETTER N WITH ACUTE
       "\u0146,\u00F1,\u0144",
      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
       "\u010D,\u00E7,\u0107",
       "!text/double_9qm_lqm",
      // U+0161: "š" LATIN SMALL LETTER S WITH CARON
      // U+00DF: "ß" LATIN SMALL LETTER SHARP S
      // U+015B: "ś" LATIN SMALL LETTER S WITH ACUTE
      // U+015F: "ş" LATIN SMALL LETTER S WITH CEDILLA
       "\u0161,\u00DF,\u015B,\u015F",
       "!text/single_9qm_lqm",
       null,
      // U+00FD: "ý" LATIN SMALL LETTER Y WITH ACUTE
      // U+00FF: "ÿ" LATIN SMALL LETTER Y WITH DIAERESIS
       "\u00FD,\u00FF",
      // U+017E: "ž" LATIN SMALL LETTER Z WITH CARON
      // U+017C: "ż" LATIN SMALL LETTER Z WITH DOT ABOVE
      // U+017A: "ź" LATIN SMALL LETTER Z WITH ACUTE
       "\u017E,\u017C,\u017A",
      // U+010F: "ď" LATIN SMALL LETTER D WITH CARON
       "\u010F",
      // U+0163: "ţ" LATIN SMALL LETTER T WITH CEDILLA
      // U+0165: "ť" LATIN SMALL LETTER T WITH CARON
       "\u0163,\u0165",
      // U+013C: "ļ" LATIN SMALL LETTER L WITH CEDILLA
      // U+0142: "ł" LATIN SMALL LETTER L WITH STROKE
      // U+013A: "ĺ" LATIN SMALL LETTER L WITH ACUTE
      // U+013E: "ľ" LATIN SMALL LETTER L WITH CARON
       "\u013C,\u0142,\u013A,\u013E",
      // U+0123: "ģ" LATIN SMALL LETTER G WITH CEDILLA
      // U+011F: "ğ" LATIN SMALL LETTER G WITH BREVE
       "\u0123,\u011F",
       null,
       null,
      // U+0157: "ŗ" LATIN SMALL LETTER R WITH CEDILLA
      // U+0159: "ř" LATIN SMALL LETTER R WITH CARON
      // U+0155: "ŕ" LATIN SMALL LETTER R WITH ACUTE
       "\u0157,\u0159,\u0155",
      // U+0137: "ķ" LATIN SMALL LETTER K WITH CEDILLA
       "\u0137",
  };


  private static final String[] TEXTS_lv = {
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+0105: "ą" LATIN SMALL LETTER A WITH OGONEK
       "\u0101,\u00E0,\u00E1,\u00E2,\u00E3,\u00E4,\u00E5,\u00E6,\u0105",
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+0151: "ő" LATIN SMALL LETTER O WITH DOUBLE ACUTE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
       "\u00F2,\u00F3,\u00F4,\u00F5,\u00F6,\u0153,\u0151,\u00F8",
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
      // U+0117: "ė" LATIN SMALL LETTER E WITH DOT ABOVE
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+0119: "ę" LATIN SMALL LETTER E WITH OGONEK
      // U+011B: "ě" LATIN SMALL LETTER E WITH CARON
       "\u0113,\u0117,\u00E8,\u00E9,\u00EA,\u00EB,\u0119,\u011B",
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
      // U+0173: "ų" LATIN SMALL LETTER U WITH OGONEK
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+016F: "ů" LATIN SMALL LETTER U WITH RING ABOVE
      // U+0171: "ű" LATIN SMALL LETTER U WITH DOUBLE ACUTE
       "\u016B,\u0173,\u00F9,\u00FA,\u00FB,\u00FC,\u016F,\u0171",
       null,
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+0131: "ı" LATIN SMALL LETTER DOTLESS I
       "\u012B,\u012F,\u00EC,\u00ED,\u00EE,\u00EF,\u0131",
      // U+0146: "ņ" LATIN SMALL LETTER N WITH CEDILLA
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
      // U+0144: "ń" LATIN SMALL LETTER N WITH ACUTE
       "\u0146,\u00F1,\u0144",
      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
       "\u010D,\u00E7,\u0107",
       "!text/double_9qm_lqm",
      // U+0161: "š" LATIN SMALL LETTER S WITH CARON
      // U+00DF: "ß" LATIN SMALL LETTER SHARP S
      // U+015B: "ś" LATIN SMALL LETTER S WITH ACUTE
      // U+015F: "ş" LATIN SMALL LETTER S WITH CEDILLA
       "\u0161,\u00DF,\u015B,\u015F",
       "!text/single_9qm_lqm",
       null,
      // U+00FD: "ý" LATIN SMALL LETTER Y WITH ACUTE
      // U+00FF: "ÿ" LATIN SMALL LETTER Y WITH DIAERESIS
       "\u00FD,\u00FF",
      // U+017E: "ž" LATIN SMALL LETTER Z WITH CARON
      // U+017C: "ż" LATIN SMALL LETTER Z WITH DOT ABOVE
      // U+017A: "ź" LATIN SMALL LETTER Z WITH ACUTE
       "\u017E,\u017C,\u017A",
      // U+010F: "ď" LATIN SMALL LETTER D WITH CARON
       "\u010F",
      // U+0163: "ţ" LATIN SMALL LETTER T WITH CEDILLA
      // U+0165: "ť" LATIN SMALL LETTER T WITH CARON
       "\u0163,\u0165",
      // U+013C: "ļ" LATIN SMALL LETTER L WITH CEDILLA
      // U+0142: "ł" LATIN SMALL LETTER L WITH STROKE
      // U+013A: "ĺ" LATIN SMALL LETTER L WITH ACUTE
      // U+013E: "ľ" LATIN SMALL LETTER L WITH CARON
       "\u013C,\u0142,\u013A,\u013E",
      // U+0123: "ģ" LATIN SMALL LETTER G WITH CEDILLA
      // U+011F: "ğ" LATIN SMALL LETTER G WITH BREVE
       "\u0123,\u011F",
       null,
       null,
      // U+0157: "ŗ" LATIN SMALL LETTER R WITH CEDILLA
      // U+0159: "ř" LATIN SMALL LETTER R WITH CARON
      // U+0155: "ŕ" LATIN SMALL LETTER R WITH ACUTE
       "\u0157,\u0159,\u0155",
      // U+0137: "ķ" LATIN SMALL LETTER K WITH CEDILLA
       "\u0137",
  };


  private static final String[] TEXTS_mk = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0410: "А" CYRILLIC CAPITAL LETTER A
      // U+0411: "Б" CYRILLIC CAPITAL LETTER BE
      // U+0412: "В" CYRILLIC CAPITAL LETTER VE
       "\u0410\u0411\u0412",

      null, null, null,

       "!text/double_9qm_lqm",
       null,
       "!text/single_9qm_lqm",

      null, null, null, null, null, null, null, null, null, null, null,

      // U+0450: "ѐ" CYRILLIC SMALL LETTER IE WITH GRAVE
       "\u0450",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null,

      // U+045D: "ѝ" CYRILLIC SMALL LETTER I WITH GRAVE
       "\u045D",
      // U+0455: "ѕ" CYRILLIC SMALL LETTER DZE
       "\u0455",
      // U+045C: "ќ" CYRILLIC SMALL LETTER KJE
       "\u045C",
      // U+0453: "ѓ" CYRILLIC SMALL LETTER GJE
       "\u0453",
  };


  private static final String[] TEXTS_ml_IN = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0D05: "അ" MALAYALAM LETTER A
       "\u0D05",

      null, null, null, null, null, null,

      // U+20B9: "₹" INDIAN RUPEE SIGN
       "\u20B9",
  };


  private static final String[] TEXTS_mn_MN = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0410: "А" CYRILLIC CAPITAL LETTER A
      // U+0411: "Б" CYRILLIC CAPITAL LETTER BE
      // U+0412: "В" CYRILLIC CAPITAL LETTER VE
       "\u0410\u0411\u0412",

      null, null, null, null, null, null,

      // U+20AE: "₮" TUGRIK SIGN
       "\u20AE",
  };


  private static final String[] TEXTS_mr_IN = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0915: "क" DEVANAGARI LETTER KA
      // U+0916: "ख" DEVANAGARI LETTER KHA
      // U+0917: "ग" DEVANAGARI LETTER GA
       "\u0915\u0916\u0917",

      null, null, null, null, null, null,

      // U+20B9: "₹" INDIAN RUPEE SIGN
       "\u20B9",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null,

      // U+0967: "१" DEVANAGARI DIGIT ONE
       "\u0967",
      // U+0968: "२" DEVANAGARI DIGIT TWO
       "\u0968",
      // U+0969: "३" DEVANAGARI DIGIT THREE
       "\u0969",
      // U+096A: "४" DEVANAGARI DIGIT FOUR
       "\u096A",
      // U+096B: "५" DEVANAGARI DIGIT FIVE
       "\u096B",
      // U+096C: "६" DEVANAGARI DIGIT SIX
       "\u096C",
      // U+096D: "७" DEVANAGARI DIGIT SEVEN
       "\u096D",
      // U+096E: "८" DEVANAGARI DIGIT EIGHT
       "\u096E",
      // U+096F: "९" DEVANAGARI DIGIT NINE
       "\u096F",
      // U+0966: "०" DEVANAGARI DIGIT ZERO
       "\u0966",
      // Label for "switch to symbols" key.
       "?\u0967\u0968\u0969",
       "1",
       "2",
       "3",
       "4",
       "5",
       "6",
       "7",
       "8",
       "9",
       "0",
  };


  private static final String[] TEXTS_nb = {
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
       "\u00E5,\u00E6,\u00E4,\u00E0,\u00E1,\u00E2,\u00E3,\u0101",
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
       "\u00F8,\u00F6,\u00F4,\u00F2,\u00F3,\u00F5,\u0153,\u014D",
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+0119: "ę" LATIN SMALL LETTER E WITH OGONEK
      // U+0117: "ė" LATIN SMALL LETTER E WITH DOT ABOVE
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
       "\u00E9,\u00E8,\u00EA,\u00EB,\u0119,\u0117,\u0113",
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00FC,\u00FB,\u00F9,\u00FA,\u016B",

      null, null, null, null,

       "!text/double_9qm_rqm",
       null,
       "!text/single_9qm_rqm",

      null, null, null, null, null, null, null, null, null, null, null, null,

      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
       "\u00E5",
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
       "\u00F8",
      // U+00E6: "æ" LATIN SMALL LETTER AE
       "\u00E6",
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
       "\u00F6",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null,

      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
       "\u00E4",
  };


  private static final String[] TEXTS_ne_NP = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0915: "क" DEVANAGARI LETTER KA
      // U+0916: "ख" DEVANAGARI LETTER KHA
      // U+0917: "ग" DEVANAGARI LETTER GA
       "\u0915\u0916\u0917",

      null, null, null, null, null, null,

      // U+0930/U+0941/U+002E "रु." NEPALESE RUPEE SIGN
       "\u0930\u0941.",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null,

      // U+0967: "१" DEVANAGARI DIGIT ONE
       "\u0967",
      // U+0968: "२" DEVANAGARI DIGIT TWO
       "\u0968",
      // U+0969: "३" DEVANAGARI DIGIT THREE
       "\u0969",
      // U+096A: "४" DEVANAGARI DIGIT FOUR
       "\u096A",
      // U+096B: "५" DEVANAGARI DIGIT FIVE
       "\u096B",
      // U+096C: "६" DEVANAGARI DIGIT SIX
       "\u096C",
      // U+096D: "७" DEVANAGARI DIGIT SEVEN
       "\u096D",
      // U+096E: "८" DEVANAGARI DIGIT EIGHT
       "\u096E",
      // U+096F: "९" DEVANAGARI DIGIT NINE
       "\u096F",
      // U+0966: "०" DEVANAGARI DIGIT ZERO
       "\u0966",
      // Label for "switch to symbols" key.
       "?\u0967\u0968\u0969",
       "1",
       "2",
       "3",
       "4",
       "5",
       "6",
       "7",
       "8",
       "9",
       "0",
       "!autoColumnOrder!8,.,\\,,',#,),(,/,;,@,:,-,\",+,\\%,&",

      null, null, null,

      // U+0964: "।" DEVANAGARI DANDA
       "\u0964",
       "!autoColumnOrder!9,.,\\,,?,!,#,),(,/,;,',@,:,-,\",+,\\%,&",
       "\u0964",
  };


  private static final String[] TEXTS_nl = {
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
       "\u00E1,\u00E4,\u00E2,\u00E0,\u00E6,\u00E3,\u00E5,\u0101",
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
       "\u00F3,\u00F6,\u00F4,\u00F2,\u00F5,\u0153,\u00F8,\u014D",
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+0119: "ę" LATIN SMALL LETTER E WITH OGONEK
      // U+0117: "ė" LATIN SMALL LETTER E WITH DOT ABOVE
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
       "\u00E9,\u00EB,\u00EA,\u00E8,\u0119,\u0117,\u0113",
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00FA,\u00FC,\u00FB,\u00F9,\u016B",
       null,
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
      // U+0133: "ĳ" LATIN SMALL LIGATURE IJ
       "\u00ED,\u00EF,\u00EC,\u00EE,\u012F,\u012B,\u0133",
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
      // U+0144: "ń" LATIN SMALL LETTER N WITH ACUTE
       "\u00F1,\u0144",
       null,
       "!text/double_9qm_rqm",
       null,
       "!text/single_9qm_rqm",
       null,
      // U+0133: "ĳ" LATIN SMALL LIGATURE IJ
       "\u0133",
  };


  private static final String[] TEXTS_pl = {
      // U+0105: "ą" LATIN SMALL LETTER A WITH OGONEK
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
       "\u0105,\u00E1,\u00E0,\u00E2,\u00E4,\u00E6,\u00E3,\u00E5,\u0101",
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
       "\u00F3,\u00F6,\u00F4,\u00F2,\u00F5,\u0153,\u00F8,\u014D",
      // U+0119: "ę" LATIN SMALL LETTER E WITH OGONEK
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+0117: "ė" LATIN SMALL LETTER E WITH DOT ABOVE
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
       "\u0119,\u00E8,\u00E9,\u00EA,\u00EB,\u0117,\u0113",

      null, null, null,

      // U+0144: "ń" LATIN SMALL LETTER N WITH ACUTE
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
       "\u0144,\u00F1",
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
       "\u0107,\u00E7,\u010D",
       "!text/double_9qm_rqm",
      // U+015B: "ś" LATIN SMALL LETTER S WITH ACUTE
      // U+00DF: "ß" LATIN SMALL LETTER SHARP S
      // U+0161: "š" LATIN SMALL LETTER S WITH CARON
       "\u015B,\u00DF,\u0161",
       "!text/single_9qm_rqm",
       null,
       null,
      // U+017C: "ż" LATIN SMALL LETTER Z WITH DOT ABOVE
      // U+017A: "ź" LATIN SMALL LETTER Z WITH ACUTE
      // U+017E: "ž" LATIN SMALL LETTER Z WITH CARON
       "\u017C,\u017A,\u017E",
       null,
       null,
      // U+0142: "ł" LATIN SMALL LETTER L WITH STROKE
       "\u0142",
  };


  private static final String[] TEXTS_pt = {
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+00AA: "ª" FEMININE ORDINAL INDICATOR
       "\u00E1,\u00E3,\u00E0,\u00E2,\u00E4,\u00E5,\u00E6,\u00AA",
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
      // U+00BA: "º" MASCULINE ORDINAL INDICATOR
       "\u00F3,\u00F5,\u00F4,\u00F2,\u00F6,\u0153,\u00F8,\u014D,\u00BA",
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+0119: "ę" LATIN SMALL LETTER E WITH OGONEK
      // U+0117: "ė" LATIN SMALL LETTER E WITH DOT ABOVE
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
       "\u00E9,\u00EA,\u00E8,\u0119,\u0117,\u0113,\u00EB",
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00FA,\u00FC,\u00F9,\u00FB,\u016B",
       null,
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
       "\u00ED,\u00EE,\u00EC,\u00EF,\u012F,\u012B",
       null,
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
       "\u00E7,\u010D,\u0107",
  };


  private static final String[] TEXTS_rm = {
       null,
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
       "\u00F2,\u00F3,\u00F6,\u00F4,\u00F5,\u0153,\u00F8",
  };


  private static final String[] TEXTS_ro = {
      // U+0103: "ă" LATIN SMALL LETTER A WITH BREVE
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
       "\u0103,\u00E2,\u00E3,\u00E0,\u00E1,\u00E4,\u00E6,\u00E5,\u0101",

      null, null, null, null,

      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
       "\u00EE,\u00EF,\u00EC,\u00ED,\u012F,\u012B",
       null,
       null,
       "!text/double_9qm_rqm",
      // U+0219: "ș" LATIN SMALL LETTER S WITH COMMA BELOW
      // U+00DF: "ß" LATIN SMALL LETTER SHARP S
      // U+015B: "ś" LATIN SMALL LETTER S WITH ACUTE
      // U+0161: "š" LATIN SMALL LETTER S WITH CARON
       "\u0219,\u00DF,\u015B,\u0161",
       "!text/single_9qm_rqm",

      null, null, null, null,

      // U+021B: "ț" LATIN SMALL LETTER T WITH COMMA BELOW
       "\u021B",
  };


  private static final String[] TEXTS_ru = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0410: "А" CYRILLIC CAPITAL LETTER A
      // U+0411: "Б" CYRILLIC CAPITAL LETTER BE
      // U+0412: "В" CYRILLIC CAPITAL LETTER VE
       "\u0410\u0411\u0412",

      null, null, null,

       "!text/double_9qm_lqm",
       null,
       "!text/single_9qm_lqm",

      null, null, null, null, null, null, null, null, null, null, null,

      // U+0451: "ё" CYRILLIC SMALL LETTER IO
       "\u0451",

      null, null, null, null,

      // U+0449: "щ" CYRILLIC SMALL LETTER SHCHA
       "\u0449",
      // U+044B: "ы" CYRILLIC SMALL LETTER YERU
       "\u044B",
      // U+044D: "э" CYRILLIC SMALL LETTER E
       "\u044D",
      // U+0438: "и" CYRILLIC SMALL LETTER I
       "\u0438",
      // U+044A: "ъ" CYRILLIC SMALL LETTER HARD SIGN
       "\u044A",
  };


  private static final String[] TEXTS_si_LK = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0D85: "අ" SINHALA LETTER AYANNA
      // U+0D86: "ආ" SINHALA LETTER AAYANNA
       "\u0D85,\u0D86",

      null, null, null, null, null, null,

      // U+0DBB/U+0DD4: "රු" SINHALA LETTER RAYANNA/SINHALA VOWEL SIGN KETTI PAA-PILLA
       "\u0DBB\u0DD4",
  };


  private static final String[] TEXTS_sk = {
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+0105: "ą" LATIN SMALL LETTER A WITH OGONEK
       "\u00E1,\u00E4,\u0101,\u00E0,\u00E2,\u00E3,\u00E5,\u00E6,\u0105",
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+0151: "ő" LATIN SMALL LETTER O WITH DOUBLE ACUTE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
       "\u00F4,\u00F3,\u00F6,\u00F2,\u00F5,\u0153,\u0151,\u00F8",
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+011B: "ě" LATIN SMALL LETTER E WITH CARON
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
      // U+0117: "ė" LATIN SMALL LETTER E WITH DOT ABOVE
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+0119: "ę" LATIN SMALL LETTER E WITH OGONEK
       "\u00E9,\u011B,\u0113,\u0117,\u00E8,\u00EA,\u00EB,\u0119",
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+016F: "ů" LATIN SMALL LETTER U WITH RING ABOVE
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
      // U+0173: "ų" LATIN SMALL LETTER U WITH OGONEK
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+0171: "ű" LATIN SMALL LETTER U WITH DOUBLE ACUTE
       "\u00FA,\u016F,\u00FC,\u016B,\u0173,\u00F9,\u00FB,\u0171",
       null,
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+0131: "ı" LATIN SMALL LETTER DOTLESS I
       "\u00ED,\u012B,\u012F,\u00EC,\u00EE,\u00EF,\u0131",
      // U+0148: "ň" LATIN SMALL LETTER N WITH CARON
      // U+0146: "ņ" LATIN SMALL LETTER N WITH CEDILLA
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
      // U+0144: "ń" LATIN SMALL LETTER N WITH ACUTE
       "\u0148,\u0146,\u00F1,\u0144",
      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
       "\u010D,\u00E7,\u0107",
       "!text/double_9qm_lqm",
      // U+0161: "š" LATIN SMALL LETTER S WITH CARON
      // U+00DF: "ß" LATIN SMALL LETTER SHARP S
      // U+015B: "ś" LATIN SMALL LETTER S WITH ACUTE
      // U+015F: "ş" LATIN SMALL LETTER S WITH CEDILLA
       "\u0161,\u00DF,\u015B,\u015F",
       "!text/single_9qm_lqm",
       null,
      // U+00FD: "ý" LATIN SMALL LETTER Y WITH ACUTE
      // U+00FF: "ÿ" LATIN SMALL LETTER Y WITH DIAERESIS
       "\u00FD,\u00FF",
      // U+017E: "ž" LATIN SMALL LETTER Z WITH CARON
      // U+017C: "ż" LATIN SMALL LETTER Z WITH DOT ABOVE
      // U+017A: "ź" LATIN SMALL LETTER Z WITH ACUTE
       "\u017E,\u017C,\u017A",
      // U+010F: "ď" LATIN SMALL LETTER D WITH CARON
       "\u010F",
      // U+0165: "ť" LATIN SMALL LETTER T WITH CARON
      // U+0163: "ţ" LATIN SMALL LETTER T WITH CEDILLA
       "\u0165,\u0163",
      // U+013E: "ľ" LATIN SMALL LETTER L WITH CARON
      // U+013A: "ĺ" LATIN SMALL LETTER L WITH ACUTE
      // U+013C: "ļ" LATIN SMALL LETTER L WITH CEDILLA
      // U+0142: "ł" LATIN SMALL LETTER L WITH STROKE
       "\u013E,\u013A,\u013C,\u0142",
      // U+0123: "ģ" LATIN SMALL LETTER G WITH CEDILLA
      // U+011F: "ğ" LATIN SMALL LETTER G WITH BREVE
       "\u0123,\u011F",
       "!text/single_raqm_laqm",
       "!text/double_raqm_laqm",
      // U+0155: "ŕ" LATIN SMALL LETTER R WITH ACUTE
      // U+0159: "ř" LATIN SMALL LETTER R WITH CARON
      // U+0157: "ŗ" LATIN SMALL LETTER R WITH CEDILLA
       "\u0155,\u0159,\u0157",
      // U+0137: "ķ" LATIN SMALL LETTER K WITH CEDILLA
       "\u0137",
  };


  private static final String[] TEXTS_sl = {

      null, null, null, null, null, null, null,

      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
       "\u010D,\u0107",
       "!text/double_9qm_lqm",
      // U+0161: "š" LATIN SMALL LETTER S WITH CARON
       "\u0161",
       "!text/single_9qm_lqm",
       null,
       null,
      // U+017E: "ž" LATIN SMALL LETTER Z WITH CARON
       "\u017E",
      // U+0111: "đ" LATIN SMALL LETTER D WITH STROKE
       "\u0111",

      null, null, null,

       "!text/single_raqm_laqm",
       "!text/double_raqm_laqm",
  };


  private static final String[] TEXTS_sr = {

      null, null, null, null,

      // END: More keys definitions for Serbian (Cyrillic)
      // Label for "switch to alphabetic" key.
      // U+0410: "А" CYRILLIC CAPITAL LETTER A
      // U+0411: "Б" CYRILLIC CAPITAL LETTER BE
      // U+0412: "В" CYRILLIC CAPITAL LETTER VE
       "\u0410\u0411\u0412",

      null, null, null,

       "!text/double_9qm_lqm",
       null,
       "!text/single_9qm_lqm",

      null, null, null, null, null, null, null,

       "!text/single_raqm_laqm",
       "!text/double_raqm_laqm",
       null,
       null,
      // U+0450: "ѐ" CYRILLIC SMALL LETTER IE WITH GRAVE
       "\u0450",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null,

      // U+045D: "ѝ" CYRILLIC SMALL LETTER I WITH GRAVE
       "\u045D",
      // TODO: Move these to sr-Latn once we can handle IETF language tag with script name specified.
      // BEGIN: More keys definitions for Serbian (Latin)
      // U+0161: "š" LATIN SMALL LETTER S WITH CARON
      // U+00DF: "ß" LATIN SMALL LETTER SHARP S
      // U+015B: "ś" LATIN SMALL LETTER S WITH ACUTE
      // <string name="morekeys_s">&#x0161;,&#x00DF;,&#x015B;</string>
      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
      // <string name="morekeys_c">&#x010D;,&#x00E7;,&#x0107;</string>
      // U+010F: "ď" LATIN SMALL LETTER D WITH CARON
      // <string name="morekeys_d">&#x010F;</string>
      // U+017E: "ž" LATIN SMALL LETTER Z WITH CARON
      // U+017A: "ź" LATIN SMALL LETTER Z WITH ACUTE
      // U+017C: "ż" LATIN SMALL LETTER Z WITH DOT ABOVE
      // <string name="morekeys_z">&#x017E;,&#x017A;,&#x017C;</string>
      // END: More keys definitions for Serbian (Latin)
      // BEGIN: More keys definitions for Serbian (Cyrillic)
      // U+0437: "з" CYRILLIC SMALL LETTER ZE
       "\u0437",
      // U+045B: "ћ" CYRILLIC SMALL LETTER TSHE
       "\u045B",
      // U+0452: "ђ" CYRILLIC SMALL LETTER DJE
       "\u0452",
  };


  private static final String[] TEXTS_sr_ZZ = {
       null,
       null,
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
       "\u00E8",
       null,
       null,
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
       "\u00EC",
       null,
      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
       "\u010D,\u0107,%",
       null,
      // U+0161: "š" LATIN SMALL LETTER S WITH CARON
       "\u0161,%",

      null, null, null,

      // U+017E: "ž" LATIN SMALL LETTER Z WITH CARON
       "\u017E,%",
      // U+0111: "đ" LATIN SMALL LETTER D WITH STROKE
       "\u0111,%"
  };


  private static final String[] TEXTS_sv = {
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+0105: "ą" LATIN SMALL LETTER A WITH OGONEK
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
       "\u00E4,\u00E5,\u00E6,\u00E1,\u00E0,\u00E2,\u0105,\u00E3",
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
       "\u00F6,\u00F8,\u0153,\u00F3,\u00F2,\u00F4,\u00F5,\u014D",
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+0119: "ę" LATIN SMALL LETTER E WITH OGONEK
       "\u00E9,\u00E8,\u00EA,\u00EB,\u0119",
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00FC,\u00FA,\u00F9,\u00FB,\u016B",
       null,
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
       "\u00ED,\u00EC,\u00EE,\u00EF",
      // U+0144: "ń" LATIN SMALL LETTER N WITH ACUTE
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
      // U+0148: "ň" LATIN SMALL LETTER N WITH CARON
       "\u0144,\u00F1,\u0148",
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
       "\u00E7,\u0107,\u010D",
       null,
      // U+015B: "ś" LATIN SMALL LETTER S WITH ACUTE
      // U+0161: "š" LATIN SMALL LETTER S WITH CARON
      // U+015F: "ş" LATIN SMALL LETTER S WITH CEDILLA
      // U+00DF: "ß" LATIN SMALL LETTER SHARP S
       "\u015B,\u0161,\u015F,\u00DF",
       null,
       null,
      // U+00FD: "ý" LATIN SMALL LETTER Y WITH ACUTE
      // U+00FF: "ÿ" LATIN SMALL LETTER Y WITH DIAERESIS
       "\u00FD,\u00FF",
      // U+017A: "ź" LATIN SMALL LETTER Z WITH ACUTE
      // U+017E: "ž" LATIN SMALL LETTER Z WITH CARON
      // U+017C: "ż" LATIN SMALL LETTER Z WITH DOT ABOVE
       "\u017A,\u017E,\u017C",
      // U+00F0: "ð" LATIN SMALL LETTER ETH
      // U+010F: "ď" LATIN SMALL LETTER D WITH CARON
       "\u00F0,\u010F",
      // U+0165: "ť" LATIN SMALL LETTER T WITH CARON
      // U+00FE: "þ" LATIN SMALL LETTER THORN
       "\u0165,\u00FE",
      // U+0142: "ł" LATIN SMALL LETTER L WITH STROKE
       "\u0142",
       null,
       "!text/single_raqm_laqm",
       "!text/double_raqm_laqm",
      // U+0159: "ř" LATIN SMALL LETTER R WITH CARON
       "\u0159",
       null,
       null,
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
       "\u00E5",
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
       "\u00F6",
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
       "\u00E4",
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
       "\u00F8,\u0153",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null,

      // U+00E6: "æ" LATIN SMALL LETTER AE
       "\u00E6",
  };


  private static final String[] TEXTS_sw = {
      // This is the same as English except morekeys_g.
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
       "\u00E0,\u00E1,\u00E2,\u00E4,\u00E6,\u00E3,\u00E5,\u0101",
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
       "\u00F4,\u00F6,\u00F2,\u00F3,\u0153,\u00F8,\u014D,\u00F5",
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
       "\u00E8,\u00E9,\u00EA,\u00EB,\u0113",
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00FB,\u00FC,\u00F9,\u00FA,\u016B",
       null,
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
       "\u00EE,\u00EF,\u00ED,\u012B,\u00EC",
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
       "\u00F1",
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
       "\u00E7",
       null,
      // U+00DF: "ß" LATIN SMALL LETTER SHARP S
       "\u00DF",

      null, null, null, null, null, null, null,

       "g'",
  };


  private static final String[] TEXTS_ta_IN = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0BA4: "த" TAMIL LETTER TA
      // U+0BAE/U+0BBF: "மி" TAMIL LETTER MA/TAMIL VOWEL SIGN I
      // U+0BB4/U+0BCD: "ழ்" TAMIL LETTER LLLA/TAMIL SIGN VIRAMA
       "\u0BA4\u0BAE\u0BBF\u0BB4\u0BCD",

      null, null, null, null, null, null,

      // U+0BF9: "௹" TAMIL RUPEE SIGN
       "\u0BF9",
  };


  private static final String[] TEXTS_ta_LK = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0BA4: "த" TAMIL LETTER TA
      // U+0BAE/U+0BBF: "மி" TAMIL LETTER MA/TAMIL VOWEL SIGN I
      // U+0BB4/U+0BCD: "ழ்" TAMIL LETTER LLLA/TAMIL SIGN VIRAMA
       "\u0BA4\u0BAE\u0BBF\u0BB4\u0BCD",

      null, null, null, null, null, null,

      // U+0DBB/U+0DD4: "රු" SINHALA LETTER RAYANNA/SINHALA VOWEL SIGN KETTI PAA-PILLA
       "\u0DBB\u0DD4",
  };


  private static final String[] TEXTS_ta_SG = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0BA4: "த" TAMIL LETTER TA
      // U+0BAE/U+0BBF: "மி" TAMIL LETTER MA/TAMIL VOWEL SIGN I
      // U+0BB4/U+0BCD: "ழ்" TAMIL LETTER LLLA/TAMIL SIGN VIRAMA
       "\u0BA4\u0BAE\u0BBF\u0BB4\u0BCD",
  };


  private static final String[] TEXTS_te_IN = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0C05: "అ" TELUGU LETTER A
      // U+0C06: "ఆ" TELUGU LETTER AA
      // U+0C07: "ఇ" TELUGU LETTER I
       "\u0C05\u0C06\u0C07",

      null, null, null, null, null, null,

      // U+20B9: "₹" INDIAN RUPEE SIGN
       "\u20B9",
  };


  private static final String[] TEXTS_th = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0E01: "ก" THAI CHARACTER KO KAI
      // U+0E02: "ข" THAI CHARACTER KHO KHAI
      // U+0E04: "ค" THAI CHARACTER KHO KHWAI
       "\u0E01\u0E02\u0E04",

      null, null, null, null, null, null,

      // U+0E3F: "฿" THAI CURRENCY SYMBOL BAHT
       "\u0E3F",
  };


  private static final String[] TEXTS_tl = {
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+0105: "ą" LATIN SMALL LETTER A WITH OGONEK
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
      // U+00AA: "ª" FEMININE ORDINAL INDICATOR
       "\u00E1,\u00E0,\u00E4,\u00E2,\u00E3,\u00E5,\u0105,\u00E6,\u0101,\u00AA",
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
      // U+00BA: "º" MASCULINE ORDINAL INDICATOR
       "\u00F3,\u00F2,\u00F6,\u00F4,\u00F5,\u00F8,\u0153,\u014D,\u00BA",
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+0119: "ę" LATIN SMALL LETTER E WITH OGONEK
      // U+0117: "ė" LATIN SMALL LETTER E WITH DOT ABOVE
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
       "\u00E9,\u00E8,\u00EB,\u00EA,\u0119,\u0117,\u0113",
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00FA,\u00FC,\u00F9,\u00FB,\u016B",
       null,
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
       "\u00ED,\u00EF,\u00EC,\u00EE,\u012F,\u012B",
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
      // U+0144: "ń" LATIN SMALL LETTER N WITH ACUTE
       "\u00F1,\u0144",
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
       "\u00E7,\u0107,\u010D",
  };


  private static final String[] TEXTS_tr = {
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
       "\u00E2,\u00E4,\u00E1",
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
       "\u00F6,\u00F4,\u0153,\u00F2,\u00F3,\u00F5,\u00F8,\u014D",
      // U+0259: "ə" LATIN SMALL LETTER SCHWA
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
       "\u0259,\u00E9",
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00FC,\u00FB,\u00F9,\u00FA,\u016B",
       null,
      // U+0131: "ı" LATIN SMALL LETTER DOTLESS I
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
       "\u0131,\u00EE,\u00EF,\u00EC,\u00ED,\u012F,\u012B",
      // U+0148: "ň" LATIN SMALL LETTER N WITH CARON
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
       "\u0148,\u00F1",
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
       "\u00E7,\u0107,\u010D",
       null,
      // U+015F: "ş" LATIN SMALL LETTER S WITH CEDILLA
      // U+00DF: "ß" LATIN SMALL LETTER SHARP S
      // U+015B: "ś" LATIN SMALL LETTER S WITH ACUTE
      // U+0161: "š" LATIN SMALL LETTER S WITH CARON
       "\u015F,\u00DF,\u015B,\u0161",
       null,
       null,
      // U+00FD: "ý" LATIN SMALL LETTER Y WITH ACUTE
       "\u00FD",
      // U+017E: "ž" LATIN SMALL LETTER Z WITH CARON
       "\u017E",

      null, null, null,

      // U+011F: "ğ" LATIN SMALL LETTER G WITH BREVE
       "\u011F",
  };


  private static final String[] TEXTS_uk = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0410: "А" CYRILLIC CAPITAL LETTER A
      // U+0411: "Б" CYRILLIC CAPITAL LETTER BE
      // U+0412: "В" CYRILLIC CAPITAL LETTER VE
       "\u0410\u0411\u0412",

      null, null, null,

       "!text/double_9qm_lqm",
       null,
       "!text/single_9qm_lqm",
      // U+20B4: "₴" HRYVNIA SIGN
       "\u20B4",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,

      // U+0449: "щ" CYRILLIC SMALL LETTER SHCHA
       "\u0449",
      // U+0456: "і" CYRILLIC SMALL LETTER BYELORUSSIAN-UKRAINIAN I
       "\u0456",
      // U+0454: "є" CYRILLIC SMALL LETTER UKRAINIAN IE
       "\u0454",
      // U+0438: "и" CYRILLIC SMALL LETTER I
       "\u0438",
      // U+044A: "ъ" CYRILLIC SMALL LETTER HARD SIGN
       "\u044A",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null,

      // U+0457: "ї" CYRILLIC SMALL LETTER YI
       "\u0457",
       null,
       null,
      // U+0491: "ґ" CYRILLIC SMALL LETTER GHE WITH UPTURN
       "\u0491",
  };


  private static final String[] TEXTS_ur = {

      null, null, null, null,

      // Label for "switch to alphabetic" key.
      // U+0627: "ا" ARABIC LETTER ALEF
      // U+200C: ZERO WIDTH NON-JOINER
      // U+0628: "ب" ARABIC LETTER BEH
      // U+067E: "پ" ARABIC LETTER PEH
       "\u0627\u200C\u0628\u200C\u067E",

      null, null, null, null, null, null,

      // U+20A8: "₨" RUPEE SIGN
       "\u20A8",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null,

      // U+06F1: "۱" EXTENDED ARABIC-INDIC DIGIT ONE
       "\u06F1",
      // U+06F2: "۲" EXTENDED ARABIC-INDIC DIGIT TWO
       "\u06F2",
      // U+06F3: "۳" EXTENDED ARABIC-INDIC DIGIT THREE
       "\u06F3",
      // U+06F4: "۴" EXTENDED ARABIC-INDIC DIGIT FOUR
       "\u06F4",
      // U+06F5: "۵" EXTENDED ARABIC-INDIC DIGIT FIVE
       "\u06F5",
      // U+06F6: "۶" EXTENDED ARABIC-INDIC DIGIT SIX
       "\u06F6",
      // U+06F7: "۷" EXTENDED ARABIC-INDIC DIGIT SEVEN
       "\u06F7",
      // U+06F8: "۸" EXTENDED ARABIC-INDIC DIGIT EIGHT
       "\u06F8",
      // U+06F9: "۹" EXTENDED ARABIC-INDIC DIGIT NINE
       "\u06F9",
      // U+06F0: "۰" EXTENDED ARABIC-INDIC DIGIT ZERO
       "\u06F0",
      // Label for "switch to symbols" key.
      // U+061F: "؟" ARABIC QUESTION MARK
       "\u06F3\u06F2\u06F1\u061F",
       "1",
       "2",
       "3",
       "4",
       "5",
       "6",
       "7",
       "8",
       "9",
      // U+066B: "٫" ARABIC DECIMAL SEPARATOR
      // U+066C: "٬" ARABIC THOUSANDS SEPARATOR
       "0,\u066B,\u066C",
       "!text/morekeys_arabic_diacritics",
       null,
       null,
      // U+060C: "،" ARABIC COMMA
      // U+061B: "؛" ARABIC SEMICOLON
      // U+061F: "؟" ARABIC QUESTION MARK
      // U+00AB: "«" LEFT-POINTING DOUBLE ANGLE QUOTATION MARK
      // U+00BB: "»" RIGHT-POINTING DOUBLE ANGLE QUOTATION MARK
       "\u060C",
       "\u06D4",
       "!text/morekeys_arabic_diacritics",
       "\u06D4",
      null, null, null, null, null, null,

      // U+2605: "★" BLACK STAR
      // U+066D: "٭" ARABIC FIVE POINTED STAR
       "\u2605,\u066D",
       "(|)",
       ")|(",
       "[|]",
       "]|[",
       "{|}",
       "}|{",
       "<|>",
       ">|<",
       "\u2264|\u2265",
       "\u2265|\u2264",
       "\u00AB|\u00BB",
       "\u00BB|\u00AB",
       "\u2039|\u203A",
       "\u203A|\u2039",
      // U+060C: "،" ARABIC COMMA
       "\u060C",
       "!fixedColumnOrder!4,:,!,\u061F,\u061B,-,!text/keyspec_left_double_angle_quote,!text/keyspec_right_double_angle_quote",
      // U+064B: "ً" ARABIC FATHATAN
       "\u064B",
      // U+00BF: "¿" INVERTED QUESTION MARK
       "?,\u00BF",

      null, null, null, null, null, null, null, null, null, null, null, null, null,

      // U+266A: "♪" EIGHTH NOTE
       "\u266A",
      // The all letters need to be mirrored are found at
      // http://www.unicode.org/Public/6.1.0/ucd/BidiMirroring.txt
      // U+FD3E: "﴾" ORNATE LEFT PARENTHESIS
      // U+FD3F: "﴿" ORNATE RIGHT PARENTHESIS
       "!fixedColumnOrder!4,\uFD3E|\uFD3F,!text/keyspecs_left_parenthesis_more_keys",
       "!fixedColumnOrder!4,\uFD3F|\uFD3E,!text/keyspecs_right_parenthesis_more_keys",
      // U+061F: "؟" ARABIC QUESTION MARK
      // U+0021: "!" EXCLAMATION MARK
      // U+064F: "ُ" ARABIC DAMMA
      // U+0650: "ِ" ARABIC KASRA
      // U+064E: "َ" ARABIC FATHA
      // U+0652: "ْ" ARABIC SUKUN
      // U+0651: "ّ" ARABIC SHADDA
      // U+064C: "ٌ" ARABIC DAMMATAN
      // U+064D: "ٍ" ARABIC KASRATAN
      // U+064B: "ً" ARABIC FATHATAN
      // U+0658: "٘" ARABIC MARK NOON GHUNNA
      // U+0654: "ٔ" ARABIC HAMZA ABOVE
      // U+0657: "ٗ" ARABIC INVERTED DAMMA
      // U+0656: "ٖ" ARABIC SUBSCRIPT ALEF
      // U+0670: "ٰ" ARABIC LETTER SUPERSCRIPT ALEF
      // Note: The space character is needed as a preceding letter to draw Arabic diacritics characters correctly.
       "!fixedColumnOrder!5,\u061F|\u061F,\u0021|\u0021, \u064F|\u064F, \u0650|\u0650, \u064E|\u064E, \u0652|\u0652, \u0651|\u0651, \u064C|\u064C, \u064D|\u064D, \u064B|\u064B, \u0658|\u0658, \u0654|\u0654, \u0657|\u0657, \u0656|\u0656, \u0670|\u0670",
       "\u061F",
       "\u064B",
       "\u061F",
       "\u061B",
      // U+066A: "٪" ARABIC PERCENT SIGN
       "\u066A",
       ";",
      // U+2030: "‰" PER MILLE SIGN
       "\\%,\u2030",
      null, null, null, null, null, null, null, null, null,

      // U+2264: "≤" LESS-THAN OR EQUAL TO
      // U+2265: "≥" GREATER-THAN EQUAL TO
      // U+00AB: "«" LEFT-POINTING DOUBLE ANGLE QUOTATION MARK
      // U+00BB: "»" RIGHT-POINTING DOUBLE ANGLE QUOTATION MARK
      // U+2039: "‹" SINGLE LEFT-POINTING ANGLE QUOTATION MARK
      // U+203A: "›" SINGLE RIGHT-POINTING ANGLE QUOTATION MARK
       "!fixedColumnOrder!3,!text/keyspec_left_single_angle_quote,!text/keyspec_less_than_equal,!text/keyspec_less_than",
       "!fixedColumnOrder!3,!text/keyspec_right_single_angle_quote,!text/keyspec_greater_than_equal,!text/keyspec_greater_than",
  };


  private static final String[] TEXTS_uz_UZ = {
      // This is the same as Turkish
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
       "\u00E2,\u00E4,\u00E1",
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
       "\u00F6,\u00F4,\u0153,\u00F2,\u00F3,\u00F5,\u00F8,\u014D",
      // U+0259: "ə" LATIN SMALL LETTER SCHWA
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
       "\u0259,\u00E9",
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00FC,\u00FB,\u00F9,\u00FA,\u016B",
       null,
      // U+0131: "ı" LATIN SMALL LETTER DOTLESS I
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
       "\u0131,\u00EE,\u00EF,\u00EC,\u00ED,\u012F,\u012B",
      // U+0148: "ň" LATIN SMALL LETTER N WITH CARON
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
       "\u0148,\u00F1",
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
       "\u00E7,\u0107,\u010D",
       null,
      // U+015F: "ş" LATIN SMALL LETTER S WITH CEDILLA
      // U+00DF: "ß" LATIN SMALL LETTER SHARP S
      // U+015B: "ś" LATIN SMALL LETTER S WITH ACUTE
      // U+0161: "š" LATIN SMALL LETTER S WITH CARON
       "\u015F,\u00DF,\u015B,\u0161",
       null,
       null,
      // U+00FD: "ý" LATIN SMALL LETTER Y WITH ACUTE
       "\u00FD",
      // U+017E: "ž" LATIN SMALL LETTER Z WITH CARON
       "\u017E",

      null, null, null,

      // U+011F: "ğ" LATIN SMALL LETTER G WITH BREVE
       "\u011F",
  };


  private static final String[] TEXTS_vi = {
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+1EA3: "ả" LATIN SMALL LETTER A WITH HOOK ABOVE
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+1EA1: "ạ" LATIN SMALL LETTER A WITH DOT BELOW
      // U+0103: "ă" LATIN SMALL LETTER A WITH BREVE
      // U+1EB1: "ằ" LATIN SMALL LETTER A WITH BREVE AND GRAVE
      // U+1EAF: "ắ" LATIN SMALL LETTER A WITH BREVE AND ACUTE
      // U+1EB3: "ẳ" LATIN SMALL LETTER A WITH BREVE AND HOOK ABOVE
      // U+1EB5: "ẵ" LATIN SMALL LETTER A WITH BREVE AND TILDE
      // U+1EB7: "ặ" LATIN SMALL LETTER A WITH BREVE AND DOT BELOW
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+1EA7: "ầ" LATIN SMALL LETTER A WITH CIRCUMFLEX AND GRAVE
      // U+1EA5: "ấ" LATIN SMALL LETTER A WITH CIRCUMFLEX AND ACUTE
      // U+1EA9: "ẩ" LATIN SMALL LETTER A WITH CIRCUMFLEX AND HOOK ABOVE
      // U+1EAB: "ẫ" LATIN SMALL LETTER A WITH CIRCUMFLEX AND TILDE
      // U+1EAD: "ậ" LATIN SMALL LETTER A WITH CIRCUMFLEX AND DOT BELOW
       "\u00E0,\u00E1,\u1EA3,\u00E3,\u1EA1,\u0103,\u1EB1,\u1EAF,\u1EB3,\u1EB5,\u1EB7,\u00E2,\u1EA7,\u1EA5,\u1EA9,\u1EAB,\u1EAD",
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+1ECF: "ỏ" LATIN SMALL LETTER O WITH HOOK ABOVE
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+1ECD: "ọ" LATIN SMALL LETTER O WITH DOT BELOW
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+1ED3: "ồ" LATIN SMALL LETTER O WITH CIRCUMFLEX AND GRAVE
      // U+1ED1: "ố" LATIN SMALL LETTER O WITH CIRCUMFLEX AND ACUTE
      // U+1ED5: "ổ" LATIN SMALL LETTER O WITH CIRCUMFLEX AND HOOK ABOVE
      // U+1ED7: "ỗ" LATIN SMALL LETTER O WITH CIRCUMFLEX AND TILDE
      // U+1ED9: "ộ" LATIN SMALL LETTER O WITH CIRCUMFLEX AND DOT BELOW
      // U+01A1: "ơ" LATIN SMALL LETTER O WITH HORN
      // U+1EDD: "ờ" LATIN SMALL LETTER O WITH HORN AND GRAVE
      // U+1EDB: "ớ" LATIN SMALL LETTER O WITH HORN AND ACUTE
      // U+1EDF: "ở" LATIN SMALL LETTER O WITH HORN AND HOOK ABOVE
      // U+1EE1: "ỡ" LATIN SMALL LETTER O WITH HORN AND TILDE
      // U+1EE3: "ợ" LATIN SMALL LETTER O WITH HORN AND DOT BELOW
       "\u00F2,\u00F3,\u1ECF,\u00F5,\u1ECD,\u00F4,\u1ED3,\u1ED1,\u1ED5,\u1ED7,\u1ED9,\u01A1,\u1EDD,\u1EDB,\u1EDF,\u1EE1,\u1EE3",
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+1EBB: "ẻ" LATIN SMALL LETTER E WITH HOOK ABOVE
      // U+1EBD: "ẽ" LATIN SMALL LETTER E WITH TILDE
      // U+1EB9: "ẹ" LATIN SMALL LETTER E WITH DOT BELOW
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+1EC1: "ề" LATIN SMALL LETTER E WITH CIRCUMFLEX AND GRAVE
      // U+1EBF: "ế" LATIN SMALL LETTER E WITH CIRCUMFLEX AND ACUTE
      // U+1EC3: "ể" LATIN SMALL LETTER E WITH CIRCUMFLEX AND HOOK ABOVE
      // U+1EC5: "ễ" LATIN SMALL LETTER E WITH CIRCUMFLEX AND TILDE
      // U+1EC7: "ệ" LATIN SMALL LETTER E WITH CIRCUMFLEX AND DOT BELOW
       "\u00E8,\u00E9,\u1EBB,\u1EBD,\u1EB9,\u00EA,\u1EC1,\u1EBF,\u1EC3,\u1EC5,\u1EC7",
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+1EE7: "ủ" LATIN SMALL LETTER U WITH HOOK ABOVE
      // U+0169: "ũ" LATIN SMALL LETTER U WITH TILDE
      // U+1EE5: "ụ" LATIN SMALL LETTER U WITH DOT BELOW
      // U+01B0: "ư" LATIN SMALL LETTER U WITH HORN
      // U+1EEB: "ừ" LATIN SMALL LETTER U WITH HORN AND GRAVE
      // U+1EE9: "ứ" LATIN SMALL LETTER U WITH HORN AND ACUTE
      // U+1EED: "ử" LATIN SMALL LETTER U WITH HORN AND HOOK ABOVE
      // U+1EEF: "ữ" LATIN SMALL LETTER U WITH HORN AND TILDE
      // U+1EF1: "ự" LATIN SMALL LETTER U WITH HORN AND DOT BELOW
       "\u00F9,\u00FA,\u1EE7,\u0169,\u1EE5,\u01B0,\u1EEB,\u1EE9,\u1EED,\u1EEF,\u1EF1",
       null,
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+1EC9: "ỉ" LATIN SMALL LETTER I WITH HOOK ABOVE
      // U+0129: "ĩ" LATIN SMALL LETTER I WITH TILDE
      // U+1ECB: "ị" LATIN SMALL LETTER I WITH DOT BELOW
       "\u00EC,\u00ED,\u1EC9,\u0129,\u1ECB",

      null, null, null, null, null,

      // U+20AB: "₫" DONG SIGN
       "\u20AB",
      // U+1EF3: "ỳ" LATIN SMALL LETTER Y WITH GRAVE
      // U+00FD: "ý" LATIN SMALL LETTER Y WITH ACUTE
      // U+1EF7: "ỷ" LATIN SMALL LETTER Y WITH HOOK ABOVE
      // U+1EF9: "ỹ" LATIN SMALL LETTER Y WITH TILDE
      // U+1EF5: "ỵ" LATIN SMALL LETTER Y WITH DOT BELOW
       "\u1EF3,\u00FD,\u1EF7,\u1EF9,\u1EF5",
       null,
      // U+0111: "đ" LATIN SMALL LETTER D WITH STROKE
       "\u0111",
  };


  private static final String[] TEXTS_zu = {
      // This is the same as English
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
       "\u00E0,\u00E1,\u00E2,\u00E4,\u00E6,\u00E3,\u00E5,\u0101",
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
       "\u00F3,\u00F4,\u00F6,\u00F2,\u0153,\u00F8,\u014D,\u00F5",
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
       "\u00E9,\u00E8,\u00EA,\u00EB,\u0113",
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
       "\u00FA,\u00FB,\u00FC,\u00F9,\u016B",
       null,
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
       "\u00ED,\u00EE,\u00EF,\u012B,\u00EC",
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
       "\u00F1",
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
       "\u00E7",
       null,
      // U+00DF: "ß" LATIN SMALL LETTER SHARP S
       "\u00DF",
  };


  private static final String[] TEXTS_zz = {
      // U+00E0: "à" LATIN SMALL LETTER A WITH GRAVE
      // U+00E1: "á" LATIN SMALL LETTER A WITH ACUTE
      // U+00E2: "â" LATIN SMALL LETTER A WITH CIRCUMFLEX
      // U+00E3: "ã" LATIN SMALL LETTER A WITH TILDE
      // U+00E4: "ä" LATIN SMALL LETTER A WITH DIAERESIS
      // U+00E5: "å" LATIN SMALL LETTER A WITH RING ABOVE
      // U+00E6: "æ" LATIN SMALL LETTER AE
      // U+0101: "ā" LATIN SMALL LETTER A WITH MACRON
      // U+0103: "ă" LATIN SMALL LETTER A WITH BREVE
      // U+0105: "ą" LATIN SMALL LETTER A WITH OGONEK
      // U+00AA: "ª" FEMININE ORDINAL INDICATOR
       "\u00E0,\u00E1,\u00E2,\u00E3,\u00E4,\u00E5,\u00E6,\u0101,\u0103,\u0105,\u00AA",
      // U+00F2: "ò" LATIN SMALL LETTER O WITH GRAVE
      // U+00F3: "ó" LATIN SMALL LETTER O WITH ACUTE
      // U+00F4: "ô" LATIN SMALL LETTER O WITH CIRCUMFLEX
      // U+00F5: "õ" LATIN SMALL LETTER O WITH TILDE
      // U+00F6: "ö" LATIN SMALL LETTER O WITH DIAERESIS
      // U+00F8: "ø" LATIN SMALL LETTER O WITH STROKE
      // U+014D: "ō" LATIN SMALL LETTER O WITH MACRON
      // U+014F: "ŏ" LATIN SMALL LETTER O WITH BREVE
      // U+0151: "ő" LATIN SMALL LETTER O WITH DOUBLE ACUTE
      // U+0153: "œ" LATIN SMALL LIGATURE OE
      // U+00BA: "º" MASCULINE ORDINAL INDICATOR
       "\u00F2,\u00F3,\u00F4,\u00F5,\u00F6,\u00F8,\u014D,\u014F,\u0151,\u0153,\u00BA",
      // U+00E8: "è" LATIN SMALL LETTER E WITH GRAVE
      // U+00E9: "é" LATIN SMALL LETTER E WITH ACUTE
      // U+00EA: "ê" LATIN SMALL LETTER E WITH CIRCUMFLEX
      // U+00EB: "ë" LATIN SMALL LETTER E WITH DIAERESIS
      // U+0113: "ē" LATIN SMALL LETTER E WITH MACRON
      // U+0115: "ĕ" LATIN SMALL LETTER E WITH BREVE
      // U+0117: "ė" LATIN SMALL LETTER E WITH DOT ABOVE
      // U+0119: "ę" LATIN SMALL LETTER E WITH OGONEK
      // U+011B: "ě" LATIN SMALL LETTER E WITH CARON
       "\u00E8,\u00E9,\u00EA,\u00EB,\u0113,\u0115,\u0117,\u0119,\u011B",
      // U+00F9: "ù" LATIN SMALL LETTER U WITH GRAVE
      // U+00FA: "ú" LATIN SMALL LETTER U WITH ACUTE
      // U+00FB: "û" LATIN SMALL LETTER U WITH CIRCUMFLEX
      // U+00FC: "ü" LATIN SMALL LETTER U WITH DIAERESIS
      // U+0169: "ũ" LATIN SMALL LETTER U WITH TILDE
      // U+016B: "ū" LATIN SMALL LETTER U WITH MACRON
      // U+016D: "ŭ" LATIN SMALL LETTER U WITH BREVE
      // U+016F: "ů" LATIN SMALL LETTER U WITH RING ABOVE
      // U+0171: "ű" LATIN SMALL LETTER U WITH DOUBLE ACUTE
      // U+0173: "ų" LATIN SMALL LETTER U WITH OGONEK
       "\u00F9,\u00FA,\u00FB,\u00FC,\u0169,\u016B,\u016D,\u016F,\u0171,\u0173",
       null,
      // U+00EC: "ì" LATIN SMALL LETTER I WITH GRAVE
      // U+00ED: "í" LATIN SMALL LETTER I WITH ACUTE
      // U+00EE: "î" LATIN SMALL LETTER I WITH CIRCUMFLEX
      // U+00EF: "ï" LATIN SMALL LETTER I WITH DIAERESIS
      // U+0129: "ĩ" LATIN SMALL LETTER I WITH TILDE
      // U+012B: "ī" LATIN SMALL LETTER I WITH MACRON
      // U+012D: "ĭ" LATIN SMALL LETTER I WITH BREVE
      // U+012F: "į" LATIN SMALL LETTER I WITH OGONEK
      // U+0131: "ı" LATIN SMALL LETTER DOTLESS I
      // U+0133: "ĳ" LATIN SMALL LIGATURE IJ
       "\u00EC,\u00ED,\u00EE,\u00EF,\u0129,\u012B,\u012D,\u012F,\u0131,\u0133",
      // U+00F1: "ñ" LATIN SMALL LETTER N WITH TILDE
      // U+0144: "ń" LATIN SMALL LETTER N WITH ACUTE
      // U+0146: "ņ" LATIN SMALL LETTER N WITH CEDILLA
      // U+0148: "ň" LATIN SMALL LETTER N WITH CARON
      // U+0149: "ŉ" LATIN SMALL LETTER N PRECEDED BY APOSTROPHE
      // U+014B: "ŋ" LATIN SMALL LETTER ENG
       "\u00F1,\u0144,\u0146,\u0148,\u0149,\u014B",
      // U+00E7: "ç" LATIN SMALL LETTER C WITH CEDILLA
      // U+0107: "ć" LATIN SMALL LETTER C WITH ACUTE
      // U+0109: "ĉ" LATIN SMALL LETTER C WITH CIRCUMFLEX
      // U+010B: "ċ" LATIN SMALL LETTER C WITH DOT ABOVE
      // U+010D: "č" LATIN SMALL LETTER C WITH CARON
       "\u00E7,\u0107,\u0109,\u010B,\u010D",
       null,
      // U+00DF: "ß" LATIN SMALL LETTER SHARP S
      // U+015B: "ś" LATIN SMALL LETTER S WITH ACUTE
      // U+015D: "ŝ" LATIN SMALL LETTER S WITH CIRCUMFLEX
      // U+015F: "ş" LATIN SMALL LETTER S WITH CEDILLA
      // U+0161: "š" LATIN SMALL LETTER S WITH CARON
      // U+017F: "ſ" LATIN SMALL LETTER LONG S
       "\u00DF,\u015B,\u015D,\u015F,\u0161,\u017F",
       null,
       null,
      // U+00FD: "ý" LATIN SMALL LETTER Y WITH ACUTE
      // U+0177: "ŷ" LATIN SMALL LETTER Y WITH CIRCUMFLEX
      // U+00FF: "ÿ" LATIN SMALL LETTER Y WITH DIAERESIS
      // U+0133: "ĳ" LATIN SMALL LIGATURE IJ
       "\u00FD,\u0177,\u00FF,\u0133",
      // U+017A: "ź" LATIN SMALL LETTER Z WITH ACUTE
      // U+017C: "ż" LATIN SMALL LETTER Z WITH DOT ABOVE
      // U+017E: "ž" LATIN SMALL LETTER Z WITH CARON
       "\u017A,\u017C,\u017E",
      // U+010F: "ď" LATIN SMALL LETTER D WITH CARON
      // U+0111: "đ" LATIN SMALL LETTER D WITH STROKE
      // U+00F0: "ð" LATIN SMALL LETTER ETH
       "\u010F,\u0111,\u00F0",
      // U+00FE: "þ" LATIN SMALL LETTER THORN
      // U+0163: "ţ" LATIN SMALL LETTER T WITH CEDILLA
      // U+0165: "ť" LATIN SMALL LETTER T WITH CARON
      // U+0167: "ŧ" LATIN SMALL LETTER T WITH STROKE
       "\u00FE,\u0163,\u0165,\u0167",
      // U+013A: "ĺ" LATIN SMALL LETTER L WITH ACUTE
      // U+013C: "ļ" LATIN SMALL LETTER L WITH CEDILLA
      // U+013E: "ľ" LATIN SMALL LETTER L WITH CARON
      // U+0140: "ŀ" LATIN SMALL LETTER L WITH MIDDLE DOT
      // U+0142: "ł" LATIN SMALL LETTER L WITH STROKE
       "\u013A,\u013C,\u013E,\u0140,\u0142",
      // U+011D: "ĝ" LATIN SMALL LETTER G WITH CIRCUMFLEX
      // U+011F: "ğ" LATIN SMALL LETTER G WITH BREVE
      // U+0121: "ġ" LATIN SMALL LETTER G WITH DOT ABOVE
      // U+0123: "ģ" LATIN SMALL LETTER G WITH CEDILLA
       "\u011D,\u011F,\u0121,\u0123",
       null,
       null,
      // U+0155: "ŕ" LATIN SMALL LETTER R WITH ACUTE
      // U+0157: "ŗ" LATIN SMALL LETTER R WITH CEDILLA
      // U+0159: "ř" LATIN SMALL LETTER R WITH CARON
       "\u0155,\u0157,\u0159",
      // U+0137: "ķ" LATIN SMALL LETTER K WITH CEDILLA
      // U+0138: "ĸ" LATIN SMALL LETTER KRA
       "\u0137,\u0138",

      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null, null, null,

      // U+0125: "ĥ" LATIN SMALL LETTER H WITH CIRCUMFLEX
       "\u0125",
      // U+0175: "ŵ" LATIN SMALL LETTER W WITH CIRCUMFLEX
       "\u0175",

      null, null, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      null,

      // U+0135: "ĵ" LATIN SMALL LETTER J WITH CIRCUMFLEX
       "\u0135",
  };

  private static final Object[] LOCALES_AND_TEXTS = {
      // "locale", TEXT_ARRAY,
      "DEFAULT", TEXTS_DEFAULT,
      "af", TEXTS_af,
      "ar", TEXTS_ar,
      "az_AZ", TEXTS_az_AZ,
      "be_BY", TEXTS_be_BY,
      "bg", TEXTS_bg,
      "bn_BD", TEXTS_bn_BD,
      "bn_IN", TEXTS_bn_IN,
      "ca", TEXTS_ca,
      "cs", TEXTS_cs,
      "da", TEXTS_da,
      "de", TEXTS_de,
      "el", TEXTS_el,
      "en", TEXTS_en,
      "eo", TEXTS_eo,
      "es", TEXTS_es,
      "et_EE", TEXTS_et_EE,
      "eu_ES", TEXTS_eu_ES,
      "fa", TEXTS_fa,
      "fi", TEXTS_fi,
      "fr", TEXTS_fr,
      "gl_ES", TEXTS_gl_ES,
      "hi", TEXTS_hi,
      "hi_ZZ", TEXTS_hi_ZZ,
      "hr", TEXTS_hr,
      "hu", TEXTS_hu,
      "hy_AM", TEXTS_hy_AM,
      "is", TEXTS_is,
      "it", TEXTS_it,
      "iw", TEXTS_iw,
      "ka_GE", TEXTS_ka_GE,
      "kk", TEXTS_kk,
      "km_KH", TEXTS_km_KH,
      "kn_IN", TEXTS_kn_IN,
      "ky", TEXTS_ky,
      "lo_LA", TEXTS_lo_LA,
      "lt", TEXTS_lt,
      "lv", TEXTS_lv,
      "mk", TEXTS_mk,
      "ml_IN", TEXTS_ml_IN,
      "mn_MN", TEXTS_mn_MN,
      "mr_IN", TEXTS_mr_IN,
      "nb", TEXTS_nb,
      "ne_NP", TEXTS_ne_NP,
      "nl", TEXTS_nl,
      "pl", TEXTS_pl,
      "pt", TEXTS_pt,
      "rm", TEXTS_rm,
      "ro", TEXTS_ro,
      "ru", TEXTS_ru,
      "si_LK", TEXTS_si_LK,
      "sk", TEXTS_sk,
      "sl", TEXTS_sl,
      "sr", TEXTS_sr,
      "sr_ZZ", TEXTS_sr_ZZ,
      "sv", TEXTS_sv,
      "sw", TEXTS_sw,
      "ta_IN", TEXTS_ta_IN,
      "ta_LK", TEXTS_ta_LK,
      "ta_SG", TEXTS_ta_SG,
      "te_IN", TEXTS_te_IN,
      "th", TEXTS_th,
      "tl", TEXTS_tl,
      "tr", TEXTS_tr,
      "uk", TEXTS_uk,
      "ur", TEXTS_ur,
      "uz_UZ", TEXTS_uz_UZ,
      "vi", TEXTS_vi,
      "zu", TEXTS_zu,
      "zz", TEXTS_zz,
  };

  static {
    for (int index = 0; index < NAMES.length; index++) {
      sNameToIndexesMap.put(NAMES[index], index);
    }

    for (int i = 0; i < LOCALES_AND_TEXTS.length; i += 2) {
      final String locale = (String) LOCALES_AND_TEXTS[i];
      final String[] textsTable = (String[]) LOCALES_AND_TEXTS[i + 1];
      sLocaleToTextsTableMap.put(locale, textsTable);

      // Uncomment to log locales where text is in range
      //int textToCheck = 95;
      //if (textsTable.length > textToCheck)
      //    Log.i("LocaleIndexCheck", textToCheck + " is used in " + locale + " total length: " + textsTable.length);
    }
  }
}
