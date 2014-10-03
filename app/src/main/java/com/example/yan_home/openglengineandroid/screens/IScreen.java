package com.example.yan_home.openglengineandroid.screens;

import com.example.yan_home.openglengineandroid.nodes.INode;

import java.util.List;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public interface IScreen {
    void onResize(float newWidth, float newHeight);

    void onUpdate();

    void onSetActive();

    List<INode> getNodeList();

    void onSetNotActive();
}
