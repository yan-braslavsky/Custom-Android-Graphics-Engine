/***
 * Excerpted from "OpenGL ES for Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/kbogla for more book information.
 ***/
package com.example.yan_home.openglengineandroid;

import android.opengl.GLSurfaceView.Renderer;

import com.example.yan_home.openglengineandroid.input.YANInputManager;
import com.example.yan_home.openglengineandroid.renderer.YANGLRenderer;
import com.example.yan_home.openglengineandroid.renderer.YANIRenderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLRenderer implements Renderer {

    //Static reference kept , to prevent recreation of the
    //implementator renderer each time screen is rotated
    private static YANIRenderer mRenderer = new YANGLRenderer();

    public void handleTouchPress(float normalizedX, float normalizedY) {
        YANInputManager.getInstance().handleTouchPress(normalizedX,normalizedY);
    }

    public void handleTouchDrag(float normalizedX, float normalizedY) {
        YANInputManager.getInstance().handleTouchDrag(normalizedX,normalizedY);
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        mRenderer.onGLSurfaceCreated();
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        mRenderer.onGLSurfaceChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        mRenderer.onDrawFrame();
    }

}