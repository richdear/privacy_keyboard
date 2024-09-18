

package com.richdear.privacykeyboard.inputmethod.latin.settings;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;

import com.richdear.privacykeyboard.R;


public class RadioButtonPreference extends Preference {
  interface OnRadioButtonClickedListener {

    void onRadioButtonClicked(RadioButtonPreference preference);
  }

  private boolean mIsSelected;
  private RadioButton mRadioButton;
  private OnRadioButtonClickedListener mListener;
  private final View.OnClickListener mClickListener = new View.OnClickListener() {
    @Override
    public void onClick(final View v) {
      callListenerOnRadioButtonClicked();
    }
  };

  public RadioButtonPreference(final Context context) {
    this(context, null);
  }

  public RadioButtonPreference(final Context context, final AttributeSet attrs) {
    this(context, attrs, android.R.attr.preferenceStyle);
  }

  public RadioButtonPreference(final Context context, final AttributeSet attrs,
                               final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setWidgetLayoutResource(R.layout.radio_button_preference_widget);
  }

  public void setOnRadioButtonClickedListener(final OnRadioButtonClickedListener listener) {
    mListener = listener;
  }

  void callListenerOnRadioButtonClicked() {
    if (mListener != null) {
      mListener.onRadioButtonClicked(this);
    }
  }

  @Override
  protected void onBindView(final View view) {
    super.onBindView(view);
    mRadioButton = view.findViewById(R.id.radio_button);
    mRadioButton.setChecked(mIsSelected);
    mRadioButton.setOnClickListener(mClickListener);
    view.setOnClickListener(mClickListener);
  }

  public void setSelected(final boolean selected) {
    if (selected == mIsSelected) {
      return;
    }
    mIsSelected = selected;
    if (mRadioButton != null) {
      mRadioButton.setChecked(selected);
    }
    notifyChanged();
  }
}
