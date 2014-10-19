package com.example.yan_home.openglengineandroid.font_test;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;

import com.example.yan_home.openglengineandroid.GLEngineApp;
import com.example.yan_home.openglengineandroid.R;
import com.example.yan_home.openglengineandroid.util.YANMatrixHelper;
import com.example.yan_home.openglengineandroid.util.YANShaderHelper;
import com.example.yan_home.openglengineandroid.util.YANTextResourceReader;
import com.example.yan_home.openglengineandroid.util.YANTextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;

public class GLTextRenderer implements Renderer {

    public TextManager textManager;

    // Misc
    Context mContext;

    public GLTextRenderer(Context c) {
        mContext = c;
    }

    public void onPause() {
    }

    public void onResume() {
    }

    @Override
    public void onDrawFrame(GL10 unused) {

        // Update the viewProjection matrix, and create an inverted matrix for
        // touch picking.
        Matrix.multiplyMM(YANMatrixHelper.viewProjectionMatrix, 0, YANMatrixHelper.projectionMatrix, 0, YANMatrixHelper.viewMatrix, 0);
        Matrix.invertM(YANMatrixHelper.invertedViewProjectionMatrix, 0, YANMatrixHelper.viewProjectionMatrix, 0);

        YANMatrixHelper.positionObjectInScene(0, 0);
        textManager.Draw(YANMatrixHelper.modelViewProjectionMatrix);
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        // Redo the Viewport, making it fullscreen.
        GLES20.glViewport(0, 0, width, height);

        //set orthographic projection
        Matrix.orthoM(YANMatrixHelper.projectionMatrix, 0, -(width / 2), (width / 2), -(height / 2), (height / 2), 1, 100);

        //fill view matrix
        Matrix.setLookAtM(YANMatrixHelper.viewMatrix, 0, 0f, 0.0f, 2.0f, 0f, 0f, 0f, 0f, 1f, 0f);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Create the image information
        loadTextureIntoMemoryAndBind();
        // Create our texts
        SetupText();

        // Set the clear color
        GLES20.glClearColor(0, 1f, 0, 1f);

        //enable alpha blending
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        //create shader programs
        createImageShaderProgram();
        createTextShaderProgram();

        // Set our shader program
        GLES20.glUseProgram(ShaderHelperTools.sp_Image);
    }

    private void createImageShaderProgram() {

//        //Load shader strings from glsl files
//        String textVertexShader = YANTextResourceReader
//                .readTextFileFromResource(GLEngineApp.getAppContext(), R.raw.text_vertext_shader);
//
//        String textFragmentShader = YANTextResourceReader
//                .readTextFileFromResource(GLEngineApp.getAppContext(), R.raw.text_fragment_shader);

        //create shader program from sources
        ShaderHelperTools.sp_Image = YANShaderHelper.buildProgram(ShaderHelperTools.vs_Image, ShaderHelperTools.fs_Image);
    }

    private void createTextShaderProgram() {
        //Load shader strings from glsl files
        String textVertexShader = YANTextResourceReader
                .readTextFileFromResource(GLEngineApp.getAppContext(), R.raw.text_vertext_shader);

        String textFragmentShader = YANTextResourceReader
                .readTextFileFromResource(GLEngineApp.getAppContext(), R.raw.text_fragment_shader);

        //create shader program from sources
        ShaderHelperTools.sp_Text = YANShaderHelper.buildProgram(textVertexShader, textFragmentShader);
    }

    public void SetupText() {
        // Create our text manager
        textManager = new TextManager();

        // Tell our text manager to use index 0 of textures loaded
        textManager.setTextureID(0);

        // Create our new textobject
        TextObject txt = new TextObject("My Random Message", 10f, 10f);

        // Add it to our manager
        textManager.addText(txt);

        // Prepare the text for rendering
        textManager.PrepareDraw();
    }

    private void loadTextureIntoMemoryAndBind() {
        int textureHandle = YANTextureHelper.loadTexture(GLEngineApp.getAppContext(), R.drawable.font);

        // Set the active texture unit to texture unit 0.
        glActiveTexture(GL_TEXTURE0);

        // Bind the texture to this unit.
        glBindTexture(GL_TEXTURE_2D, textureHandle);
    }


}
