package com.example.yan_home.openglengineandroid.screens;

import com.example.yan_home.openglengineandroid.R;
import com.yan.glengine.nodes.YANButtonNode;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.screens.YANNodeScreen;
import com.yan.glengine.tween.YANTweenNodeAccessor;
import com.yan.glengine.util.math.YANMathUtils;
import com.yan.glengine.util.math.YANVector2;

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
    private TweenManager mTweenManager;
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
    protected int getAtlasResourceID() {
        return R.raw.ui_atlas;
    }

    @Override
    protected void onLayoutNodes() {

        for (YANButtonNode yanButtonNode : mBtnList) {
            yanButtonNode.getPosition().setX(YANMathUtils.randomInRange(0, getSceneSize().getX() - mButtonOriginalSize));
            yanButtonNode.getPosition().setY(YANMathUtils.randomInRange(0, getSceneSize().getY() - mButtonOriginalSize));
            yanButtonNode.setRotation(YANMathUtils.randomInRange(0, 180));
            yanButtonNode.setOpacity(YANMathUtils.randomInRange(0.2f, 0.95f));
        }
    }

    @Override
    protected void onChangeNodesSize() {

        mButtonOriginalSize = Math.min(getSceneSize().getX(), getSceneSize().getY()) * 0.2f;
        for (YANButtonNode yanButtonNode : mBtnList) {
            yanButtonNode.setSize(new YANVector2(mButtonOriginalSize, mButtonOriginalSize));
        }

    }


    @Override
    protected void onCreateNodes() {

        mBtnList = new ArrayList<>();

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

        }

    }

    private void tweenTheButton(YANButtonNode btn) {
        Timeline.createSequence()
                .beginParallel()
                .push(Tween.to(btn, YANTweenNodeAccessor.POSITION_X, 0.5f).target(YANMathUtils.randomInRange(0, getSceneSize().getX() - btn.getSize().getX())))
                .push(Tween.to(btn, YANTweenNodeAccessor.POSITION_Y, 0.5f).target(YANMathUtils.randomInRange(0, getSceneSize().getY() - btn.getSize().getY())))
                .push(Tween.to(btn, YANTweenNodeAccessor.ROTATION_CW, 0.5f).target(YANMathUtils.randomInRange(-360, 360)))
                .push(Tween.to(btn, YANTweenNodeAccessor.OPACITY, 0.5f).target(YANMathUtils.randomInRange(0.1f, 1f)))
                .end()
                .push(Tween.to(btn, YANTweenNodeAccessor.OPACITY, 0.5f).target(YANMathUtils.randomInRange(0.1f, 1f)))
                .push(Tween.to(btn, YANTweenNodeAccessor.ROTATION_CW, 0.5f).target(YANMathUtils.randomInRange(-360, 360)))
                .pushPause(0.5f)
                .push(Tween.to(btn, YANTweenNodeAccessor.SIZE_X, 0.5f).target(YANMathUtils.randomInRange(15, 300)))
                .push(Tween.to(btn, YANTweenNodeAccessor.SIZE_Y, 0.5f).target(YANMathUtils.randomInRange(15, 300)))
                .push(Tween.to(btn, YANTweenNodeAccessor.POSITION_X, 0.5f).target(YANMathUtils.randomInRange(0, getSceneSize().getX() - btn.getSize().getX())))
                .push(Tween.to(btn, YANTweenNodeAccessor.POSITION_Y, 0.5f).target(YANMathUtils.randomInRange(0, getSceneSize().getY() - btn.getSize().getY())))
                .push(Tween.to(btn, YANTweenNodeAccessor.SIZE_X, 0.5f).target(mButtonOriginalSize))
                .push(Tween.to(btn, YANTweenNodeAccessor.SIZE_Y, 0.5f).target(mButtonOriginalSize))
                .start(mTweenManager);
    }

    @Override
    public void onUpdate(float deltaTimeSeconds) {
        mTweenManager.update(deltaTimeSeconds * 1);
    }

    @Override
    public void onSetActive() {
        super.onSetActive();
        for (YANButtonNode yanButtonNode : mBtnList) {
            tweenTheButton(yanButtonNode);
        }

    }

    @Override
    public void onSetNotActive() {
        super.onSetNotActive();
    }
}
