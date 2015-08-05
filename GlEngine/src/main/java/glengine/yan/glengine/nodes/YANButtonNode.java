package glengine.yan.glengine.nodes;

import glengine.yan.glengine.assets.atlas.YANAtlasTextureRegion;
import glengine.yan.glengine.input.YANInputManager;
import glengine.yan.glengine.screens.YANNodeScreen;
import glengine.yan.glengine.service.ServiceLocator;
import glengine.yan.glengine.util.geometry.YANRectangle;
import glengine.yan.glengine.util.geometry.YANVector2;

/**
 * Created by Yan-Home on 10/12/2014.
 */
public class YANButtonNode extends YANTexturedNode {

    private YANAtlasTextureRegion mDefaultTextureRegion;
    private YANAtlasTextureRegion mPressedTextureRegion;
    private YANButtonState mState;
    private YanButtonNodeClickListener mClickListener;
    private YANInputManager.TouchListener mInputManagerTouchListener = new YANInputManager.TouchListener() {
        @Override
        public boolean onTouchDown(float normalizedX, float normalizedY) {
            YANVector2 touchToWorldPoint = YANInputManager.touchToWorld(normalizedX, normalizedY,
                    getScreen().getRenderer().getSurfaceSize().getX(), getScreen().getRenderer().getSurfaceSize().getY());
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
                    getScreen().getRenderer().getSurfaceSize().getX(), getScreen().getRenderer().getSurfaceSize().getY()))) {

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
                return false;

            if (getBoundingRectangle().contains(YANInputManager.touchToWorld(normalizedX, normalizedY,
                    getScreen().getRenderer().getSurfaceSize().getX(), getScreen().getRenderer().getSurfaceSize().getY()))) {
                //node still touched inside , do nothing
                return true;
            } else {
                //dragged outside the button , reset the state
                changeState(YANButtonState.DEFAULT);
                return false;
            }

        }

        @Override
        public int getSortingLayer() {
            return YANButtonNode.this.getSortingLayer();
        }
    };

    private void changeState(YANButtonState state) {
        if (mState == state)
            return;
        mState = state;
        setTextureRegion(getCurrentStateTextureRegion());
    }

    public YANAtlasTextureRegion getCurrentStateTextureRegion() {
        if (mState == YANButtonState.DEFAULT)
            return mDefaultTextureRegion;
        else return mPressedTextureRegion;
    }

    public interface YanButtonNodeClickListener {
        void onButtonClick();
    }

    public YANButtonNode(YANAtlasTextureRegion defaultTextureRegion, YANAtlasTextureRegion pressedTextureRegion) {
        super(defaultTextureRegion);
        mDefaultTextureRegion = defaultTextureRegion;
        mPressedTextureRegion = pressedTextureRegion;
        mState = YANButtonState.DEFAULT;
    }

    @Override
    public void onAttachedToScreen(YANNodeScreen screen,YANNodeScreen.SortingLayerChangeListener sortingLayerChangeListener) {
        super.onAttachedToScreen(screen,sortingLayerChangeListener);
        // Add itself to input manager
        ServiceLocator.locateService(YANInputManager.class).addEventListener(mInputManagerTouchListener);
    }

    @Override
    public void onDetachedFromScreen() {
        super.onDetachedFromScreen();
        // Add itself to input manager
        ServiceLocator.locateService(YANInputManager.class).removeEventListener(mInputManagerTouchListener);
    }

    public void setClickListener(YanButtonNodeClickListener clickListener) {
        mClickListener = clickListener;
    }

    private enum YANButtonState {
        PRESSED, DEFAULT
    }

    public YANAtlasTextureRegion getDefaultTextureRegion() {
        return mDefaultTextureRegion;
    }

    public YANAtlasTextureRegion getPressedTextureRegion() {
        return mPressedTextureRegion;
    }

    public YANButtonState getState() {
        return mState;
    }

    public void setState(YANButtonState state) {
        mState = state;
    }

    public void setDefaultTextureRegion(YANAtlasTextureRegion defaultTextureRegion) {
        mDefaultTextureRegion = defaultTextureRegion;
    }

    public void setPressedTextureRegion(YANAtlasTextureRegion pressedTextureRegion) {
        mPressedTextureRegion = pressedTextureRegion;
    }

    public YanButtonNodeClickListener getClickListener() {
        return mClickListener;
    }
}