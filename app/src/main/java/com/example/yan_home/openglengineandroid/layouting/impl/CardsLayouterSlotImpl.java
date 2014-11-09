package com.example.yan_home.openglengineandroid.layouting.impl;

import com.example.yan_home.openglengineandroid.layouting.CardsLayoutSlot;
import com.yan.glengine.util.math.YANVector2;

/**
 * Created by Yan-Home on 11/8/2014.
 */
public class CardsLayouterSlotImpl implements CardsLayoutSlot {

    private YANVector2 mPosition;
    private float mRotation;

    public CardsLayouterSlotImpl() {
        mPosition = new YANVector2();
    }

    @Override
    public YANVector2 getPosition() {
        return mPosition;
    }

    public void setPosition(float x, float y) {
        mPosition.setX(x);
        mPosition.setY(y);
    }

    public void setRotation(float rotation) {
        mRotation = rotation;
    }

    @Override
    public float getRotation() {
        return mRotation;
    }
}
