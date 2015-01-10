package com.yan.glengine.assets;


import com.yan.glengine.EngineWrapper;
import com.yan.glengine.assets.atlas.YANTextureAtlasLoader;
import com.yan.glengine.assets.atlas.YANTextureAtlas;
import com.yan.glengine.util.YANTextureHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yan-Home on 10/3/2014.
 * <p/>
 * Responsible for assets loading and management.
 * Implemented as Singleton.
 */
public class YANAssetManager {

    private static final YANAssetManager INSTANCE = new YANAssetManager();
    private YANTextureAtlasLoader mTextureAtlasLoader;

    public static final YANAssetManager getInstance() {
        return INSTANCE;
    }

    /**
     * Used to map reference between texture names and openGL handles
     * for those loaded textures
     */
    private Map<String, Integer> mTextureHandlesMap;

    /**
     * Used to map  between texture atlases names and actual loaded atlases
     */
    private Map<String, YANTextureAtlas> mAtlasesMap;

    private YANAssetManager() {
        mAtlasesMap = new HashMap<>();
        mTextureHandlesMap = new HashMap<>();
        mTextureAtlasLoader = new YANTextureAtlasLoader();
    }

    /**
     * @param atlasName name of the atlas
     * @return {@link YANTextureAtlas} for requested atlas if the atlas was loaded into memory otherwise null.
     */
    public YANTextureAtlas getLoadedAtlas(String atlasName) {
        return mAtlasesMap.get(atlasName);
    }

    /**
     * Preloads all the given assets into a memory for efficient access during the application.
     *
     * @param assets array of asset descriptors
     */
    public void preloadAssets(ArrayList<YANAssetDescriptor> assets) {
        for (YANAssetDescriptor asset : assets) {
            switch (asset.getType()) {
                case TEXTURE_ATLAS:
                    loadTextureAtlas(asset);
                    break;
                case FONT:
                    break;
                default:
                    throw new RuntimeException("don't know how to load asset type " + asset.getType());
            }
        }
    }

    /**
     * Loads a texture atlas and puts it into the memory for later access.
     *
     * @param atlasAsset asset descriptor
     */
    private void loadTextureAtlas(YANAssetDescriptor atlasAsset) {
        YANTextureAtlas atlas = mTextureAtlasLoader.loadAtlas(atlasAsset);
        mAtlasesMap.put(atlasAsset.getAssetName(), atlas);
    }

    /**
     * Checks if texture is loaded into a memory
     *
     * @param texturePath the entire path from assets folder to the texture including extension
     * @return true if texture was loaded , false otherwise
     */
    public boolean isTextureLoaded(String texturePath) {
        return mTextureHandlesMap.get(texturePath) != null;
    }

    /**
     * Loads texture to openGL
     *
     * @param texturePath full path to a texture located at assets folder including extension
     */
    public void loadTexture(String texturePath) {
        int handle = YANTextureHelper.loadTexture(EngineWrapper.getContext(), texturePath);
        mTextureHandlesMap.put(texturePath, handle);
    }

    /**
     * Removes loaded texture from memory .
     *
     * @param texturePath full path to a texture located at assets folder including extension
     */
    public void unloadTexture(String texturePath) {
        YANTextureHelper.deleteTexture(mTextureHandlesMap.get(texturePath));
        mTextureHandlesMap.remove(texturePath);
    }

    /**
     * Returns an openGL handle for loaded texture
     *
     * @param texturePath full path to a texture located at assets folder including extension
     */
    public int getLoadedTextureOpenGLHandle(String texturePath) {
        return mTextureHandlesMap.get(texturePath);
    }

    /**
     * Deletes all loaded textures from the context and reloads them again
     */
    public void reloadAllLoadedTextures() {
        for (String textureResourceName : mTextureHandlesMap.keySet()) {

            //delete textureResourceName rom GL context
            YANTextureHelper.deleteTexture(mTextureHandlesMap.get(textureResourceName));

            //reload the textureResourceName into gl context again
            loadTexture(textureResourceName);
        }
    }

}
