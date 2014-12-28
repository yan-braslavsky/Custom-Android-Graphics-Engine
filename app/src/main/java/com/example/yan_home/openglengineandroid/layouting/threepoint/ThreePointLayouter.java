package com.example.yan_home.openglengineandroid.layouting.threepoint;

import com.example.yan_home.openglengineandroid.layouting.impl.CardsLayouterSlotImpl;
import com.yan.glengine.util.geometry.YANVector2;

import java.util.List;

/**
 * Created by Yan-Home on 12/28/2014.
 * <p/>
 * This layouter take 3 points as a reference to create a layout
 */
public interface ThreePointLayouter {

    /**
     * Set the 3 points to define the layout
     *
     * @param originPoint origin of the triangle that defines the layout
     * @param leftBasis   left basis vertex of the triangle that defines the layout
     * @param rightBasis  right basis vertex of the triangle that defines the layout
     */
    void setThreePoints(YANVector2 originPoint, YANVector2 leftBasis, YANVector2 rightBasis);

    /**
     * Takes the slots list and layouts them in a row
     */
    void layoutRowOfSlots(List<CardsLayouterSlotImpl> slots);
}
