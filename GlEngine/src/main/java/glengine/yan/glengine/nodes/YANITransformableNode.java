package glengine.yan.glengine.nodes;

import glengine.yan.glengine.util.colors.YANColor;
import glengine.yan.glengine.util.geometry.YANReadOnlyVector2;
import glengine.yan.glengine.util.geometry.YANRectangle;
import glengine.yan.glengine.util.geometry.YANVector2;

/**
 * Created by Yan-Home on 16/8/2015.
 */
public interface YANITransformableNode {
    YANReadOnlyVector2 getPosition();

    YANReadOnlyVector2 getSize();

    void setPosition(float x, float y);

    void setSize(float width, float height);

    YANRectangle getBoundingRectangle();

    YANVector2 getAnchorPoint();

    /**
     * Rotation around Z axis.
     * Defined in degrees.
     */
    float getRotationZ();

    /**
     * Rotation around Z axis.
     * Defined in degrees.
     */
    void setRotationZ(float rotation);

    /**
     * Rotation around Y axis.
     * Defined in degrees.
     */
    float getRotationY();

    /**
     * Rotation around Y axis.
     * Defined in degrees.
     */
    void setRotationY(float rotation);

    float getOpacity();

    void setOpacity(float opacity);

    void setSortingLayer(int sortingLayer);

    int getSortingLayer();

    YANColor getOverlayColor();

    void setOverlayColor(float r, float g, float b, float a);
}
