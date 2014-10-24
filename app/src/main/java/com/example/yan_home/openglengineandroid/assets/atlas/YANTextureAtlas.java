package com.example.yan_home.openglengineandroid.assets.atlas;

import com.example.yan_home.openglengineandroid.assets.YANTexture;

import java.util.Map;

/**
 * Created by Yan-Home on 10/24/2014.
 */
public class YANTextureAtlas {

    private int mAtlasResourceID;
    private YANTexture mAtlasTexture;
    private Map<String, YANTextureRegion> mTextureRegions;

    public YANTextureAtlas(int atlasResourceID, YANTexture yanTexture, Map<String, YANTextureRegion> textureRegions) {
        mAtlasResourceID = atlasResourceID;
        mAtlasTexture = yanTexture;
        mTextureRegions = textureRegions;
    }

    public int getAtlasResourceID() {
        return mAtlasResourceID;
    }

    public YANTexture getAtlasTexture() {
        return mAtlasTexture;
    }

    public YANTextureRegion getTextureRegion(String name) {
        return mTextureRegions.get(name);
    }
}
