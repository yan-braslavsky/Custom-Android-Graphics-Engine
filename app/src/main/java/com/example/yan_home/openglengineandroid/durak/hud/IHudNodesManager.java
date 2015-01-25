package com.example.yan_home.openglengineandroid.durak.hud;

import com.yan.glengine.assets.atlas.YANTextureAtlas;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.util.geometry.YANReadOnlyVector2;

import java.util.List;

/**
 * Created by Yan-Home on 1/25/2015.
 */
public interface IHudNodesManager {

    public enum CockPosition {
        BOTTOM_RIGHT, TOP_RIGHT, TOP_LEFT;
    }


    void createHudNodes(YANTextureAtlas hudAtlas);

    void setHudNodesSizes(YANReadOnlyVector2 sceneSize);

    List<YANTexturedNode> getAllHudNodes();

    void layoutNodes(YANReadOnlyVector2 sceneSize);

    void update(float deltaTimeSeconds);

    void resetCockAnimation(CockPosition position);

    void disableCockAnimation();
}
