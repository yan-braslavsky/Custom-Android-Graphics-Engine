package com.example.yan_home.openglengineandroid.nodes;

import com.example.yan_home.openglengineandroid.programs.TextureShaderProgram;
import com.example.yan_home.openglengineandroid.util.math.Vector2;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public interface INode {
    void bindData(TextureShaderProgram textureProgram);

    void draw();

    int getSpriteResourceId();

    Vector2 getPosition();
}
