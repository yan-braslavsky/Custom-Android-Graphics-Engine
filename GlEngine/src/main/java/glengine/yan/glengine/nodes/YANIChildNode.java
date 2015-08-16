package glengine.yan.glengine.nodes;

import android.support.annotation.NonNull;

import glengine.yan.glengine.programs.ShaderProgram;

/**
 * Created by Yan-Home on 16/8/2015.
 * <p/>
 * Node that can be parented to a {@link YANIParentNode}
 */
public interface YANIChildNode<T extends ShaderProgram> extends YANIRenderableNode<T> {

    enum Attribute {
        SORTING_LAYER,SIZE, POSITION, ROTATION_Z, ROTATION_Y, OPACITY, OVERLAY_COLOR, ANCHOR_POINT
    }

    void onAttachedToParentNode(@NonNull final YANIParentNode parentNode);

    void onDetachedFromParentNode(@NonNull final YANIParentNode parentNode);

    void onParentAttributeChanged(@NonNull final YANIParentNode parentNode, @NonNull final Attribute attribute);
}
