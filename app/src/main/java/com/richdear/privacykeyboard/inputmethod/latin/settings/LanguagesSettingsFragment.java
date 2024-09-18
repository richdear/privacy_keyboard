

package com.richdear.privacykeyboard.inputmethod.latin.settings;

import static com.richdear.privacykeyboard.inputmethod.latin.settings.SingleLanguageSettingsFragment.LOCALE_BUNDLE_KEY;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.richdear.privacykeyboard.R;
import com.richdear.privacykeyboard.inputmethod.compat.MenuItemIconColorCompat;
import com.richdear.privacykeyboard.inputmethod.latin.RichInputMethodManager;
import com.richdear.privacykeyboard.inputmethod.latin.Subtype;
import com.richdear.privacykeyboard.inputmethod.latin.common.LocaleUtils;
import com.richdear.privacykeyboard.inputmethod.latin.utils.LocaleResourceUtils;
import com.richdear.privacykeyboard.inputmethod.latin.utils.SubtypeLocaleUtils;

import java.util.Comparator;
import java.util.Locale;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


public final class LanguagesSettingsFragment extends PreferenceFragment {
  private static final String TAG = LanguagesSettingsFragment.class.getSimpleName();

  private static final boolean DEBUG_SUBTYPE_ID = false;

  private RichInputMethodManager mRichImm;
  private CharSequence[] mUsedLocaleNames;
  private String[] mUsedLocaleValues;
  private CharSequence[] mUnusedLocaleNames;
  private String[] mUnusedLocaleValues;
  private AlertDialog mAlertDialog;
  private View mView;

  @Override
  public void onCreate(final Bundle icicle) {
    super.onCreate(icicle);
    RichInputMethodManager.init(getActivity());
    mRichImm = RichInputMethodManager.getInstance();

    addPreferencesFromResource(R.xml.empty_settings);

    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                           final Bundle savedInstanceState) {
    mView = super.onCreateView(inflater, container, savedInstanceState);
    return mView;
  }

  @Override
  public void onStart() {
    super.onStart();
    buildContent();
  }

  @Override
  public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
    inflater.inflate(R.menu.remove_language, menu);
    inflater.inflate(R.menu.add_language, menu);

