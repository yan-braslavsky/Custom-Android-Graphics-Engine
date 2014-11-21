package com.example.yan_home.openglengineandroid.screens;

import com.example.yan_home.openglengineandroid.R;
import com.yan.glengine.assets.YANAssetManager;
import com.yan.glengine.assets.atlas.YANTextureAtlas;
import com.yan.glengine.nodes.YANButtonNode;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.screens.YANNodeScreen;
import com.yan.glengine.util.colors.YANColor;

/**
 * Created by Yan-Home on 11/21/2014.
 */
public abstract class BaseGameScreen extends YANNodeScreen {

    private static final int BG_HEX_COLOR = 0x9F9E36;

    protected YANTextureAtlas mAtlas;
    private YANButtonNode mRemoveCardButton;
    private YANButtonNode mResetLayoutButton;
    protected YANTexturedNode mFence;
    private YANTexturedNode mGlade;

    public BaseGameScreen(YANGLRenderer renderer) {
        super(renderer);
        mAtlas = YANAssetManager.getInstance().getLoadedAtlas(R.raw.ui_atlas);
    }

    @Override
    protected void onAddNodesToScene() {
        //add all the other nodes
        addNode(mGlade);
        addNode(mFence);
        addNode(mRemoveCardButton);
        addNode(mResetLayoutButton);
    }


    @Override
    protected void onLayoutNodes() {
        //fence
        float centerX = (getSceneSize().getX() - mFence.getSize().getX()) / 2;
        float centerY = (getSceneSize().getY() - mFence.getSize().getY());
        mFence.setPosition(centerX, centerY);

        //glade
        centerX = (getSceneSize().getX() - mGlade.getSize().getX()) / 2;
        centerY = (getSceneSize().getY() - mGlade.getSize().getY()) / 2;
        mGlade.setPosition(centerX, centerY);

        //buttons
        mResetLayoutButton.setPosition(getSceneSize().getX() - mResetLayoutButton.getSize().getX(), 0);
        mRemoveCardButton.setPosition(0, 0);

    }


    @Override
    protected void onChangeNodesSize() {
        float aspectRatio;

        //fence
        aspectRatio = mFence.getTextureRegion().getWidth() / mFence.getTextureRegion().getHeight();
        mFence.setSize(getSceneSize().getX(), getSceneSize().getX() / aspectRatio);

        //glade
        aspectRatio = mGlade.getTextureRegion().getWidth() / mGlade.getTextureRegion().getHeight();
        float gladeWidth = Math.min(getSceneSize().getX(), getSceneSize().getY()) * 0.9f;
        mGlade.setSize(gladeWidth, gladeWidth / aspectRatio);

        mRemoveCardButton.setSize(150, 150);
        mResetLayoutButton.setSize(150, 150);
    }


    @Override
    protected void onCreateNodes() {

        mFence = new YANTexturedNode(mAtlas.getTextureRegion("fence.png"));

        //fence is on top of cards
        mFence.setSortingLayer(50);
        mGlade = new YANTexturedNode(mAtlas.getTextureRegion("glade.png"));

        mRemoveCardButton = new YANButtonNode(mAtlas.getTextureRegion("call_btn_default.png"), mAtlas.getTextureRegion("call_btn_pressed.png"));
        mRemoveCardButton.setClickListener(new YANButtonNode.YanButtonNodeClickListener() {
            @Override
            public void onButtonClick() {
                onRemoveCardButtonClicked();
            }
        });

        mResetLayoutButton = new YANButtonNode(mAtlas.getTextureRegion("call_btn_default.png"), mAtlas.getTextureRegion("call_btn_pressed.png"));
        mResetLayoutButton.setClickListener(new YANButtonNode.YanButtonNodeClickListener() {
            @Override
            public void onButtonClick() {
                onResetLayoutButtonClicked();
            }
        });

    }

    protected abstract void onResetLayoutButtonClicked();

    protected abstract void onRemoveCardButtonClicked();

    @Override
    public void onSetActive() {
        super.onSetActive();
        getRenderer().setRendererBackgroundColor(YANColor.createFromHexColor(BG_HEX_COLOR));
        YANAssetManager.getInstance().loadTexture(mAtlas.getAtlasImageResourceID());
    }

    @Override
    public void onSetNotActive() {
        super.onSetNotActive();
        YANAssetManager.getInstance().unloadTexture(mAtlas.getAtlasImageResourceID());
    }
}