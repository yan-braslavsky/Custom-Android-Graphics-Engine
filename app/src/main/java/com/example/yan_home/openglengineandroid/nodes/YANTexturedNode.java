/**
 * Created by Yan-Home on 10/3/2014.
 * This node is rendered by using a texture
 */
package com.example.yan_home.openglengineandroid.nodes;

public class YANTexturedNode extends YANBaseNode {

    private final int spriteResourceId;

    public YANTexturedNode(int spriteResourceId) {
        super();
        this.spriteResourceId = spriteResourceId;
    }

    public int getSpriteResourceId() {
        return spriteResourceId;
    }
}