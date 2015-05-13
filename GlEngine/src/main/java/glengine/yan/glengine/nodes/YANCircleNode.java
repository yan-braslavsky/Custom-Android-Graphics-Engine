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

    public YANCircleNode() {
        super();
        buffer = new float[30 * POSITION_COMPONENT_COUNT];
    }

    @Override
    protected void onRender() {

        int valuesPerVertex = POSITION_COMPONENT_COUNT * Float.SIZE;

        //draw circle as filled shape
//        int vertexCount = buffer.length / valuesPerVertex;
        int vertexCount = 30;
        glDrawArrays(GL_TRIANGLE_FAN, 0, vertexCount);

        //draw circle contours (skip center vertex at start of buffer)
//        glDrawArrays(GL_LINE_LOOP, 2, outerVertexCount);
    }

    @Override
    protected float[] createVertexData() {

//        float halfWidth = getSize().getX() / 2f;
//        float halfHeight = getSize().getY() / 2f;
//
//
//        // Order of coordinates: X, Y, U, V
//        // Triangle Fan
//
//        //first vertex (center)
//        buffer[0] = 0f;
//        buffer[1] = 0f;
//
//
//        //second vertex (bottom left)
//        buffer[2] = -halfWidth;
//        buffer[3] = -halfHeight;
//
//
//        //third vertex (top left)
//        buffer[4] = -halfWidth;
//        buffer[5] = halfHeight;
//
//
//        //fourth vertex (top right)
//        buffer[6] = halfWidth;
//        buffer[7] = halfHeight;
//
//
//        //fifth vertex (bottom right)
//        buffer[8] = halfWidth;
//        buffer[9] = -halfHeight;
//
//
//        //sixth vertex (bottom left)
//        buffer[10] = -halfWidth;
//        buffer[11] = -halfHeight;
//
//        return buffer;

        int vertexCount = 30;
        float radius = getSize().getX() / 2f;
        float center_x = 0.0f;
        float center_y = 0.0f;

//        //create a buffer for vertex data
//        buffer = new float[vertexCount * POSITION_COMPONENT_COUNT ]; // (x,y) for each vertex
        int idx = 0;

        //center vertex for triangle fan
        buffer[idx++] = center_x;
        buffer[idx++] = center_y;

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
        }

        //TODO : create VBO from buffer with glBufferData()

        return buffer;
    }

    @Override
    public void bindData(YANColorShaderProgram shaderProgram) {

        int stride = (POSITION_COMPONENT_COUNT) * BYTES_PER_FLOAT;

        vertexArray.setVertexAttribPointer(
                0,
                shaderProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                stride);

//        vertexArray.setVertexAttribPointer(
//                POSITION_COMPONENT_COUNT,
//                shaderProgram.getColorAttributeLocation(),
//                COLOR_COMPONENT_COUNT,
//                STRIDE);
    }

    public YANColor getColor() {
        return mColor;
    }
}
