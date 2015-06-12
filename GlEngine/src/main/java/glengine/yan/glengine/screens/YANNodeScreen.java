package glengine.yan.glengine.screens;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import glengine.yan.glengine.nodes.YANIRenderableNode;
import glengine.yan.glengine.renderer.YANGLRenderer;
import glengine.yan.glengine.util.geometry.YANReadOnlyVector2;
import glengine.yan.glengine.util.sort.YANSort;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public abstract class YANNodeScreen implements YANIScreen {

    public interface SortingLayerChangeListener {
        void onNodeChangesSortingLayer(int prevSortingLayer, int newSortingLayer, YANIRenderableNode node);
    }

    private final YANGLRenderer mRenderer;
    private List<YANIRenderableNode> mNodeList;

    private Comparator<YANIRenderableNode> mSortingLayerComparator;

    //This flag is used for optimisation
    //we don't want to sort nodes every frame
    private boolean shouldSortNodes;
    private SortingLayerChangeListener mSortingLayerChangeListener;

    public YANNodeScreen(YANGLRenderer renderer) {
        mRenderer = renderer;
        mNodeList = new ArrayList<>();

        mSortingLayerComparator = new Comparator<YANIRenderableNode>() {
            @Override
            public int compare(YANIRenderableNode lhs, YANIRenderableNode rhs) {
                return lhs.getSortingLayer() - rhs.getSortingLayer();
            }
        };

        mSortingLayerChangeListener = new SortingLayerChangeListener() {
            @Override
            public void onNodeChangesSortingLayer(int prevSortingLayer, int newSortingLayer, YANIRenderableNode node) {
                shouldSortNodes = true;
            }
        };
    }

    protected abstract void onAddNodesToScene();

    protected abstract void onLayoutNodes();

    protected abstract void onChangeNodesSize();

    /**
     * Nodes and their data should be created.
     * No screen size may be yet available
     */
    protected abstract void onCreateNodes();

    protected void addNode(YANIRenderableNode node) {
        node.onAttachedToScreen(this,mSortingLayerChangeListener);
        getNodeList().add(node);
        shouldSortNodes = true;
    }

    private void sortNodesBySortingLayer() {
        //reorder children by sorting layer
        YANSort.sort(getNodeList(), mSortingLayerComparator);
    }

    protected void removeNode(YANIRenderableNode node) {
        node.onDetachedFromScreen();
        getNodeList().remove(node);
        shouldSortNodes = true;
    }

    @Override
    public void onBackPressed() {
        //Shuts down the renderer by default
        getRenderer().shutDown();
    }

    @Override
    public void onSetActive() {
        onCreateNodes();

        if (mRenderer.getSurfaceSize().getX() != 0 && mRenderer.getSurfaceSize().getY() != 0) {
            onChangeNodesSize();
            onLayoutNodes();
        }

        onAddNodesToScene();
    }

    @Override
    public void onSetNotActive() {
    }

    protected YANReadOnlyVector2 getSceneSize() {
        return mRenderer.getSurfaceSize();
    }

    @Override
    public void onResize(float newWidth, float newHeight) {
        onChangeNodesSize();
        onLayoutNodes();
    }

    @Override
    public void onUpdate(float deltaTimeSeconds) {

        //Sorting nodes every frame is expensive
        if (shouldSortNodes) {
            sortNodesBySortingLayer();
            shouldSortNodes = false;
        }
    }

    @Override
    public List<YANIRenderableNode> getNodeList() {
        return mNodeList;
    }

    public YANGLRenderer getRenderer() {
        return mRenderer;
    }
}
