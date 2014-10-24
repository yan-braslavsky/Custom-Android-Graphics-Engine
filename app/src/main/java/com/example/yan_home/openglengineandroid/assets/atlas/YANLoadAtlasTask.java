package com.example.yan_home.openglengineandroid.assets.atlas;

import android.os.AsyncTask;

import com.example.yan_home.openglengineandroid.GLEngineApp;
import com.example.yan_home.openglengineandroid.assets.YANAssetManager;
import com.example.yan_home.openglengineandroid.assets.YANTexture;
import com.example.yan_home.openglengineandroid.assets.atlas.pojos.YANTexturePackerPojos;
import com.example.yan_home.openglengineandroid.util.YANTextResourceReader;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yan-Home on 10/24/2014.
 */
public class YANLoadAtlasTask extends AsyncTask<Integer, Integer, YANTextureAtlas> {

    private YANAssetManager.YANTextureAtlasLoadListener _listener;
    private int _resourceID;
    private int _atlasImageResourceID;

    public YANLoadAtlasTask(int resourceID, int atlasImageResourceID, YANAssetManager.YANTextureAtlasLoadListener listener) {
        _listener = listener;
        _resourceID = resourceID;
        _atlasImageResourceID = atlasImageResourceID;
    }

    @Override
    protected YANTextureAtlas doInBackground(Integer... params) {
        String jsonAtlasString = YANTextResourceReader.readTextFileFromResource(GLEngineApp.getAppContext(), _resourceID);
        YANTexturePackerPojos.WrappingObject loadedPojo = (new Gson()).fromJson(jsonAtlasString, YANTexturePackerPojos.WrappingObject.class);
        Map<String, YANTextureRegion> textureRegionsMap = createTextureRegionsMap(loadedPojo);
        return new YANTextureAtlas(_resourceID, new YANTexture(_atlasImageResourceID), textureRegionsMap);
    }

    private Map<String, YANTextureRegion> createTextureRegionsMap(YANTexturePackerPojos.WrappingObject loadedPojo) {
        Map<String, YANTextureRegion> retMap = new HashMap<String, YANTextureRegion>();
        for (YANTexturePackerPojos.Frame frame : loadedPojo.getFramesList()) {
            retMap.put(frame.getTextureFileName(), createTextureRegionFromFrame(frame, loadedPojo.getMetaData().getAtlasImageSize()));
        }

        return retMap;
    }

    private YANTextureRegion createTextureRegionFromFrame(YANTexturePackerPojos.Frame frame, YANTexturePackerPojos.AtlasImageSize atlasImageSize) {
        String regionName = frame.getTextureFileName();
        int atlasImageResourceID = _atlasImageResourceID;
        float u0 = frame.getFrameData().getX() /atlasImageSize.getW();
        float u1 = (frame.getFrameData().getX() + frame.getFrameData().getW()) / atlasImageSize.getW();
        float v0 = 1 - ((frame.getFrameData().getY()) / atlasImageSize.getH());
        float v1 = 1 - ((frame.getFrameData().getY() + frame.getFrameData().getH()) / atlasImageSize.getH());
        return new YANTextureRegion(regionName, atlasImageResourceID, u0, u1, v0, v1);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        //TODO : update progress
    }

    @Override
    protected void onPostExecute(YANTextureAtlas atlas) {
        super.onPostExecute(atlas);
        if(_listener != null){
            _listener.onAtlasLoaded(atlas);
        }
    }
}
