package glengine.yan.glengine.nodes;

import glengine.yan.glengine.programs.ShaderProgram;
import glengine.yan.glengine.renderer.YANGLRenderer;
import glengine.yan.glengine.screens.YANNodeScreen;

/**
 * Created by Yan-Home on 10/3/2014.
 * <p/>
 * Node is a basic renderable element of the engine.
 */
public interface YANIRenderableNode<T extends ShaderProgram> extends YANITransformableNode {

    void bindData(T shaderProgram);

    void render(YANGLRenderer renderer);

    void onAttachedToScreen(YANNodeScreen screen, YANNodeScreen.SortingLayerChangeListener sortingLayerChangeListener);

    void onDetachedFromScreen();

}
