package glengine.yan.glengine.nodes;

import android.support.annotation.NonNull;

import java.util.Collection;

/**
 * Created by Yan-Home on 16/8/2015.
 * <p/>
 * Node that can contain other {@link YANIChildNode} nodes
 */
public interface YANIParentNode extends YANITransformableNode {

    void addChildNode(@NonNull final YANIChildNode node);

    void removeChildNode(@NonNull final YANIChildNode node);

    void removeAllChildNodes();

    @NonNull
    Collection<YANIChildNode> getChildNodes();
}
