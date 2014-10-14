package com.example.yan_home.openglengineandroid.font_test;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class GLTextSurface extends GLSurfaceView {

    private final GLTextRenderer mRenderer;

    public GLTextSurface(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new GLTextRenderer(context);
        setRenderer(mRenderer);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mRenderer.onPause();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mRenderer.onResume();
    }

}
