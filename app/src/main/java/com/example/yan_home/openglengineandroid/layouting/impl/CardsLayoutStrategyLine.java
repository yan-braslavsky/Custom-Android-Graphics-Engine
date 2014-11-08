package com.example.yan_home.openglengineandroid.layouting.impl;

import java.util.List;

/**
 * Created by Yan-Home on 11/8/2014.
 */
public class CardsLayoutStrategyLine {
    private float mBaseYPosition;
    private float mMaxWidth;
    private float mSlotWidth;
    private float mSlotHeight;
    private float mBaseXPosition;

    public void init(float xPosition, float yPosition, float maxAvailableWidth, float slotWidth, float slotHeight) {
        mBaseXPosition = xPosition;
        mBaseYPosition = yPosition;
        mMaxWidth = maxAvailableWidth;
        mSlotWidth = slotWidth;
        mSlotHeight = slotHeight;
    }

    public void layoutRowOfSlots(List<CardsLayouterSlotImpl> slots) {

        float yStartPosition = mBaseYPosition - mSlotHeight;
        float xStartPosition = mBaseXPosition - (mMaxWidth / 2);

        float distanceBetweenCards = mMaxWidth / slots.size();
        distanceBetweenCards -= (mSlotWidth - distanceBetweenCards) / (slots.size() - 1);

        CardsLayouterSlotImpl slot;
        float cursorPosition = xStartPosition;
        for (int i = 0; i < slots.size(); i++) {
            slot = slots.get(i);
            slot.setPosition(cursorPosition, yStartPosition);
            cursorPosition += distanceBetweenCards;
        }
    }
}
