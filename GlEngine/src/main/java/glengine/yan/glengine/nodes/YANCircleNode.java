package glengine.yan.glengine.nodes;

import android.opengl.GLES20;

import glengine.yan.glengine.programs.YANColorShaderProgram;
import glengine.yan.glengine.util.colors.YANColor;
import glengine.yan.glengine.util.math.YANMathUtils;

import static android.opengl.GLES20.glDrawArrays;

/**
 * Created by yan.braslavsky on 5/12/2015.
 */
public class YANCircleNode extends YANBaseNode<YANColorShaderProgram> {


    public static final int CIRCLE_OUTER_VERTEX_COUNT = 360;

    //We need one additional vertex for center and another for closing the loop
    public static final int TOTAL_VERTEX_COUNT = CIRCLE_OUTER_VERTEX_COUNT + 2;
    private float[] buffer;
    private YANColor mColor;

    //We are using this value to  draw partial pie like circle
    //if the percentage is full full circle will be drawn
    private float mPieCirclePercentage;

    //defines in what direction the circlie is drawn
    private boolean clockWiseDraw;

    public YANCircleNode() {
        super();
        buffer = new float[TOTAL_VERTEX_COUNT * POSITION_COMPONENT_COUNT];
        mPieCirclePercentage = 1.0f;
        clockWiseDraw = true;
        mColor = YANColor.createFromHexColor(0xFFFFFF);
    }

    @Override
    protected void onRender() {
        //draw circle as filled shape
        glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, TOTAL_VERTEX_COUNT);
    }

    @Override
    protected float[] createVertexData() {

        float radius = getSize().getX() / 2f;
        float centerX = 0.0f;
        float centerY = 0.0f;
        int index = 0;

        //center vertex for triangle fan
        buffer[index++] = centerX;
        buffer[index++] = centerY;

        //we choosing step in degrees according to outer vertex count
        float degreeStep = 360f / CIRCLE_OUTER_VERTEX_COUNT;

        //the final matrix is getting offset , that is why we drawing with initial
        ///rotation offset
        float rotationOffset = 270f;

        //outer vertices of the circle
        int verticesToDraw = (clockWiseDraw) ? CIRCLE_OUTER_VERTEX_COUNT : (int) ((float)CIRCLE_OUTER_VERTEX_COUNT - ((1f - mPieCirclePercentage) * (float) CIRCLE_OUTER_VERTEX_COUNT));
        int startingVertex = (clockWiseDraw) ? (int) ((1f - mPieCirclePercentage) * (float) CIRCLE_OUTER_VERTEX_COUNT) : 0;

        for (int i = startingVertex; i < verticesToDraw; ++i) {
            float rad = (float) Math.toRadians(rotationOffset - (degreeStep * (float) i));
            buffer[index++] = (float) (centerX + radius * Math.cos(rad));
            buffer[index++] = (float) (centerY + radius * Math.sin(rad));
        }

        //when we drawing a full circle we want to close the loop
        //close the loop with the first drawn vertex
        if (mPieCirclePercentage == 1.0f) {
            buffer[index++] = buffer[2];
            buffer[index++] = buffer[3];
        }

        //fill the rest of the buffer with zero
        while (index < buffer.length)
            buffer[index++] = 0f;

        return buffer;
    }

    @Override
    public void setOverlayColor(float r, float g, float b, float a) {
        throw new UnsupportedOperationException("Overlay color is not yet implemented for this node");
    }

    @Override
    public void bindData(YANColorShaderProgram shaderProgram) {

        int stride = POSITION_COMPONENT_COUNT * BYTES_PER_FLOAT;
        vertexArray.setVertexAttribPointer(
                0,
                shaderProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                stride);
    }


    public float getPieCirclePercentage() {
        return mPieCirclePercentage;
    }

    /**
     * Used to draw pie like circle.
     * When percentage is full (1.0) , then full circle will be drawn.
     *
     * @param pieCirclePercentage must be between 0.0f - 1.0f <br/>
     *                            Input with incorrect range will be clamped to 0.0f - 1.0f;
     */
    public void setPieCirclePercentage(float pieCirclePercentage) {
        //we are clamping the input
        pieCirclePercentage = YANMathUtils.clamp(pieCirclePercentage, 0f, 1f);

        mPieCirclePercentage = pieCirclePercentage;
        recalculateDimensions();
    }

    /**
     * Indicates the direction of circle draw
     *
     * @return true if drawn in clockwise order
     */
    public boolean isClockWiseDraw() {
        return clockWiseDraw;
    }

    /**
     * Indicates the direction of circle draw
     *
     * @param clockWiseDraw true if drawn in clockwise order , false if drawn in counterclockwise
     */
    public void setClockWiseDraw(boolean clockWiseDraw) {
        this.clockWiseDraw = clockWiseDraw;
    }

    public void setColor(float r, float g, float b) {
        mColor.setColor(r, g, b, 1f);
    }

    public YANColor getColor() {
        return mColor;
    }

}
