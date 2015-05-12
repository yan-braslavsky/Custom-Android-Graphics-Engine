package glengine.yan.glengine.nodes;

import glengine.yan.glengine.programs.YANColorShaderProgram;
import glengine.yan.glengine.util.colors.YANColor;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;

/**
 * Created by yan.braslavsky on 5/12/2015.
 * <p/>
 * Stack overflow reference : http://stackoverflow.com/questions/18140117/how-to-draw-basic-circle-in-opengl-es-2-0-android
 */
public class YANCircleNode extends YANBaseNode<YANColorShaderProgram> {

    private float[] buffer;
    private YANColor mColor = YANColor.createFromHexColor(0xFF00FF);

    @Override
    protected void onRender() {

        int valuesPerVertex = POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT;

        //draw circle as filled shape
        glDrawArrays(GL_TRIANGLE_FAN, 0, buffer.length / valuesPerVertex);

        //draw circle contours (skip center vertex at start of buffer)
//        glDrawArrays(GL_LINE_LOOP, 2, outerVertexCount);
    }

    @Override
    protected float[] createVertexData() {

        int vertexCount = 30;
        float radius = 1.0f;
        float center_x = 0.0f;
        float center_y = 0.0f;

        //create a buffer for vertex data
        buffer = new float[vertexCount * POSITION_COMPONENT_COUNT * COLOR_COMPONENT_COUNT]; // (x,y) for each vertex
        int idx = 0;

        //center vertex for triangle fan
        buffer[idx++] = center_x;
        buffer[idx++] = center_y;

        buffer[idx++] = mColor.getR();
        buffer[idx++] = mColor.getG();
        buffer[idx++] = mColor.getB();
        buffer[idx++] = mColor.getA();

        //outer vertices of the circle
        int outerVertexCount = vertexCount - 1;

        for (int i = 0; i < outerVertexCount; ++i) {
            float percent = (i / (float) (outerVertexCount - 1));
            float rad = (float) (percent * 2 * Math.PI);

            //vertex position
            float outer_x = (float) (center_x + radius * Math.cos(rad));
            float outer_y = (float) (center_y + radius * Math.sin(rad));

            buffer[idx++] = outer_x;
            buffer[idx++] = outer_y;

            buffer[idx++] = mColor.getR();
            buffer[idx++] = mColor.getG();
            buffer[idx++] = mColor.getB();
            buffer[idx++] = mColor.getA();
        }

        //TODO : create VBO from buffer with glBufferData()

        return buffer;
    }

    @Override
    public void bindData(YANColorShaderProgram shaderProgram) {
        vertexArray.setVertexAttribPointer(
                0,
                shaderProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);

        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                shaderProgram.getColorAttributeLocation(),
                COLOR_COMPONENT_COUNT,
                STRIDE);
    }

    public YANColor getColor() {
        return mColor;
    }
}
