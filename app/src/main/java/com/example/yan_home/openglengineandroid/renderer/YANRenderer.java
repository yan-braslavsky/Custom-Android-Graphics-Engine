package com.example.yan_home.openglengineandroid.renderer;

import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.example.yan_home.openglengineandroid.GLEngineApp;
import com.example.yan_home.openglengineandroid.assets.YANAssetManager;
import com.example.yan_home.openglengineandroid.assets.YANTexture;
import com.example.yan_home.openglengineandroid.nodes.YANIRenderableNode;
import com.example.yan_home.openglengineandroid.nodes.YANTexturedNode;
import com.example.yan_home.openglengineandroid.programs.YANColorShaderProgram;
import com.example.yan_home.openglengineandroid.programs.YANTextureShaderProgram;
import com.example.yan_home.openglengineandroid.screens.YANIScreen;
import com.example.yan_home.openglengineandroid.screens.impl.YANTestScreen;
import com.example.yan_home.openglengineandroid.util.YANMatrixHelper;
import com.example.yan_home.openglengineandroid.util.colors.YANColor;
import com.example.yan_home.openglengineandroid.util.math.Vector2;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Yan-Home on 10/3/2014.
 * <p/>
 * Implementer of graphics rendering using openGL
 */
public class YANRenderer implements YANIRenderer {

    private YANIScreen mCurrentScreen;

    private Vector2 mSurficeSize;

    // shader programs
    private YANTextureShaderProgram textureProgram;
    private YANColorShaderProgram colorProgram;

    @Override
    public void onGLSurfaceCreated() {
        setClearColor();
        loadShaderPrograms();
        setActiveScreen(new YANTestScreen());
    }

    @Override
    public void onGLSurfaceChanged(int width, int height) {
        // Set the OpenGL viewport to fill the entire surface.
        GLES20.glViewport(0, 0, width, height);

        mSurficeSize = new Vector2(width,height);

        // Enable blending using pre-multiplied alpha.
        GLES20.glEnable(GL10.GL_BLEND);
        GLES20.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);

        float aspectRatio = (float) width / (float) height;
        float newWidth = 10;//width;
        float newHeight =  newWidth / aspectRatio;

        Matrix.orthoM(YANMatrixHelper.projectionMatrix, 0, -(newWidth / 2), (newWidth / 2), -(newHeight / 2), (newHeight / 2), 1, 100);

        //fill view matrix
        Matrix.setLookAtM(YANMatrixHelper.viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f);
        mCurrentScreen.onResize(newWidth, newHeight);
    }

    @Override
    public void onDrawFrame() {
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

            if (iNode instanceof YANTexturedNode) {
                textureProgram.useProgram();
                textureProgram.setUniforms(YANMatrixHelper.modelViewProjectionMatrix, YANAssetManager.getInstance().getLoadedTextureHandle(((YANTexturedNode) iNode).getTexture()));
            } else {
                throw new RuntimeException("Don't know how to render node of type " + iNode.getClass().getSimpleName());
            }

            iNode.bindData(textureProgram);
            iNode.draw();
        }
    }

    private void setClearColor() {
        YANColor color = YANColor.createFromHexColor(Color.GRAY);
        GLES20.glClearColor(color.getR(), color.getG(), color.getB(), color.getA());
    }

    private void loadShaderPrograms() {
        textureProgram = new YANTextureShaderProgram(GLEngineApp.getAppContext());
        colorProgram = new YANColorShaderProgram(GLEngineApp.getAppContext());
    }

    void setActiveScreen(YANIScreen screen) {

        if (this.mCurrentScreen != null) {
            this.mCurrentScreen.onSetNotActive();
        }

        this.mCurrentScreen = screen;
        mCurrentScreen.onSetActive();

        //TODO : load all textures related to this screen
        for (YANIRenderableNode iNode : mCurrentScreen.getNodeList()) {

            if (iNode instanceof YANTexturedNode) {
                YANTexturedNode node = (YANTexturedNode) iNode;
                YANTexture nodeTexture = node.getTexture();

                //if texture for current node is note loaded , load it into GLContext
                if (!(YANAssetManager.getInstance().isTextureLoaded(nodeTexture))) {
                    YANAssetManager.getInstance().loadTexture(nodeTexture);
                }
            }
        }
    }
}
