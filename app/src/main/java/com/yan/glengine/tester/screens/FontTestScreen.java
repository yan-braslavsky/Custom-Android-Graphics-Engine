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
    public static final String COLORED_TEXT_STRING = "I am Colored Text !";
    public static final String SCALED_TEXT_STRING = "I am Scaled Text";
    YANTextNode mColoredTextNode;
    YANTextNode mColoredTextNodePivotCenter;
    YANTextNode mScaledText;
    YANTextNode mAnimatedTextNode;

    float animationChangeAccumulator;
    boolean isDecreasing;
    private YANFont mFont;
    private YANFont mChunkFiveFont;

    public FontTestScreen(YANGLRenderer renderer) {
        super(renderer);
    }

    @Override
    protected void onCreateNodes() {
        super.onCreateNodes();
        mFont = YANAssetManager.getInstance().getLoadedFont("tale_font");
        mChunkFiveFont = YANAssetManager.getInstance().getLoadedFont("chunkfive");

        mColoredTextNode = new YANTextNode(mChunkFiveFont, COLORED_TEXT_STRING.length());
        mColoredTextNode.setTextColor(1f, 0, 0);
        mColoredTextNodePivotCenter = new YANTextNode(mChunkFiveFont, COLORED_TEXT_STRING.length());
        mColoredTextNodePivotCenter.setTextColor(0.5f, 0.7f, 0.3f);
        mScaledText = new YANTextNode(mChunkFiveFont,SCALED_TEXT_STRING.length());
        mScaledText.setText(SCALED_TEXT_STRING);
        mScaledText.setTextScale(2f);
        mAnimatedTextNode = new YANTextNode(mFont,ANIMATION_STRING.length());
        mColoredTextNode.setText(COLORED_TEXT_STRING);
        mColoredTextNodePivotCenter.setText(COLORED_TEXT_STRING);
        mAnimatedTextNode.setText(ANIMATION_STRING);
    }

    @Override
    protected void onChangeNodesSize() {
        super.onChangeNodesSize();
        //Text node size cannot be changed , only scale
    }

    @Override
    public void onSetActive() {
        super.onSetActive();

        YANAssetManager.getInstance().loadTexture(mFont.getGlyphImageFilePath());
        YANAssetManager.getInstance().loadTexture(mChunkFiveFont.getGlyphImageFilePath());
    }

    @Override
    public void onSetNotActive() {
        super.onSetNotActive();

        YANAssetManager.getInstance().unloadTexture(mFont.getGlyphImageFilePath());
        YANAssetManager.getInstance().unloadTexture(mChunkFiveFont.getGlyphImageFilePath());
    }

    @Override
    protected YANIScreen onSetNextScreen() {
        return new ScissoringTestScreen(getRenderer());
    }

    @Override
    protected YANIScreen onSetPreviousScreen() {
        return new ColorOverlayTestScreen(getRenderer());
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
        mColoredTextNode.setPosition(0, 0);
        mColoredTextNodePivotCenter.setAnchorPoint(0.5f,0f);
        mColoredTextNodePivotCenter.setPosition(0, 80);
        mAnimatedTextNode.setPosition(200, 400);
        mScaledText.setPosition(150,500);
    }

    @Override
    protected void onAddNodesToScene() {
        super.onAddNodesToScene();
        addNode(mColoredTextNode);
        addNode(mAnimatedTextNode);
        addNode(mScaledText);
        addNode(mColoredTextNodePivotCenter);
    }


}