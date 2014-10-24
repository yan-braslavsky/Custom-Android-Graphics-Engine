package com.example.yan_home.openglengineandroid.nodes;

import com.example.yan_home.openglengineandroid.EngineWrapper;
import com.example.yan_home.openglengineandroid.assets.atlas.YANTextureRegion;
import com.example.yan_home.openglengineandroid.input.YANInputManager;

/**
 * Created by Yan-Home on 10/12/2014.
 */
public class YANButtonNode extends YANTexturedNode {

    private YANTextureRegion mDefaultTextureRegion;
    private YANTextureRegion mPressedTextureRegion;
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
        setTextureRegion(getCurrentStateTextureRegion());
    }

    public YANTextureRegion getCurrentStateTextureRegion() {
        if (mState == YANButtonState.DEFAULT)
            return mDefaultTextureRegion;
        else return mPressedTextureRegion;
    }

    public interface YanButtonNodeClickListener {
        void onButtonClick();
    }

    public YANButtonNode(YANTextureRegion defaultTextureRegion, YANTextureRegion pressedTextureRegion) {
        super(defaultTextureRegion);
        mDefaultTextureRegion = defaultTextureRegion;
        mPressedTextureRegion = pressedTextureRegion;
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

    public YANTextureRegion getDefaultTextureRegion() {
        return mDefaultTextureRegion;
    }

    public YANTextureRegion getPressedTextureRegion() {
        return mPressedTextureRegion;
    }

    public YANButtonState getState() {
        return mState;
    }

    public void setState(YANButtonState state) {
        mState = state;
    }

    public void setDefaultTextureRegion(YANTextureRegion defaultTextureRegion) {
        mDefaultTextureRegion = defaultTextureRegion;
    }

    public void setPressedTextureRegion(YANTextureRegion pressedTextureRegion) {
        mPressedTextureRegion = pressedTextureRegion;
    }

    public YanButtonNodeClickListener getClickListener() {
        return mClickListener;
    }
}