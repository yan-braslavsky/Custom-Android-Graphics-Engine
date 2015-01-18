package com.example.yan_home.openglengineandroid;

import com.example.yan_home.openglengineandroid.durak.screens.RemoteGameTestScreen;
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
        assets.add(new YANAssetDescriptor(YANAssetDescriptor.YANAssetType.TEXTURE_ATLAS, "texture_atlases" + File.separator, "ui_atlas", "json"));
        assets.add(new YANAssetDescriptor(YANAssetDescriptor.YANAssetType.TEXTURE_ATLAS, "texture_atlases" + File.separator, "cards_atlas", "json"));
        assets.add(new YANAssetDescriptor(YANAssetDescriptor.YANAssetType.FONT, "fonts" + File.separator, "standard_font", "fnt"));
        return assets;
    }

    @Override
    protected YANIScreen onCreateStartScreen(YANGLRenderer renderer) {
//        return new FontTestScreen(renderer);
        return new RemoteGameTestScreen(renderer);
    }

}
