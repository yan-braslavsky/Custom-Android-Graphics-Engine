package glengine.yan.glengine.nodes;

import glengine.yan.glengine.programs.ShaderProgram;
import glengine.yan.glengine.renderer.YANGLRenderer;
import glengine.yan.glengine.util.geometry.YANReadOnlyVector2;
import glengine.yan.glengine.util.geometry.YANRectangle;
import glengine.yan.glengine.util.geometry.YANVector2;

/**
 * Created by Yan-Home on 10/3/2014.
 * <p/>
 * Node is a basic renderable element of the engine.
 */
public interface YANIRenderableNode<T extends ShaderProgram> {

    void bindData(T shaderProgram);

    void render(YANGLRenderer renderer);

    YANReadOnlyVector2 getPosition();

    YANReadOnlyVector2 getSize();

    void setPosition(float x, float y);


    void setSize(float width, float height);

    YANRectangle getBoundingRectangle();

    void onAttachedToScreen();

    void onDetachedFromScreen();

    YANVector2 getAnchorPoint();

    /**
     * Rotation around Z axis.
     * Defined in degrees.
     */
    float getRotationZ();

    /**
     * Rotation around Z axis.
     * Defined in degrees.
     */
    void setRotationZ(float rotation);

    /**
     * Rotation around Y axis.
     * Defined in degrees.
     */
    float getRotationY();

    /**
     * Rotation around Y axis.
     * Defined in degrees.
     */
    void setRotationY(float rotation);

    float getOpacity();

    void setOpacity(float opacity);

    void setSortingLayer(int sortingLayer);

    int getSortingLayer();
}
