package com.example.yan_home.openglengineandroid.screens;

import com.example.yan_home.openglengineandroid.nodes.YANIRenderableNode;
import com.example.yan_home.openglengineandroid.util.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public abstract class YANBaseScreen implements YANIScreen {

    private List<YANIRenderableNode> mNodeList;
    private Vector2 mSceneSize;

    public YANBaseScreen() {
        mNodeList = new ArrayList<YANIRenderableNode>();
        mSceneSize = new Vector2();
    }

    protected Vector2 getSceneSize() {
        return mSceneSize;
    }

    @Override
    public void onResize(float newWidth, float newHeight) {
        mSceneSize = new Vector2(newWidth, newHeight);
    }

    @Override
    public List<YANIRenderableNode> getNodeList() {
        return mNodeList;
    }


}
