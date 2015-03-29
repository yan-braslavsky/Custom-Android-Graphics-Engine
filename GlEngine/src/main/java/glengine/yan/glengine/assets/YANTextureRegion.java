package glengine.yan.glengine.assets;

/**
 * Created by Yan-Home on 1/11/2015.
 */
public class YANTextureRegion {

    private float mU0;
    private float mU1;
    private float mV0;
    private float mV1;

    public YANTextureRegion(float u0, float u1, float v0, float v1) {
        mU0 = u0;
        mU1 = u1;
        mV0 = v0;
        mV1 = v1;
    }

    public float getU0() {
        return mU0;
    }

    public float getU1() {
        return mU1;
    }

    public float getV0() {
        return mV0;
    }

    public float getV1() {
        return mV1;
    }
}
