

package com.richdear.privacykeyboard.inputmethod.keyboard;

import android.view.View;
import android.view.ViewGroup;

public interface MoreKeysPanel {
  interface Controller {

    void onShowMoreKeysPanel(final MoreKeysPanel panel);


    void onDismissMoreKeysPanel();


    void onCancelMoreKeysPanel();
  }

  Controller EMPTY_CONTROLLER = new Controller() {
    @Override
    public void onShowMoreKeysPanel(final MoreKeysPanel panel) {
    }

    @Override
    public void onDismissMoreKeysPanel() {
    }

    @Override
    public void onCancelMoreKeysPanel() {
    }
  };


  // TODO: Currently the MoreKeysPanel is inside a container view that is added to the parent.
  // Consider the simpler approach of placing the MoreKeysPanel itself into the parent view.
  void showMoreKeysPanel(View parentView, Controller controller, int pointX,
                         int pointY, KeyboardActionListener listener);


  void dismissMoreKeysPanel();


  void onMoveEvent(final int x, final int y, final int pointerId);


  void onDownEvent(final int x, final int y, final int pointerId);


  void onUpEvent(final int x, final int y, final int pointerId);


  int translateX(int x);


  int translateY(int y);


  void showInParent(ViewGroup parentView);


  void removeFromParent();


  boolean isShowingInParent();
}
