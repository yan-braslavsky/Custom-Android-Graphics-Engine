package com.example.yan_home.openglengineandroid.assets.atlas;

import java.util.Map;

/**
 * Created by Yan-Home on 10/24/2014.
 */
public class YANTextureAtlas {

    private int mAtlasResourceID;
    private int mAtlasImageResourceID;
    private Map<String, YANTextureRegion> mTextureRegions;

    public YANTextureAtlas(int atlasResourceID,int atlasImageResourceID,  Map<String, YANTextureRegion> textureRegions) {
        mAtlasResourceID = atlasResourceID;
        mTextureRegions = textureRegions;
        mAtlasImageResourceID = atlasImageResourceID;
    }

    public int getAtlasResourceID() {
        return mAtlasResourceID;
    }

    public YANTextureRegion getTextureRegion(String name) {
        return mTextureRegions.get(name);
    }

    public int getAtlasImageResourceID() {
        return mAtlasImageResourceID;
    }
}
