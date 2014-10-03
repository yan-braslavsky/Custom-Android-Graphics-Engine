/***
 * Excerpted from "OpenGL ES for Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/kbogla for more book information.
 ***/
package com.example.yan_home.openglengineandroid;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;

import com.example.yan_home.openglengineandroid.nodes.INode;
import com.example.yan_home.openglengineandroid.programs.ColorShaderProgram;
import com.example.yan_home.openglengineandroid.programs.TextureShaderProgram;
import com.example.yan_home.openglengineandroid.screens.BaseScreen;
import com.example.yan_home.openglengineandroid.screens.IScreen;
import com.example.yan_home.openglengineandroid.util.TextureHelper;
import com.example.yan_home.openglengineandroid.util.colors.YANColor;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class AppRenderer implements Renderer {
    private final Context context;
    private IScreen mCurrentScreen;

    // matricies that used for vertex calculations
    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];
    private final float[] invertedViewProjectionMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];


    // shader programs
    private TextureShaderProgram textureProgram;
    private ColorShaderProgram colorProgram;
    private int mLoadedTableTextureHandle;

    public AppRenderer(Context activityCtx) {
        this.context = activityCtx;

    }

    public void handleTouchPress(float normalizedX, float normalizedY) {
        //TODO : handle
    }


    public void handleTouchDrag(float normalizedX, float normalizedY) {
        //TODO: handle
    }

    void setActiveScreen(IScreen screen){

        if(this.mCurrentScreen != null){
            this.mCurrentScreen.onSetNotActive();
        }

        this.mCurrentScreen = screen;
        mCurrentScreen.onSetActive();

        //TODO : load all textures related to this screen
        for (INode iNode : mCurrentScreen.getNodeList()) {
            mLoadedTableTextureHandle = TextureHelper.loadTexture(context,
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
        textureProgram = new TextureShaderProgram(context);
        colorProgram = new ColorShaderProgram(context);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {

        // Set the OpenGL viewport to fill the entire surface.
        GLES20.glViewport(0, 0, width, height);

        float aspectRatio = (float) width / (float) height;
        float newWidth = 4;
        float newHeight = newWidth / aspectRatio;

        Matrix.orthoM(projectionMatrix, 0, -(newWidth / 2), (newWidth / 2), -(newHeight / 2), (newHeight / 2), 1, 100);

        //fill view matrix
        Matrix.setLookAtM(viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f);
        mCurrentScreen.onResize(newWidth,newHeight);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {

        //update screen state
        mCurrentScreen.onUpdate();

        // Clear the rendering surface.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Update the viewProjection matrix, and create an inverted matrix for
        // touch picking.
        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        Matrix.invertM(invertedViewProjectionMatrix, 0, viewProjectionMatrix, 0);

        drawNodes();
    }

    private void drawNodes() {
        for (INode iNode : mCurrentScreen.getNodeList()) {
            positionObjectInScene(iNode.getPosition().getX(),iNode.getPosition().getY());
            textureProgram.useProgram();
            textureProgram.setUniforms(modelViewProjectionMatrix, mLoadedTableTextureHandle);
            iNode.bindData(textureProgram);
            iNode.draw();
        }
    }


    // The mallets and the puck are positioned on the same plane as the table.
    private void positionObjectInScene(float x, float y) {
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, x, y, 0);
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0,
                modelMatrix, 0);
    }
}