    MenuItem addLanguageStripItem = menu.findItem(R.id.action_add_language);
    MenuItemIconColorCompat.matchStripIconColor(mView, addLanguageStripItem,
        getActivity().getActionBar());
    MenuItem removeLanguageStripItem = menu.findItem(R.id.action_remove_language);
    MenuItemIconColorCompat.matchStripIconColor(mView, removeLanguageStripItem,
        getActivity().getActionBar());
  }

  @Override
  public boolean onOptionsItemSelected(final MenuItem item) {
    final int itemId = item.getItemId();
    if (itemId == R.id.action_add_language) {
      showAddLanguagePopup();
    } else if (itemId == R.id.action_remove_language) {
      showRemoveLanguagePopup();
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onPrepareOptionsMenu(Menu menu) {
    if (mUsedLocaleNames != null) {
      menu.findItem(R.id.action_remove_language).setVisible(mUsedLocaleNames.length > 1);
    }
  }


  private void buildContent() {
    final Context context = getActivity();
    final PreferenceGroup group = getPreferenceScreen();
    group.removeAll();

    final PreferenceCategory languageCategory = new PreferenceCategory(context);
    languageCategory.setTitle(R.string.user_languages);
    group.addPreference(languageCategory);

    final Comparator<Locale> comparator = new LocaleUtils.LocaleComparator();
    final Set<Subtype> enabledSubtypes = mRichImm.getEnabledSubtypes(false);
    final SortedSet<Locale> usedLocales = getUsedLocales(enabledSubtypes, comparator);
    final SortedSet<Locale> unusedLocales = getUnusedLocales(usedLocales, comparator);

    buildLanguagePreferences(usedLocales, group, context);
    setLocaleEntries(usedLocales, unusedLocales);
  }


  private SortedSet<Locale> getUsedLocales(final Set<Subtype> subtypes,
                                           final Comparator<Locale> comparator) {
    final SortedSet<Locale> locales = new TreeSet<>(comparator);

    for (final Subtype subtype : subtypes) {
      if (DEBUG_SUBTYPE_ID) {
        Log.d(TAG, String.format("Enabled subtype: %-6s 0x%08x %11d %s",
            subtype.getLocale(), subtype.hashCode(), subtype.hashCode(),
            subtype.getName()));
      }
      locales.add(subtype.getLocaleObject());
    }

    return locales;
  }


  private SortedSet<Locale> getUnusedLocales(final Set<Locale> usedLocales,
                                             final Comparator<Locale> comparator) {
    final SortedSet<Locale> locales = new TreeSet<>(comparator);
    for (String localeString : SubtypeLocaleUtils.getSupportedLocales()) {
      final Locale locale = LocaleUtils.constructLocaleFromString(localeString);
      if (usedLocales.contains(locale)) {
        continue;
      }
      locales.add(locale);
    }
    return locales;
  }


  private void buildLanguagePreferences(final SortedSet<Locale> locales,
                                        final PreferenceGroup group, final Context context) {
    for (final Locale locale : locales) {
      final String localeString = LocaleUtils.getLocaleString(locale);
      final SingleLanguagePreference pref =
          new SingleLanguagePreference(context, localeString);
      group.addPreference(pref);
    }
  }


  private void setLocaleEntries(final SortedSet<Locale> usedLocales,
                                final SortedSet<Locale> unusedLocales) {
    mUsedLocaleNames = new CharSequence[usedLocales.size()];
    mUsedLocaleValues = new String[usedLocales.size()];
    int i = 0;
    for (Locale locale : usedLocales) {
      final String localeString = LocaleUtils.getLocaleString(locale);
      mUsedLocaleValues[i] = localeString;
      mUsedLocaleNames[i] =
          LocaleResourceUtils.getLocaleDisplayNameInSystemLocale(localeString);
      i++;
    }

    mUnusedLocaleNames = new CharSequence[unusedLocales.size()];
    mUnusedLocaleValues = new String[unusedLocales.size()];
    i = 0;
    for (Locale locale : unusedLocales) {
      final String localeString = LocaleUtils.getLocaleString(locale);
      mUnusedLocaleValues[i] = localeString;
      mUnusedLocaleNames[i] =
          LocaleResourceUtils.getLocaleDisplayNameInSystemLocale(localeString);
      i++;
    }
  }


  private void showAddLanguagePopup() {
    showMultiChoiceDialog(mUnusedLocaleNames, R.string.add_language, R.string.add, true,
        new OnMultiChoiceDialogAcceptListener() {
          @Override
          public void onClick(boolean[] checkedItems) {
            // enable the default layout for all of the checked languages
            for (int i = 0; i < checkedItems.length; i++) {
              if (!checkedItems[i]) {
                continue;
              }
              final Subtype subtype = SubtypeLocaleUtils.getDefaultSubtype(
                  mUnusedLocaleValues[i],
                  LanguagesSettingsFragment.this.getResources());
              mRichImm.addSubtype(subtype);
            }

            // refresh the list of enabled languages
            getActivity().invalidateOptionsMenu();
            buildContent();
          }
        });
  }


  private void showRemoveLanguagePopup() {
    showMultiChoiceDialog(mUsedLocaleNames, R.string.remove_language, R.string.remove, false,
        new OnMultiChoiceDialogAcceptListener() {
          @Override
          public void onClick(boolean[] checkedItems) {
            // disable the layouts for all of the checked languages
            for (int i = 0; i < checkedItems.length; i++) {
              if (!checkedItems[i]) {
                continue;
              }
              final Set<Subtype> subtypes =
                  mRichImm.getEnabledSubtypesForLocale(mUsedLocaleValues[i]);
              for (final Subtype subtype : subtypes) {
                mRichImm.removeSubtype(subtype);
              }
            }

            // refresh the list of enabled languages
            getActivity().invalidateOptionsMenu();
            buildContent();
          }
        });
  }


  private void showMultiChoiceDialog(final CharSequence[] names, final int titleRes,
                                     final int positiveButtonRes, final boolean allowAllChecked,
                                     final OnMultiChoiceDialogAcceptListener listener) {
    final boolean[] checkedItems = new boolean[names.length];
    mAlertDialog = new AlertDialog.Builder(getActivity())
        .setTitle(titleRes)
        .setMultiChoiceItems(names, checkedItems,
            new DialogInterface.OnMultiChoiceClickListener() {
              @Override
              public void onClick(final DialogInterface dialogInterface,
                                  final int which, final boolean isChecked) {
                // make sure the positive button is only enabled when at least one
                // item is checked and when not all of the items are checked (unless
                // allowAllChecked is true)
                boolean hasCheckedItem = false;
                boolean hasUncheckedItem = false;
                for (final boolean itemChecked : checkedItems) {
                  if (itemChecked) {
                    hasCheckedItem = true;
                    if (allowAllChecked) {
                      break;
                    }
                  } else {
                    hasUncheckedItem = true;
                  }
                  if (hasCheckedItem && hasUncheckedItem) {
                    break;
                  }
                }
                mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(
                    hasCheckedItem && (hasUncheckedItem || allowAllChecked));
              }
            })
        .setPositiveButton(positiveButtonRes, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(final DialogInterface dialog, final int which) {
            listener.onClick(checkedItems);
          }
        })
        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(final DialogInterface dialog, final int which) {
          }
        })
        .create();
    mAlertDialog.show();
    // disable the positive button since nothing is checked by default
    mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
  }


  private interface OnMultiChoiceDialogAcceptListener {

    void onClick(final boolean[] checkedItems);
  }


  private static class SingleLanguagePreference extends Preference {
    private final String mLocale;
    private Bundle mExtras;


    public SingleLanguagePreference(final Context context, final String localeString) {
      super(context);
      mLocale = localeString;

      setTitle(LocaleResourceUtils.getLocaleDisplayNameInSystemLocale(localeString));
      setFragment(SingleLanguageSettingsFragment.class.getName());
    }

    @Override
    public Bundle getExtras() {
      if (mExtras == null) {
        mExtras = new Bundle();
        mExtras.putString(LOCALE_BUNDLE_KEY, mLocale);
      }
      return mExtras;
    }

    @Override
    public Bundle peekExtras() {
      return mExtras;
    }
  }
}
