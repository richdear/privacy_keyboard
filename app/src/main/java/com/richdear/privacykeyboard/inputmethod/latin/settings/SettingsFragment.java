

package com.richdear.privacykeyboard.inputmethod.latin.settings;

import android.os.Bundle;
import android.preference.PreferenceScreen;

import com.richdear.privacykeyboard.R;
import com.richdear.privacykeyboard.inputmethod.latin.utils.ApplicationUtils;

public final class SettingsFragment extends InputMethodSettingsFragment {
  @Override
  public void onCreate(final Bundle icicle) {
    super.onCreate(icicle);
    setHasOptionsMenu(true);
    addPreferencesFromResource(R.xml.prefs);
    final PreferenceScreen preferenceScreen = getPreferenceScreen();
    preferenceScreen.setTitle(ApplicationUtils.getActivityTitleResId(getActivity(), SettingsActivity.class));
  }
}
