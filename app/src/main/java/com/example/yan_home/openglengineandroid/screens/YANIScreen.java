package com.example.yan_home.openglengineandroid.screens;

import com.example.yan_home.openglengineandroid.nodes.YANIRenderableNode;

import java.util.List;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public interface YANIScreen {
    void onResize(float newWidth, float newHeight);

    void onUpdate();

    void onSetActive();

    List<YANIRenderableNode> getNodeList();

    void onSetNotActive();
}
