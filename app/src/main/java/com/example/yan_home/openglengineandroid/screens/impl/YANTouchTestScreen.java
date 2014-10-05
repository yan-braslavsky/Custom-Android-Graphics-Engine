package com.example.yan_home.openglengineandroid.screens.impl;

import com.example.yan_home.openglengineandroid.R;
import com.example.yan_home.openglengineandroid.assets.YANAssetManager;
import com.example.yan_home.openglengineandroid.assets.YANTexture;
import com.example.yan_home.openglengineandroid.input.YANInputManager;
import com.example.yan_home.openglengineandroid.nodes.YANIRenderableNode;
import com.example.yan_home.openglengineandroid.nodes.YANTexturedNode;
import com.example.yan_home.openglengineandroid.screens.YANBaseScreen;
import com.example.yan_home.openglengineandroid.util.MyLogger;
import com.example.yan_home.openglengineandroid.util.math.Vector2;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANTouchTestScreen extends YANBaseScreen {

    private int[] mImageResources = {
            R.drawable.football,
            R.drawable.volleyball,
    };

    public YANTouchTestScreen() {
        super();
    }

    @Override
    public void onResize(float newWidth, float newHeight) {
        super.onResize(newWidth, newHeight);

        float spriteSize = Math.min(getSceneSize().getX(),getSceneSize().getY()) * 0.2f;

        //position sprites in line
        for (YANIRenderableNode sprite : getNodeList()) {
            sprite.setSize(new Vector2(spriteSize, spriteSize));
        }

        loadScreenTextures();
    }

    private void loadScreenTextures() {
        for (int imageResource : mImageResources) {
            YANAssetManager.getInstance().loadTexture(new YANTexture(imageResource));
        }
    }

    private void unloadScreenTextures() {
        for (int imageResource : mImageResources) {
            YANAssetManager.getInstance().unloadTexture(new YANTexture(imageResource));
        }
    }

    @Override
    public void onUpdate() {
        //TODO : update nodes state
    }

    @Override
    public void onSetActive() {
        final YANTexturedNode texturedNode = new YANTexturedNode(new YANTexture(mImageResources[0]));

        YANInputManager.getInstance().addEventListener(new YANInputManager.TouchListener() {
            private boolean isDragged = false;

            @Override
            public void onTouchDown(float normalizedX, float normalizedY) {
                if (texturedNode.getBoundingRectangle().contains(YANInputManager.touchToWorld(normalizedX, normalizedY, getSceneSize().getX(), getSceneSize().getY()))) {
                    MyLogger.log("node is touched !!!");
                    isDragged = true;
                    texturedNode.setTexture(new YANTexture(mImageResources[1]));
                }
            }

            @Override
            public void onTouchUp(float normalizedX, float normalizedY) {
                isDragged = false;
                texturedNode.setTexture(new YANTexture(mImageResources[0]));
            }

            @Override
            public void onTouchDrag(float normalizedX, float normalizedY) {
                Vector2 worldTouchPoint = YANInputManager.touchToWorld(normalizedX, normalizedY, getSceneSize().getX(), getSceneSize().getY());
                texturedNode.getPosition().setX(worldTouchPoint.getX());
                texturedNode.getPosition().setY(worldTouchPoint.getY());
            }
        });

        getNodeList().add(texturedNode);
    }

    @Override
    public void onSetNotActive() {
        unloadScreenTextures();
    }
}
