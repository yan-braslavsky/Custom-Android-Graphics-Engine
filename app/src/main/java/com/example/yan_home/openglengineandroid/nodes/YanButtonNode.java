package com.example.yan_home.openglengineandroid.nodes;

import com.example.yan_home.openglengineandroid.EngineWrapper;
import com.example.yan_home.openglengineandroid.assets.YANTexture;
import com.example.yan_home.openglengineandroid.input.YANInputManager;

/**
 * Created by Yan-Home on 10/12/2014.
 */
public class YANButtonNode extends YANBaseNode {

    private YANTexture mDefaultTexture;
    private YANTexture mPressedTexture;
    private YANButtonState mState;
    private YanButtonNodeClickListener mClickListener;
    private YANInputManager.TouchListener mInputManagerTouchListener = new YANInputManager.TouchListener() {
        @Override
        public void onTouchDown(float normalizedX, float normalizedY) {

            if (getBoundingRectangle().contains(YANInputManager.touchToWorld(normalizedX, normalizedY,
                    EngineWrapper.getRenderer().getSurfaceSize().getX(), EngineWrapper.getRenderer().getSurfaceSize().getY()))) {
                changeState(YANButtonState.PRESSED);
            }
        }

        @Override
        public void onTouchUp(float normalizedX, float normalizedY) {
            if (getBoundingRectangle().contains(YANInputManager.touchToWorld(normalizedX, normalizedY,
                    EngineWrapper.getRenderer().getSurfaceSize().getX(), EngineWrapper.getRenderer().getSurfaceSize().getY()))) {

                //on click will be called only if we press up happens after press down
                boolean wasPressed = YANButtonState.PRESSED == mState;
                changeState(YANButtonState.DEFAULT);

                if (wasPressed && mClickListener != null) {
                    mClickListener.onButtonClick();
                }
            }
        }

        @Override
        public void onTouchDrag(float normalizedX, float normalizedY) {

            //we are not handling hovering above the button
            if (mState == YANButtonState.DEFAULT)
                return;

            if (getBoundingRectangle().contains(YANInputManager.touchToWorld(normalizedX, normalizedY,
                    EngineWrapper.getRenderer().getSurfaceSize().getX(), EngineWrapper.getRenderer().getSurfaceSize().getY()))) {
                //node still touched inside , do nothing
            } else {
                //dragged outside the button , reset the state
                changeState(YANButtonState.DEFAULT);
            }
        }
    };

    private void changeState(YANButtonState state) {
        if (mState == state)
            return;
        mState = state;
    }

    public YANTexture getCurrentStateTexture() {
        if (mState == YANButtonState.DEFAULT)
            return mDefaultTexture;
        else return mPressedTexture;
    }

    public interface YanButtonNodeClickListener {
        void onButtonClick();
    }

    public YANButtonNode(YANTexture defaultTexture, YANTexture pressedTexture) {
        super();
        mDefaultTexture = defaultTexture;
        mPressedTexture = pressedTexture;
        mState = YANButtonState.DEFAULT;
    }

    @Override
    public void onAttachedToScreen() {
        // Add itself to input manager
        YANInputManager.getInstance().addEventListener(mInputManagerTouchListener);
    }

    @Override
    public void onDetachedFromScreen() {
        // Add itself to input manager
        YANInputManager.getInstance().removeEventListener(mInputManagerTouchListener);
    }

    public void setClickListener(YanButtonNodeClickListener clickListener) {
        mClickListener = clickListener;
    }

    private enum YANButtonState {
        PRESSED, DEFAULT;
    }

    public YANTexture getDefaultTexture() {
        return mDefaultTexture;
    }

    public YANTexture getPressedTexture() {
        return mPressedTexture;
    }

    public YANButtonState getState() {
        return mState;
    }

    public void setState(YANButtonState state) {
        mState = state;
    }

    public void setDefaultTexture(YANTexture defaultTexture) {
        mDefaultTexture = defaultTexture;
    }

    public void setPressedTexture(YANTexture pressedTexture) {
        mPressedTexture = pressedTexture;
    }

    public YanButtonNodeClickListener getClickListener() {
        return mClickListener;
    }
}