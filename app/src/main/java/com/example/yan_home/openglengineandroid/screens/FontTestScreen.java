package com.example.yan_home.openglengineandroid.screens;

import com.yan.glengine.assets.YANAssetManager;
import com.yan.glengine.assets.atlas.YANTextureAtlas;
import com.yan.glengine.assets.font.YANFont;
import com.yan.glengine.nodes.YANButtonNode;
import com.yan.glengine.nodes.YANTextNode;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.screens.YANNodeScreen;
import com.yan.glengine.util.colors.YANColor;

/**
 * Created by Yan-Home on 1/11/2015.
 */
public class FontTestScreen extends YANNodeScreen {

    private final YANFont mFont;
    private YANTextNode mTextNode;
    private YANTextureAtlas mUIAtlas;
    private YANButtonNode mButtonNode;

    public FontTestScreen(YANGLRenderer renderer) {
        super(renderer);

        //get reference to loaded font
        mFont = YANAssetManager.getInstance().getLoadedFont("standard_font");
        mUIAtlas = YANAssetManager.getInstance().getLoadedAtlas("ui_atlas");
    }

    @Override
    public void onSetActive() {
        super.onSetActive();
//        getRenderer().setRendererBackgroundColor(YANColor.createFromHexColor(0x9F9E36));
        getRenderer().setRendererBackgroundColor(YANColor.createFromHexColor(0x000000));
        YANAssetManager.getInstance().loadTexture(mFont.getGlyphImageFilePath());
        YANAssetManager.getInstance().loadTexture(mUIAtlas. getAtlasImageFilePath());
    }

    @Override
    public void onSetNotActive() {
        super.onSetNotActive();
        YANAssetManager.getInstance().unloadTexture(mFont.getGlyphImageFilePath());
        YANAssetManager.getInstance().unloadTexture(mUIAtlas. getAtlasImageFilePath());
    }

    @Override
    protected void onAddNodesToScene() {
        mTextNode = new YANTextNode(mFont);
        mTextNode.setText("START");
        mButtonNode = new YANButtonNode(mUIAtlas.getTextureRegion("call_btn_default.png"),mUIAtlas.getTextureRegion("call_btn_pressed.png"));
        addNode(mButtonNode);
        addNode(mTextNode);
    }

    @Override
    protected void onLayoutNodes() {
        //TODO
        mTextNode.setPosition(10,20);
        mButtonNode.setPosition(0,40);
    }

    @Override
    protected void onChangeNodesSize() {
        //TODO
        mButtonNode.setSize(100,100);
    }

    @Override
    protected void onCreateNodes() {
        //TODO
    }
}