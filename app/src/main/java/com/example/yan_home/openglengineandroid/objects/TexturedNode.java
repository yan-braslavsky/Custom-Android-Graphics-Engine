/***
 * Excerpted from "OpenGL ES for Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/kbogla for more book information.
 ***/
package com.example.yan_home.openglengineandroid.objects;


import com.example.yan_home.openglengineandroid.constants.Constants;
import com.example.yan_home.openglengineandroid.data.VertexArray;
import com.example.yan_home.openglengineandroid.nodes.INode;
import com.example.yan_home.openglengineandroid.programs.TextureShaderProgram;
import com.example.yan_home.openglengineandroid.util.math.Vector2;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;

public class TexturedNode implements INode {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT;

    private Vector2 mPosition;

    private static final float[] VERTEX_DATA = {
            // Order of coordinates: X, Y, S, T
            // Triangle Fan
            0f, 0f, 0.5f, 0.5f,
            -0.5f, -0.8f, 0f, 0.9f,
            0.5f, -0.8f, 1f, 0.9f,
            0.5f, 0.8f, 1f, 0.1f,
            -0.5f, 0.8f, 0f, 0.1f,
            -0.5f, -0.8f, 0f, 0.9f};

    private final VertexArray vertexArray;
    private final int spriteResourceId;

    @Override
    public int getSpriteResourceId() {
        return spriteResourceId;
    }

    @Override
    public Vector2 getPosition() {
        return mPosition;
    }

    public TexturedNode(int spriteResourceId) {
        this.spriteResourceId = spriteResourceId;
        vertexArray = new VertexArray(VERTEX_DATA);
        mPosition = new Vector2();
    }

    @Override
    public void bindData(TextureShaderProgram textureProgram) {
        vertexArray.setVertexAttribPointer(
                0,
                textureProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);

        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                textureProgram.getTextureCoordinatesAttributeLocation(),
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE);
    }

    @Override
    public void draw() {
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
    }



}