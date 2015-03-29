package glengine.yan.glengine.assets.atlas;

import glengine.yan.glengine.assets.YANTextureRegion;

/**
 * Created by Yan-Home on 10/24/2014.
 */
public class YANAtlasTextureRegion extends YANTextureRegion {

    private final float mWidth;
    private final float mHeight;
    private final YANTextureAtlas mAtlas;
    private String mRegionName;


    public YANAtlasTextureRegion(YANTextureAtlas atlas, String regionName, float u0, float u1, float v0, float v1, float width, float height) {
        super(u0, u1, v0, v1);
        this.mRegionName = regionName;
        this.mWidth = width;
        this.mHeight = height;
        this.mAtlas = atlas;
    }

    public String getRegionName() {
        return mRegionName;
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
