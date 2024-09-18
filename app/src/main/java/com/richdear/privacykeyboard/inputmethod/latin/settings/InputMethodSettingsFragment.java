
package com.richdear.privacykeyboard.inputmethod.latin.settings;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.richdear.privacykeyboard.inputmethod.compat.PreferenceManagerCompat;


public abstract class InputMethodSettingsFragment extends PreferenceFragment {
  private final InputMethodSettingsImpl mSettings = new InputMethodSettingsImpl();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final Context context = getActivity();
    setPreferenceScreen(getPreferenceManager().createPreferenceScreen(
        PreferenceManagerCompat.getDeviceContext(context)));
    mSettings.init(context, getPreferenceScreen());
  }


  @Override
  public void onResume() {
    super.onResume();
    mSettings.updateEnabledSubtypeList();
  }
}
