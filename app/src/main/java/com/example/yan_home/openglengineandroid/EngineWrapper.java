
package com.example.yan_home.openglengineandroid;

import android.opengl.GLSurfaceView.Renderer;

import com.example.yan_home.openglengineandroid.input.YANInputManager;
import com.example.yan_home.openglengineandroid.renderer.IRenderer;
import com.example.yan_home.openglengineandroid.renderer.YANGLRenderer;
import com.example.yan_home.openglengineandroid.util.MyLogger;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * This object serves as an entry point for android events ,
 * which are going to be handled by different managers.
 */
public class EngineWrapper implements Renderer {

    //Static reference kept , to prevent recreation of the
    //implementator renderer each time screen is rotated
    private static IRenderer mRenderer = new YANGLRenderer();

    public void handleTouchPress(float normalizedX, float normalizedY) {
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
        MyLogger.log("onSurfaceCreated");
        mRenderer.onGLSurfaceCreated();
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        MyLogger.log("onSurfaceChanged");
        mRenderer.onGLSurfaceChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        mRenderer.onDrawFrame();
    }

}