package glengine.yan.glengine.util.object_pool;

/**
 * Created by Yan-Home on 4/5/2015.
 * <p/>
 * This interface defines a functionality of objects that can be reused using Object pool.
 * Poolable object must have a default constructor to be initialized by the object pool.
 */
public interface YANIPoolableObject {

    /**
     * Resets this object to it's default state, as it was just created.
     */
    void resetState();
}
