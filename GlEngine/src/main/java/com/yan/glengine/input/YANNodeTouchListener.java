package com.yan.glengine.input;

import com.yan.glengine.util.math.YANVector2;

/**
 * Created by Yan-Home on 10/5/2014.
 */
public interface YANNodeTouchListener {
    void onTouchDown(YANVector2 worldTouchPoint);
    void onTouchUp(YANVector2 worldTouchPoint);
    void onTouchDrag(YANVector2 worldTouchPoint);
}
