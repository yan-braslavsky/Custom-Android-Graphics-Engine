package com.example.yan_home.openglengineandroid.screens;

import com.example.yan_home.openglengineandroid.nodes.YANIRenderableNode;
import com.example.yan_home.openglengineandroid.renderer.YANGLRenderer;
import com.example.yan_home.openglengineandroid.util.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public abstract class YANNodeScreen implements YANIScreen {

    private final YANGLRenderer mRenderer;
    private List<YANIRenderableNode> mNodeList;

    public YANNodeScreen(YANGLRenderer renderer) {
        mRenderer = renderer;
        mNodeList = new ArrayList<YANIRenderableNode>();

        //at this point nodes can be created
        onCreateNodes();
        onChangeNodesSize();
        onLayoutNodes();
        onAddNodesToScene();
    }

    protected abstract void onAddNodesToScene();

    protected abstract void onLayoutNodes();

    protected abstract void onChangeNodesSize();

    protected abstract void onCreateNodes();

    protected void addNode(YANIRenderableNode node) {
        getNodeList().add(node);
    }

    @Override
    public void onSetActive() {
        //TODO : here screen became active... do something ?
    }

    protected Vector2 getSceneSize() {
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


}
