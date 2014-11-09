package com.example.yan_home.openglengineandroid.layouting.impl;

import com.example.yan_home.openglengineandroid.layouting.CardsLayoutStrategy;
import com.yan.glengine.util.math.YANMathUtils;
import com.yan.glengine.util.math.YANVector2;

import java.util.List;

/**
 * Created by Yan-Home on 11/8/2014.
 */
public class CardsLayoutStrategyFan extends CardsLayoutStrategy {


    public void layoutRowOfSlots(List<CardsLayouterSlotImpl> slots) {

        float xCenterPosition = mBaseXPosition;
        float yStartPosition = mBaseYPosition - mSlotHeight;

        float yRotationPoint = mBaseYPosition * 3;
        float halfAvailableWidth = (mMaxWidth / 2);
        double tanHalfAngle = halfAvailableWidth / (yRotationPoint - yStartPosition);
        double halfAngleInRadians = Math.atan(tanHalfAngle);

        float halfAngleInDegrees = (float) (Math.toDegrees(halfAngleInRadians));
        float fullAngleInDegrees = halfAngleInDegrees * 2;
        float angleStep = fullAngleInDegrees / slots.size();

        //adjust the step , to leave it inside the bounds of the screen
        angleStep = angleStep * 0.8f;

        float currentRotation;
        float currentDistanceFromOrigin = 0;
        YANVector2 rotationOrigin = new YANVector2(xCenterPosition, yRotationPoint);

        for (int i = 0; i < slots.size(); i++) {
            //slot that will be repositioned
            CardsLayouterSlotImpl slot = slots.get(i);

            //side represents left or right from the origin
            int side = ((i % 2) == 0) ? -1 : 1;

            //the amount of rotation in degrees
            currentRotation = currentDistanceFromOrigin * angleStep * side;

            //slot will be rotated around origin starting from the center point
            slot.setPosition(xCenterPosition, yStartPosition);

            //rotate the slot
            YANMathUtils.rotatePointAroundOrigin(slot.getPosition(), rotationOrigin, currentRotation);
            slot.setRotation(currentRotation);

            //fix the offset from the pivot point
            slot.setPosition(slot.getPosition().getX() - mSlotWidth / 2, slot.getPosition().getY());

            //increase the distance only when both sides are placed
            if (side < 0) {
                currentDistanceFromOrigin++;
            }
        }
    }
}
