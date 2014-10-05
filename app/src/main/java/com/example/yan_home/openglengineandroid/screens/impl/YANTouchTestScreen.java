package com.example.yan_home.openglengineandroid.screens.impl;

import com.example.yan_home.openglengineandroid.R;
import com.example.yan_home.openglengineandroid.assets.YANAssetManager;
import com.example.yan_home.openglengineandroid.assets.YANTexture;
import com.example.yan_home.openglengineandroid.input.YANInputManager;
import com.example.yan_home.openglengineandroid.nodes.YANTexturedNode;
import com.example.yan_home.openglengineandroid.renderer.YANGLRenderer;
import com.example.yan_home.openglengineandroid.screens.YANNodeScreen;
import com.example.yan_home.openglengineandroid.util.MyLogger;
import com.example.yan_home.openglengineandroid.util.math.Vector2;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANTouchTestScreen extends YANNodeScreen {

    private YANTexturedNode mBallNode;

    public YANTouchTestScreen(YANGLRenderer renderer) {
        super(renderer);
    }

    @Override
    protected void onAddNodesToScene() {
        addNode(mBallNode);
    }

    @Override
    protected void onLayoutNodes() {
        //position the ball at bottom left corner
        mBallNode.getPosition().setX(mBallNode.getSize().getX() - (getSceneSize().getX() / 2));
        mBallNode.getPosition().setY(mBallNode.getSize().getY() - (getSceneSize().getY() / 2));
    }

    @Override
    protected void onChangeNodesSize() {
        //define size and position of the node
        float spriteSize = Math.min(getSceneSize().getX(), getSceneSize().getY()) * 0.2f;
        mBallNode.setSize(new Vector2(spriteSize, spriteSize));
    }

    @Override
    protected void onCreateNodes() {

        //create node
        mBallNode = new YANTexturedNode(new YANTexture(R.drawable.football));
        YANInputManager.getInstance().addEventListener(new YANInputManager.TouchListener() {
            private boolean isDragged = false;

            @Override
            public void onTouchDown(float normalizedX, float normalizedY) {
                if (mBallNode.getBoundingRectangle().contains(YANInputManager.touchToWorld(normalizedX, normalizedY, getSceneSize().getX(), getSceneSize().getY()))) {
                    MyLogger.log("node is touched !!!");
                    isDragged = true;
                    mBallNode.setTexture(new YANTexture(R.drawable.volleyball));
                }
            }

            @Override
            public void onTouchUp(float normalizedX, float normalizedY) {
                isDragged = false;
                mBallNode.setTexture(new YANTexture(R.drawable.football));
            }

            @Override
            public void onTouchDrag(float normalizedX, float normalizedY) {
                if (isDragged) {
                    Vector2 worldTouchPoint = YANInputManager.touchToWorld(normalizedX, normalizedY, getSceneSize().getX(), getSceneSize().getY());
                    mBallNode.getPosition().setX(worldTouchPoint.getX());
                    mBallNode.getPosition().setY(worldTouchPoint.getY());
                }
            }
        });
    }

    private void loadScreenTextures() {
        YANAssetManager.getInstance().loadTexture(new YANTexture(R.drawable.football));
        YANAssetManager.getInstance().loadTexture(new YANTexture(R.drawable.volleyball));
    }

    private void unloadScreenTextures() {
        YANAssetManager.getInstance().unloadTexture(new YANTexture(R.drawable.football));
        YANAssetManager.getInstance().unloadTexture(new YANTexture(R.drawable.volleyball));
    }

    @Override
    public void onUpdate() {
        //TODO : update nodes state
    }

    @Override
    public void onSetActive() {
        super.onSetActive();
        loadScreenTextures();
    }

    @Override
    public void onSetNotActive() {
        unloadScreenTextures();
    }
}
