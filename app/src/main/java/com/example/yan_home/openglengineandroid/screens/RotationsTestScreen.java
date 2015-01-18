package com.example.yan_home.openglengineandroid.screens;

import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.screens.YANIScreen;

/**
 * Created by Yan-Home on 1/11/2015.
 */
public class RotationsTestScreen extends BaseTestScreen {

    private YANTexturedNode mGreyCock1;
    private YANTexturedNode mGreyCock2;

    private float mRotationY = 0;
    private float mRotationYSpeed = 2f;

    public RotationsTestScreen(YANGLRenderer renderer) {
        super(renderer);
    }


    @Override
    protected void onAddNodesToScene() {
        super.onAddNodesToScene();
        addNode(mGreyCock1);
        addNode(mGreyCock2);
    }

    @Override
    protected void onLayoutNodes() {
        super.onLayoutNodes();
        mGreyCock1.setPosition(getSceneSize().getX() / 2, getSceneSize().getX() / 2);
        mGreyCock2.setPosition(getSceneSize().getX() / 3, getSceneSize().getX() / 2);
    }

    @Override
    protected void onChangeNodesSize() {
        super.onChangeNodesSize();
        mGreyCock1.setSize(100, 100);
        mGreyCock2.setSize(100, 100);
    }

    @Override
    protected void onCreateNodes() {
        super.onCreateNodes();
        mGreyCock1 = new YANTexturedNode(mUiAtlas.getTextureRegion("grey_cock.png"));
        mGreyCock2 = new YANTexturedNode(mUiAtlas.getTextureRegion("grey_cock.png"));
    }

    @Override
    protected YANIScreen onSetNextScreen() {
        return new TweeningTestScreen(getRenderer());
    }

    @Override
    protected YANIScreen onSetPreviousScreen() {
        return new ScissoringTestScreen(getRenderer());
    }

    @Override
    public void onUpdate(float deltaTimeSeconds) {
        super.onUpdate(deltaTimeSeconds);

        mGreyCock1.setRotationY(mRotationY);
        mGreyCock2.setRotationZ(mRotationY);
        mRotationY += mRotationYSpeed;

        if (mRotationY > 360) {
            mRotationY = 0;
        }

    }
}