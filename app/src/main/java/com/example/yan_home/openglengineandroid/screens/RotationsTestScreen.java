package com.example.yan_home.openglengineandroid.screens;

import com.yan.glengine.assets.YANAssetManager;
import com.yan.glengine.assets.atlas.YANTextureAtlas;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.screens.YANNodeScreen;
import com.yan.glengine.util.colors.YANColor;

/**
 * Created by Yan-Home on 1/11/2015.
 */
public class RotationsTestScreen extends YANNodeScreen {

    private YANTextureAtlas mUIAtlas;
    private YANTexturedNode mGreyCock;

    private float mRotationY = 0;
    private float mRotationYSpeed = 2f;

    public RotationsTestScreen(YANGLRenderer renderer) {
        super(renderer);

        //get reference to loaded font
        mUIAtlas = YANAssetManager.getInstance().getLoadedAtlas("ui_atlas");
    }

    @Override
    public void onSetActive() {
        super.onSetActive();
        getRenderer().setRendererBackgroundColor(YANColor.createFromHexColor(0x9F9E36));
        YANAssetManager.getInstance().loadTexture(mUIAtlas.getAtlasImageFilePath());
    }

    @Override
    public void onSetNotActive() {
        super.onSetNotActive();
        YANAssetManager.getInstance().unloadTexture(mUIAtlas.getAtlasImageFilePath());
    }

    @Override
    protected void onAddNodesToScene() {
        addNode(mGreyCock);
    }

    @Override
    protected void onLayoutNodes() {
        mGreyCock.setPosition(getSceneSize().getX() / 2, getSceneSize().getX() / 2);
    }

    @Override
    protected void onChangeNodesSize() {
        mGreyCock.setSize(100, 100);
    }

    @Override
    protected void onCreateNodes() {
        mGreyCock = new YANTexturedNode(mUIAtlas.getTextureRegion("grey_cock.png"));
    }

    @Override
    public void onUpdate(float deltaTimeSeconds) {
        super.onUpdate(deltaTimeSeconds);

        mGreyCock.setRotationY(mRotationY);
        mRotationY += mRotationYSpeed;

        if (mRotationY > 360) {
            mRotationY = 0;
        }

    }
}