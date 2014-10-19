package com.example.yan_home.openglengineandroid.font_test;

import android.opengl.GLES20;

public class ShaderHelperTools {

    // Program variables
    public static int sp_Image;
    public static int sp_Text;

    /* SHADER Image
     *
     * This shader is for rendering 2D images straight from a texture
     * No additional effects.
     *
     */
//    public static final String vs_Image =
//            "uniform mat4 u_Matrix;" +
//                    "attribute vec4 a_Position;" +
//                    "attribute vec2 a_TextureCoordinates;" +
//                    "varying vec2 v_TextureCoordinates;" +
//                    "void main() {" +
//                    "  gl_Position = u_Matrix * a_Position;" +
//                    "  v_TextureCoordinates = a_TextureCoordinates;" +
//                    "}";

//    public static final String fs_Image =
//            "precision mediump float;" +
//                    "varying vec2 v_TextureCoordinates;" +
//                    "uniform sampler2D u_TextureUnit;" +
//                    "void main() {" +
//                    "  gl_FragColor = texture2D( u_TextureUnit, v_TextureCoordinates );" +
//                    "}";

//    /* SHADER Text
//     *
//     * This shader is for rendering 2D text textures straight from a texture
//     * Color and alpha blended.
//     *
//     */
//    public static final String vs_Text =
//            "uniform mat4 uMVPMatrix;" +
//                    "attribute vec4 vPosition;" +
//                    "attribute vec4 a_Color;" +
//                    "attribute vec2 a_texCoord;" +
//                    "varying vec4 v_Color;" +
//                    "varying vec2 v_texCoord;" +
//                    "void main() {" +
//                    "  gl_Position = uMVPMatrix * vPosition;" +
//                    "  v_texCoord = a_texCoord;" +
//                    "  v_Color = a_Color;" +
//                    "}";
//    public static final String fs_Text =
//            "precision mediump float;" +
//                    "varying vec4 v_Color;" +
//                    "varying vec2 v_texCoord;" +
//                    "uniform sampler2D s_texture;" +
//                    "void main() {" +
//                    "  gl_FragColor = texture2D( s_texture, v_texCoord ) * v_Color;" +
//                    "  gl_FragColor.rgb *= v_Color.a;" +
//                    "}";


    public static int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        // return the shader
        return shader;
    }
}
