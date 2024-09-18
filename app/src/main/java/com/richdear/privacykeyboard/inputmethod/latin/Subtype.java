

package com.richdear.privacykeyboard.inputmethod.latin;

import android.content.res.Resources;

import com.richdear.privacykeyboard.R;
import com.richdear.privacykeyboard.inputmethod.latin.common.LocaleUtils;
import com.richdear.privacykeyboard.inputmethod.latin.utils.LocaleResourceUtils;

import java.util.Locale;


public final class Subtype {
  private static final int NO_RESOURCE = 0;

  private final String mLocale;
  private final String mLayoutSet;
  private final int mLayoutNameRes;
  private final String mLayoutNameStr;
  private final boolean mShowLayoutInName;
  private final Resources mResources;


  public Subtype(final String locale, final String layoutSet, final int layoutNameRes,
                 final boolean showLayoutInName, final Resources resources) {
    mLocale = locale;
    mLayoutSet = layoutSet;
    mLayoutNameRes = layoutNameRes;
    mLayoutNameStr = null;
    mShowLayoutInName = showLayoutInName;
    mResources = resources;
  }


  public Subtype(final String locale, final String layoutSet, final String layoutNameStr,
                 final boolean showLayoutInName, final Resources resources) {
    mLocale = locale;
    mLayoutSet = layoutSet;
    mLayoutNameRes = NO_RESOURCE;
    mLayoutNameStr = layoutNameStr;
    mShowLayoutInName = showLayoutInName;
    mResources = resources;
  }


  public String getLocale() {
    return mLocale;
  }


  public Locale getLocaleObject() {
    return LocaleUtils.constructLocaleFromString(mLocale);
  }


  public String getName() {
    final String localeDisplayName =
        LocaleResourceUtils.getLocaleDisplayNameInSystemLocale(mLocale);
    if (mShowLayoutInName) {
      if (mLayoutNameRes != NO_RESOURCE) {
        return mResources.getString(R.string.subtype_generic_layout, localeDisplayName,
            mResources.getString(mLayoutNameRes));
      }
      if (mLayoutNameStr != null) {
        return mResources.getString(R.string.subtype_generic_layout, localeDisplayName,
            mLayoutNameStr);
      }
    }
    return localeDisplayName;
  }


  public String getKeyboardLayoutSet() {
    return mLayoutSet;
  }


  public String getLayoutDisplayName() {
    final String displayName;
    if (mLayoutNameRes != NO_RESOURCE) {
      displayName = mResources.getString(mLayoutNameRes);
    } else if (mLayoutNameStr != null) {
      displayName = mLayoutNameStr;
    } else {
      displayName = LocaleResourceUtils.getLanguageDisplayNameInSystemLocale(mLocale);
    }
    return displayName;
  }

  @Override
  public boolean equals(final Object o) {
    if (!(o instanceof Subtype)) {
      return false;
    }
    final Subtype other = (Subtype) o;
    return mLocale.equals(other.mLocale) && mLayoutSet.equals(other.mLayoutSet);
  }

  @Override
  public int hashCode() {
    int hashCode = 31 + mLocale.hashCode();
    hashCode = hashCode * 31 + mLayoutSet.hashCode();
    return hashCode;
  }

  @Override
  public String toString() {
    return "subtype " + mLocale + ":" + mLayoutSet;
  }
}
