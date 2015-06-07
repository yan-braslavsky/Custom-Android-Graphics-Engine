package glengine.yan.glengine.nodes;

import android.opengl.GLES20;

import glengine.yan.glengine.data.YANVertexArray;
import glengine.yan.glengine.input.YANNodeTouchListener;
import glengine.yan.glengine.programs.ShaderProgram;
import glengine.yan.glengine.renderer.YANGLRenderer;
import glengine.yan.glengine.screens.YANNodeScreen;
import glengine.yan.glengine.util.colors.YANColor;
import glengine.yan.glengine.util.geometry.YANReadOnlyVector2;
import glengine.yan.glengine.util.geometry.YANRectangle;
import glengine.yan.glengine.util.geometry.YANVector2;

import static android.opengl.GLES20.glDisable;

/**
 * Created by Yan-Home on 10/3/2014.
 * <p/>
 * Basic implementation of renderable node.
 */
public abstract class YANBaseNode<T extends ShaderProgram> implements YANIRenderableNode<T> {

    protected static final int POSITION_COMPONENT_COUNT = 2;
    protected static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    protected static final int COLOR_COMPONENT_COUNT = 4;
    protected static final int VALUES_PER_VERTEX = POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT;
    protected static final int BYTES_PER_FLOAT = 4;
    protected static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    protected YANVertexArray vertexArray;
    private YANVector2 mPosition;
    protected YANVector2 mSize;
    private YANNodeTouchListener mNodeTouchListener;
    private YANVector2 mAnchorPoint;
    private float mRotationZ;
    private float mRotationY;
    private float mOpacity;
    private int mSortingLayer;
    private YANColor mOverlayColor;
    private YANNodeScreen.SortingLayerChangeListener mSortingLayerChangeListener;

    protected YANBaseNode() {
        mSize = new YANVector2(0, 0);
        mPosition = new YANVector2();
        mAnchorPoint = new YANVector2();
        mRotationZ = 0;
        mRotationY = 0;
        mOpacity = 1f;

        //no overlay color tint by default
        mOverlayColor = new YANColor(0f, 0f, 0f, 0f);
    }

    @Override
    public void onAttachedToScreen(YANNodeScreen.SortingLayerChangeListener sortingLayerChangeListener) {
        mSortingLayerChangeListener = sortingLayerChangeListener;
    }

    @Override
    public void onDetachedFromScreen() {
        mSortingLayerChangeListener = null;
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
     *
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
     *
     * @param renderer
     */
    protected void onBeforeRendering(YANGLRenderer renderer) {
        //disable culling if needed
        if (getRotationY() != 0)
            glDisable(GLES20.GL_CULL_FACE);

        //TODO : This is not efficient due to fact that we can switch states that are
        //already there.
        //change blending function as needed
        if (getOpacity() < 1.0f) {
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        } else {
            GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        }
    }

    protected void recalculateDimensions() {
        //as an optimisation we will not reallocate  the vertex array if it is already exists
        if (vertexArray == null) {
            vertexArray = new YANVertexArray(createVertexData());
        } else {
            vertexArray.setData(createVertexData());
        }
    }

    protected abstract float[] createVertexData();

    @Override
    public YANRectangle getBoundingRectangle() {

        //TODO : for optimization it can be cached and updated only on size or anchor change
        //calculate offset for anchor point
        float anchorPointOffsetX = getSize().getX() * getAnchorPoint().getX();
        float anchorPointOffsetY = getSize().getY() * getAnchorPoint().getY();

        YANVector2 leftTop = new YANVector2(getPosition().getX() - anchorPointOffsetX,
                getPosition().getY() - anchorPointOffsetY);

        YANVector2 rightBottom = new YANVector2((getPosition().getX() + getSize().getX()) - anchorPointOffsetX,
                (getPosition().getY() + getSize().getY()) - anchorPointOffsetY);

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
        int prevSortingLayer = mSortingLayer;
        mSortingLayer = sortingLayer;
        if (mSortingLayerChangeListener != null) {
            mSortingLayerChangeListener.onNodeChangesSortingLayer(prevSortingLayer, sortingLayer, this);
        }
    }

    @Override
    public int getSortingLayer() {
        return mSortingLayer;
    }

    @Override
    public YANColor getOverlayColor() {
        return mOverlayColor;
    }

    @Override
    public void setOverlayColor(float r, float g, float b, float a) {
        mOverlayColor.setColor(r, g, b, a);
    }
}
