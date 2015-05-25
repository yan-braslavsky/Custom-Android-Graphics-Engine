/**
 * Excerpted from "OpenGL ES for Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/kbogla for more book information.
 * *
 */
package glengine.yan.glengine.programs;

import android.content.Context;

import com.yan.glengine.R;

import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1f;
import static android.opengl.GLES20.glUniform4fv;
import static android.opengl.GLES20.glUniformMatrix4fv;


public class YANColorShaderProgram extends ShaderProgram {

    // Uniform locations
    private final int uMatrixLocation;
    private final int uColorLocation;
    private final int uOpacityLocation;

    // Attribute locations
    private final int aPositionLocation;

    public YANColorShaderProgram(Context context) {
        super(context, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader);

        // Retrieve uniform locations for the shader program.
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
        uColorLocation = glGetUniformLocation(program, U_COLOR);
        uOpacityLocation = glGetUniformLocation(program, U_OPACITY);

        // Retrieve attribute locations for the shader program.
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
    }

    public void setUniforms(float[] matrix,  float[] color , float opacity) {
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
        glUniform4fv(uColorLocation, 1, color, 0);
        glUniform1f(uOpacityLocation, opacity);
    }

    public int getPositionAttributeLocation() {
        return aPositionLocation;
    }
}