package com.example.yan_home.openglengineandroid.input;

import com.example.yan_home.openglengineandroid.util.YANLogger;

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

    public interface TouchListener {
        void onTouch(float normalizedX, float normalizedY);
    }

    private List<TouchListener> mListeners;
    private YANInputManager() {
        mListeners = new ArrayList<TouchListener>();
    }

    public void handleTouchPress(float normalizedX, float normalizedY) {
        YANLogger.log("touched at : " + normalizedX + ":" + normalizedY);

        for (TouchListener listener : mListeners) {
            listener.onTouch(normalizedX, normalizedY);
        }
    }

    public void handleTouchDrag(float normalizedX, float normalizedY) {
        //TODO : implement
    }

    public void addEventListener(TouchListener listener) {
        mListeners.add(listener);
    }

    public void removeEventListener(TouchListener listener) {
        mListeners.remove(listener);
    }
}
