package com.example.yan_home.openglengineandroid.screens;

import com.yan.glengine.assets.YANAssetManager;
import com.yan.glengine.nodes.YANTextNode;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.screens.YANIScreen;

/**
 * Created by Yan-Home on 1/11/2015.
 */
public class FontTestScreen extends BaseTestScreen {

    public static final String ANIMATION_STRING = "Animation";
    YANTextNode mTextNode;
    YANTextNode mAnimatedTextNode;

    float animationChangeAccumulator;
    boolean isDecreasing;

    public FontTestScreen(YANGLRenderer renderer) {
        super(renderer);
    }

    @Override
    protected void onCreateNodes() {
        super.onCreateNodes();
        mTextNode = new YANTextNode(YANAssetManager.getInstance().getLoadedFont("standard_font"));
        mAnimatedTextNode = new YANTextNode(YANAssetManager.getInstance().getLoadedFont("standard_font"));
        mTextNode.setText("Hello World !");
        mAnimatedTextNode.setText(ANIMATION_STRING);
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