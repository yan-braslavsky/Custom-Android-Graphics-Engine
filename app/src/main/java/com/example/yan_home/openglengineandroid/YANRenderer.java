/***
 * Excerpted from "OpenGL ES for Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/kbogla for more book information.
 ***/
package com.example.yan_home.openglengineandroid;

import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;

import com.example.yan_home.openglengineandroid.nodes.INode;
import com.example.yan_home.openglengineandroid.programs.ColorShaderProgram;
import com.example.yan_home.openglengineandroid.programs.TextureShaderProgram;
import com.example.yan_home.openglengineandroid.screens.BaseScreen;
import com.example.yan_home.openglengineandroid.screens.IScreen;
import com.example.yan_home.openglengineandroid.util.MatrixHelper;
import com.example.yan_home.openglengineandroid.util.TextureHelper;
import com.example.yan_home.openglengineandroid.util.colors.YANColor;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class YANRenderer implements Renderer {


    private IScreen mCurrentScreen;

    // shader programs
    private TextureShaderProgram textureProgram;
    private ColorShaderProgram colorProgram;
    private int mLoadedTableTextureHandle;

    public void handleTouchPress(float normalizedX, float normalizedY) {
        //TODO : handle
    }


    public void handleTouchDrag(float normalizedX, float normalizedY) {
        //TODO: handle
    }

    void setActiveScreen(IScreen screen) {

        if (this.mCurrentScreen != null) {
            this.mCurrentScreen.onSetNotActive();
        }

        this.mCurrentScreen = screen;
        mCurrentScreen.onSetActive();

        //TODO : load all textures related to this screen
        for (INode iNode : mCurrentScreen.getNodeList()) {
            mLoadedTableTextureHandle = TextureHelper.loadTexture(GLEngineApp.getAppContext(),
                    iNode.getSpriteResourceId());
        }

    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        setClearColor();
        loadShaderPrograms();
        setActiveScreen(new BaseScreen());
    }

    private void setClearColor() {
        YANColor color = YANColor.createFromHexColor(Color.GRAY);
        GLES20.glClearColor(color.getR(), color.getG(), color.getB(), color.getA());
    }

    private void loadShaderPrograms() {
        textureProgram = new TextureShaderProgram(GLEngineApp.getAppContext());
        colorProgram = new ColorShaderProgram(GLEngineApp.getAppContext());
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {

        // Set the OpenGL viewport to fill the entire surface.
        GLES20.glViewport(0, 0, width, height);

        float aspectRatio = (float) width / (float) height;
        float newWidth = 4;
        float newHeight = newWidth / aspectRatio;

        Matrix.orthoM(MatrixHelper.projectionMatrix, 0, -(newWidth / 2), (newWidth / 2), -(newHeight / 2), (newHeight / 2), 1, 100);

        //fill view matrix
        Matrix.setLookAtM(MatrixHelper.viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f);
        mCurrentScreen.onResize(newWidth, newHeight);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {

        //update screen state
        mCurrentScreen.onUpdate();

        // Clear the rendering surface.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Update the viewProjection matrix, and create an inverted matrix for
        // touch picking.
        Matrix.multiplyMM(MatrixHelper.viewProjectionMatrix, 0, MatrixHelper.projectionMatrix, 0, MatrixHelper.viewMatrix, 0);
        Matrix.invertM(MatrixHelper.invertedViewProjectionMatrix, 0, MatrixHelper.viewProjectionMatrix, 0);

        drawNodes();
    }

    private void drawNodes() {
        for (INode iNode : mCurrentScreen.getNodeList()) {
            MatrixHelper.positionObjectInScene(iNode.getPosition().getX(), iNode.getPosition().getY());
            textureProgram.useProgram();
            textureProgram.setUniforms(MatrixHelper.modelViewProjectionMatrix, mLoadedTableTextureHandle);
            iNode.bindData(textureProgram);
            iNode.draw();
        }
    }


}