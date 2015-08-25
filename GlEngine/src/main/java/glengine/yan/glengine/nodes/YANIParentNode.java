package glengine.yan.glengine.nodes;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Yan-Home on 16/8/2015.
 * <p/>
 * Node that can contain other {@link YANIChildNode} nodes
 */
public interface YANIParentNode extends YANITransformableNode {

    void addChildNode(@NonNull final YANBaseNode node);

    void removeChildNode(@NonNull final YANBaseNode node);

    void removeAllChildNodes();

    @NonNull
    List<YANBaseNode> getChildNodes();
}
