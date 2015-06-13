
package glengine.yan.glengine;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import glengine.yan.glengine.input.YANInputManager;
import glengine.yan.glengine.renderer.YANGLRenderer;
import glengine.yan.glengine.service.ServiceLocator;

/**
 * This object serves as an entry point for android events ,
 * which are going to be handled by different managers.
 */
public class EngineWrapper implements Renderer {

    //Static reference kept , to prevent recreation of the
    //implementator renderer each time screen is rotated
    private YANGLRenderer mRenderer;
    private EngineActivity mEngineActivity;

    public YANGLRenderer getRenderer() {
        return mRenderer;
    }

    public EngineWrapper(EngineActivity engineActivity) {
        mRenderer = new YANGLRenderer(engineActivity);
        mEngineActivity = engineActivity;
    }

    public void handleTouchDown(float normalizedX, float normalizedY) {
        ServiceLocator.locateService(YANInputManager.class).handleTouchPress(normalizedX, normalizedY);
    }

    public void handleTouchDrag(float normalizedX, float normalizedY) {
        ServiceLocator.locateService(YANInputManager.class).handleTouchDrag(normalizedX, normalizedY);
    }

    public void handleTouchUp(float normalizedX, float normalizedY) {
        ServiceLocator.locateService(YANInputManager.class).handleTouchUp(normalizedX, normalizedY);
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

    public Context getContext() {
        return mEngineActivity.getApplicationContext();
    }

    /**
     * Called when user presses back button
     */
    public void onBackPressed() {
        mRenderer.onBackPressed();
    }

}