package com.yan.glengine.tester.screens;

import glengine.yan.glengine.nodes.YANCircleNode;
import glengine.yan.glengine.renderer.YANGLRenderer;
import glengine.yan.glengine.screens.YANIScreen;

/**
 * Created by Yan-Home on 1/11/2015.
 */
public class CircleTestScreen extends BaseTestScreen {


    private YANCircleNode mCircleNode;
    private float mPercentage = 0f;
    private float mChangeSpeed = 0.002f;

    public CircleTestScreen(YANGLRenderer renderer) {
        super(renderer);
    }

    @Override
    protected void onAddNodesToScene() {
        super.onAddNodesToScene();
        addNode(mCircleNode);
    }

    @Override
    protected void onLayoutNodes() {
        super.onLayoutNodes();
        mCircleNode.setPosition((getSceneSize().getX() - mCircleNode.getSize().getX()) / 2, (getSceneSize().getY() - mCircleNode.getSize().getY()) / 2);
    }

    @Override
    protected void onChangeNodesSize() {
        super.onChangeNodesSize();
        mCircleNode.setSize(300, 300);
        mCircleNode.setColor(0, 0, 0);
    }

    @Override
    protected void onCreateNodes() {
        super.onCreateNodes();
        mCircleNode = new YANCircleNode();
    }

    @Override
    protected YANIScreen onSetNextScreen() {
        return new ColorOverlayTestScreen(getRenderer());
    }

    @Override
    protected YANIScreen onSetPreviousScreen() {
        return null;
    }

    @Override
    public void onUpdate(float deltaTimeSeconds) {
        super.onUpdate(deltaTimeSeconds);
        mPercentage += mChangeSpeed;
        if (mPercentage > 1f) {
            mPercentage = 0f;
            mCircleNode.setClockWiseDraw(!mCircleNode.isClockWiseDraw());
        }

        mCircleNode.setPieCirclePercentage(mPercentage);
    }
}