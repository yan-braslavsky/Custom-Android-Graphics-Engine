package com.yan.glengine.util.math;

import com.yan.glengine.util.geometry.YANVector2;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANMathUtils {

    public static final float randomInRange(float min, float max) {
        return (float) (Math.random() < 0.5 ? ((1 - Math.random()) * (max - min) + min) : (Math.random() * (max - min) + min));
    }

    /**
     * Rotates point around other point counter clockwise.
     */
    public static void rotatePointAroundOrigin(YANVector2 point, YANVector2 origin, float angleDegrees) {
        double angleRadians = Math.toRadians(angleDegrees);

        float s = (angleRadians == Math.PI) ? 0 : (angleRadians == (Math.PI * 2)) ? 0 : (float) Math.sin(angleRadians);
        float c = (angleRadians == (Math.PI / 2)) ? 0 : (angleRadians == (Math.PI + (Math.PI / 2))) ? 0 : (float) Math.cos(angleRadians);

        // translate point back to origin:
        point.setX(point.getX() - origin.getX());
        point.setY(point.getY() - origin.getY());

        // rotate point
        float xNew = point.getX() * c - point.getY() * s;
        float yNew = point.getX() * s + point.getY() * c;

        // translate point back:
        point.setX(xNew + origin.getX());
        point.setY(yNew + origin.getY());
    }

}
