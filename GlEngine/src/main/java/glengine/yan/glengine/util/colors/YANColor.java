package glengine.yan.glengine.util.colors;

import android.graphics.Color;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANColor {

    private final float[] mArr;

    public YANColor(float r, float g, float b, float a) {
        mArr = new float[]{0, 0, 0, 0};
        setColor(r, g, b, a);
    }

    public static YANColor createFromHexColor(int hexaColor) {
        return new YANColor((float) Color.red(hexaColor) / (float) 0xFF, (float) Color.green(hexaColor) / (float) 0xFF, (float) Color.blue(hexaColor) / (float) 0xFF, (float) Color.alpha(hexaColor) / (float) 0xFF);
    }

    public float getR() {
        return mArr[0];
    }

    public float getG() {
        return mArr[1];
    }

    public float getB() {
        return mArr[2];
    }

    public float getA() {
        return mArr[3];
    }

    /**
     * Returns float array that defines a color.
     * If values of the array will be changed , then the color will be changed too
     *
     * @return float array {red,green,blue,alpha}
     */
    public float[] asFloatArray() {
        return mArr;
    }

    public void setColor(float r, float g, float b, float a) {
        mArr[0] = r;
        mArr[1] = g;
        mArr[2] = b;
        mArr[3] = a;
    }
}
