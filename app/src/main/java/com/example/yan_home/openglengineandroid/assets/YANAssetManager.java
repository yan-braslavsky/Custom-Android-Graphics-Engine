package com.example.yan_home.openglengineandroid.assets;

import com.example.yan_home.openglengineandroid.GLEngineApp;
import com.example.yan_home.openglengineandroid.assets.atlas.YANLoadAtlasTask;
import com.example.yan_home.openglengineandroid.assets.atlas.YANTextureAtlas;
import com.example.yan_home.openglengineandroid.util.YANTextureHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANAssetManager {

    public interface YANTextureAtlasLoadListener {
        void onAtlasLoaded(YANTextureAtlas atlas);
        void onProgress(float percentLoaded);
    }

    private static final YANAssetManager INSTANCE = new YANAssetManager();

    public static final YANAssetManager getInstance() {
        return INSTANCE;
    }

    private Map<YANTexture, Integer> mTextureHandlesMap;

    private YANAssetManager() {
        mTextureHandlesMap = new HashMap<YANTexture, Integer>();
    }


    public void loadTextureAtlas(int atlasJsonResourceID, int atlasImageID, YANTextureAtlasLoadListener listener) {
        (new YANLoadAtlasTask(atlasJsonResourceID, atlasImageID, listener)).execute();
    }

    public boolean isTextureLoaded(YANTexture texture) {
        return mTextureHandlesMap.get(texture) != null;
    }

    public void loadTexture(YANTexture texture) {
        int handle = YANTextureHelper.loadTexture(GLEngineApp.getAppContext(),
                texture.getTextureResourceID());
        mTextureHandlesMap.put(texture, handle);
    }

    public void unloadAllTextures() {
        for (YANTexture yanTexture : mTextureHandlesMap.keySet()) {
            YANTextureHelper.deleteTexture(mTextureHandlesMap.get(yanTexture));
        }
        mTextureHandlesMap.clear();
    }

    public void unloadTexture(YANTexture texture) {
        YANTextureHelper.deleteTexture(mTextureHandlesMap.get(texture));
        mTextureHandlesMap.remove(texture);
    }

    public int getLoadedTextureHandle(YANTexture texture) {
        return mTextureHandlesMap.get(texture);
    }

    /**
     * Deletes all loaded textures from the context and reloads them again
     */
    public void reloadAllLoadedTextures() {
        for (YANTexture texture : mTextureHandlesMap.keySet()) {

            //FIXME : causes random deletion of the sprite
            //delete texture rom GL context
//           YANTextureHelper.deleteTexture(mTextureHandlesMap.get(texture));

            //reload the texture into gl context again
            loadTexture(texture);
        }
    }

}
