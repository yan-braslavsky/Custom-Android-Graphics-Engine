package com.example.yan_home.openglengineandroid.screens.impl;

import com.example.yan_home.openglengineandroid.nodes.YANButtonNode;
import com.example.yan_home.openglengineandroid.renderer.YANGLRenderer;
import com.example.yan_home.openglengineandroid.screens.YANNodeScreen;
import com.example.yan_home.openglengineandroid.tween.TweenNodeAccessor;
import com.example.yan_home.openglengineandroid.util.math.MathUtils;
import com.example.yan_home.openglengineandroid.util.math.Vector2;

import java.util.ArrayList;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANTweenTestScreen extends YANNodeScreen {

    public static final int BUTTONS_ON_STAGE = 10;
    private ArrayList<YANButtonNode> mBtnList;
    TweenManager mTweenManager;
    private float mButtonOriginalSize;

    public YANTweenTestScreen(YANGLRenderer renderer) {
        super(renderer);

    }

    @Override
    protected void onAddNodesToScene() {

        for (YANButtonNode yanButtonNode : mBtnList) {
            addNode(yanButtonNode);
        }

    }

    @Override
    protected void onLayoutNodes() {
        for (YANButtonNode yanButtonNode : mBtnList) {
            yanButtonNode.getPosition().setX(MathUtils.randomInRange(0, getSceneSize().getX() - mButtonOriginalSize));
            yanButtonNode.getPosition().setY(MathUtils.randomInRange(0, getSceneSize().getY() - mButtonOriginalSize));
            yanButtonNode.setRotation(MathUtils.randomInRange(0, 180));
            yanButtonNode.setOpacity(MathUtils.randomInRange(0.2f, 0.95f));
        }
    }

    @Override
    protected void onChangeNodesSize() {
        for (YANButtonNode yanButtonNode : mBtnList) {
            yanButtonNode.setSize(new Vector2(mButtonOriginalSize, mButtonOriginalSize));
        }
    }

    @Override
    protected void onCreateNodes() {

        mBtnList = new ArrayList<YANButtonNode>();
        mButtonOriginalSize = Math.min(getSceneSize().getX(), getSceneSize().getY()) * 0.2f;

        // We need a mTweenManager to handle every tween.
        mTweenManager = new TweenManager();

        for (int i = 0; i < BUTTONS_ON_STAGE; i++) {
            //create node
            final YANButtonNode btn = new YANButtonNode(getTextureAtlas().getTextureRegion("call_btn_default.png"), getTextureAtlas().getTextureRegion("call_btn_pressed.png"));

            btn.setClickListener(new YANButtonNode.YanButtonNodeClickListener() {
                @Override
                public void onButtonClick() {
                    tweenTheButton(btn);
                }
            });

            mBtnList.add(btn);
            tweenTheButton(btn);

        }


    }

    private void tweenTheButton(YANButtonNode btn) {
        Timeline.createSequence()
                .beginParallel()
                .push(Tween.to(btn, TweenNodeAccessor.POSITION_X, 0.5f).target(MathUtils.randomInRange(0, getSceneSize().getX() - btn.getSize().getX())))
                .push(Tween.to(btn, TweenNodeAccessor.POSITION_Y, 0.5f).target(MathUtils.randomInRange(0, getSceneSize().getY() - btn.getSize().getY())))
                .push(Tween.to(btn, TweenNodeAccessor.ROTATION_CW, 0.5f).target(MathUtils.randomInRange(-360, 360)))
                .push(Tween.to(btn, TweenNodeAccessor.OPACITY, 0.5f).target(MathUtils.randomInRange(0.1f, 1f)))
                .end()
                .push(Tween.to(btn, TweenNodeAccessor.OPACITY, 0.5f).target(MathUtils.randomInRange(0.1f, 1f)))
                .push(Tween.to(btn, TweenNodeAccessor.ROTATION_CW, 0.5f).target(MathUtils.randomInRange(-360, 360)))
                .pushPause(0.5f)
                .push(Tween.to(btn, TweenNodeAccessor.SIZE_X, 0.5f).target(MathUtils.randomInRange(15, 300)))
                .push(Tween.to(btn, TweenNodeAccessor.SIZE_Y, 0.5f).target(MathUtils.randomInRange(15, 300)))
                .push(Tween.to(btn, TweenNodeAccessor.POSITION_X, 0.5f).target(MathUtils.randomInRange(0, getSceneSize().getX() - btn.getSize().getX())))
                .push(Tween.to(btn, TweenNodeAccessor.POSITION_Y, 0.5f).target(MathUtils.randomInRange(0, getSceneSize().getY() - btn.getSize().getY())))
                .push(Tween.to(btn, TweenNodeAccessor.SIZE_X, 0.5f).target(mButtonOriginalSize))
                .push(Tween.to(btn, TweenNodeAccessor.SIZE_Y, 0.5f).target(mButtonOriginalSize))
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
    }
}
