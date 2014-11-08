package com.yan.glengine.util.math;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANVector2 implements YANReadOnlyVector2 {
    private float mX;
    private float mY;


    public YANVector2() {
        this(0, 0);
    }

    public YANVector2(float x, float y) {
        this.mX = x;
        this.mY = y;
    }

    @Override
    public float getX() {
        return mX;
    }

    @Override
    public float getY() {
        return mY;
    }

    public void setX(float x) {
        mX = x;
    }

    public void setY(float y) {
        mY = y;
    }
}
