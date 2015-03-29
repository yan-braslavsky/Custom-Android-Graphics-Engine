package com.yan.glengine.tester.screens;

import glengine.yan.glengine.assets.YANAssetManager;
import glengine.yan.glengine.assets.font.YANFont;
import glengine.yan.glengine.nodes.YANTextNode;
import glengine.yan.glengine.renderer.YANGLRenderer;
import glengine.yan.glengine.screens.YANIScreen;

/**
 * Created by Yan-Home on 1/11/2015.
 */
public class FontTestScreen extends BaseTestScreen {

    public static final String ANIMATION_STRING = "Animation";
    YANTextNode mTextNode;
    YANTextNode mAnimatedTextNode;

    float animationChangeAccumulator;
    boolean isDecreasing;
    private YANFont mFont;

    public FontTestScreen(YANGLRenderer renderer) {
        super(renderer);
    }

    @Override
    protected void onCreateNodes() {
        super.onCreateNodes();
        mFont = YANAssetManager.getInstance().getLoadedFont("tale_font");

        mTextNode = new YANTextNode(mFont);
        mAnimatedTextNode = new YANTextNode(mFont);
        mTextNode.setText("Hello World !");
        mAnimatedTextNode.setText(ANIMATION_STRING);
    }

    @Override
    public void onSetActive() {
        super.onSetActive();

        YANAssetManager.getInstance().loadTexture(mFont.getGlyphImageFilePath());
    }

    @Override
    public void onSetNotActive() {
        super.onSetNotActive();

        YANAssetManager.getInstance().unloadTexture(mFont.getGlyphImageFilePath());
    }

    @Override
    protected YANIScreen onSetNextScreen() {
        return new ScissoringTestScreen(getRenderer());
    }

    @Override
    protected YANIScreen onSetPreviousScreen() {
        return null;
    }

    @Override
    public void onUpdate(float deltaTimeSeconds) {
        super.onUpdate(deltaTimeSeconds);
        animationChangeAccumulator += deltaTimeSeconds;
        if (animationChangeAccumulator > 0.4) {
            animationChangeAccumulator = 0;

            if (mAnimatedTextNode.getText().equals(" ")) {
                isDecreasing = false;
            } else if (mAnimatedTextNode.getText().equals(ANIMATION_STRING)) {
                isDecreasing = true;
            }

            if (isDecreasing) {
                //decrease
                mAnimatedTextNode.setText(mAnimatedTextNode.getText().substring(0, mAnimatedTextNode.getText().length() - 1));
            } else {
                //increase
                mAnimatedTextNode.setText(ANIMATION_STRING.substring(0, mAnimatedTextNode.getText().length() + 1));
            }
        }
    }

    @Override
    protected void onLayoutNodes() {
        super.onLayoutNodes();
        mTextNode.setPosition(100, 200);
        mAnimatedTextNode.setPosition(200, 400);
    }

    @Override
    protected void onAddNodesToScene() {
        super.onAddNodesToScene();
        addNode(mTextNode);
        addNode(mAnimatedTextNode);
    }


}