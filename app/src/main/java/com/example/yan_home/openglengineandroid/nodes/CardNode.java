package com.example.yan_home.openglengineandroid.nodes;

import com.yan.glengine.EngineWrapper;
import com.yan.glengine.assets.atlas.YANTextureRegion;
import com.yan.glengine.input.YANInputManager;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.util.geometry.YANRectangle;
import com.yan.glengine.util.geometry.YANVector2;

/**
 * Created by Yan-Home on 10/26/2014.
 */
public class CardNode extends YANTexturedNode {

    public interface CardNodeListener {
        void onCardPicked(CardNode cardNode);

        void onCardReleased(CardNode cardNode);

        void onCardHovered(CardNode cardNode);

        void onCardHoverEnd(CardNode cardNode);
    }

    private boolean isDragged;
    private CardNodeListener mCardNodeListener;
    private YANVector2 mDragOffset;
    private boolean mHovered;


    private YANInputManager.TouchListener mInputManagerTouchListener = new YANInputManager.TouchListener() {
        @Override
        public boolean onTouchDown(float normalizedX, float normalizedY) {

            YANVector2 touchToWorldPoint = YANInputManager.touchToWorld(normalizedX, normalizedY,
                    EngineWrapper.getRenderer().getSurfaceSize().getX(), EngineWrapper.getRenderer().getSurfaceSize().getY());
            YANRectangle boundingRectangle = getBoundingRectangle();
            if (boundingRectangle.contains(touchToWorldPoint)) {
                isDragged = true;

                mDragOffset.setX(getPosition().getX() - touchToWorldPoint.getX());
                mDragOffset.setY(getPosition().getY() - touchToWorldPoint.getY());


                if (mCardNodeListener != null) {
                    mCardNodeListener.onCardPicked(CardNode.this);
                }

                //resetting the anchor point that assumable is 0
                setPosition(touchToWorldPoint.getX() + mDragOffset.getX(),touchToWorldPoint.getY() + mDragOffset.getY());

                return true;
            }

            return false;
        }

        @Override
        public boolean onTouchUp(float normalizedX, float normalizedY) {
            if (intersects(normalizedX, normalizedY)) {

                if (mCardNodeListener != null) {
                    if (isDragged) {
                        mCardNodeListener.onCardReleased(CardNode.this);
                    }

                    if (mHovered) {
                        mCardNodeListener.onCardHoverEnd(CardNode.this);
                    }
                }

                isDragged = false;
                mHovered = false;

                return true;

            }

            //in case card was dragged very fast
            isDragged = false;
            mHovered = false;

            return false;
        }

        @Override
        public boolean onTouchDrag(float normalizedX, float normalizedY) {
            YANVector2 touchToWorldPoint = YANInputManager.touchToWorld(normalizedX, normalizedY,
                    EngineWrapper.getRenderer().getSurfaceSize().getX(), EngineWrapper.getRenderer().getSurfaceSize().getY());
            if (isDragged) {

                //resetting the anchor point that assumable is 0
                setPosition(touchToWorldPoint.getX() + mDragOffset.getX(),touchToWorldPoint.getY() + mDragOffset.getY());

                return true;
            } else {

                if (mCardNodeListener != null) {

                    if (intersects(normalizedX, normalizedY)) {

                        if(!mHovered){
                            //dispatch hover event
                            mCardNodeListener.onCardHovered(CardNode.this);
                            mHovered = true;
                        }

                    }else {
                        mHovered = false;
                        mCardNodeListener.onCardHoverEnd(CardNode.this);
                    }

                }
            }

            return false;
        }

        private boolean intersects(float normalizedX, float normalizedY) {
            return getBoundingRectangle().contains(YANInputManager.touchToWorld(normalizedX, normalizedY,
                    EngineWrapper.getRenderer().getSurfaceSize().getX(), EngineWrapper.getRenderer().getSurfaceSize().getY()));
        }
    };


    public CardNode(YANTextureRegion textureRegion) {
        super(textureRegion);
        mDragOffset = new YANVector2();
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
        mCardNodeListener = null;
    }

    public void setCardNodeListener(CardNodeListener cardNodeListener) {
        mCardNodeListener = cardNodeListener;
    }
}
