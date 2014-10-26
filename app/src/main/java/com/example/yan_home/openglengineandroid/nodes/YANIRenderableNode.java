package com.example.yan_home.openglengineandroid.nodes;

import com.example.yan_home.openglengineandroid.programs.ShaderProgram;
import com.example.yan_home.openglengineandroid.util.math.Rectangle;
import com.example.yan_home.openglengineandroid.util.math.Vector2;

/**
 * Created by Yan-Home on 10/3/2014.
 * <p/>
 * Node is a basic renderable element of the engine.
 */
public interface YANIRenderableNode<T extends ShaderProgram>  {

    void bindData(T shaderProgram);

    void draw();

    Vector2 getPosition();

    Vector2 getSize();

    //TODO : Optimize with scale!
    void setSize(Vector2 size);

    Rectangle getBoundingRectangle();

    void onAttachedToScreen();

    void onDetachedFromScreen();

    Vector2 getAnchorPoint();

    float getRotation();

    /**
     * Defined in degrees
     */
    void setRotation(float rotation);

    float getOpacity();

    void setOpacity(float opacity);
}
