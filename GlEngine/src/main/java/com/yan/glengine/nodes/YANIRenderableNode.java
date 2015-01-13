package com.yan.glengine.nodes;

import com.yan.glengine.programs.ShaderProgram;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.util.geometry.YANReadOnlyVector2;
import com.yan.glengine.util.geometry.YANRectangle;
import com.yan.glengine.util.geometry.YANVector2;

/**
 * Created by Yan-Home on 10/3/2014.
 * <p/>
 * Node is a basic renderable element of the engine.
 */
public interface YANIRenderableNode<T extends ShaderProgram>  {

    void bindData(T shaderProgram);

    void render(YANGLRenderer renderer);

    YANReadOnlyVector2 getPosition();

    YANReadOnlyVector2 getSize();

    void setPosition(float x, float y);



    void setSize(float width,float height);

    YANRectangle getBoundingRectangle();

    void onAttachedToScreen();

    void onDetachedFromScreen();

    YANVector2 getAnchorPoint();

    float getRotation();

    /**
     * Defined in degrees
     */
    void setRotation(float rotation);

    float getOpacity();

    void setOpacity(float opacity);

     void setSortingLayer(int sortingLayer);
    int getSortingLayer();
}
