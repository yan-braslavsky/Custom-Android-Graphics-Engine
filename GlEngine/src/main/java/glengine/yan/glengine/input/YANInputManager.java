package glengine.yan.glengine.input;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import glengine.yan.glengine.service.IService;
import glengine.yan.glengine.util.geometry.YANVector2;
import glengine.yan.glengine.util.sort.YANSort;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANInputManager implements IService {

    public interface TouchListener {
        boolean onTouchDown(float normalizedX, float normalizedY);

        boolean onTouchUp(float normalizedX, float normalizedY);

        boolean onTouchDrag(float normalizedX, float normalizedY);

        int getSortingLayer();
    }

    private final Comparator<YANInputManager.TouchListener> mInputSortingLayerComparator;
    private final List<TouchListener> mListeners;

    public YANInputManager() {
        mListeners = new ArrayList<>();
        mInputSortingLayerComparator = new Comparator<YANInputManager.TouchListener>() {
            @Override
            public int compare(YANInputManager.TouchListener lhs, YANInputManager.TouchListener rhs) {
                return lhs.getSortingLayer() - rhs.getSortingLayer();
            }
        };
    }

    public static final YANVector2 touchToWorld(float normalizedX, float normalizedY, float worldWidth, float worldHeight) {
        //convert touch point to world coordinates
        float realTouchX = normalizedX * worldWidth;
        float realTouchY = normalizedY * worldHeight;
        return new YANVector2(realTouchX, realTouchY);
    }

    public void handleTouchUp(float normalizedX, float normalizedY) {

        for (int i = mListeners.size() - 1; i >= 0; i--) {
            boolean consumed = mListeners.get(i).onTouchUp(normalizedX, normalizedY);
            if (consumed)
                break;
        }

    }

    public void handleTouchPress(float normalizedX, float normalizedY) {
        YANSort.sort(mListeners, mInputSortingLayerComparator);

        for (int i = mListeners.size() - 1; i >= 0; i--) {
            boolean consumed = mListeners.get(i).onTouchDown(normalizedX, normalizedY);
            if (consumed)
                break;
        }
    }

    public void handleTouchDrag(float normalizedX, float normalizedY) {
        for (int i = mListeners.size() - 1; i >= 0; i--) {
            boolean consumed = mListeners.get(i).onTouchDrag(normalizedX, normalizedY);
            if (consumed)
                break;
        }
    }


    public void addEventListener(TouchListener listener) {

        //do not add the same instance twice
        if (mListeners.contains(listener))
            return;

        mListeners.add(listener);
    }

    public void removeEventListener(TouchListener listener) {
        mListeners.remove(listener);
    }

    @Override
    public void clearServiceData() {
        mListeners.clear();
    }
}
