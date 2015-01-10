package com.yan.glengine.assets.atlas;

import com.google.gson.Gson;
import com.yan.glengine.EngineWrapper;
import com.yan.glengine.assets.YANAssetDescriptor;
import com.yan.glengine.assets.YANAssetLoader;
import com.yan.glengine.assets.atlas.pojos.YANTexturePackerPojos;
import com.yan.glengine.util.YANTextResourceReader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yan-Home on 1/10/2015.
 */
public class YANTextureAtlasLoader implements YANAssetLoader<YANTextureAtlas> {

    @Override
    public YANTextureAtlas loadAsset(YANAssetDescriptor assetDescriptor) {
        //load json ui atlas descriptor
        String jsonAtlasString = YANTextResourceReader.readTextFileFromAssets(EngineWrapper.getContext(), assetDescriptor.getPathToAsset() + assetDescriptor.getAssetName() + "." + assetDescriptor.getAssetExtension());
        YANTexturePackerPojos.WrappingObject loadedPojo = (new Gson()).fromJson(jsonAtlasString, YANTexturePackerPojos.WrappingObject.class);

        //allocate texture atlas
        YANTextureAtlas atlas = new YANTextureAtlas(assetDescriptor.getPathToAsset() + loadedPojo.getMetaData().getAtlasImageFileName());

        //load texture regions
        Map<String, YANTextureRegion> textureRegionsMap = createTextureRegionsMap(loadedPojo, atlas);

        //assign texture regions to atlas
        atlas.setTextureRegions(textureRegionsMap);

        //atlas is ready
        return atlas;
    }

    private Map<String, YANTextureRegion> createTextureRegionsMap(YANTexturePackerPojos.WrappingObject loadedPojo, YANTextureAtlas atlas) {
        Map<String, YANTextureRegion> retMap = new HashMap<>();
        for (YANTexturePackerPojos.Frame frame : loadedPojo.getFramesList()) {
            retMap.put(frame.getTextureFileName(), createTextureRegionFromFrame(atlas, frame, loadedPojo.getMetaData().getAtlasImageSize()));
        }

        return retMap;
    }

    private YANTextureRegion createTextureRegionFromFrame(YANTextureAtlas atlas, YANTexturePackerPojos.Frame frame, YANTexturePackerPojos.AtlasImageSize atlasImageSize) {
        String regionName = frame.getTextureFileName();
        float u0 = frame.getFrameData().getX() / atlasImageSize.getW();
        float u1 = (frame.getFrameData().getX() + frame.getFrameData().getW()) / atlasImageSize.getW();
        float v0 = ((frame.getFrameData().getY()) / atlasImageSize.getH());
        float v1 = ((frame.getFrameData().getY() + frame.getFrameData().getH()) / atlasImageSize.getH());
        return new YANTextureRegion(atlas, regionName, u0, u1, v0, v1, frame.getFrameData().getW(), frame.getFrameData().getH());
    }


}
