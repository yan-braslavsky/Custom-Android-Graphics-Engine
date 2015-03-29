package glengine.yan.glengine.screens;

import glengine.yan.glengine.nodes.YANIRenderableNode;

import java.util.List;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public interface YANIScreen {
    void onResize(float newWidth, float newHeight);

    void onUpdate(float deltaTimeSeconds);

    void onSetActive();

    List<YANIRenderableNode> getNodeList();

    void onSetNotActive();
}
