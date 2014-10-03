package com.example.yan_home.openglengineandroid.util.math;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class Vector2 {
    private float mX;
    private float mY;


    public Vector2() {
        this(0, 0);
    }

    public Vector2(float x, float y) {
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
