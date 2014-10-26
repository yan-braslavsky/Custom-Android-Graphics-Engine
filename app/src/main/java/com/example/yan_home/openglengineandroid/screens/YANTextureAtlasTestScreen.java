package com.example.yan_home.openglengineandroid.screens;

import com.example.yan_home.openglengineandroid.R;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.screens.YANNodeScreen;
import com.yan.glengine.util.math.YANVector2;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANTextureAtlasTestScreen extends YANNodeScreen {

    YANTexturedNode mTexturedNode;

    public YANTextureAtlasTestScreen(YANGLRenderer renderer) {
        super(renderer);
    }

    @Override
    protected int getAtlasResourceID() {
        return R.raw.ui_atlas;
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
        mTexturedNode.setSize(new YANVector2(spriteSize, spriteSize));
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
