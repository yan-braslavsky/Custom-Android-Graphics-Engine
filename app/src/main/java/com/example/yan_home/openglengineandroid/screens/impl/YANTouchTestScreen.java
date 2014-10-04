package com.example.yan_home.openglengineandroid.screens.impl;

import com.example.yan_home.openglengineandroid.R;
import com.example.yan_home.openglengineandroid.assets.YANAssetManager;
import com.example.yan_home.openglengineandroid.assets.YANTexture;
import com.example.yan_home.openglengineandroid.input.YANInputManager;
import com.example.yan_home.openglengineandroid.nodes.YANIRenderableNode;
import com.example.yan_home.openglengineandroid.nodes.YANTexturedNode;
import com.example.yan_home.openglengineandroid.screens.YANBaseScreen;
import com.example.yan_home.openglengineandroid.util.YANLogger;
import com.example.yan_home.openglengineandroid.util.math.Rectangle;
import com.example.yan_home.openglengineandroid.util.math.Vector2;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANTouchTestScreen extends YANBaseScreen {

    private int[] mImageResources = {
            R.drawable.football,
    };

    public YANTouchTestScreen() {
        super();
    }

    @Override
    public void onResize(float newWidth, float newHeight) {
        super.onResize(newWidth, newHeight);

        float spriteSize = getSceneSize().getX() * 0.1f;

        //position sprites in line
        for (YANIRenderableNode sprite : getNodeList()) {
            sprite.setSize(new Vector2(spriteSize, spriteSize));
        }

        loadScreenTextures();
    }

    private void loadScreenTextures() {
        for (YANIRenderableNode iNode : getNodeList()) {
            if (iNode instanceof YANTexturedNode) {
                YANTexturedNode node = (YANTexturedNode) iNode;
                YANTexture nodeTexture = node.getTexture();
                //if texture for current node is note loaded , load it into GLContext
                if (!(YANAssetManager.getInstance().isTextureLoaded(nodeTexture))) {
                    YANAssetManager.getInstance().loadTexture(nodeTexture);
                }
            }
        }
    }

    private void unloadScreenTextures() {
        for (YANIRenderableNode iNode : getNodeList()) {
            if (iNode instanceof YANTexturedNode) {
                YANTexturedNode node = (YANTexturedNode) iNode;
                YANTexture nodeTexture = node.getTexture();
                //if texture for current node is note loaded , load it into GLContext
                if (!(YANAssetManager.getInstance().isTextureLoaded(nodeTexture))) {
                    YANAssetManager.getInstance().unloadTexture(nodeTexture);
                }
            }
        }
    }

    @Override
    public void onUpdate() {
        //TODO : update nodes state
    }

    @Override
    public void onSetActive() {
        for (int i = 0; i < mImageResources.length; i++) {
            final YANTexturedNode texturedNode = new YANTexturedNode(new YANTexture(mImageResources[i]));

            YANInputManager.getInstance().addEventListener(new YANInputManager.TouchListener() {
                @Override
                public void onTouch(float normalizedX, float normalizedY) {

                    float realTouchX = normalizedX * (getSceneSize().getX() / 2);
                    float realTouchY = normalizedY * (getSceneSize().getY() / 2);

                    Vector2 realTouchPoint = new Vector2(realTouchX, realTouchY);

                    Vector2 leftTop = new Vector2(texturedNode.getPosition().getX() - texturedNode.getSize().getX() / 2,
                            texturedNode.getPosition().getY() + texturedNode.getSize().getY() / 2);

                    Vector2 rightBottom = new Vector2(texturedNode.getPosition().getX() + texturedNode.getSize().getX() / 2,
                            texturedNode.getPosition().getY() - texturedNode.getSize().getY() / 2);

                    Rectangle boundingRectangle = new Rectangle(leftTop, rightBottom);

                    if (boundingRectangle.contains(realTouchPoint)) {
                        YANLogger.log("Touched the sprite !");
                    }else{
                        texturedNode.getPosition().setX(realTouchX);
                        texturedNode.getPosition().setY(realTouchY);
                    }

                }
            });


            getNodeList().add(texturedNode);
        }
    }

    @Override
    public void onSetNotActive() {
        unloadScreenTextures();
    }
}
