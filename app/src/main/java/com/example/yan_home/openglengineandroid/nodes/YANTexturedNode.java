/**
 * Created by Yan-Home on 10/3/2014.
 * This node is rendered by using a texture
 */
package com.example.yan_home.openglengineandroid.nodes;

import com.example.yan_home.openglengineandroid.assets.YANTexture;

public class YANTexturedNode extends YANBaseNode {

    private final YANTexture mTexture;

    public YANTexturedNode(YANTexture texture) {
        super();
        mTexture = texture;
    }

    public YANTexture getTexture() {
        return mTexture;
    }
}