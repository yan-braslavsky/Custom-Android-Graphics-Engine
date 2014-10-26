package com.yan.glengine.renderer;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.yan.glengine.assets.YANAssetManager;
import com.yan.glengine.nodes.YANIRenderableNode;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.programs.YANColorShaderProgram;
import com.yan.glengine.programs.YANTextureShaderProgram;
import com.yan.glengine.screens.YANIScreen;
import com.yan.glengine.util.YANMatrixHelper;
import com.yan.glengine.util.colors.YANColor;
import com.yan.glengine.util.math.YANVector2;

/**
 * Created by Yan-Home on 10/3/2014.
 * <p/>
 * Implementer of graphics rendering using openGL
 */
public class YANGLRenderer  {

    private YANIScreen mCurrentScreen;
    private YANVector2 mSurfaceSize;

    // shader programs
    private YANTextureShaderProgram textureProgram;
    private YANColorShaderProgram colorProgram;
    private long mPreviousFrameTime;
    private Context mCtx;


    public YANGLRenderer(Context appContext) {
        mCtx = appContext;
        mSurfaceSize = new YANVector2();
    }

    public void onGLSurfaceCreated() {
        // Enable blending using pre-multiplied alpha.
        setGlInitialStates();
        loadShaderPrograms();


        mPreviousFrameTime = System.currentTimeMillis();
    }

    public void onGLSurfaceChanged(int width, int height) {
        // Set the OpenGL viewport to fill the entire surface.
        GLES20.glViewport(0, 0, width, height);

        //the size of the surface will be used by each screen
        mSurfaceSize = new YANVector2(width, height);

        //when context is recreated all previously loaded textures must be cleaned.
        YANAssetManager.getInstance().reloadAllLoadedTextures();

        //set orthographic projection
        Matrix.orthoM(YANMatrixHelper.projectionMatrix, 0, 0, width, height, 0, 1, 100);

        //fill view matrix
        Matrix.setLookAtM(YANMatrixHelper.viewMatrix, 0, 0f, 0.0f, 2.0f, 0f, 0f, 0f, 0f, 1f, 0f);

        if (mCurrentScreen != null) {
            //call screen on resize method
            mCurrentScreen.onResize(mSurfaceSize.getX(), mSurfaceSize.getY());
        }
    }

    public void onDrawFrame() {

        //update screen state
        if (mCurrentScreen != null) {
            mCurrentScreen.onUpdate(((float) (System.currentTimeMillis() - mPreviousFrameTime)) / 1000f);
        }

        // Clear the rendering surface.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Update the viewProjection matrix, and create an inverted matrix for
        // touch picking.
        Matrix.multiplyMM(YANMatrixHelper.viewProjectionMatrix, 0, YANMatrixHelper.projectionMatrix, 0, YANMatrixHelper.viewMatrix, 0);
        Matrix.invertM(YANMatrixHelper.invertedViewProjectionMatrix, 0, YANMatrixHelper.viewProjectionMatrix, 0);

        //draw each node
        if (mCurrentScreen != null) {
            drawNodes();
        }

        mPreviousFrameTime = System.currentTimeMillis();
    }

    private void drawNodes() {
        for (YANIRenderableNode iNode : mCurrentScreen.getNodeList()) {
            YANMatrixHelper.positionObjectInScene(iNode);

            if (iNode instanceof YANTexturedNode) {
                textureProgram.useProgram();
                textureProgram.setUniforms(
                        YANMatrixHelper.modelViewProjectionMatrix,
                        YANAssetManager.getInstance().getLoadedTextureHandle(((YANTexturedNode) iNode).getTextureRegion().getAtlasImageResourceId()),
                        iNode.getOpacity());
            } else {
                throw new RuntimeException("Don't know how to render node of type " + iNode.getClass().getSimpleName());
            }

            iNode.bindData(textureProgram);
            iNode.draw();
        }
    }

    private void setGlInitialStates() {

        //clear color
        YANColor color = YANColor.createFromHexColor(Color.GRAY);
        GLES20.glClearColor(color.getR(), color.getG(), color.getB(), color.getA());

        //TODO : when batching will be implemented , consider enable and disable this
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
    }

    private void loadShaderPrograms() {
        textureProgram = new YANTextureShaderProgram(mCtx);
        colorProgram = new YANColorShaderProgram(mCtx);
    }

    public void setActiveScreen(YANIScreen screen) {
        if (mCurrentScreen != null) {
            mCurrentScreen.onSetNotActive();
        }
        mCurrentScreen = screen;
        mCurrentScreen.onSetActive();
    }

    public YANVector2 getSurfaceSize() {
        return mSurfaceSize;
    }
}
