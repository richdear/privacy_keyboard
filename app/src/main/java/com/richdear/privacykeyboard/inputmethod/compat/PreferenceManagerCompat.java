

package com.richdear.privacykeyboard.inputmethod.compat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

public class PreferenceManagerCompat {
  public static Context getDeviceContext(Context context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      return context.createDeviceProtectedStorageContext();
    }

    return context;
  }

  public static SharedPreferences getDeviceSharedPreferences(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(getDeviceContext(context));
  }
}
