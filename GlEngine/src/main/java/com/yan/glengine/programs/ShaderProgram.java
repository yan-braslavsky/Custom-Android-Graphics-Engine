package com.yan.glengine.programs;

import android.content.Context;

import com.yan.glengine.util.YANShaderHelper;
import com.yan.glengine.util.YANTextResourceReader;

import static android.opengl.GLES20.glUseProgram;

/**
 * Created by Yan-Home on 10/25/2014.
 */
public abstract class ShaderProgram {

    // Uniform constants
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_COLOR = "u_Color";
    protected static final String U_OPACITY = "u_opacity";

    // Attribute constants
    protected static final String A_POSITION = "a_Position";


    // Shader program
    protected final int program;

    protected ShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        // Compile the shaders and link the program.
        program = YANShaderHelper.buildProgram(
                YANTextResourceReader
                        .readTextFileFromResource(context, vertexShaderResourceId),
                YANTextResourceReader
                        .readTextFileFromResource(context, fragmentShaderResourceId));
    }

    public void useProgram() {
        // Set the current OpenGL shader program to this program.
        glUseProgram(program);
    }

}
