package glengine.yan.glengine.nodes;

import android.support.annotation.NonNull;

import java.util.HashMap;

import glengine.yan.glengine.assets.atlas.YANAtlasTextureRegion;
import glengine.yan.glengine.input.YANInputManager;
import glengine.yan.glengine.screens.YANNodeScreen;
import glengine.yan.glengine.service.ServiceLocator;
import glengine.yan.glengine.util.YANPair;
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
    private final HashMap<YANPair<YANButtonState, YANButtonState>, StateChangeAnimator> mFromToButtonStateAnimators;
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
            } else {
                //we must change button state to default , because it is not pressed any more
                changeState(YANButtonState.DEFAULT);
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

    public YANButtonNode(YANAtlasTextureRegion defaultTextureRegion, YANAtlasTextureRegion pressedTextureRegion) {
        super(defaultTextureRegion);
        mDefaultTextureRegion = defaultTextureRegion;
        mPressedTextureRegion = pressedTextureRegion;
        mState = YANButtonState.DEFAULT;
        mFromToButtonStateAnimators = new HashMap<>(YANButtonState.values().length * (YANButtonState.values().length - 1));
    }


    private void changeState(YANButtonState state) {
        if (mState == state)
            return;

        //check for state change animation and run it
        runStateAnimation(mState,state);

        mState = state;
        setTextureRegion(getCurrentStateTextureRegion());
    }

    private YANPair<YANButtonState,YANButtonState> _cachedStatePair = new YANPair<>(null,null);
    private void runStateAnimation(YANButtonState fromState,YANButtonState toState) {
        _cachedStatePair.setFirst(fromState);
        _cachedStatePair.setSecond(toState);
        StateChangeAnimator animator = mFromToButtonStateAnimators.get(_cachedStatePair);
        if (animator != null)
            animator.animate();
    }

    public YANAtlasTextureRegion getCurrentStateTextureRegion() {
        if (mState == YANButtonState.DEFAULT)
            return mDefaultTextureRegion;
        else return mPressedTextureRegion;
    }

    public interface YanButtonNodeClickListener {
        void onButtonClick();
    }


    @Override
    public void onAttachedToScreen(YANNodeScreen screen, YANNodeScreen.SortingLayerChangeListener sortingLayerChangeListener) {
        super.onAttachedToScreen(screen, sortingLayerChangeListener);
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

    public void setStateChangeAnimator(@NonNull final StateChangeAnimator animator) {
        mFromToButtonStateAnimators.put(new YANPair<>(animator.getFromState(), animator.getToState()), animator);
    }

    public YanButtonNodeClickListener getClickListener() {
        return mClickListener;
    }

    public enum YANButtonState {
        PRESSED, DEFAULT
    }

    public interface ButtonAnimation {
        void startButtonAnimation(@NonNull final YANButtonNode buttonNode);
    }

    public StateChangeAnimator createStateChangeAnimator(@NonNull final YANButtonState fromState,
                                                         @NonNull final YANButtonState toState,
                                                         @NonNull final ButtonAnimation animation) {
        return new StateChangeAnimator(fromState, toState, animation);
    }

    public class StateChangeAnimator {
        private final YANButtonState mFromState;
        private final YANButtonState mToState;
        private final ButtonAnimation mAnimation;

        public StateChangeAnimator(@NonNull final YANButtonState fromState,
                                   @NonNull final YANButtonState toState,
                                   @NonNull final ButtonAnimation animation) {
            this.mFromState = fromState;
            this.mToState = toState;
            this.mAnimation = animation;
        }

        public void animate() {
            mAnimation.startButtonAnimation(YANButtonNode.this);
        }

        public YANButtonState getFromState() {
            return mFromState;
        }

        public YANButtonState getToState() {
            return mToState;
        }

        public ButtonAnimation getAnimation() {
            return mAnimation;
        }
    }
}