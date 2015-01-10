package com.yan.glengine.assets.atlas;

import java.util.Map;

/**
 * Created by Yan-Home on 10/24/2014.
 */
public class YANTextureAtlas {

    private final String mAtlasImageFileName;
    private Map<String, YANTextureRegion> mTextureRegions;

    public YANTextureAtlas(String atlasImageFileName) {
        mAtlasImageFileName = atlasImageFileName;
    }

    public YANTextureRegion getTextureRegion(String name) {
        return mTextureRegions.get(name);
    }

    public String getAtlasImageFileName() {
        return mAtlasImageFileName;
    }

    public void setTextureRegions(Map<String, YANTextureRegion> textureRegions) {
        mTextureRegions = textureRegions;
    }
}
