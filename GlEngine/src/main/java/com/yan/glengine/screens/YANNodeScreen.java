package com.yan.glengine.screens;

import com.yan.glengine.nodes.YANIRenderableNode;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.util.geometry.YANReadOnlyVector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public abstract class YANNodeScreen implements YANIScreen {

    private final YANGLRenderer mRenderer;
    private List<YANIRenderableNode> mNodeList;

    private Comparator<YANIRenderableNode> mSortingLayerComparator;

    public YANNodeScreen(YANGLRenderer renderer) {
        mRenderer = renderer;
        mNodeList = new ArrayList<>();

        mSortingLayerComparator = new Comparator<YANIRenderableNode>() {
            @Override
            public int compare(YANIRenderableNode lhs, YANIRenderableNode rhs) {
                return lhs.getSortingLayer() - rhs.getSortingLayer();
            }
        };
    }

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


    @Override
    public void onSetActive() {
        onCreateNodes();

        if (mRenderer.getSurfaceSize().getX() != 0 && mRenderer.getSurfaceSize().getY() != 0) {
            onChangeNodesSize();
            onLayoutNodes();
        }

        onAddNodesToScene();
    }

    @Override
    public void onSetNotActive() {
    }

    protected YANReadOnlyVector2 getSceneSize() {
        return mRenderer.getSurfaceSize();
    }

    @Override
    public void onResize(float newWidth, float newHeight) {
        onChangeNodesSize();
        onLayoutNodes();
    }

    @Override
    public void onUpdate(float deltaTimeSeconds) {
        //reorder children by sorting layer
        Collections.sort(getNodeList(), mSortingLayerComparator);
    }

    @Override
    public List<YANIRenderableNode> getNodeList() {
        return mNodeList;
    }

    public YANGLRenderer getRenderer() {
        return mRenderer;
    }
}
