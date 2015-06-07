package glengine.yan.glengine.screens;

import java.util.List;

import glengine.yan.glengine.nodes.YANIRenderableNode;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public interface YANIScreen {
    void onResize(float newWidth, float newHeight);

    void onUpdate(float deltaTimeSeconds);

    void onSetActive();

    List<YANIRenderableNode> getNodeList();

    void onSetNotActive();

    /**
     * Called when user presses back button
     */
    void onBackPressed();
}
