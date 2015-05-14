package com.yan.glengine.tester.screens;

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


    public ColorOverlayTestScreen(YANGLRenderer renderer) {
        super(renderer);
    }

    @Override
    protected void onAddNodesToScene() {
        super.onAddNodesToScene();
        addNode(mGreyCock1);
    }

    @Override
    protected void onLayoutNodes() {
        super.onLayoutNodes();
        mGreyCock1.setPosition((getSceneSize().getX() - mGreyCock1.getSize().getX()) / 2, (getSceneSize().getY() - mGreyCock1.getSize().getY()) / 2);
    }

    @Override
    protected void onChangeNodesSize() {
        super.onChangeNodesSize();
        mGreyCock1.setSize(300, 300);
    }

    @Override
    protected void onCreateNodes() {
        super.onCreateNodes();
        mGreyCock1 = new YANTexturedNode(mUiAtlas.getTextureRegion("grey_cock.png"));
    }

    @Override
    protected YANIScreen onSetNextScreen() {
        return new FontTestScreen(getRenderer());
    }

    @Override
    protected YANIScreen onSetPreviousScreen() {
        return new CircleTestScreen(getRenderer());
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