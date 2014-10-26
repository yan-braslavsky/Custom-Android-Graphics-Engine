package com.yan.glengine.util.math;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANVector2 {
    private float mX;
    private float mY;


    public YANVector2() {
        this(0, 0);
    }

    public YANVector2(float x, float y) {
        this.mX = x;
        this.mY = y;
    }

    public float getX() {
        return mX;
    }

    public void setX(float x) {
        mX = x;
    }

    public float getY() {
        return mY;
    }

    public void setY(float y) {
        mY = y;
    }
}
