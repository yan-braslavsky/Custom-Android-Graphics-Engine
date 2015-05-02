package com.yan.glengine.tester.activities;

import com.yan.glengine.tester.screens.ColorOverlayTestScreen;

import java.io.File;
import java.util.ArrayList;

import glengine.yan.glengine.EngineActivity;
import glengine.yan.glengine.assets.YANAssetDescriptor;
import glengine.yan.glengine.renderer.YANGLRenderer;
import glengine.yan.glengine.screens.YANIScreen;

public class VisualTestsActivity extends EngineActivity {

    @Override
    protected ArrayList<YANAssetDescriptor> onCreateAssets() {
        ArrayList<YANAssetDescriptor> assets = new ArrayList<>();
        assets.add(new YANAssetDescriptor(YANAssetDescriptor.YANAssetType.TEXTURE_ATLAS, "texture_atlases" + File.separator, "ui_atlas", "json"));
        assets.add(new YANAssetDescriptor(YANAssetDescriptor.YANAssetType.TEXTURE_ATLAS, "texture_atlases" + File.separator, "ui_atlas_game", "json"));
        assets.add(new YANAssetDescriptor(YANAssetDescriptor.YANAssetType.TEXTURE_ATLAS, "texture_atlases" + File.separator, "cards_atlas", "json"));
        assets.add(new YANAssetDescriptor(YANAssetDescriptor.YANAssetType.FONT, "fonts" + File.separator, "standard_font", "fnt"));
        assets.add(new YANAssetDescriptor(YANAssetDescriptor.YANAssetType.FONT, "fonts" + File.separator, "tale_font", "fnt"));
        return assets;
    }

    @Override
    protected YANIScreen onCreateStartScreen(YANGLRenderer renderer) {
        return new ColorOverlayTestScreen(renderer);
    }

}
