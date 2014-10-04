package com.example.yan_home.openglengineandroid.screens.impl;

import com.example.yan_home.openglengineandroid.R;
import com.example.yan_home.openglengineandroid.assets.YANAssetManager;
import com.example.yan_home.openglengineandroid.assets.YANTexture;
import com.example.yan_home.openglengineandroid.nodes.YANIRenderableNode;
import com.example.yan_home.openglengineandroid.nodes.YANTexturedNode;
import com.example.yan_home.openglengineandroid.screens.YANBaseScreen;
import com.example.yan_home.openglengineandroid.util.math.Vector2;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANPositionTestScreen extends YANBaseScreen {

    private int[] mImageResources = {
            R.drawable.basketball,
            R.drawable.billiardball,
            R.drawable.bowlingball,
            R.drawable.football,
            R.drawable.golfball,
            R.drawable.tennisball,
            R.drawable.volleyball,
            R.drawable.basketball,
            R.drawable.billiardball,
            R.drawable.bowlingball
    };

    public YANPositionTestScreen() {
        super();
    }

    @Override
    public void onResize(float newWidth, float newHeight) {
        super.onResize(newWidth, newHeight);

        float spriteSize = getSceneSize().getX() * 0.1f;
        float delta = (getSceneSize().getX() - spriteSize) / 2;
        int i = 0;

        //position sprites in line
        for (YANIRenderableNode sprite : getNodeList()) {
            sprite.setSize(new Vector2(spriteSize, spriteSize));
            sprite.getPosition().setX(i * spriteSize - delta);
            sprite.getPosition().setY(spriteSize);
            i++;
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
            getNodeList().add(new YANTexturedNode(new YANTexture(mImageResources[i])));
        }
    }

    @Override
    public void onSetNotActive() {
        unloadScreenTextures();
    }
}
