package com.example.yan_home.openglengineandroid.durak.hud;

import android.support.annotation.IntDef;

import com.yan.glengine.assets.atlas.YANTextureAtlas;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.util.geometry.YANReadOnlyVector2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;

/**
 * Created by Yan-Home on 1/25/2015.
 */
public interface IHudNodesManager {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            AVATAR_BOTTOM_RIGHT_INDEX,
            AVATAR_TOP_RIGHT_INDEX,
            AVATAR_TOP_LEFT_INDEX,
            COCK_BOTTOM_RIGHT_INDEX,
            COCK_TOP_RIGHT_INDEX,
            COCK_TOP_LEFT_INDEX,
            COCK_SCISSOR_INDEX,
    })
    public @interface HudNode {
    }

    public static final int AVATAR_BOTTOM_RIGHT_INDEX = 0;
    public static final int AVATAR_TOP_RIGHT_INDEX = 1;
    public static final int AVATAR_TOP_LEFT_INDEX = 2;
    public static final int COCK_BOTTOM_RIGHT_INDEX = 3;
    public static final int COCK_TOP_RIGHT_INDEX = 4;
    public static final int COCK_TOP_LEFT_INDEX = 5;
    public static final int COCK_SCISSOR_INDEX = 6;


    void createHudNodes(YANTextureAtlas hudAtlas);

    void setHudNodesSizes(YANReadOnlyVector2 sceneSize);

    Collection<YANTexturedNode> getAllHudNodes();

    void layoutNodes(YANReadOnlyVector2 sceneSize);

    void update(float deltaTimeSeconds);

    void resetCockAnimation(@HudNode int index);
}
