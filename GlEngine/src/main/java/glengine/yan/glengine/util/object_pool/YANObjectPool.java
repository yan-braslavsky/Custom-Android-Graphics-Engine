package glengine.yan.glengine.util.object_pool;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yan-Home on 4/5/2015.
 * <p/>
 * Pool that manages {@link glengine.yan.glengine.util.object_pool.YANIPoolableObject} objects.
 * Thread safe.
 */
public class YANObjectPool {

    private static YANObjectPool INSTANCE = new YANObjectPool();
    private List<YANIPoolableObject> mCachedList;

    /**
     * Map that contains all recycled objects.
     * Objects can be accessed by their class key.
     */
    private Map<Class<? extends YANIPoolableObject>, Deque<YANIPoolableObject>> mObjectsMap;

    public static YANObjectPool getInstance() {
        return INSTANCE;
    }

    private YANObjectPool() {
        mObjectsMap = new ConcurrentHashMap<>();
        mCachedList = new ArrayList<>();
    }

    /**
     * Preallocate a certain amount of objects to be in the pool for future usage.
     *
     * @param objClass class of objects to be preallocated
     * @param count    the amount of objects to be preallocated
     */
    public synchronized <T extends YANIPoolableObject> void preallocate(Class<T> objClass, int count) {

        mCachedList.clear();
        for (int i = 0; i < count; i++) {
            mCachedList.add(this.obtain(objClass));
        }

        for (int i = 0; i < mCachedList.size(); i++) {
            YANIPoolableObject obj = mCachedList.get(i);
            obj.resetState();
            this.offer(obj);
        }
    }

    /**
     * Obtain a reference to recycle object of a type.
     *
     * @param objClass class of objects to be obtained
     * @return a recycled instance of an object.In case there is no object in a deque
     * of required type , returns a new instance of an object.
     */
    public synchronized <T extends YANIPoolableObject> T obtain(Class<T> objClass) {
        Deque<YANIPoolableObject> deque = mObjectsMap.get(objClass);

        if (deque == null) {
            deque = new ArrayDeque<>();
            mObjectsMap.put(objClass, deque);
        }

        T recycledInstance = (T) deque.pollLast();

        try {
            return (recycledInstance == null) ? objClass.newInstance() : recycledInstance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (recycledInstance == null)
            throw new RuntimeException("Object pool cannot instantiate object of type " + objClass.getSimpleName() + " . Poolable objects must have a default constructor");

        return null;

    }

    /**
     * Places an object into an object pool.
     * Placed object should not be referenced any more directly.
     *
     * @param obj the poolable object to be placed back to the pool
     */
    public synchronized void offer(YANIPoolableObject obj) {

        Deque<YANIPoolableObject> deque;
        if (mObjectsMap.containsKey(obj.getClass())) {
            deque = mObjectsMap.get(obj.getClass());
        } else {
            deque = new ArrayDeque<>();
            mObjectsMap.put(obj.getClass(), deque);
        }

        //put an object into a deque
        deque.offer(obj);
    }
}
