package com.example.yan_home.openglengineandroid.screens;

import com.example.yan_home.openglengineandroid.nodes.YANIRenderableNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public abstract class YANBaseScreen implements YANIScreen {

    private List<YANIRenderableNode> mNodeList;

    public YANBaseScreen() {
        mNodeList = new ArrayList<YANIRenderableNode>();
    }

    @Override
    public List<YANIRenderableNode> getNodeList() {
        return mNodeList;
    }


}
