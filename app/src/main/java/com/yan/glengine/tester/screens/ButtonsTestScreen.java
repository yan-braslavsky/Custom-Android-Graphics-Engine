package com.yan.glengine.tester.screens;

import glengine.yan.glengine.assets.YANAssetManager;
import glengine.yan.glengine.nodes.YANButtonNode;
import glengine.yan.glengine.nodes.YANTextNode;
import glengine.yan.glengine.renderer.YANGLRenderer;
import glengine.yan.glengine.screens.YANIScreen;

/**
 * Created by Yan-Home on 1/18/2015.
 */
public class ButtonsTestScreen extends BaseTestScreen {

    private YANTextNode mTitleText;
    private YANButtonNode mSimpleButton;


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
        mSimpleButton.setPosition((getSceneSize().getX() - mSimpleButton.getSize().getX()) / 2, (getSceneSize().getY() - mSimpleButton.getSize().getY()) / 2);


    }

    @Override
    protected void onAddNodesToScene() {
        super.onAddNodesToScene();

        //add text node
        addNode(mTitleText);
        addNode(mSimpleButton);
    }

    @Override
    protected void onChangeNodesSize() {
        super.onChangeNodesSize();

        //set size
        mSimpleButton.setSize(mUiAtlas.getTextureRegion("call_btn_default.png").getWidth() *3, mUiAtlas.getTextureRegion("call_btn_default.png").getHeight()*3);
        mSimpleButton.setAnchorPoint(0.5f,0.5f);

    }

    @Override
    protected void onCreateNodes() {
        super.onCreateNodes();

        //create a text node
        mTitleText = new YANTextNode(YANAssetManager.getInstance().getLoadedFont("standard_font"));
        mTitleText.setText("Buttons Test");
        mTitleText.setSortingLayer(OVERLAY_SORTING_LAYER);

        //create glade
        mSimpleButton = new YANButtonNode(mUiAtlas.getTextureRegion("call_btn_default.png"), mUiAtlas.getTextureRegion("call_btn_pressed.png"));

    }


    @Override
    protected YANIScreen onSetNextScreen() {
        return null;
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
