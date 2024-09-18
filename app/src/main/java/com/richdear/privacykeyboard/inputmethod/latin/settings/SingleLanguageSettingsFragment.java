

package com.richdear.privacykeyboard.inputmethod.latin.settings;

import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.preference.SwitchPreference;

import com.richdear.privacykeyboard.R;
import com.richdear.privacykeyboard.inputmethod.latin.RichInputMethodManager;
import com.richdear.privacykeyboard.inputmethod.latin.Subtype;
import com.richdear.privacykeyboard.inputmethod.latin.utils.LocaleResourceUtils;
import com.richdear.privacykeyboard.inputmethod.latin.utils.SubtypeLocaleUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public final class SingleLanguageSettingsFragment extends PreferenceFragment {

  public static final String LOCALE_BUNDLE_KEY = "LOCALE";

  private RichInputMethodManager mRichImm;
  private List<SubtypePreference> mSubtypePreferences;

  @Override
  public void onCreate(final Bundle icicle) {
    super.onCreate(icicle);

    RichInputMethodManager.init(getActivity());
    mRichImm = RichInputMethodManager.getInstance();
    addPreferencesFromResource(R.xml.empty_settings);
  }

  @Override
  public void onActivityCreated(final Bundle savedInstanceState) {
    final Context context = getActivity();

    final Bundle args = getArguments();
    if (args != null) {
      final String locale = getArguments().getString(LOCALE_BUNDLE_KEY);
      buildContent(locale, context);
    }

    super.onActivityCreated(savedInstanceState);
  }


  private void buildContent(final String locale, final Context context) {
    if (locale == null) {
      return;
    }
    final PreferenceGroup group = getPreferenceScreen();
    group.removeAll();

    final PreferenceCategory mainCategory = new PreferenceCategory(context);
    final String localeName = LocaleResourceUtils.getLocaleDisplayNameInSystemLocale(locale);
    mainCategory.setTitle(context.getString(R.string.generic_language_layouts, localeName));
    group.addPreference(mainCategory);

    buildSubtypePreferences(locale, group, context);
  }


  private void buildSubtypePreferences(final String locale, final PreferenceGroup group,
                                       final Context context) {
    final Set<Subtype> enabledSubtypes = mRichImm.getEnabledSubtypes(false);
    final List<Subtype> subtypes =
        SubtypeLocaleUtils.getSubtypes(locale, context.getResources());
    mSubtypePreferences = new ArrayList<>();
    for (final Subtype subtype : subtypes) {
      final boolean isChecked = enabledSubtypes.contains(subtype);
      final SubtypePreference pref = createSubtypePreference(subtype, isChecked, context);
      group.addPreference(pref);
      mSubtypePreferences.add(pref);
    }

    // if there is only one subtype that is checked, the preference for it should be disabled to
    // prevent all of the subtypes for the language from being removed
    final List<SubtypePreference> checkedPrefs = getCheckedSubtypePreferences();
    if (checkedPrefs.size() == 1) {
      checkedPrefs.get(0).setEnabled(false);
    }
  }


  private SubtypePreference createSubtypePreference(final Subtype subtype,
                                                    final boolean checked,
                                                    final Context context) {
    final SubtypePreference pref = new SubtypePreference(context, subtype);
    pref.setTitle(subtype.getLayoutDisplayName());
    pref.setChecked(checked);

    pref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
      @Override
      public boolean onPreferenceChange(final Preference preference, final Object newValue) {
        if (!(newValue instanceof Boolean)) {
          return false;
        }
        final boolean isEnabling = (boolean) newValue;
        final SubtypePreference pref = (SubtypePreference) preference;
        final List<SubtypePreference> checkedPrefs = getCheckedSubtypePreferences();
        if (checkedPrefs.size() == 1) {
          checkedPrefs.get(0).setEnabled(false);
        }
        if (isEnabling) {
          final boolean added = mRichImm.addSubtype(pref.getSubtype());
          // if only one subtype was checked before, the preference would have been
          // disabled, but now that there are two, it can be enabled to allow it to be
          // unchecked
          if (added && checkedPrefs.size() == 1) {
            checkedPrefs.get(0).setEnabled(true);
          }
          return added;
        } else {
          final boolean removed = mRichImm.removeSubtype(pref.getSubtype());
          // if there is going to be only one subtype that is checked, the preference for
          // it should be disabled to prevent all of the subtypes for the language from
          // being removed
          if (removed && checkedPrefs.size() == 2) {
            final SubtypePreference onlyCheckedPref;
            if (checkedPrefs.get(0).equals(pref)) {
              onlyCheckedPref = checkedPrefs.get(1);
            } else {
              onlyCheckedPref = checkedPrefs.get(0);
            }
            onlyCheckedPref.setEnabled(false);
          }
          return removed;
        }
      }
    });

    return pref;
  }


  private List<SubtypePreference> getCheckedSubtypePreferences() {
    final List<SubtypePreference> prefs = new ArrayList<>();
    for (final SubtypePreference pref : mSubtypePreferences) {
      if (pref.isChecked()) {
        prefs.add(pref);
      }
    }
    return prefs;
  }


  private static class SubtypePreference extends SwitchPreference {
    final Subtype mSubtype;


    public SubtypePreference(final Context context, final Subtype subtype) {
      super(context);
      mSubtype = subtype;
    }


    public Subtype getSubtype() {
      return mSubtype;
    }
  }
}
