package com.example.yan_home.openglengineandroid.util;

import android.opengl.Matrix;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANMatrixHelper {

    // matricies that used for vertex calculations
    public static final float[] projectionMatrix = new float[16];
    public static final float[] modelMatrix = new float[16];
    public static final float[] viewMatrix = new float[16];
    public static final float[] viewProjectionMatrix = new float[16];
    public static final float[] invertedViewProjectionMatrix = new float[16];
    public static final float[] modelViewProjectionMatrix = new float[16];

    // The mallets and the puck are positioned on the same plane as the table.
    public static final void positionObjectInScene(float x, float y) {
        Matrix.setIdentityM(YANMatrixHelper.modelMatrix, 0);
        Matrix.translateM(YANMatrixHelper.modelMatrix, 0, x, y, 0);
        Matrix.multiplyMM(YANMatrixHelper.modelViewProjectionMatrix, 0, YANMatrixHelper.viewProjectionMatrix, 0,
                YANMatrixHelper.modelMatrix, 0);
    }

}
