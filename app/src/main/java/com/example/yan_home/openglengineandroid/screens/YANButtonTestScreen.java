package com.example.yan_home.openglengineandroid.screens;

import com.example.yan_home.openglengineandroid.R;
import com.yan.glengine.nodes.YANButtonNode;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.screens.YANNodeScreen;
import com.yan.glengine.util.YANLogger;
import com.yan.glengine.util.math.YANVector2;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANButtonTestScreen extends YANNodeScreen {

    YANButtonNode mButtonNode;

    public YANButtonTestScreen(YANGLRenderer renderer) {
        super(renderer);
    }

    @Override
    protected int getAtlasResourceID() {
       return R.raw.ui_atlas;
    }

    @Override
    protected void onAddNodesToScene() {
        addNode(mButtonNode);
    }

    @Override
    protected void onLayoutNodes() {
        mButtonNode.getPosition().setX(150);
        mButtonNode.getPosition().setY(150);
    }

    @Override
    protected void onChangeNodesSize() {
        //define size and position of the node
        float spriteSize = Math.min(getSceneSize().getX(), getSceneSize().getY()) * 0.2f;
        mButtonNode.setSize(spriteSize, spriteSize);
    }

    @Override
    protected void onCreateNodes() {

        //create node
        mButtonNode = new YANButtonNode(getTextureAtlas().getTextureRegion("call_btn_default.png"), getTextureAtlas().getTextureRegion("call_btn_pressed.png"));

        mButtonNode.setClickListener(new YANButtonNode.YanButtonNodeClickListener() {
            @Override
            public void onButtonClick() {
                YANLogger.log("Button is clicked !");
                YANVector2 newSize = new YANVector2(
                        mButtonNode.getSize().getX() + mButtonNode.getSize().getX() * 0.1f,
                        mButtonNode.getSize().getY() + mButtonNode.getSize().getY() * 0.1f
                );
                mButtonNode.setSize(newSize.getX(),newSize.getY());
            }
        });
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
        mButtonNode.setClickListener(null);
    }
}
