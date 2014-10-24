package com.example.yan_home.openglengineandroid.nodes;

import com.example.yan_home.openglengineandroid.data.YANVertexArray;
import com.example.yan_home.openglengineandroid.input.YANNodeTouchListener;
import com.example.yan_home.openglengineandroid.programs.YANTextureShaderProgram;
import com.example.yan_home.openglengineandroid.util.math.Rectangle;
import com.example.yan_home.openglengineandroid.util.math.Vector2;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;

/**
 * Created by Yan-Home on 10/3/2014.
 * <p/>
 * Basic implementation of renderable node.
 */
public abstract class YANBaseNode implements YANIRenderableNode {

    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    public static final int BYTES_PER_FLOAT = 4;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    private YANVertexArray vertexArray;
    private Vector2 mPosition;
    private Vector2 mSize;
    private YANNodeTouchListener mNodeTouchListener;
    private Vector2 mAnchorPoint;


    protected YANBaseNode() {
        mSize = new Vector2(1, 2);
        mPosition = new Vector2();
        mAnchorPoint = new Vector2();
        recalculateDimensions();
    }

    @Override
    public void onAttachedToScreen() {
        //TODO : Override
    }

    @Override
    public void onDetachedFromScreen() {
        //TODO : Override
    }


    @Override
    public Vector2 getPosition() {
        return mPosition;
    }

    @Override
    public void setSize(Vector2 size) {
        mSize = size;
        recalculateDimensions();
    }

    @Override
    public Vector2 getSize() {
        return mSize;
    }

    @Override
    public void bindData(YANTextureShaderProgram textureProgram) {
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

    private float[] createVertexData() {
        float halfWidth = mSize.getX() / 2f;
        float halfHeight = mSize.getY() / 2f;
        return new float[]{
                // Order of coordinates: X, Y, S, T
                // Triangle Fan

                //first vertex (center)
                0f, 0f, 0.5f, 0.5f,

                //second vertex (bottom left)
                -halfWidth, -halfHeight, 0f, 0f,

                //third vertex (top left)
                -halfWidth, halfHeight, 0f, 1.0f,

                //fourth vertex (top right)
                halfWidth, halfHeight, 1f, 1.0f,

                //fifth vertex (bottom right)
                halfWidth, -halfHeight, 1.0f, 0f,

                //sixth vertex (bottom left)
                -halfWidth, -halfHeight, 0f, 0f,
        };
    }

    private void recalculateDimensions() {
        vertexArray = new YANVertexArray(createVertexData());
    }

    @Override
    public Rectangle getBoundingRectangle() {

        Vector2 leftTop = new Vector2(getPosition().getX() + getSize().getX() * getAnchorPoint().getX(),
                getPosition().getY() + getSize().getY() * getAnchorPoint().getY());

        Vector2 rightBottom = new Vector2(getPosition().getX() + getSize().getX() + getSize().getX() * getAnchorPoint().getX(),
                getPosition().getY() + getSize().getY()  + getSize().getY() * getAnchorPoint().getY());

        return new Rectangle(leftTop, rightBottom);
    }

    public void setNodeTouchListener(YANNodeTouchListener nodeTouchListener) {
        mNodeTouchListener = nodeTouchListener;
    }

    public void setAnchorPoint(float x, float y) {
        mAnchorPoint.setX(x);
        mAnchorPoint.setY(y);
    }

    @Override
    public Vector2 getAnchorPoint() {
        return mAnchorPoint;
    }


}
