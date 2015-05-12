package com.yan.glengine.tester.screens;

import glengine.yan.glengine.nodes.YANCircleNode;
import glengine.yan.glengine.nodes.YANTexturedNode;
import glengine.yan.glengine.renderer.YANGLRenderer;
import glengine.yan.glengine.screens.YANIScreen;

/**
 * Created by Yan-Home on 1/11/2015.
 */
public class ColorOverlayTestScreen extends BaseTestScreen {

    private YANTexturedNode mGreyCock1;
    private double mColorAngle;
    private double mColorChangeSpeed = 0.02f;

    private YANCircleNode mCircleNode;

    public ColorOverlayTestScreen(YANGLRenderer renderer) {
        super(renderer);
    }

    @Override
    protected void onAddNodesToScene() {
        super.onAddNodesToScene();
        addNode(mGreyCock1);
        addNode(mCircleNode);
    }

    @Override
    protected void onLayoutNodes() {
        super.onLayoutNodes();
        mGreyCock1.setPosition((getSceneSize().getX() - mGreyCock1.getSize().getX()) / 2, (getSceneSize().getX() - mGreyCock1.getSize().getY()) / 2);
        mCircleNode.setPosition(mGreyCock1.getPosition().getX(),mGreyCock1.getPosition().getY());
    }

    @Override
    protected void onChangeNodesSize() {
        super.onChangeNodesSize();
        mGreyCock1.setSize(300, 300);
        mCircleNode.setSize(500, 500);
    }

    @Override
    protected void onCreateNodes() {
        super.onCreateNodes();
        mGreyCock1 = new YANTexturedNode(mUiAtlas.getTextureRegion("grey_cock.png"));
        mCircleNode = new YANCircleNode();
    }

    @Override
    protected YANIScreen onSetNextScreen() {
        return new FontTestScreen(getRenderer());
    }

    @Override
    protected YANIScreen onSetPreviousScreen() {
        return null;
    }

    @Override
    public void onUpdate(float deltaTimeSeconds) {
        super.onUpdate(deltaTimeSeconds);

        mColorAngle += mColorChangeSpeed;

        if (mColorAngle > 360) {
            mColorAngle = 0;
        }
        mGreyCock1.setOverlayColor((float) Math.cos(mColorAngle), (float) Math.sin(mColorAngle), (float) Math.cos(mColorAngle), 1f);
    }
}