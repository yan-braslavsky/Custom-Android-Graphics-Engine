package com.yan.glengine.tester.screens;

import glengine.yan.glengine.assets.YANAssetManager;
import glengine.yan.glengine.nodes.YANButtonNode;
import glengine.yan.glengine.nodes.YANTextNode;
import glengine.yan.glengine.renderer.YANGLRenderer;
import glengine.yan.glengine.screens.YANIScreen;
import glengine.yan.glengine.service.ServiceLocator;
import glengine.yan.glengine.util.colors.YANColor;

/**
 * Created by Yan-Home on 1/18/2015.
 */
public class ButtonsTestScreen extends BaseTestScreen {

    private YANTextNode mTitleText;
    private YANButtonNode mSimpleAnchoredButton;
    private YANButtonNode mSimpleButton;
    private YANButtonNode mButtonWithStateChangeAnimator;


    public ButtonsTestScreen(YANGLRenderer renderer) {
        super(renderer);

    }

    @Override
    public void onSetActive() {
        super.onSetActive();
    }


    @Override
    public void onSetNotActive() {
        super.onSetNotActive();
    }

    @Override
    protected void onLayoutNodes() {
        super.onLayoutNodes();

        mTitleText.setPosition(50, 50);
        mSimpleAnchoredButton.setPosition((getSceneSize().getX() - mSimpleAnchoredButton.getSize().getX()) / 2, (getSceneSize().getY() - mSimpleAnchoredButton.getSize().getY()) / 2);
        mSimpleButton.setPosition(200, 200);
        mButtonWithStateChangeAnimator.setPosition(300, 450);
    }

    @Override
    protected void onAddNodesToScene() {
        super.onAddNodesToScene();

        //add text node
        addNode(mTitleText);
        addNode(mSimpleAnchoredButton);
        addNode(mSimpleButton);
        addNode(mButtonWithStateChangeAnimator);
    }

    @Override
    protected void onChangeNodesSize() {
        super.onChangeNodesSize();

        //set size
        mSimpleAnchoredButton.setSize(mUiAtlas.getTextureRegion("call_btn_default.png").getWidth() * 3, mUiAtlas.getTextureRegion("call_btn_default.png").getHeight() * 3);
        mSimpleButton.setSize(mUiAtlas.getTextureRegion("call_btn_default.png").getWidth() * 3, mUiAtlas.getTextureRegion("call_btn_default.png").getHeight() * 3);
        mSimpleAnchoredButton.setAnchorPoint(0.9f, 0.5f);
        mSimpleButton.setAnchorPoint(0f, 0f);
        mButtonWithStateChangeAnimator.setSize(mSimpleAnchoredButton.getSize().getX(),mSimpleAnchoredButton.getSize().getY());

    }

    @Override
    protected void onCreateNodes() {
        super.onCreateNodes();

        //create a text node
        mTitleText = new YANTextNode(ServiceLocator.locateService(YANAssetManager.class).getLoadedFont("standard_font"), "Buttons Test".length());
        mTitleText.setText("Buttons Test");
        mTitleText.setSortingLayer(OVERLAY_SORTING_LAYER);

        mSimpleAnchoredButton = new YANButtonNode(mUiAtlas.getTextureRegion("call_btn_default.png"), mUiAtlas.getTextureRegion("call_btn_pressed.png"));
        mSimpleButton = new YANButtonNode(mUiAtlas.getTextureRegion("call_btn_default.png"), mUiAtlas.getTextureRegion("call_btn_pressed.png"));
        mButtonWithStateChangeAnimator = new YANButtonNode(mUiAtlas.getTextureRegion("call_btn_default.png"), mUiAtlas.getTextureRegion("call_btn_pressed.png"));

        YANColor color = YANColor.createFromHexColor(0xFF5987);
        mButtonWithStateChangeAnimator.setOverlayColor(color.getR(),color.getG(),color.getB(),0.5f);
    }


    @Override
    protected YANIScreen onSetNextScreen() {
        return new CircleTestScreen(getRenderer());
    }

    @Override
    protected YANIScreen onSetPreviousScreen() {
        return null;
    }

    @Override
    public void onUpdate(float deltaTimeSeconds) {
        super.onUpdate(deltaTimeSeconds);
    }
}
