package com.example.yan_home.openglengineandroid.screens.impl;

import com.example.yan_home.openglengineandroid.nodes.YANTexturedNode;
import com.example.yan_home.openglengineandroid.renderer.YANGLRenderer;
import com.example.yan_home.openglengineandroid.screens.YANNodeScreen;
import com.example.yan_home.openglengineandroid.util.math.Vector2;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANTextureAtlasTestScreen extends YANNodeScreen {

    YANTexturedNode mTexturedNode;

    public YANTextureAtlasTestScreen(YANGLRenderer renderer) {
        super(renderer);
    }

    @Override
    protected void onAddNodesToScene() {
        addNode(mTexturedNode);
    }

    @Override
    protected void onLayoutNodes() {
        mTexturedNode.getPosition().setX(150);
        mTexturedNode.getPosition().setY(150);
    }

    @Override
    protected void onChangeNodesSize() {
        //define size and position of the node
        float spriteSize = Math.min(getSceneSize().getX(), getSceneSize().getY()) * 0.2f;
        mTexturedNode.setSize(new Vector2(spriteSize, spriteSize));
    }

    @Override
    protected void onCreateNodes() {
        mTexturedNode = new YANTexturedNode(getTextureAtlas().getTextureRegion("basketball.png"));
    }

    @Override
    public void onUpdate(float deltaTimeSeconds) {
        //TODO : update nodes state
    }

    @Override
    public void onSetActive() {
        super.onSetActive();
    }

    @Override
    public void onSetNotActive() {
        super.onSetNotActive();
    }
}
