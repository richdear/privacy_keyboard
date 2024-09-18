

package com.richdear.privacykeyboard.inputmethod.keyboard.internal;

import com.richdear.privacykeyboard.inputmethod.keyboard.Key;
import com.richdear.privacykeyboard.inputmethod.keyboard.MoreKeysPanel;
import com.richdear.privacykeyboard.inputmethod.keyboard.PointerTracker;

public interface DrawingProxy {
  
  void onKeyPressed(Key key, boolean withPreview);

  
  void onKeyReleased(Key key, boolean withAnimation);

  
  MoreKeysPanel showMoreKeysKeyboard(Key key, PointerTracker tracker);

  
  void startWhileTypingAnimation(int fadeInOrOut);

  int FADE_IN = 0;
  int FADE_OUT = 1;
}
