package com.example.yan_home.openglengineandroid.screens.impl;

import com.example.yan_home.openglengineandroid.R;
import com.example.yan_home.openglengineandroid.assets.YANAssetManager;
import com.example.yan_home.openglengineandroid.assets.YANTexture;
import com.example.yan_home.openglengineandroid.assets.atlas.YANTextureAtlas;
import com.example.yan_home.openglengineandroid.nodes.YANButtonNode;
import com.example.yan_home.openglengineandroid.renderer.YANGLRenderer;
import com.example.yan_home.openglengineandroid.screens.YANNodeScreen;
import com.example.yan_home.openglengineandroid.util.MyLogger;
import com.example.yan_home.openglengineandroid.util.math.Vector2;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANButtonTestScreen extends YANNodeScreen {

    private YANButtonNode mButtonNode;

    public YANButtonTestScreen(YANGLRenderer renderer) {
        super(renderer);
    }

    @Override
    protected void onAddNodesToScene() {
        addNode(mButtonNode);
    }

    @Override
    protected void onLayoutNodes() {
//        mButtonNode.getPosition().setX(/*getSceneSize().getX() - */mButtonNode.getSize().getX());
//        mButtonNode.getPosition().setY((/*getSceneSize().getY() - */mButtonNode.getSize().getY()));
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
        mButtonNode = new YANButtonNode(new YANTexture(R.drawable.call_btn_default),
                new YANTexture(R.drawable.call_btn_pressed));

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

    private void loadScreenTextures() {

        YANAssetManager.getInstance().loadTextureAtlas(R.raw.ui_atlas,R.drawable.ui_atlas,new YANAssetManager.YANTextureAtlasLoadListener() {
            @Override
            public void onAtlasLoaded(YANTextureAtlas atlas) {
                MyLogger.log("atlas is loaded");
            }

            @Override
            public void onProgress(float percentLoaded) {

            }
        });

        YANAssetManager.getInstance().loadTexture(new YANTexture(R.drawable.call_btn_default));
        YANAssetManager.getInstance().loadTexture(new YANTexture(R.drawable.call_btn_pressed));
    }

    private void unloadScreenTextures() {
        YANAssetManager.getInstance().unloadTexture(new YANTexture(R.drawable.call_btn_default));
        YANAssetManager.getInstance().unloadTexture(new YANTexture(R.drawable.call_btn_pressed));
    }

    @Override
    public void onUpdate(float deltaTime) {
        //TODO : update nodes state
    }

    @Override
    public void onSetActive() {
        super.onSetActive();
        loadScreenTextures();
    }

    @Override
    public void onSetNotActive() {
        unloadScreenTextures();
    }
}
