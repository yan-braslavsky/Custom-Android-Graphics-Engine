package com.example.yan_home.openglengineandroid.screens;

import com.yan.glengine.assets.YANAssetManager;
import com.yan.glengine.assets.font.YANFont;
import com.yan.glengine.nodes.YANTextNode;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.screens.YANNodeScreen;
import com.yan.glengine.util.colors.YANColor;

/**
 * Created by Yan-Home on 1/11/2015.
 */
public class FontTestScreen extends YANNodeScreen {

    private final YANFont mFont;

    public FontTestScreen(YANGLRenderer renderer) {
        super(renderer);

        //get reference to loaded font
        mFont = YANAssetManager.getInstance().getLoadedFont("standard_font");
    }

    @Override
    public void onSetActive() {
        super.onSetActive();
        getRenderer().setRendererBackgroundColor(YANColor.createFromHexColor(0x9F9E36));
        YANAssetManager.getInstance().loadTexture(mFont.getTextureFile());
    }

    @Override
    public void onSetNotActive() {
        super.onSetNotActive();
        YANAssetManager.getInstance().unloadTexture(mFont.getTextureFile());
    }

    @Override
    protected void onAddNodesToScene() {
        YANTextNode textNode = new YANTextNode(mFont);
        textNode.setText("Hello");
        textNode.setSize(250, 250);
        addNode(textNode);
    }

    @Override
    protected void onLayoutNodes() {
        //TODO
    }

    @Override
    protected void onChangeNodesSize() {
        //TODO

    }

    @Override
    protected void onCreateNodes() {
        //TODO
    }
}