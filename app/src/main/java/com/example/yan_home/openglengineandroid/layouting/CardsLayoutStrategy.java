package com.example.yan_home.openglengineandroid.layouting;

import com.example.yan_home.openglengineandroid.layouting.impl.CardsLayouterSlotImpl;
import com.yan.glengine.util.geometry.YANTriangle;
import com.yan.glengine.util.geometry.YANVector2;
import com.yan.glengine.util.math.YANMathUtils;

import java.util.List;

/**
 * Created by Yan-Home on 11/9/2014.
 */
public abstract class CardsLayoutStrategy {

    protected float mNormalizedBaseYPosition;
    protected float mMaxWidth;
    protected float mSlotWidth;
    protected float mSlotHeight;
    protected float mNormalizedBaseXPosition;
    protected float mFanRotationOffset;
    protected YANTriangle mFanTriangle;

    public void init(float xPosition, float yPosition, float maxAvailableWidth, float slotWidth, float slotHeight) {
        mNormalizedBaseXPosition = xPosition;
        mNormalizedBaseYPosition = yPosition;
        mMaxWidth = maxAvailableWidth;
        mSlotWidth = slotWidth;
        mSlotHeight = slotHeight;
    }

    public void initFan(YANTriangle fanTriangle, float slotWidth, float slotHeight) {

        mFanTriangle = fanTriangle;
        mSlotWidth = slotWidth;
        mSlotHeight = slotHeight;

        //calculate values

        //rotation offset
        YANVector2 centerOfTheBase = fanTriangle.calculateCenterPointBetweenBaseVertices();

        //looking for angle between the center of base and horizontal line
        //reference : http://hotmath.com/hotmath_help/topics/magnitude-and-direction-of-vectors.html

        //our starting point is the origin of the triangle (top vertex)
        //our end point is the center of the base of triangle
        float xChange = centerOfTheBase.getX() - fanTriangle.getTopVertex().getX();
        float yChange = centerOfTheBase.getY() - fanTriangle.getTopVertex().getY();

        //the angle between x axis and the vector (topVertex -> center of base)
        mFanRotationOffset = (float) Math.toDegrees(Math.atan(yChange / xChange));

        //rotate back for calculations in normalized coordinates
        YANMathUtils.rotatePointAroundOrigin(fanTriangle.getTopVertex(), centerOfTheBase, mFanRotationOffset);

        mNormalizedBaseXPosition = centerOfTheBase.getX();
        mNormalizedBaseYPosition = centerOfTheBase.getY();

        //TODO :remove hard coded values
        mMaxWidth = 250;
    }

    public abstract void layoutRowOfSlots(List<CardsLayouterSlotImpl> slots);
}
