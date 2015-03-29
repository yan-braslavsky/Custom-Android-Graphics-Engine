package glengine.yan.glengine.util.colors;

import android.graphics.Color;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANColor {

    private float mR;
    private float mG;
    private float mB;
    private float mA;

    public YANColor(float mR, float mG, float mB, float mA) {
        this.mR = mR;
        this.mG = mG;
        this.mB = mB;
        this.mA = mA;
    }

    public static YANColor createFromHexColor(int hexaColor) {
        return new YANColor((float) Color.red(hexaColor) / (float) 0xFF, (float) Color.green(hexaColor) / (float) 0xFF, (float) Color.blue(hexaColor) / (float) 0xFF, (float) Color.alpha(hexaColor) / (float) 0xFF);
    }

    public float getR() {
        return mR;
    }

    public float getG() {
        return mG;
    }

    public float getB() {
        return mB;
    }

    public float getA() {
        return mA;
    }
}
