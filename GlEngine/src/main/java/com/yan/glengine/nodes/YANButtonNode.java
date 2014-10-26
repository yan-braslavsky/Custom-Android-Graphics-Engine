package com.yan.glengine.nodes;

import com.yan.glengine.EngineWrapper;
import com.yan.glengine.assets.atlas.YANTextureRegion;
import com.yan.glengine.input.YANInputManager;
import com.yan.glengine.util.math.YANRectangle;
import com.yan.glengine.util.math.YANVector2;

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
        public boolean onTouchDown(float normalizedX, float normalizedY) {

            YANVector2 touchToWorldPoint = YANInputManager.touchToWorld(normalizedX, normalizedY,
                    EngineWrapper.getRenderer().getSurfaceSize().getX(), EngineWrapper.getRenderer().getSurfaceSize().getY());
            YANRectangle boundingRectangle = getBoundingRectangle();
            if (boundingRectangle.contains(touchToWorldPoint)) {
                changeState(YANButtonState.PRESSED);
                return true;
            }

            return false;
        }

        @Override
        public boolean onTouchUp(float normalizedX, float normalizedY) {
            if (getBoundingRectangle().contains(YANInputManager.touchToWorld(normalizedX, normalizedY,
                    EngineWrapper.getRenderer().getSurfaceSize().getX(), EngineWrapper.getRenderer().getSurfaceSize().getY()))) {

                //on click will be called only if we press up happens after press down
                boolean wasPressed = YANButtonState.PRESSED == mState;
                changeState(YANButtonState.DEFAULT);

                if (wasPressed && mClickListener != null) {
                    mClickListener.onButtonClick();
                }

                return true;
            }

            return false;
        }

        @Override
        public boolean onTouchDrag(float normalizedX, float normalizedY) {

            //we are not handling hovering above the button
            if (mState == YANButtonState.DEFAULT)
                return true;

            if (getBoundingRectangle().contains(YANInputManager.touchToWorld(normalizedX, normalizedY,
                    EngineWrapper.getRenderer().getSurfaceSize().getX(), EngineWrapper.getRenderer().getSurfaceSize().getY()))) {
                //node still touched inside , do nothing
                return true;
            } else {
                //dragged outside the button , reset the state
                changeState(YANButtonState.DEFAULT);
                return false;
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