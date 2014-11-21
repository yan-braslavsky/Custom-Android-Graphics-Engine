package com.yan.glengine.assets;


import com.yan.glengine.EngineWrapper;
import com.yan.glengine.assets.atlas.YANLoadAtlasTask;
import com.yan.glengine.assets.atlas.YANTextureAtlas;
import com.yan.glengine.util.YANTextureHelper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANAssetManager {

    public interface YANAssetManagerListener {
        void onAssetsPreloaded();
    }

    public YANTextureAtlas getLoadedAtlas(int atlasResourceID) {
        return mAtlasesMap.get(atlasResourceID);
    }

    private static final YANAssetManager INSTANCE = new YANAssetManager();

    public static final YANAssetManager getInstance() {
        return INSTANCE;
    }

    private Map<Integer, Integer> mTextureHandlesMap;
    private Map<Integer, YANTextureAtlas> mAtlasesMap;

    private YANAssetManager() {
        mAtlasesMap = new HashMap<Integer, YANTextureAtlas>();
        mTextureHandlesMap = new HashMap<Integer, Integer>();
    }

    /**
     * @param atlasMap key is json atlas resource value is the image atlas resource
     * @param listener
     */
    public void preloadAssets(Map<Integer, Integer> atlasMap, final YANAssetManagerListener listener) {

        Iterator it = atlasMap.entrySet().iterator();
        while (it.hasNext()) {
            final Map.Entry<Integer, Integer> pairs = (Map.Entry) it.next();

            loadTextureAtlas(pairs.getKey(), pairs.getValue(), new YANLoadAtlasTask.YANLoadAtlasTaskListener() {
                @Override
                public void onAtlasLoaded(YANTextureAtlas atlas) {
                    mAtlasesMap.put(pairs.getKey(), atlas);
                    if (listener != null) {
                        listener.onAssetsPreloaded();
                    }
                }

                @Override
                public void onProgress(float percentLoaded) {
                    //TODO : not implemented
                }
            });
        }
    }

    private void loadTextureAtlas(int atlasJsonResourceID, int atlasImageID, YANLoadAtlasTask.YANLoadAtlasTaskListener listener) {
        (new YANLoadAtlasTask(atlasJsonResourceID, atlasImageID, listener)).execute();
    }

    public boolean isTextureLoaded(int textureResourceID) {
        return mTextureHandlesMap.get(textureResourceID) != null;
    }

    public void loadTexture(int textureResourceID) {
        int handle = YANTextureHelper.loadTexture(EngineWrapper.getContext(), textureResourceID);
        mTextureHandlesMap.put(textureResourceID, handle);
    }

    public void unloadAllTextures() {
        for (Integer yanTexture : mTextureHandlesMap.keySet()) {
            YANTextureHelper.deleteTexture(mTextureHandlesMap.get(yanTexture));
        }
        mTextureHandlesMap.clear();
    }

    public void unloadTexture(int textureResourceID) {
        YANTextureHelper.deleteTexture(mTextureHandlesMap.get(textureResourceID));
        mTextureHandlesMap.remove(textureResourceID);
    }

    public int getLoadedTextureHandle(int textureResourceID) {
        return mTextureHandlesMap.get(textureResourceID);
    }

    /**
     * Deletes all loaded textures from the context and reloads them again
     */
    public void reloadAllLoadedTextures() {
        for (Integer textureResourceName : mTextureHandlesMap.keySet()) {

            //FIXME : causes random deletion of the sprite
            //delete textureResourceName rom GL context
           YANTextureHelper.deleteTexture(mTextureHandlesMap.get(textureResourceName));

            //reload the textureResourceName into gl context again
            loadTexture(textureResourceName);
        }
    }

}
