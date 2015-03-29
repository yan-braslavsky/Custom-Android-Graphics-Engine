package com.yan.glengine.tester.screens;

import glengine.yan.glengine.nodes.YANTexturedNode;
import glengine.yan.glengine.nodes.YANTexturedScissorNode;
import glengine.yan.glengine.renderer.YANGLRenderer;
import glengine.yan.glengine.screens.YANIScreen;

/**
 * Created by Yan-Home on 1/11/2015.
 */
public class ScissoringTestScreen extends BaseTestScreen {

    private YANTexturedScissorNode mScissoredCockNode;
    private YANTexturedNode mGreyCock;

    private float mVisibleAreaY = 0;
    private float mVisibleAreaYSpeed = 0.005f;

    public ScissoringTestScreen(YANGLRenderer renderer) {
        super(renderer);
    }


    @Override
    protected void onAddNodesToScene() {
        super.onAddNodesToScene();

        //scissored node will occlude the other
        addNode(mGreyCock);
        addNode(mScissoredCockNode);
    }

    @Override
    protected void onLayoutNodes() {
        super.onLayoutNodes();
        mScissoredCockNode.setPosition(getSceneSize().getX() / 2, getSceneSize().getX() / 2);
        mGreyCock.setPosition(getSceneSize().getX() / 2, getSceneSize().getX() / 2);
    }

    @Override
    protected void onChangeNodesSize() {
        super.onChangeNodesSize();
        mScissoredCockNode.setSize(100, 100);
        mGreyCock.setSize(100, 100);
    }

    @Override
    protected void onCreateNodes() {
        super.onCreateNodes();
        mScissoredCockNode = new YANTexturedScissorNode(mUiAtlas.getTextureRegion("yellow_cock.png"));
        mGreyCock = new YANTexturedNode(mUiAtlas.getTextureRegion("grey_cock.png"));
    }


    @Override
    protected YANIScreen onSetNextScreen() {
        return new RotationsTestScreen(getRenderer());
    }

    @Override
    protected YANIScreen onSetPreviousScreen() {
        return new FontTestScreen(getRenderer());
    }

    @Override
    public void onUpdate(float deltaTimeSeconds) {
        super.onUpdate(deltaTimeSeconds);

        mScissoredCockNode.setVisibleArea(0f, mVisibleAreaY, 1f, 1f);

        mVisibleAreaY += mVisibleAreaYSpeed;
        if (mVisibleAreaY > 1.0f) {
            mVisibleAreaY = 1.0f;
            mVisibleAreaYSpeed *= -1;
        }

        if (mVisibleAreaY < 0f) {
            mVisibleAreaY = 0f;
            mVisibleAreaYSpeed *= -1;
        }
    }
}