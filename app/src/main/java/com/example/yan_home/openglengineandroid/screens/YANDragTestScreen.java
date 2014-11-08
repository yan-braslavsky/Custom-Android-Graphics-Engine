package com.example.yan_home.openglengineandroid.screens;

import com.example.yan_home.openglengineandroid.R;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.screens.YANNodeScreen;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANDragTestScreen extends YANNodeScreen {

    private YANTexturedNode mBallNode;

    public YANDragTestScreen(YANGLRenderer renderer) {
        super(renderer);
    }

    @Override
    protected int getAtlasResourceID() {
        return R.raw.ui_atlas;
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
        mBallNode.setSize(spriteSize, spriteSize);
    }

    @Override
    protected void onCreateNodes() {

//        //create node
//        mBallNode = new YANTexturedNode(new YANTexture(R.drawable.football));
//        YANInputManager.getInstance().addEventListener(new YANInputManager.TouchListener() {
//            private boolean isDragged = false;
//
//            @Override
//            public void onTouchDown(float normalizedX, float normalizedY) {
//                if (mBallNode.getBoundingRectangle().contains(YANInputManager.touchToWorld(normalizedX, normalizedY, getSceneSize().getX(), getSceneSize().getY()))) {
//                    MyLogger.log("node is touched !!!");
//                    isDragged = true;
//                    mBallNode.setTexture(new YANTexture(R.drawable.volleyball));
//                }
//            }
//
//            @Override
//            public void onTouchUp(float normalizedX, float normalizedY) {
//                isDragged = false;
//                mBallNode.setTexture(new YANTexture(R.drawable.football));
//            }
//
//            @Override
//            public void onTouchDrag(float normalizedX, float normalizedY) {
//                if (isDragged) {
//                    Vector2 worldTouchPoint = YANInputManager.touchToWorld(normalizedX, normalizedY, getSceneSize().getX(), getSceneSize().getY());
//                    mBallNode.getPosition().setX(worldTouchPoint.getX());
//                    mBallNode.getPosition().setY(worldTouchPoint.getY());
//                }
//            }
//        });
    }

    private void loadScreenTextures() {
//        YANAssetManager.getInstance().loadTexture(new YANTexture(R.drawable.football));
//        YANAssetManager.getInstance().loadTexture(new YANTexture(R.drawable.volleyball));
    }

    private void unloadScreenTextures() {
//        YANAssetManager.getInstance().unloadTexture(new YANTexture(R.drawable.football));
//        YANAssetManager.getInstance().unloadTexture(new YANTexture(R.drawable.volleyball));
    }

    @Override
    public void onUpdate(float deltaTimeSeconds) {
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
