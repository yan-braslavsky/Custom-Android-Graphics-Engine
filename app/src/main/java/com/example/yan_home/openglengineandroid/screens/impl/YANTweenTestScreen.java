package com.example.yan_home.openglengineandroid.screens.impl;

import com.example.yan_home.openglengineandroid.nodes.YANButtonNode;
import com.example.yan_home.openglengineandroid.renderer.YANGLRenderer;
import com.example.yan_home.openglengineandroid.screens.YANNodeScreen;
import com.example.yan_home.openglengineandroid.tween.TweenNodeAccessor;
import com.example.yan_home.openglengineandroid.util.MyLogger;
import com.example.yan_home.openglengineandroid.util.math.Vector2;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANTweenTestScreen extends YANNodeScreen {

    YANButtonNode mButtonNode;
    TweenManager mTweenManager;

    public YANTweenTestScreen(YANGLRenderer renderer) {
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
        mButtonNode.setRotation(0);
    }

    @Override
    protected void onChangeNodesSize() {
        //define size and position of the node
        float spriteSize = Math.min(getSceneSize().getX(), getSceneSize().getY()) * 0.2f;
        mButtonNode.setSize(new Vector2(spriteSize, spriteSize));
    }

    @Override
    protected void onCreateNodes() {

        // We need a mTweenManager to handle every tween.
        mTweenManager = new TweenManager();

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


        Timeline.createSequence().beginParallel()
                    .push(Tween.to(mButtonNode, TweenNodeAccessor.POSITION_X, 0.5f).target(200))
                    .push(Tween.to(mButtonNode, TweenNodeAccessor.POSITION_Y, 0.5f).target(0))
                    .push(Tween.to(mButtonNode, TweenNodeAccessor.ROTATION_CW, 0.5f).target(30))
                .end()
                .pushPause(0.5f)
                .push(Tween.to(mButtonNode, TweenNodeAccessor.POSITION_X, 0.5f).target(500))
                .push(Tween.to(mButtonNode, TweenNodeAccessor.POSITION_Y, 0.5f).target(250))
                .start(mTweenManager);

    }

    @Override
    public void onUpdate(float deltaTimeSeconds) {
        mTweenManager.update(deltaTimeSeconds * 1);
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
