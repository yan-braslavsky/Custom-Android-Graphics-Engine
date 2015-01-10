package com.example.yan_home.openglengineandroid;

import com.example.yan_home.openglengineandroid.screens.ThreePointLayoutingTestScreen;
import com.yan.glengine.EngineActivity;
import com.yan.glengine.assets.YANAssetDescriptor;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.screens.YANIScreen;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends EngineActivity {

    @Override
    protected ArrayList<YANAssetDescriptor> onCreateAssets() {
        ArrayList<YANAssetDescriptor> assets = new ArrayList<>();
        YANAssetDescriptor atlasAsset = new YANAssetDescriptor(YANAssetDescriptor.YANAssetType.TEXTURE_ATLAS, "texture_atlasses" + File.separator, "ui_atlas", "json");
        assets.add(atlasAsset);
        return assets;
    }

    @Override
    protected YANIScreen onCreateStartScreen(YANGLRenderer renderer) {
        return new ThreePointLayoutingTestScreen(renderer);
    }

}
