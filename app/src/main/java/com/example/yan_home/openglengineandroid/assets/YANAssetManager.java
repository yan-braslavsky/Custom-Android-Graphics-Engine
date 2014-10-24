package com.example.yan_home.openglengineandroid.assets;

import com.example.yan_home.openglengineandroid.GLEngineApp;
import com.example.yan_home.openglengineandroid.R;
import com.example.yan_home.openglengineandroid.assets.atlas.YANLoadAtlasTask;
import com.example.yan_home.openglengineandroid.assets.atlas.YANTextureAtlas;
import com.example.yan_home.openglengineandroid.util.YANTextureHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANAssetManager {


    public interface YANAssetManagerListener{
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

    public void preloadAssets(final YANAssetManagerListener listener){

        loadTextureAtlas(R.raw.ui_atlas, R.drawable.ui_atlas, new YANLoadAtlasTask.YANLoadAtlasTaskListener() {
            @Override
            public void onAtlasLoaded(YANTextureAtlas atlas) {
                mAtlasesMap.put(R.raw.ui_atlas,atlas);
                if(listener != null){
                    listener.onAssetsPreloaded();
                }
            }

            @Override
            public void onProgress(float percentLoaded) {
            }
        });

    }

    private void loadTextureAtlas(int atlasJsonResourceID, int atlasImageID, YANLoadAtlasTask.YANLoadAtlasTaskListener listener) {
        (new YANLoadAtlasTask(atlasJsonResourceID, atlasImageID, listener)).execute();
    }

    public boolean isTextureLoaded(int textureResourceID) {
        return mTextureHandlesMap.get(textureResourceID) != null;
    }

    public void loadTexture(int textureResourceID) {
        int handle = YANTextureHelper.loadTexture(GLEngineApp.getAppContext(),  textureResourceID);
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
//           YANTextureHelper.deleteTexture(mTextureHandlesMap.get(textureResourceName));

            //reload the textureResourceName into gl context again
            loadTexture(textureResourceName);
        }
    }

}
