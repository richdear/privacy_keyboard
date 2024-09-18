

package com.richdear.privacykeyboard.inputmethod.latin.settings;

import android.content.Context;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.text.TextUtils;

import com.richdear.privacykeyboard.R;
import com.richdear.privacykeyboard.inputmethod.latin.RichInputMethodManager;
import com.richdear.privacykeyboard.inputmethod.latin.Subtype;

import java.util.Set;

 class InputMethodSettingsImpl {
  private Preference mSubtypeEnablerPreference;
  private RichInputMethodManager mRichImm;


  public boolean init(final Context context, final PreferenceScreen prefScreen) {
    RichInputMethodManager.init(context);
    mRichImm = RichInputMethodManager.getInstance();

    mSubtypeEnablerPreference = new Preference(context);
    mSubtypeEnablerPreference.setTitle(R.string.select_language);
    mSubtypeEnablerPreference.setFragment(LanguagesSettingsFragment.class.getName());
    prefScreen.addPreference(mSubtypeEnablerPreference);
    updateEnabledSubtypeList();
    return true;
  }

  private static String getEnabledSubtypesLabel(final RichInputMethodManager richImm) {
    if (richImm == null) {
      return null;
    }

    final Set<Subtype> subtypes = richImm.getEnabledSubtypes(true);

    final StringBuilder sb = new StringBuilder();
    for (final Subtype subtype : subtypes) {
      if (sb.length() > 0) {
        sb.append(", ");
      }
      sb.append(subtype.getName());
    }
    return sb.toString();
  }

  public void updateEnabledSubtypeList() {
    if (mSubtypeEnablerPreference != null) {
      final String summary = getEnabledSubtypesLabel(mRichImm);
      if (!TextUtils.isEmpty(summary)) {
        mSubtypeEnablerPreference.setSummary(summary);
      }
    }
  }
}
