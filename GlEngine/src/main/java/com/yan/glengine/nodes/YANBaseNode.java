package com.yan.glengine.nodes;

import android.opengl.GLES20;

import com.yan.glengine.data.YANVertexArray;
import com.yan.glengine.input.YANNodeTouchListener;
import com.yan.glengine.programs.ShaderProgram;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.util.geometry.YANReadOnlyVector2;
import com.yan.glengine.util.geometry.YANRectangle;
import com.yan.glengine.util.geometry.YANVector2;

import static android.opengl.GLES20.glDisable;

/**
 * Created by Yan-Home on 10/3/2014.
 * <p/>
 * Basic implementation of renderable node.
 */
public abstract class YANBaseNode<T extends ShaderProgram> implements YANIRenderableNode<T> {

    protected static final int POSITION_COMPONENT_COUNT = 2;
    protected static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    protected static final int VALUES_PER_VERTEX = POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT;
    protected static final int BYTES_PER_FLOAT = 4;
    protected static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    protected YANVertexArray vertexArray;
    private YANVector2 mPosition;
    private YANVector2 mSize;
    private YANNodeTouchListener mNodeTouchListener;
    private YANVector2 mAnchorPoint;
    private float mRotationZ;
    private float mRotationY;
    private float mOpacity;
    private int mSortingLayer;

    protected YANBaseNode() {
        mSize = new YANVector2(0, 0);
        mPosition = new YANVector2();
        mAnchorPoint = new YANVector2();
        mRotationZ = 0;
        mRotationY = 0;
        mOpacity = 1f;
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
    public YANReadOnlyVector2 getPosition() {
        return mPosition;
    }

    @Override
    public void setPosition(float x, float y) {
        mPosition.setX(x);
        mPosition.setY(y);
    }

    @Override
    public void setSize(float width, float height) {
        mSize.setX(width);
        mSize.setY(height);
        recalculateDimensions();
    }

    @Override
    public YANReadOnlyVector2 getSize() {
        return mSize;
    }


    @Override
    public void render(YANGLRenderer renderer) {

        //call to manipulate possible GL states
        onBeforeRendering(renderer);

        //execute the rendering
        onRender();

        //call to manipulate possible GL states
        onAfterRendering(renderer);
    }

    /**
     * Here actual rendering command is executed glDrawArrays or glDrawElements
     */
    protected abstract void onRender();

    /**
     * This method called right before glDrawArrays is executed.
     * This is a good place to change relevant GL states.
     * @param renderer
     */
    protected void onAfterRendering(YANGLRenderer renderer) {
        //enable culling again
        if (getRotationY() != 0)
            glDisable(GLES20.GL_CULL_FACE);
    }

    /**
     * This method called right after glDrawArrays is executed.
     * This is a good place to change relevant GL states.
     * @param renderer
     */
    protected void onBeforeRendering(YANGLRenderer renderer) {
        //disable culling if needed
        if (getRotationY() != 0)
            glDisable(GLES20.GL_CULL_FACE);
    }

    protected void recalculateDimensions() {
        vertexArray = new YANVertexArray(createVertexData());
    }

    protected abstract float[] createVertexData();

    @Override
    public YANRectangle getBoundingRectangle() {

        YANVector2 leftTop = new YANVector2(getPosition().getX() + getSize().getX() * getAnchorPoint().getX(),
                getPosition().getY() + getSize().getY() * getAnchorPoint().getY());

        YANVector2 rightBottom = new YANVector2(getPosition().getX() + getSize().getX() + getSize().getX() * getAnchorPoint().getX(),
                getPosition().getY() + getSize().getY() + getSize().getY() * getAnchorPoint().getY());

        return new YANRectangle(leftTop, rightBottom);
    }


    public void setNodeTouchListener(YANNodeTouchListener nodeTouchListener) {
        mNodeTouchListener = nodeTouchListener;
    }

    public void setAnchorPoint(float x, float y) {
        mAnchorPoint.setX(x);
        mAnchorPoint.setY(y);
    }

    @Override
    public YANVector2 getAnchorPoint() {
        return mAnchorPoint;
    }

    @Override
    public float getRotationZ() {
        return mRotationZ;
    }

    @Override
    public void setRotationY(float rotationY) {
        mRotationY = rotationY;
    }

    @Override
    public float getRotationY() {
        return mRotationY;
    }

    @Override
    public void setRotationZ(float rotationZ) {
        mRotationZ = rotationZ;
    }

    @Override
    public float getOpacity() {
        return mOpacity;
    }

    @Override
    public void setOpacity(float opacity) {
        mOpacity = opacity;
    }

    @Override
    public void setSortingLayer(int sortingLayer) {
        mSortingLayer = sortingLayer;
    }

    @Override
    public int getSortingLayer() {
        return mSortingLayer;
    }
}
