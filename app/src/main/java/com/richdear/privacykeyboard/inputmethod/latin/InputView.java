

package com.richdear.privacykeyboard.inputmethod.latin;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.richdear.privacykeyboard.R;
import com.richdear.privacykeyboard.inputmethod.keyboard.MainKeyboardView;
import com.richdear.privacykeyboard.inputmethod.latin.e2ee.E2EEMenuView;

public final class InputView extends FrameLayout {
  private MainKeyboardView mMainKeyboardView;
  private KeyboardTopPaddingForwarder mKeyboardTopPaddingForwarder;

  public InputView(final Context context, final AttributeSet attrs) {
    super(context, attrs, 0);
  }

  @Override
  protected void onFinishInflate() {
    final E2EEMenuView e2EeMenuView = findViewById(R.id.e2ee_strip_view);
    mMainKeyboardView = findViewById(R.id.keyboard_view);
    mKeyboardTopPaddingForwarder = new KeyboardTopPaddingForwarder(
        mMainKeyboardView, e2EeMenuView);

    super.onFinishInflate();
  }

  public void setKeyboardTopPadding(final int keyboardTopPadding) {
    mKeyboardTopPaddingForwarder.setKeyboardTopPadding(keyboardTopPadding);
  }

  
  private static class KeyboardTopPaddingForwarder
      extends MotionEventForwarder<MainKeyboardView, E2EEMenuView> {
    private int mKeyboardTopPadding;

    public KeyboardTopPaddingForwarder(final MainKeyboardView mainKeyboardView,
                                       final E2EEMenuView e2EeMenuView) {
      super(mainKeyboardView, e2EeMenuView);
    }

    public void setKeyboardTopPadding(final int keyboardTopPadding) {
      mKeyboardTopPadding = keyboardTopPadding;
    }

    private boolean isInKeyboardTopPadding(final int y) {
      return y < mEventSendingRect.top + mKeyboardTopPadding;
    }

    @Override
    protected boolean needsToForward(final int x, final int y) {
      // Forwarding an event only when {@link MainKeyboardView} is visible.
      // Because the visibility of {@link MainKeyboardView} is controlled by its parent
      // view in {@link KeyboardSwitcher#setMainKeyboardFrame()}, we should check the
      // visibility of the parent view.
      final View mainKeyboardFrame = (View) mSenderView.getParent();
      return mainKeyboardFrame.getVisibility() == View.VISIBLE && isInKeyboardTopPadding(y);
    }

    @Override
    protected int translateY(final int y) {
      final int translatedY = super.translateY(y);
      if (isInKeyboardTopPadding(y)) {
        // The forwarded event should have coordinates that are inside of the target.
        return Math.min(translatedY, mEventReceivingRect.height() - 1);
      }
      return translatedY;
    }
  }

  
  private static abstract class
  MotionEventForwarder<SenderView extends View, ReceiverView extends View> {
    protected final SenderView mSenderView;
    protected final ReceiverView mReceiverView;

    protected final Rect mEventSendingRect = new Rect();
    protected final Rect mEventReceivingRect = new Rect();

    public MotionEventForwarder(final SenderView senderView, final ReceiverView receiverView) {
      mSenderView = senderView;
      mReceiverView = receiverView;
    }

    // Return true if a touch event of global coordinate x, y needs to be forwarded.
    protected abstract boolean needsToForward(final int x, final int y);

    // Translate global x-coordinate to <code>ReceiverView</code> local coordinate.
    protected int translateX(final int x) {
      return x - mEventReceivingRect.left;
    }

    // Translate global y-coordinate to <code>ReceiverView</code> local coordinate.
    protected int translateY(final int y) {
      return y - mEventReceivingRect.top;
    }

    
    protected void onForwardingEvent(final MotionEvent me) {
    }

    // Returns true if a {@link MotionEvent} is needed to be forwarded to
    // <code>ReceiverView</code>. Otherwise returns false.
    public boolean onInterceptTouchEvent(final int x, final int y, final MotionEvent me) {
      // Forwards a {link MotionEvent} only if both <code>SenderView</code> and
      // <code>ReceiverView</code> are visible.
      if (mSenderView.getVisibility() != View.VISIBLE ||
          mReceiverView.getVisibility() != View.VISIBLE) {
        return false;
      }
      mSenderView.getGlobalVisibleRect(mEventSendingRect);
      if (!mEventSendingRect.contains(x, y)) {
        return false;
      }

      if (me.getActionMasked() == MotionEvent.ACTION_DOWN) {
        // If the down event happens in the forwarding area, successive
        // {@link MotionEvent}s should be forwarded to <code>ReceiverView</code>.
        return needsToForward(x, y);
      }

      return false;
    }

    // Returns true if a {@link MotionEvent} is forwarded to <code>ReceiverView</code>.
    // Otherwise returns false.
    public boolean onTouchEvent(final int x, final int y, final MotionEvent me) {
      mReceiverView.getGlobalVisibleRect(mEventReceivingRect);
      // Translate global coordinates to <code>ReceiverView</code> local coordinates.
      me.setLocation(translateX(x), translateY(y));
      mReceiverView.dispatchTouchEvent(me);
      onForwardingEvent(me);
      return true;
    }
  }
}
