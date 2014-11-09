package com.example.yan_home.openglengineandroid.layouting;

import com.example.yan_home.openglengineandroid.layouting.impl.CardsLayouterSlotImpl;

import java.util.List;

/**
 * Created by Yan-Home on 11/9/2014.
 */
public abstract class CardsLayoutStrategy {

    protected float mBaseYPosition;
    protected float mMaxWidth;
    protected float mSlotWidth;
    protected float mSlotHeight;
    protected float mBaseXPosition;

    public void init(float xPosition, float yPosition, float maxAvailableWidth, float slotWidth, float slotHeight){
        mBaseXPosition = xPosition;
        mBaseYPosition = yPosition;
        mMaxWidth = maxAvailableWidth;
        mSlotWidth = slotWidth;
        mSlotHeight = slotHeight;
    }
    public abstract void layoutRowOfSlots(List<CardsLayouterSlotImpl> slots);
}
