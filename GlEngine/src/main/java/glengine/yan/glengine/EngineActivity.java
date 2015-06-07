package glengine.yan.glengine;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.content.res.Configuration;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

import glengine.yan.glengine.assets.YANAssetDescriptor;
import glengine.yan.glengine.assets.YANAssetManager;
import glengine.yan.glengine.renderer.YANGLRenderer;
import glengine.yan.glengine.screens.YANIScreen;
import glengine.yan.glengine.service.ServiceLocator;
import glengine.yan.glengine.setup.YANEngineSetup;

public abstract class EngineActivity extends Activity {

    private EngineWrapper wrapper;
    private GLSurfaceView glSurfaceView;

    public EngineActivity() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        wrapper = new EngineWrapper(this);

        //can be loaded during the atlas parsing
        ArrayList<YANAssetDescriptor> assets = onCreateAssets();

        //preload assets
        ServiceLocator.addService(new YANAssetManager(getApplicationContext()));
        ServiceLocator.locateService(YANAssetManager.class).preloadAssets(assets);

        //setup the tween engine
        YANEngineSetup.setupTweenEngine();

        //set the first screen
        wrapper.getRenderer().setActiveScreen(onCreateStartScreen(wrapper.getRenderer()));

        //init the engine
        init();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //called when orientation changes
    }

    protected abstract ArrayList<YANAssetDescriptor> onCreateAssets();

    protected abstract YANIScreen onCreateStartScreen(YANGLRenderer renderer);

    // This snippet hides the system bars.
    public void hideSystemUI() {

        if (glSurfaceView == null)
            return;

        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        glSurfaceView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }


    private void init() {

        glSurfaceView = new GLSurfaceView(this);

        // Check if the system supports OpenGL ES 2.0.
        ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager
                .getDeviceConfigurationInfo();
        // Even though the latest emulator supports OpenGL ES 2.0,
        // it has a bug where it doesn't set the reqGlEsVersion so
        // the above check doesn't work. The below will detect if the
        // app is running on an emulator, and assume that it supports
        // OpenGL ES 2.0.
        final boolean supportsEs2 =
                configurationInfo.reqGlEsVersion >= 0x20000
                        || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                        && (Build.FINGERPRINT.startsWith("generic")
                        || Build.FINGERPRINT.startsWith("unknown")
                        || Build.MODEL.contains("google_sdk")
                        || Build.MODEL.contains("Emulator")
                        || Build.MODEL.contains("Android SDK built for x86")));

        if (supportsEs2) {
            // ...
            // Request an OpenGL ES 2.0 compatible context.
            glSurfaceView.setEGLContextClientVersion(2);

            //TODO : Uncomment for devices , Genymotion crashes
//            glSurfaceView.setEGLConfigChooser(new YANConfigChooser());

            // Assign our wrapper.
            glSurfaceView.setRenderer(wrapper);
        } else {
            /*
             * This is where you could create an OpenGL ES 1.x compatible
             * wrapper if you wanted to support both ES 1 and ES 2. Since
             * we're not doing anything, the app will crash if the device
             * doesn't support OpenGL ES 2.0. If we publish on the market, we
             * should also add the following to AndroidManifest.xml:
             *
             * <uses-feature android:glEsVersion="0x00020000"
             * android:required="true" />
             *
             * This hides our app from those devices which don't support OpenGL
             * ES 2.0.
             */
            Toast.makeText(this, "This device does not support OpenGL ES 2.0.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        glSurfaceView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event != null) {
                    // Convert touch coordinates into normalized device
                    // coordinates, keeping in mind that Android's Y
                    // coordinates are inverted.
                    final float normalizedX =
                            (event.getX() / (float) v.getWidth());
                    final float normalizedY =
                            ((event.getY() / (float) v.getHeight()));

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        glSurfaceView.queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                wrapper.handleTouchDown(
                                        normalizedX, normalizedY);
                            }
                        });
                    } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        glSurfaceView.queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                wrapper.handleTouchDrag(
                                        normalizedX, normalizedY);
                            }
                        });
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        glSurfaceView.queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                wrapper.handleTouchUp(
                                        normalizedX, normalizedY);
                            }
                        });
                    }

                    return true;
                } else {
                    return false;
                }
            }
        });

        setContentView(glSurfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (glSurfaceView != null) {
            glSurfaceView.onPause();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        wrapper.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();

        if (glSurfaceView != null) {
            glSurfaceView.onResume();
        }
    }
}