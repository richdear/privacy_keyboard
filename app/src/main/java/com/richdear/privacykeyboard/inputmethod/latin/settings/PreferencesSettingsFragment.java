

package com.richdear.privacykeyboard.inputmethod.latin.settings;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.SwitchPreference;

import com.richdear.privacykeyboard.R;
import com.richdear.privacykeyboard.inputmethod.keyboard.KeyboardLayoutSet;


public final class PreferencesSettingsFragment extends SubScreenFragment {
  @Override
  public void onCreate(final Bundle icicle) {
    super.onCreate(icicle);
    addPreferencesFromResource(R.xml.prefs_screen_preferences);

    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
      removePreference(Settings.PREF_ENABLE_IME_SWITCH);
    } else {
      updateImeSwitchEnabledPref();
    }
  }

  @Override
  public void onSharedPreferenceChanged(final SharedPreferences prefs, final String key) {
    if (key.equals(Settings.PREF_HIDE_SPECIAL_CHARS) ||
        key.equals(Settings.PREF_SHOW_NUMBER_ROW)) {
      KeyboardLayoutSet.onKeyboardThemeChanged();
    } else if (key.equals(Settings.PREF_HIDE_LANGUAGE_SWITCH_KEY)) {
      updateImeSwitchEnabledPref();
    }
  }

  
  private void updateImeSwitchEnabledPref() {
    final Preference enableImeSwitch = findPreference(Settings.PREF_ENABLE_IME_SWITCH);
    final Preference hideLanguageSwitchKey =
        findPreference(Settings.PREF_HIDE_LANGUAGE_SWITCH_KEY);
    if (enableImeSwitch == null || hideLanguageSwitchKey == null) {
      return;
    }
    final boolean hideLanguageSwitchKeyIsChecked;
    // depending on the version of Android, the preferences could be different types
    if (hideLanguageSwitchKey instanceof CheckBoxPreference) {
      hideLanguageSwitchKeyIsChecked =
          ((CheckBoxPreference) hideLanguageSwitchKey).isChecked();
    } else if (hideLanguageSwitchKey instanceof SwitchPreference) {
      hideLanguageSwitchKeyIsChecked =
          ((SwitchPreference) hideLanguageSwitchKey).isChecked();
    } else {
      // in case it can be something else, don't bother doing anything
      return;
    }
    enableImeSwitch.setEnabled(!hideLanguageSwitchKeyIsChecked);
  }
}
