package com.example.yan_home.openglengineandroid.input;

import com.example.yan_home.openglengineandroid.util.math.Vector2;

/**
 * Created by Yan-Home on 10/5/2014.
 */
public interface YANNodeTouchListener {
    void onTouchDown(Vector2 worldTouchPoint);
    void onTouchUp(Vector2 worldTouchPoint);
    void onTouchDrag(Vector2 worldTouchPoint);
}
