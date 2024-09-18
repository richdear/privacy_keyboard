

package com.richdear.privacykeyboard.inputmethod.keyboard;

import com.richdear.privacykeyboard.inputmethod.latin.common.Constants;

public interface KeyboardActionListener {

  void onPressKey(int primaryCode, int repeatCount, boolean isSinglePointer);


  void onReleaseKey(int primaryCode, boolean withSliding);


  // TODO: change this to send an Event object instead
  void onCodeInput(int primaryCode, int x, int y, boolean isKeyRepeat);


  void onTextInput(final String rawText);


  void onFinishSlidingInput();


  boolean onCustomRequest(int requestCode);

  void onMovePointer(int steps);

  void onMoveDeletePointer(int steps);

  void onUpWithDeletePointerActive();

  KeyboardActionListener EMPTY_LISTENER = new Adapter();

  class Adapter implements KeyboardActionListener {
    @Override
    public void onPressKey(int primaryCode, int repeatCount, boolean isSinglePointer) {
    }

    @Override
    public void onReleaseKey(int primaryCode, boolean withSliding) {
    }

    @Override
    public void onCodeInput(int primaryCode, int x, int y, boolean isKeyRepeat) {
    }

    @Override
    public void onTextInput(String text) {
    }

    @Override
    public void onFinishSlidingInput() {
    }

    @Override
    public boolean onCustomRequest(int requestCode) {
      return false;
    }

    @Override
    public void onMovePointer(int steps) {
    }

    @Override
    public void onMoveDeletePointer(int steps) {
    }

    @Override
    public void onUpWithDeletePointerActive() {
    }
  }
}
