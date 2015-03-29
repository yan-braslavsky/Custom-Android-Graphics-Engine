package glengine.yan.glengine.assets.atlas;

import java.util.Map;

/**
 * Created by Yan-Home on 10/24/2014.
 */
public class YANTextureAtlas {

    private final String mAtlasImageFileName;
    private Map<String, YANAtlasTextureRegion> mTextureRegions;

    public YANTextureAtlas(String atlasImageFileName) {
        mAtlasImageFileName = atlasImageFileName;
    }

    public YANAtlasTextureRegion getTextureRegion(String name) {
        return mTextureRegions.get(name);
    }

    public String getAtlasImageFilePath() {
        return mAtlasImageFileName;
    }

    public void setTextureRegions(Map<String, YANAtlasTextureRegion> textureRegions) {
        mTextureRegions = textureRegions;
    }
}
