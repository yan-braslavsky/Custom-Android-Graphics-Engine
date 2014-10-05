package com.example.yan_home.openglengineandroid.screens.impl;

import com.example.yan_home.openglengineandroid.R;
import com.example.yan_home.openglengineandroid.assets.YANAssetManager;
import com.example.yan_home.openglengineandroid.assets.YANTexture;
import com.example.yan_home.openglengineandroid.input.YANNodeTouchListener;
import com.example.yan_home.openglengineandroid.nodes.YANIRenderableNode;
import com.example.yan_home.openglengineandroid.nodes.YANTexturedNode;
import com.example.yan_home.openglengineandroid.screens.YANBaseScreen;
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

        YANTexture texture = new YANTexture(R.drawable.bowlingball);
        if (!(YANAssetManager.getInstance().isTextureLoaded(texture))) {
            YANAssetManager.getInstance().loadTexture(texture);
        }
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
                if ((YANAssetManager.getInstance().isTextureLoaded(nodeTexture))) {
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

            texturedNode.setNodeTouchListener(new YANNodeTouchListener() {
                @Override
                public void onTouchDown(Vector2 worldTouchPoint) {
                    texturedNode.setTexture(new YANTexture(R.drawable.bowlingball));
                }

                @Override
                public void onTouchUp(Vector2 worldTouchPoint) {
                    texturedNode.setTexture(new YANTexture(R.drawable.football));
                }

                @Override
                public void onTouchDrag(Vector2 worldTouchPoint) {
                    texturedNode.getPosition().setX(worldTouchPoint.getX());
                    texturedNode.getPosition().setY(worldTouchPoint.getY());
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
