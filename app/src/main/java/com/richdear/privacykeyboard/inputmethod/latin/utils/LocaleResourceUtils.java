

package com.richdear.privacykeyboard.inputmethod.latin.utils;

import android.content.Context;
import android.content.res.Resources;

import com.richdear.privacykeyboard.R;
import com.richdear.privacykeyboard.inputmethod.latin.common.LocaleUtils;
import com.richdear.privacykeyboard.inputmethod.latin.common.StringUtils;

import java.util.HashMap;
import java.util.Locale;


public final class LocaleResourceUtils {
  // This reference class {@link R} must be located in the same package as LatinIME.java.
  private static final String RESOURCE_PACKAGE_NAME = "com.richdear.privacykeyboard";

  private static volatile boolean sInitialized = false;
  private static final Object sInitializeLock = new Object();
  private static Resources sResources;
  // Exceptional locale whose name should be displayed in Locale.ROOT.
  private static final HashMap<String, Integer> sExceptionalLocaleDisplayedInRootLocale =
      new HashMap<>();
  // Exceptional locale to locale name resource id map.
  private static final HashMap<String, Integer> sExceptionalLocaleToNameIdsMap = new HashMap<>();
  private static final String LOCALE_NAME_RESOURCE_PREFIX =
      "string/locale_name_";
  private static final String LOCALE_NAME_RESOURCE_IN_ROOT_LOCALE_PREFIX =
      "string/locale_name_in_root_locale_";

  private LocaleResourceUtils() {
    // Intentional empty constructor for utility class.
  }

  // Note that this initialization method can be called multiple times.
  public static void init(final Context context) {
    synchronized (sInitializeLock) {
      if (sInitialized == false) {
        initLocked(context);
        sInitialized = true;
      }
    }
  }

  private static void initLocked(final Context context) {
    final Resources res = context.getResources();
    sResources = res;

    final String[] exceptionalLocaleInRootLocale = res.getStringArray(
        R.array.locale_displayed_in_root_locale);
    for (int i = 0; i < exceptionalLocaleInRootLocale.length; i++) {
      final String localeString = exceptionalLocaleInRootLocale[i];
      final String resourceName = LOCALE_NAME_RESOURCE_IN_ROOT_LOCALE_PREFIX + localeString;
      final int resId = res.getIdentifier(resourceName, null, RESOURCE_PACKAGE_NAME);
      sExceptionalLocaleDisplayedInRootLocale.put(localeString, resId);
    }

    final String[] exceptionalLocales = res.getStringArray(R.array.locale_exception_keys);
    for (int i = 0; i < exceptionalLocales.length; i++) {
      final String localeString = exceptionalLocales[i];
      final String resourceName = LOCALE_NAME_RESOURCE_PREFIX + localeString;
      final int resId = res.getIdentifier(resourceName, null, RESOURCE_PACKAGE_NAME);
      sExceptionalLocaleToNameIdsMap.put(localeString, resId);
    }
  }

  private static Locale getDisplayLocale(final String localeString) {
    if (sExceptionalLocaleDisplayedInRootLocale.containsKey(localeString)) {
      return Locale.ROOT;
    }
    return LocaleUtils.constructLocaleFromString(localeString);
  }

  
  public static String getLocaleDisplayNameInSystemLocale(
      final String localeString) {
    final Locale displayLocale = sResources.getConfiguration().locale;
    return getLocaleDisplayNameInternal(localeString, displayLocale);
  }

  
  public static String getLocaleDisplayNameInLocale(final String localeString) {
    final Locale displayLocale = getDisplayLocale(localeString);
    return getLocaleDisplayNameInternal(localeString, displayLocale);
  }

  
  public static String getLanguageDisplayNameInSystemLocale(
      final String localeString) {
    final Locale displayLocale = sResources.getConfiguration().locale;
    final String languageString;
    if (sExceptionalLocaleDisplayedInRootLocale.containsKey(localeString)) {
      languageString = localeString;
    } else {
      languageString = LocaleUtils.constructLocaleFromString(localeString).getLanguage();
    }
    return getLocaleDisplayNameInternal(languageString, displayLocale);
  }

  
  public static String getLanguageDisplayNameInLocale(final String localeString) {
    final Locale displayLocale = getDisplayLocale(localeString);
    final String languageString;
    if (sExceptionalLocaleDisplayedInRootLocale.containsKey(localeString)) {
      languageString = localeString;
    } else {
      languageString = LocaleUtils.constructLocaleFromString(localeString).getLanguage();
    }
    return getLocaleDisplayNameInternal(languageString, displayLocale);
  }

  private static String getLocaleDisplayNameInternal(final String localeString,
                                                     final Locale displayLocale) {
    final Integer exceptionalNameResId;
    if (displayLocale.equals(Locale.ROOT)
        && sExceptionalLocaleDisplayedInRootLocale.containsKey(localeString)) {
      exceptionalNameResId = sExceptionalLocaleDisplayedInRootLocale.get(localeString);
    } else if (sExceptionalLocaleToNameIdsMap.containsKey(localeString)) {
      exceptionalNameResId = sExceptionalLocaleToNameIdsMap.get(localeString);
    } else {
      exceptionalNameResId = null;
    }

    final String displayName;
    if (exceptionalNameResId != null) {
      displayName = String.valueOf(sResources.getString(exceptionalNameResId));
    } else {
      displayName = LocaleUtils.constructLocaleFromString(localeString)
          .getDisplayName(displayLocale);
    }
    return StringUtils.capitalizeFirstCodePoint(displayName, displayLocale);
  }
}
