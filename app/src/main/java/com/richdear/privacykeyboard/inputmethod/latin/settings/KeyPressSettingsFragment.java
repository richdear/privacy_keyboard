

package com.richdear.privacykeyboard.inputmethod.latin.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Bundle;

import com.richdear.privacykeyboard.R;
import com.richdear.privacykeyboard.inputmethod.latin.AudioAndHapticFeedbackManager;


public final class KeyPressSettingsFragment extends SubScreenFragment {
  @Override
  public void onCreate(final Bundle icicle) {
    super.onCreate(icicle);
    addPreferencesFromResource(R.xml.prefs_screen_key_press);

    final Context context = getActivity();

    // When we are called from the Settings application but we are not already running, some
    // singleton and utility classes may not have been initialized.  We have to call
    // initialization method of these classes here. See {@link LatinIME#onCreate()}.
    AudioAndHapticFeedbackManager.init(context);

    if (!AudioAndHapticFeedbackManager.getInstance().hasVibrator()) {
      removePreference(Settings.PREF_VIBRATE_ON);
      removePreference(Settings.PREF_VIBRATION_DURATION_SETTINGS);
    }

    setupKeypressVibrationDurationSettings();
    setupKeypressSoundVolumeSettings();
    setupKeyLongpressTimeoutSettings();
  }

  private void setupKeypressVibrationDurationSettings() {
    final SeekBarDialogPreference pref = (SeekBarDialogPreference) findPreference(
        Settings.PREF_VIBRATION_DURATION_SETTINGS);
    if (pref == null) {
      return;
    }
    final SharedPreferences prefs = getSharedPreferences();
    final Resources res = getResources();
    pref.setInterface(new SeekBarDialogPreference.ValueProxy() {
      @Override
      public void writeValue(final int value, final String key) {
        prefs.edit().putInt(key, value).apply();
      }

      @Override
      public void writeDefaultValue(final String key) {
        prefs.edit().remove(key).apply();
      }

      @Override
      public int readValue(final String key) {
        return Settings.readKeypressVibrationDuration(prefs, res);
      }

      @Override
      public int readDefaultValue(final String key) {
        return Settings.readDefaultKeypressVibrationDuration(res);
      }

      @Override
      public void feedbackValue(final int value) {
        AudioAndHapticFeedbackManager.getInstance().vibrate(value);
      }

      @Override
      public String getValueText(final int value) {
        if (value < 0) {
          return res.getString(R.string.settings_system_default);
        }
        return res.getString(R.string.abbreviation_unit_milliseconds, value);
      }
    });
  }

  private void setupKeypressSoundVolumeSettings() {
    final SeekBarDialogPreference pref = (SeekBarDialogPreference) findPreference(
        Settings.PREF_KEYPRESS_SOUND_VOLUME);
    if (pref == null) {
      return;
    }
    final SharedPreferences prefs = getSharedPreferences();
    final Resources res = getResources();
    final AudioManager am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
    pref.setInterface(new SeekBarDialogPreference.ValueProxy() {
      private static final float PERCENTAGE_FLOAT = 100.0f;

      private float getValueFromPercentage(final int percentage) {
        return percentage / PERCENTAGE_FLOAT;
      }

      private int getPercentageFromValue(final float floatValue) {
        return (int) (floatValue * PERCENTAGE_FLOAT);
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
        return getPercentageFromValue(Settings.readKeypressSoundVolume(prefs, res));
      }

      @Override
      public int readDefaultValue(final String key) {
        return getPercentageFromValue(Settings.readDefaultKeypressSoundVolume(res));
      }

      @Override
      public String getValueText(final int value) {
        if (value < 0) {
          return res.getString(R.string.settings_system_default);
        }
        return Integer.toString(value);
      }

      @Override
      public void feedbackValue(final int value) {
        am.playSoundEffect(
            AudioManager.FX_KEYPRESS_STANDARD, getValueFromPercentage(value));
      }
    });
  }

  private void setupKeyLongpressTimeoutSettings() {
    final SharedPreferences prefs = getSharedPreferences();
    final Resources res = getResources();
    final SeekBarDialogPreference pref = (SeekBarDialogPreference) findPreference(
        Settings.PREF_KEY_LONGPRESS_TIMEOUT);
    if (pref == null) {
      return;
    }
    pref.setInterface(new SeekBarDialogPreference.ValueProxy() {
      @Override
      public void writeValue(final int value, final String key) {
        prefs.edit().putInt(key, value).apply();
      }

      @Override
      public void writeDefaultValue(final String key) {
        prefs.edit().remove(key).apply();
      }

      @Override
      public int readValue(final String key) {
        return Settings.readKeyLongpressTimeout(prefs, res);
      }

      @Override
      public int readDefaultValue(final String key) {
        return Settings.readDefaultKeyLongpressTimeout(res);
      }

      @Override
      public String getValueText(final int value) {
        return res.getString(R.string.abbreviation_unit_milliseconds, value);
      }

      @Override
      public void feedbackValue(final int value) {
      }
    });
  }
}
