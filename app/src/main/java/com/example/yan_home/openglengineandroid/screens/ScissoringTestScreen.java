package com.example.yan_home.openglengineandroid.screens;

import com.yan.glengine.assets.YANAssetManager;
import com.yan.glengine.assets.atlas.YANTextureAtlas;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.nodes.YANTexturedScissorNode;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.screens.YANNodeScreen;
import com.yan.glengine.util.colors.YANColor;

/**
 * Created by Yan-Home on 1/11/2015.
 */
public class ScissoringTestScreen extends YANNodeScreen {

    private YANTextureAtlas mUIAtlas;
    private YANTexturedScissorNode mScissoredCockNode;
    private YANTexturedNode mGreyCock;

    private float mVisibleAreaY = 0;
    private float mVisibleAreaYSpeed = 0.005f;

    public ScissoringTestScreen(YANGLRenderer renderer) {
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
        mScissoredCockNode = new YANTexturedScissorNode(mUIAtlas.getTextureRegion("yellow_cock.png"));
        mGreyCock = new YANTexturedNode(mUIAtlas.getTextureRegion("grey_cock.png"));

        //scissored node will occlude the other
        addNode(mGreyCock);
        addNode(mScissoredCockNode);
    }

    @Override
    protected void onLayoutNodes() {
        //TODO
        mScissoredCockNode.setPosition(getSceneSize().getX() / 2, getSceneSize().getX() / 2);
        mGreyCock.setPosition(getSceneSize().getX() / 2, getSceneSize().getX() / 2);
    }

    @Override
    protected void onChangeNodesSize() {
        //TODO
        mScissoredCockNode.setSize(100, 100);
        mGreyCock.setSize(100, 100);
    }

    @Override
    protected void onCreateNodes() {
        //TODO
    }

    @Override
    public void onUpdate(float deltaTimeSeconds) {
        super.onUpdate(deltaTimeSeconds);

        mScissoredCockNode.setVisibleArea(0f, mVisibleAreaY, 1f, 1f);

        mVisibleAreaY += mVisibleAreaYSpeed;
        if (mVisibleAreaY > 1.0f) {
            mVisibleAreaY = 1.0f;
            mVisibleAreaYSpeed *= -1;
        }

        if (mVisibleAreaY < 0f) {
            mVisibleAreaY = 0f;
            mVisibleAreaYSpeed *= -1;
        }
    }
}