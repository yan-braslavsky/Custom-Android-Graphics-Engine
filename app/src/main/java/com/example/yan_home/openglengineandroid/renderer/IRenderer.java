package com.example.yan_home.openglengineandroid.renderer;

/**
 * Created by Yan-Home on 10/4/2014.
 */
public interface IRenderer {
    public void onGLSurfaceCreated();
    public void onGLSurfaceChanged(int width, int height) ;
    public void onDrawFrame() ;
}
