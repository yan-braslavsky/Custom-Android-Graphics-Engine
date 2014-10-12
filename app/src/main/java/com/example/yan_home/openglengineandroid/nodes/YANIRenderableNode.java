package com.example.yan_home.openglengineandroid.nodes;

import com.example.yan_home.openglengineandroid.programs.YANTextureShaderProgram;
import com.example.yan_home.openglengineandroid.util.math.Rectangle;
import com.example.yan_home.openglengineandroid.util.math.Vector2;

/**
 * Created by Yan-Home on 10/3/2014.
 *
 * Node is a basic renderable element of the engine.
 */
public interface YANIRenderableNode {

    void bindData(YANTextureShaderProgram textureProgram);
    void draw();

    Vector2 getPosition();
    Vector2 getSize();
    void setSize(Vector2 size);
    Rectangle getBoundingRectangle();

    void onAttachedToScreen();
    void onDetachedFromScreen();
}
