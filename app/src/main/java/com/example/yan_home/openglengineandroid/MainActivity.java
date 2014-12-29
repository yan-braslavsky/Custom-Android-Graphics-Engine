package com.example.yan_home.openglengineandroid;

import com.example.yan_home.openglengineandroid.screens.RemoteGameTestScreen;
import com.yan.glengine.EngineActivity;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.screens.YANIScreen;

import java.util.HashMap;

public class MainActivity extends EngineActivity {
    @Override
    protected HashMap<Integer, Integer> onCreateAssetsMap() {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(R.raw.ui_atlas, R.drawable.ui_atlas);
        return map;
    }

    @Override
    protected YANIScreen onCreateStartScreen(YANGLRenderer renderer) {
        return new RemoteGameTestScreen(renderer);
    }

}
