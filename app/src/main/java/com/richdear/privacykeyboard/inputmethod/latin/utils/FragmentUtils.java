

package com.richdear.privacykeyboard.inputmethod.latin.utils;

import com.richdear.privacykeyboard.inputmethod.latin.settings.AppearanceSettingsFragment;
import com.richdear.privacykeyboard.inputmethod.latin.settings.KeyPressSettingsFragment;
import com.richdear.privacykeyboard.inputmethod.latin.settings.LanguagesSettingsFragment;
import com.richdear.privacykeyboard.inputmethod.latin.settings.PreferencesSettingsFragment;
import com.richdear.privacykeyboard.inputmethod.latin.settings.SettingsFragment;
import com.richdear.privacykeyboard.inputmethod.latin.settings.SingleLanguageSettingsFragment;
import com.richdear.privacykeyboard.inputmethod.latin.settings.ThemeSettingsFragment;

import java.util.HashSet;

public class FragmentUtils {
  private static final HashSet<String> sLatinImeFragments = new HashSet<>();

  static {
    sLatinImeFragments.add(PreferencesSettingsFragment.class.getName());
    sLatinImeFragments.add(KeyPressSettingsFragment.class.getName());
    sLatinImeFragments.add(AppearanceSettingsFragment.class.getName());
    sLatinImeFragments.add(ThemeSettingsFragment.class.getName());
    sLatinImeFragments.add(SettingsFragment.class.getName());
    sLatinImeFragments.add(LanguagesSettingsFragment.class.getName());
    sLatinImeFragments.add(SingleLanguageSettingsFragment.class.getName());
  }

  public static boolean isValidFragment(String fragmentName) {
    return sLatinImeFragments.contains(fragmentName);
  }
}
