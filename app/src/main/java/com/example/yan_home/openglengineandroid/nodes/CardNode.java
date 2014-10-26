package com.example.yan_home.openglengineandroid.nodes;

import com.yan.glengine.EngineWrapper;
import com.yan.glengine.assets.atlas.YANTextureRegion;
import com.yan.glengine.input.YANInputManager;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.util.math.YANRectangle;
import com.yan.glengine.util.math.YANVector2;

/**
 * Created by Yan-Home on 10/26/2014.
 */
public class CardNode extends YANTexturedNode {

    private boolean isDragged;

    private YANInputManager.TouchListener mInputManagerTouchListener = new YANInputManager.TouchListener() {
        @Override
        public boolean onTouchDown(float normalizedX, float normalizedY) {

            YANVector2 touchToWorldPoint = YANInputManager.touchToWorld(normalizedX, normalizedY,
                    EngineWrapper.getRenderer().getSurfaceSize().getX(), EngineWrapper.getRenderer().getSurfaceSize().getY());
            YANRectangle boundingRectangle = getBoundingRectangle();
            if (boundingRectangle.contains(touchToWorldPoint)) {
                isDragged = true;

                //resetting the anchor point that assumable is 0
                getPosition().setX(touchToWorldPoint.getX() - (getSize().getX() / 2));
                getPosition().setY(touchToWorldPoint.getY() - (getSize().getY() / 2));

                return true;
            }

            return false;
        }

        @Override
        public boolean onTouchUp(float normalizedX, float normalizedY) {
            if (getBoundingRectangle().contains(YANInputManager.touchToWorld(normalizedX, normalizedY,
                    EngineWrapper.getRenderer().getSurfaceSize().getX(), EngineWrapper.getRenderer().getSurfaceSize().getY()))) {

                isDragged = false;

                return true;

            }

            return false;
        }

        @Override
        public boolean onTouchDrag(float normalizedX, float normalizedY) {
            YANVector2 touchToWorldPoint = YANInputManager.touchToWorld(normalizedX, normalizedY,
                    EngineWrapper.getRenderer().getSurfaceSize().getX(), EngineWrapper.getRenderer().getSurfaceSize().getY());
            if (isDragged) {

                //resetting the anchor point that assumable is 0
                getPosition().setX(touchToWorldPoint.getX() - (getSize().getX() / 2));
                getPosition().setY(touchToWorldPoint.getY() - (getSize().getY() / 2));

                return true;
            }

            return false;
        }
    };


    public CardNode(YANTextureRegion textureRegion) {
        super(textureRegion);
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

}
