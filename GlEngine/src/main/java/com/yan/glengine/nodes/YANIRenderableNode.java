package com.yan.glengine.nodes;

import com.yan.glengine.programs.ShaderProgram;
import com.yan.glengine.util.math.YANRectangle;
import com.yan.glengine.util.math.YANVector2;

/**
 * Created by Yan-Home on 10/3/2014.
 * <p/>
 * Node is a basic renderable element of the engine.
 */
public interface YANIRenderableNode<T extends ShaderProgram>  {

    void bindData(T shaderProgram);

    void draw();

    YANVector2 getPosition();

    YANVector2 getSize();

    //TODO : Optimize with scale!
    void setSize(YANVector2 size);

    YANRectangle getBoundingRectangle();

    void onAttachedToScreen();

    void onDetachedFromScreen();

    YANVector2 getAnchorPoint();

    float getRotation();

    /**
     * Defined in degrees
     */
    void setRotation(float rotation);

    float getOpacity();

    void setOpacity(float opacity);
}
