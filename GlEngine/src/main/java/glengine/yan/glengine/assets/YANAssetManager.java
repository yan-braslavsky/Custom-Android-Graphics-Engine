package glengine.yan.glengine.assets;


import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import glengine.yan.glengine.assets.atlas.YANTextureAtlas;
import glengine.yan.glengine.assets.atlas.YANTextureAtlasLoader;
import glengine.yan.glengine.assets.font.YANFont;
import glengine.yan.glengine.assets.font.YANFontLoader;
import glengine.yan.glengine.service.IService;
import glengine.yan.glengine.util.helpers.YANTextureHelper;

/**
 * Created by Yan-Home on 10/3/2014.
 * <p/>
 * Responsible for assets loading and management.
 * Implemented as Singleton.
 */
public class YANAssetManager implements IService {

    private final Context mCtx;
    private final YANTextureAtlasLoader mTextureAtlasLoader;
    private final YANFontLoader mFontLoader;

    /**
     * Used to map reference between texture names and openGL handles
     * for those loaded textures
     */
    private HashMap<String, Integer> mTextureHandlesMap;

    /**
     * Used to map  between texture atlases names and actual loaded atlases
     */
    private HashMap<String, YANTextureAtlas> mAtlasesMap;

    /**
     * Used to map  between font names and actual loaded fonts
     */
    private Map<String, YANFont> mFontsMap;


    public YANAssetManager(final Context appContext) {
        mAtlasesMap = new HashMap<>();
        mTextureHandlesMap = new HashMap<>();
        mFontsMap = new HashMap<>();
        mTextureAtlasLoader = new YANTextureAtlasLoader(appContext);
        mFontLoader = new YANFontLoader(appContext);
        mCtx = appContext;
    }

    /**
     * @param atlasName name of the atlas
     * @return {@link YANTextureAtlas} for requested atlas if the atlas was loaded into memory otherwise null.
     */
    public YANTextureAtlas getLoadedAtlas(String atlasName) {
        return mAtlasesMap.get(atlasName);
    }

    public YANFont getLoadedFont(String fontName) {
        return mFontsMap.get(fontName);
    }

    /**
     * Preloads all the given assets into a memory for efficient access during the application.
     *
     * @param assets array of asset descriptors
     */
    public void preloadAssets(ArrayList<YANAssetDescriptor> assets) {

        for (int i = 0; i < assets.size(); i++) {
            YANAssetDescriptor asset = assets.get(i);
            switch (asset.getType()) {
                case TEXTURE_ATLAS:
                    loadTextureAtlas(asset);
                    break;
                case FONT:
                    loadFont(asset);
                    break;
                default:
                    throw new RuntimeException("don't know how to load asset type " + asset.getType());
            }
        }
    }

    private void loadFont(YANAssetDescriptor asset) {
        YANFont font = mFontLoader.loadAsset(asset);

        if (font == null)
            return;

        mFontsMap.put(asset.getAssetName(), font);
    }

    /**
     * Loads a texture atlas and puts it into the memory for later access.
     *
     * @param atlasAsset asset descriptor
     */
    private void loadTextureAtlas(YANAssetDescriptor atlasAsset) {
        YANTextureAtlas atlas = mTextureAtlasLoader.loadAsset(atlasAsset);
        if (atlas == null)
            return;

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
        int handle = YANTextureHelper.loadTexture(mCtx, texturePath);
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
        if (!mTextureHandlesMap.containsKey(texturePath)) {
            throw new RuntimeException("Texture " + texturePath + " is not loaded into openGL");
        }

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
