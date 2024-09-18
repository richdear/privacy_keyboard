

package com.richdear.privacykeyboard.inputmethod.latin.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import com.richdear.privacykeyboard.R;
import com.richdear.privacykeyboard.inputmethod.keyboard.KeyboardTheme;


public final class AppearanceSettingsFragment extends SubScreenFragment {
  @Override
  public void onCreate(final Bundle icicle) {
    super.onCreate(icicle);
    addPreferencesFromResource(R.xml.prefs_screen_appearance);

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
      removePreference(Settings.PREF_MATCHING_NAVBAR_COLOR);
    }

    setupKeyboardHeightSettings();
    setupKeyboardColorSettings();
  }

  @Override
  public void onResume() {
    super.onResume();
    refreshSettings();
  }

  @Override
  public void onSharedPreferenceChanged(final SharedPreferences prefs, final String key) {
    refreshSettings();
  }

  private void refreshSettings() {
    ThemeSettingsFragment.updateKeyboardThemeSummary(findPreference(Settings.SCREEN_THEME));

    final SharedPreferences prefs = getSharedPreferences();
    final KeyboardTheme theme = KeyboardTheme.getKeyboardTheme(prefs);
    setPreferenceEnabled(Settings.PREF_KEYBOARD_COLOR, false);
  }

  private void setupKeyboardHeightSettings() {
    final SeekBarDialogPreference pref = (SeekBarDialogPreference) findPreference(
        Settings.PREF_KEYBOARD_HEIGHT);
    if (pref == null) {
      return;
    }
    final SharedPreferences prefs = getSharedPreferences();
    final Resources res = getResources();
    pref.setInterface(new SeekBarDialogPreference.ValueProxy() {
      private static final float PERCENTAGE_FLOAT = 100.0f;

      private float getValueFromPercentage(final int percentage) {
        return percentage / PERCENTAGE_FLOAT;
      }

      private int getPercentageFromValue(final float floatValue) {
        return Math.round(floatValue * PERCENTAGE_FLOAT);
      }

      @Override
      public void writeValue(final int value, final String key) {
        prefs.edit().putFloat(key, getValueFromPercentage(value)).apply();
      }

      @Override
      public void writeDefaultValue(final String key) {
        prefs.edit().remove(key).apply();
      }

      @Override
      public int readValue(final String key) {
        return getPercentageFromValue(Settings.readKeyboardHeight(prefs, 1));
      }

      @Override
      public int readDefaultValue(final String key) {
        return getPercentageFromValue(1);
      }

      @Override
      public String getValueText(final int value) {
        if (value < 0) {
          return res.getString(R.string.settings_system_default);
        }
        return res.getString(R.string.abbreviation_unit_percent, value);
      }

      @Override
      public void feedbackValue(final int value) {
      }
    });
  }

  private void setupKeyboardColorSettings() {
    final ColorDialogPreference pref = (ColorDialogPreference) findPreference(
        Settings.PREF_KEYBOARD_COLOR);
    if (pref == null) {
      return;
    }
    final SharedPreferences prefs = getSharedPreferences();
    final Context context = this.getActivity().getApplicationContext();
    pref.setInterface(new ColorDialogPreference.ValueProxy() {
      @Override
      public void writeValue(final int value, final String key) {
        prefs.edit().putInt(key, value).apply();
      }

      @Override
      public int readValue(final String key) {
        return Settings.readKeyboardColor(prefs, context);
      }

      @Override
      public void writeDefaultValue(final String key) {
        Settings.removeKeyboardColor(prefs);
      }
    });
  }
}
