package com.example.yan_home.openglengineandroid.font_test;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class TextMainActivity extends Activity {

	// Our OpenGL Surfaceview
	private GLSurfaceView glSurfaceView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        // We create our Surfaceview for our OpenGL here.
        glSurfaceView = new GLSurf(this);
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
