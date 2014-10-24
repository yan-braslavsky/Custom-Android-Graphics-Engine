package com.example.yan_home.openglengineandroid.screens.impl;

import com.example.yan_home.openglengineandroid.nodes.YANButtonNode;
import com.example.yan_home.openglengineandroid.renderer.YANGLRenderer;
import com.example.yan_home.openglengineandroid.screens.YANNodeScreen;
import com.example.yan_home.openglengineandroid.util.MyLogger;
import com.example.yan_home.openglengineandroid.util.math.Vector2;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANButtonTestScreen extends YANNodeScreen {

    YANButtonNode mButtonNode;

    public YANButtonTestScreen(YANGLRenderer renderer) {
        super(renderer);
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
        mButtonNode.setSize(new Vector2(spriteSize, spriteSize));
    }

    @Override
    protected void onCreateNodes() {

        //create node
        mButtonNode = new YANButtonNode(getTextureAtlas().getTextureRegion("call_btn_default.png"), getTextureAtlas().getTextureRegion("call_btn_pressed.png"));

        mButtonNode.setClickListener(new YANButtonNode.YanButtonNodeClickListener() {
            @Override
            public void onButtonClick() {
                MyLogger.log("Button is clicked !");
                Vector2 newSize = new Vector2(
                        mButtonNode.getSize().getX() + mButtonNode.getSize().getX() * 0.1f,
                        mButtonNode.getSize().getY() + mButtonNode.getSize().getY() * 0.1f
                );
                mButtonNode.setSize(newSize);
            }
        });
    }

    @Override
    public void onUpdate(float deltaTime) {
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
