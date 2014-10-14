package com.example.yan_home.openglengineandroid.font_test;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class TextRenderActivity extends Activity {

    // Our OpenGL Surfaceview
    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Super
        super.onCreate(savedInstanceState);

        // We create our Surfaceview for our OpenGL here.
        glSurfaceView = new GLTextSurface(this);

        // Set our view.	
        setContentView(glSurfaceView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

}
