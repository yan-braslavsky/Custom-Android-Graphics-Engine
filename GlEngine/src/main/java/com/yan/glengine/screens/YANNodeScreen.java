package com.yan.glengine.screens;

import com.yan.glengine.assets.YANAssetManager;
import com.yan.glengine.assets.atlas.YANTextureAtlas;
import com.yan.glengine.nodes.YANIRenderableNode;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.util.math.YANVector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public abstract class YANNodeScreen implements YANIScreen {

    private final YANGLRenderer mRenderer;
    private List<YANIRenderableNode> mNodeList;
    YANTextureAtlas mAtlas;

    public YANNodeScreen(YANGLRenderer renderer) {
        mRenderer = renderer;
        mNodeList = new ArrayList<>();
        mAtlas = YANAssetManager.getInstance().getLoadedAtlas(getAtlasResourceID());
    }

    protected abstract int getAtlasResourceID();

    protected abstract void onAddNodesToScene();

    protected abstract void onLayoutNodes();

    protected abstract void onChangeNodesSize();

    /**
     * Nodes and their data should be created.
     * No screen size may be yet available
     */
    protected abstract void onCreateNodes();

    protected void addNode(YANIRenderableNode node) {
        node.onAttachedToScreen();
        getNodeList().add(node);
    }

    protected void removeNode(YANIRenderableNode node) {
        node.onDetachedFromScreen();
        getNodeList().remove(node);
    }

    /**
     * puts node at the end of node list ,
     * that makes this node to be drawn on top
     */
    protected void pushNodeToFront(YANIRenderableNode node) {
        getNodeList().remove(node);
        getNodeList().add(node);
    }

    public YANTextureAtlas getTextureAtlas() {
        return mAtlas;
    }

    @Override
    public void onSetActive() {
        loadScreenTextures();
        onCreateNodes();

        if (mRenderer.getSurfaceSize().getX() != 0 && mRenderer.getSurfaceSize().getY() != 0) {
            onChangeNodesSize();
            onLayoutNodes();
        }

        onAddNodesToScene();
    }

    @Override
    public void onSetNotActive() {
        unloadScreenTextures();
    }

    protected YANVector2 getSceneSize() {
        return mRenderer.getSurfaceSize();
    }

    @Override
    public void onResize(float newWidth, float newHeight) {
        onChangeNodesSize();
        onLayoutNodes();
    }

    @Override
    public List<YANIRenderableNode> getNodeList() {
        return mNodeList;
    }

    private void loadScreenTextures() {
        YANAssetManager.getInstance().loadTexture(mAtlas.getAtlasImageResourceID());
    }

    private void unloadScreenTextures() {
        YANAssetManager.getInstance().unloadTexture(mAtlas.getAtlasImageResourceID());
    }

    public YANGLRenderer getRenderer() {
        return mRenderer;
    }
}
