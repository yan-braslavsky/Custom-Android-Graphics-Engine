package com.yan.glengine.assets.atlas;

/**
 * Created by Yan-Home on 10/24/2014.
 */
public class YANTextureRegion {

    private final float mWidth;
    private final float mHeight;
    private final YANTextureAtlas mAtlas;
    private String mRegionName;
    private float mU0;
    private float mU1;
    private float mV0;
    private float mV1;

    public YANTextureRegion(YANTextureAtlas atlas, String regionName, float u0, float u1, float v0, float v1, float width, float height) {
        this.mRegionName = regionName;
        this.mU0 = u0;
        this.mU1 = u1;
        this.mV0 = v0;
        this.mV1 = v1;
        this.mWidth = width;
        this.mHeight = height;
        this.mAtlas = atlas;
    }

    public String getRegionName() {
        return mRegionName;
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

    public float getWidth() {
        return mWidth;
    }

    public float getHeight() {
        return mHeight;
    }

    /**
     * @return and atlas this region is associated with
     */
    public YANTextureAtlas getAtlas() {
        return mAtlas;
    }
}
