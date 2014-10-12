package com.example.yan_home.openglengineandroid.input;

import com.example.yan_home.openglengineandroid.util.math.Vector2;

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

    public static Vector2 touchToWorld(float normalizedX, float normalizedY, float worldWidth, float worldHeight) {
        //convert touch point to world coordinates
        float realTouchX = normalizedX * (worldWidth / 2);
        float realTouchY = normalizedY * (worldHeight / 2);
        return new Vector2(realTouchX, realTouchY);
    }

    public void handleTouchUp(float normalizedX, float normalizedY) {
//        MyLogger.log("touched Up at : " + normalizedX + ":" + normalizedY);
        for (TouchListener listener : mListeners) {
            listener.onTouchUp(normalizedX, normalizedY);
        }
    }

    public void handleTouchDrag(float normalizedX, float normalizedY) {
//        MyLogger.log("touched DRAG at : " + normalizedX + ":" + normalizedY);
        for (TouchListener listener : mListeners) {
            listener.onTouchDrag(normalizedX, normalizedY);
        }
    }

    public interface TouchListener {
        void onTouchDown(float normalizedX, float normalizedY);
        void onTouchUp(float normalizedX, float normalizedY);
        void onTouchDrag(float normalizedX, float normalizedY);
    }

    private List<TouchListener> mListeners;
    private YANInputManager() {
        mListeners = new ArrayList<TouchListener>();
    }

    public void handleTouchPress(float normalizedX, float normalizedY) {
//        MyLogger.log("touched DOWN at : " + normalizedX + ":" + normalizedY);
        for (TouchListener listener : mListeners) {
            listener.onTouchDown(normalizedX, normalizedY);
        }
    }

    public void addEventListener(TouchListener listener) {
        mListeners.add(listener);
    }
    public void removeEventListener(TouchListener listener) {
        mListeners.remove(listener);
    }
}
