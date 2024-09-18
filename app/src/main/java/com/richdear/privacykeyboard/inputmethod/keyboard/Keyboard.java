

package com.richdear.privacykeyboard.inputmethod.keyboard;

import android.util.SparseArray;

import com.richdear.privacykeyboard.inputmethod.keyboard.internal.KeyVisualAttributes;
import com.richdear.privacykeyboard.inputmethod.keyboard.internal.KeyboardIconsSet;
import com.richdear.privacykeyboard.inputmethod.keyboard.internal.KeyboardParams;
import com.richdear.privacykeyboard.inputmethod.latin.common.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Keyboard {
  public final KeyboardId mId;


  public final int mOccupiedHeight;

  public final int mOccupiedWidth;


  public final float mBottomPadding;

  public final float mTopPadding;

  public final float mVerticalGap;

  public final float mHorizontalGap;


  public final KeyVisualAttributes mKeyVisualAttributes;

  public final int mMostCommonKeyHeight;
  public final int mMostCommonKeyWidth;


  public final int mMoreKeysTemplate;


  private final List<Key> mSortedKeys;
  public final List<Key> mShiftKeys;
  public final List<Key> mAltCodeKeysWhileTyping;
  public final KeyboardIconsSet mIconsSet;

  private final SparseArray<Key> mKeyCache = new SparseArray<>();

  private final ProximityInfo mProximityInfo;

  public Keyboard(final KeyboardParams params) {
    mId = params.mId;
    mOccupiedHeight = params.mOccupiedHeight;
    mOccupiedWidth = params.mOccupiedWidth;
    mMostCommonKeyHeight = params.mMostCommonKeyHeight;
    mMostCommonKeyWidth = params.mMostCommonKeyWidth;
    mMoreKeysTemplate = params.mMoreKeysTemplate;
    mKeyVisualAttributes = params.mKeyVisualAttributes;
    mBottomPadding = params.mBottomPadding;
    mTopPadding = params.mTopPadding;
    mVerticalGap = params.mVerticalGap;
    mHorizontalGap = params.mHorizontalGap;

    mSortedKeys = Collections.unmodifiableList(new ArrayList<>(params.mSortedKeys));
    mShiftKeys = Collections.unmodifiableList(params.mShiftKeys);
    mAltCodeKeysWhileTyping = Collections.unmodifiableList(params.mAltCodeKeysWhileTyping);
    mIconsSet = params.mIconsSet;

    mProximityInfo = new ProximityInfo(params.mGridWidth, params.mGridHeight,
        mOccupiedWidth, mOccupiedHeight, mSortedKeys);
  }


  public List<Key> getSortedKeys() {
    return mSortedKeys;
  }

  public Key getKey(final int code) {
    if (code == Constants.CODE_UNSPECIFIED) {
      return null;
    }
    synchronized (mKeyCache) {
      final int index = mKeyCache.indexOfKey(code);
      if (index >= 0) {
        return mKeyCache.valueAt(index);
      }

      for (final Key key : getSortedKeys()) {
        if (key.getCode() == code) {
          mKeyCache.put(code, key);
          return key;
        }
      }
      mKeyCache.put(code, null);
      return null;
    }
  }

  public boolean hasKey(final Key aKey) {
    if (mKeyCache.indexOfValue(aKey) >= 0) {
      return true;
    }

    for (final Key key : getSortedKeys()) {
      if (key == aKey) {
        mKeyCache.put(key.getCode(), key);
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return mId.toString();
  }


  public List<Key> getNearestKeys(final int x, final int y) {
    // Avoid dead pixels at edges of the keyboard
    final int adjustedX = Math.max(0, Math.min(x, mOccupiedWidth - 1));
    final int adjustedY = Math.max(0, Math.min(y, mOccupiedHeight - 1));
    return mProximityInfo.getNearestKeys(adjustedX, adjustedY);
  }
}
