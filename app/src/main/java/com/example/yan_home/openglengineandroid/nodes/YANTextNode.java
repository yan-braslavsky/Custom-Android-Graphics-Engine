package com.example.yan_home.openglengineandroid.nodes;

import com.example.yan_home.openglengineandroid.data.YANVertexArray;
import com.example.yan_home.openglengineandroid.programs.ShaderProgram;
import com.example.yan_home.openglengineandroid.util.math.Rectangle;
import com.example.yan_home.openglengineandroid.util.math.Vector2;

/**
 * Created by Yan-Home on 10/26/2014.
 */
public class YANTextNode implements YANIRenderableNode {

    private YANVertexArray mVertexArray;

    public YANTextNode() {
        mVertexArray = new YANVertexArray(createVertexData());
    }

    private float[] createVertexData() {

        //TODO : create data form mnager
        return new float[0];
    }

    @Override
    public void bindData(ShaderProgram shaderProgram) {
        //TODO : bind vertex data to
    }

    @Override
    public void draw() {

    }

    @Override
    public Vector2 getPosition() {
        return null;
    }

    @Override
    public Vector2 getSize() {
        return null;
    }

    @Override
    public void setSize(Vector2 size) {

    }

    @Override
    public Rectangle getBoundingRectangle() {
        return null;
    }

    @Override
    public void onAttachedToScreen() {

    }

    @Override
    public void onDetachedFromScreen() {

    }

    @Override
    public Vector2 getAnchorPoint() {
        return null;
    }

    @Override
    public float getRotation() {
        return 0;
    }

    @Override
    public void setRotation(float rotation) {

    }

    @Override
    public float getOpacity() {
        return 0;
    }

    @Override
    public void setOpacity(float opacity) {

    }
}
