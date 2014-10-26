package com.yan.glengine.input;

import com.yan.glengine.util.math.YANVector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANInputManager {

    private static final YANInputManager INSTANCE = new YANInputManager();

    public static final YANInputManager getInstance() {
        return INSTANCE;
    }

    public static YANVector2 touchToWorld(float normalizedX, float normalizedY, float worldWidth, float worldHeight) {
        //convert touch point to world coordinates
        float realTouchX = normalizedX * worldWidth;
        float realTouchY = normalizedY * worldHeight;
        return new YANVector2(realTouchX, realTouchY);
    }

    public void handleTouchUp(float normalizedX, float normalizedY) {

        for (int i = mListeners.size() -1; i >= 0; i--) {
            boolean consumed = mListeners.get(i).onTouchUp(normalizedX, normalizedY);
            if (consumed)
                break;
        }

    }

    public void handleTouchPress(float normalizedX, float normalizedY) {
        for (int i = mListeners.size() -1; i >= 0; i--) {
            boolean consumed = mListeners.get(i).onTouchDown(normalizedX, normalizedY);
            if (consumed)
                break;
        }
    }

    public void handleTouchDrag(float normalizedX, float normalizedY) {
        for (int i = mListeners.size() -1; i >= 0; i--) {
            boolean consumed = mListeners.get(i).onTouchDrag(normalizedX, normalizedY);
            if (consumed)
                break;
        }
    }

    public interface TouchListener {
        boolean onTouchDown(float normalizedX, float normalizedY);

        boolean onTouchUp(float normalizedX, float normalizedY);

        boolean onTouchDrag(float normalizedX, float normalizedY);
    }

    private List<TouchListener> mListeners;

    private YANInputManager() {
        mListeners = new ArrayList<TouchListener>();
    }



    public void addEventListener(TouchListener listener) {
        mListeners.add(listener);
    }

    public void removeEventListener(TouchListener listener) {
        mListeners.remove(listener);
    }
}
