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

import com.example.yan_home.openglengineandroid.nodes.YANIRenderableNode;
import com.example.yan_home.openglengineandroid.nodes.YANTexturedNode;
import com.example.yan_home.openglengineandroid.programs.YANColorShaderProgram;
import com.example.yan_home.openglengineandroid.programs.YANTextureShaderProgram;
import com.example.yan_home.openglengineandroid.screens.YANBaseScreen;
import com.example.yan_home.openglengineandroid.screens.YANIScreen;
import com.example.yan_home.openglengineandroid.util.YANMatrixHelper;
import com.example.yan_home.openglengineandroid.util.YANTextureHelper;
import com.example.yan_home.openglengineandroid.util.colors.YANColor;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class YANRenderer implements Renderer {

    private YANIScreen mCurrentScreen;

    // shader programs
    private YANTextureShaderProgram textureProgram;
    private YANColorShaderProgram colorProgram;
    private int mLoadedTableTextureHandle;

    public void handleTouchPress(float normalizedX, float normalizedY) {
        //TODO : handle
    }


    public void handleTouchDrag(float normalizedX, float normalizedY) {
        //TODO: handle
    }

    void setActiveScreen(YANIScreen screen) {

        if (this.mCurrentScreen != null) {
            this.mCurrentScreen.onSetNotActive();
        }

        this.mCurrentScreen = screen;
        mCurrentScreen.onSetActive();

        //TODO : load all textures related to this screen
        for (YANIRenderableNode iNode : mCurrentScreen.getNodeList()) {
            mLoadedTableTextureHandle = YANTextureHelper.loadTexture(GLEngineApp.getAppContext(),
                    ((YANTexturedNode) iNode).getSpriteResourceId());
        }

    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        setClearColor();
        loadShaderPrograms();
        setActiveScreen(new YANBaseScreen());
    }

    private void setClearColor() {
        YANColor color = YANColor.createFromHexColor(Color.GRAY);
        GLES20.glClearColor(color.getR(), color.getG(), color.getB(), color.getA());
    }

    private void loadShaderPrograms() {
        textureProgram = new YANTextureShaderProgram(GLEngineApp.getAppContext());
        colorProgram = new YANColorShaderProgram(GLEngineApp.getAppContext());
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {

        // Set the OpenGL viewport to fill the entire surface.
        GLES20.glViewport(0, 0, width, height);

        // Enable blending using pre-multiplied alpha.
        GLES20.glEnable(GL10.GL_BLEND);
        GLES20.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);

        float aspectRatio = (float) width / (float) height;
        float newWidth = 4;
        float newHeight = newWidth / aspectRatio;

        Matrix.orthoM(YANMatrixHelper.projectionMatrix, 0, -(newWidth / 2), (newWidth / 2), -(newHeight / 2), (newHeight / 2), 1, 100);

        //fill view matrix
        Matrix.setLookAtM(YANMatrixHelper.viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f);
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
        Matrix.multiplyMM(YANMatrixHelper.viewProjectionMatrix, 0, YANMatrixHelper.projectionMatrix, 0, YANMatrixHelper.viewMatrix, 0);
        Matrix.invertM(YANMatrixHelper.invertedViewProjectionMatrix, 0, YANMatrixHelper.viewProjectionMatrix, 0);

        drawNodes();
    }

    private void drawNodes() {
        for (YANIRenderableNode iNode : mCurrentScreen.getNodeList()) {
            YANMatrixHelper.positionObjectInScene(iNode.getPosition().getX(), iNode.getPosition().getY());
            textureProgram.useProgram();
            textureProgram.setUniforms(YANMatrixHelper.modelViewProjectionMatrix, mLoadedTableTextureHandle);
            iNode.bindData(textureProgram);
            iNode.draw();
        }
    }


}