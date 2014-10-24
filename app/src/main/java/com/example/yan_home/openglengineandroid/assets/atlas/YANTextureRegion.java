package com.example.yan_home.openglengineandroid.assets.atlas;

/**
 * Created by Yan-Home on 10/24/2014.
 */
public class YANTextureRegion {

    private String mRegionName;
    private int mAtlasImageResourceId;
    private float u0;
    private float u1;
    private float v0;
    private float v1;

    public YANTextureRegion(String regionName, int atlasImageResourceID, float u0, float u1, float v0, float v1) {
        mRegionName = regionName;
        mAtlasImageResourceId = atlasImageResourceID;
        this.u0 = u0;
        this.u1 = u1;
        this.v0 = v0;
        this.v1 = v1;
    }

    public String getRegionName() {
        return mRegionName;
    }

    public int getAtlasImageResourceId() {
        return mAtlasImageResourceId;
    }

    public float getU0() {
        return u0;
    }

    public float getU1() {
        return u1;
    }

    public float getV0() {
        return v0;
    }

    public float getV1() {
        return v1;
    }
}
