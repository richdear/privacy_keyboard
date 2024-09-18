

package com.richdear.privacykeyboard.inputmethod.latin.utils;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.richdear.privacykeyboard.inputmethod.latin.Subtype;

import java.util.ArrayList;
import java.util.List;

public final class SubtypePreferenceUtils {
  private static final String TAG = SubtypePreferenceUtils.class.getSimpleName();

  private SubtypePreferenceUtils() {
    // This utility class is not publicly instantiable.
  }

  private static final String LOCALE_AND_LAYOUT_SEPARATOR = ":";
  private static final int INDEX_OF_LOCALE = 0;
  private static final int INDEX_OF_KEYBOARD_LAYOUT = 1;
  private static final int PREF_ELEMENTS_LENGTH = (INDEX_OF_KEYBOARD_LAYOUT + 1);
  private static final String PREF_SUBTYPE_SEPARATOR = ";";

  private static String getPrefString(final Subtype subtype) {
    final String localeString = subtype.getLocale();
    final String keyboardLayoutSetName = subtype.getKeyboardLayoutSet();
    return localeString + LOCALE_AND_LAYOUT_SEPARATOR + keyboardLayoutSetName;
  }

  public static List<Subtype> createSubtypesFromPref(final String prefSubtypes,
                                                     final Resources resources) {
    if (TextUtils.isEmpty(prefSubtypes)) {
      return new ArrayList<>();
    }
    final String[] prefSubtypeArray = prefSubtypes.split(PREF_SUBTYPE_SEPARATOR);
    final ArrayList<Subtype> subtypesList = new ArrayList<>(prefSubtypeArray.length);
    for (final String prefSubtype : prefSubtypeArray) {
      final String[] elements = prefSubtype.split(LOCALE_AND_LAYOUT_SEPARATOR);
      if (elements.length != PREF_ELEMENTS_LENGTH) {
        Log.w(TAG, "Unknown subtype specified: " + prefSubtype + " in "
            + prefSubtypes);
        continue;
      }
      final String localeString = elements[INDEX_OF_LOCALE];
      final String keyboardLayoutSetName = elements[INDEX_OF_KEYBOARD_LAYOUT];
      final Subtype subtype =
          SubtypeLocaleUtils.getSubtype(localeString, keyboardLayoutSetName, resources);
      if (subtype == null) {
        continue;
      }
      subtypesList.add(subtype);
    }
    return subtypesList;
  }

  public static String createPrefSubtypes(final List<Subtype> subtypes) {
    if (subtypes == null || subtypes.size() == 0) {
      return "";
    }
    final StringBuilder sb = new StringBuilder();
    for (final Subtype subtype : subtypes) {
      if (sb.length() > 0) {
        sb.append(PREF_SUBTYPE_SEPARATOR);
      }
      sb.append(getPrefString(subtype));
    }
    return sb.toString();
  }
}
