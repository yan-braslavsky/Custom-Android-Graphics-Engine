package com.example.yan_home.openglengineandroid.util.math;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class MathUtils {

    public static final float randomInRange(float min, float max) {
        return (float) (Math.random() < 0.5 ? ((1 - Math.random()) * (max - min) + min) : (Math.random() * (max - min) + min));
    }
}
