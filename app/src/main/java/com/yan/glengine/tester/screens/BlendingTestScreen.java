package com.yan.glengine.tester.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import glengine.yan.glengine.assets.YANAssetManager;
import glengine.yan.glengine.nodes.YANTextNode;
import glengine.yan.glengine.nodes.YANTexturedNode;
import glengine.yan.glengine.renderer.YANGLRenderer;
import glengine.yan.glengine.screens.YANIScreen;
import glengine.yan.glengine.tween.YANTweenNodeAccessor;
import glengine.yan.glengine.util.colors.YANColor;
import glengine.yan.glengine.util.math.YANMathUtils;

/**
 * Created by Yan-Home on 1/18/2015.
 */
public class BlendingTestScreen extends BaseTestScreen {

    private TweenManager mTweenManager;
    private YANTextNode mNodesAmountText;
    private YANTexturedNode mGlade;
    private YANTexturedNode mCard;

    public BlendingTestScreen(YANGLRenderer renderer) {
        super(renderer);
        mTweenManager = new TweenManager();

    }

    @Override
    public void onSetActive() {
        super.onSetActive();
        getRenderer().setRendererBackgroundColor(YANColor.createFromHexColor(0x9F9E36));

        float randomX = YANMathUtils.randomInRange(0, getSceneSize().getX() * 0.7f);
        float randomY = YANMathUtils.randomInRange(0, getSceneSize().getY() * 0.6f);
        float randomRotationZ = YANMathUtils.randomInRange(0, 360);

        Timeline.createSequence()
                .beginParallel()
                .repeatYoyo(500, 0.1f)
                .push(Tween.to(mCard, YANTweenNodeAccessor.OPACITY, 1.0f).target(0.1f))
                .push(Tween.to(mCard, YANTweenNodeAccessor.POSITION_X, 1.0f).target(randomX))
                .push(Tween.to(mCard, YANTweenNodeAccessor.POSITION_Y, 1.0f).target(randomY))
                .push(Tween.to(mCard, YANTweenNodeAccessor.ROTATION_Z_CW, 1.0f).target(randomRotationZ))
                .start(mTweenManager);
    }


    @Override
    public void onSetNotActive() {
        super.onSetNotActive();

        //remove all animations
        mTweenManager.killAll();
    }

    @Override
    protected void onLayoutNodes() {
        super.onLayoutNodes();

        mNodesAmountText.setPosition(50, 50);
        mGlade.setPosition((getSceneSize().getX() - mGlade.getSize().getX()) / 2, (getSceneSize().getY() - mGlade.getSize().getY()) / 2);
        mCard.setPosition(100, 500);
    }

    @Override
    protected void onAddNodesToScene() {
        super.onAddNodesToScene();

        //add text node
        addNode(mNodesAmountText);
        addNode(mGlade);
        addNode(mCard);
    }

    @Override
    protected void onChangeNodesSize() {
        super.onChangeNodesSize();

        //set size
        mGlade.setSize(mUiAtlas.getTextureRegion("glade.png").getWidth(), mUiAtlas.getTextureRegion("glade.png").getHeight());
        mCard.setSize(mCardsAtlas.getTextureRegion("cards_diamonds_queen.png").getWidth(), mCardsAtlas.getTextureRegion("cards_diamonds_queen.png").getHeight());
    }

    @Override
    protected void onCreateNodes() {
        super.onCreateNodes();

        //create a text node
        mNodesAmountText = new YANTextNode(YANAssetManager.getInstance().getLoadedFont("standard_font"));
        mNodesAmountText.setText("blending test");
        mNodesAmountText.setSortingLayer(OVERLAY_SORTING_LAYER);

        //create glade
        mGlade = new YANTexturedNode(mUiAtlas.getTextureRegion("glade.png"));

        //semi transparent card
        mCard = new YANTexturedNode(mCardsAtlas.getTextureRegion("cards_diamonds_queen.png"));
    }


    @Override
    protected YANIScreen onSetNextScreen() {
        return null;
    }

    @Override
    protected YANIScreen onSetPreviousScreen() {
        return new TweeningTestScreen(getRenderer());
    }

    @Override
    public void onUpdate(float deltaTimeSeconds) {
        super.onUpdate(deltaTimeSeconds);
        mTweenManager.update(deltaTimeSeconds);
    }
}
