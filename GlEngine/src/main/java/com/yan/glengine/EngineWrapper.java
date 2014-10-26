
package com.yan.glengine;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;

import com.yan.glengine.input.YANInputManager;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.util.YANLogger;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * This object serves as an entry point for android events ,
 * which are going to be handled by different managers.
 */
public class EngineWrapper implements Renderer {

    //Static reference kept , to prevent recreation of the
    //implementator renderer each time screen is rotated
    private static YANGLRenderer mRenderer;
    private static Context mCtx;

    public static YANGLRenderer getRenderer() {
        return mRenderer;
    }

    public EngineWrapper(Context ctx) {
        mRenderer = new YANGLRenderer(ctx);
        mCtx = ctx;
    }

    public void handleTouchDown(float normalizedX, float normalizedY) {
        YANInputManager.getInstance().handleTouchPress(normalizedX, normalizedY);
    }

    public void handleTouchDrag(float normalizedX, float normalizedY) {
        YANInputManager.getInstance().handleTouchDrag(normalizedX, normalizedY);
    }

    public void handleTouchUp(float normalizedX, float normalizedY) {
        YANInputManager.getInstance().handleTouchUp(normalizedX, normalizedY);
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        YANLogger.log("onSurfaceCreated");
        mRenderer.onGLSurfaceCreated();
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        YANLogger.log("onSurfaceChanged");
        mRenderer.onGLSurfaceChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        mRenderer.onDrawFrame();
    }

    public static Context getContext() {
        return mCtx;
    }
}