package glengine.yan.glengine.service;

/**
 * Created by Yan-Home on 4/25/2015.
 *
 * Marker interface to be used by service locator
 */
public interface IService {

    /**
     * Called when service is removed from locator.
     * Used to clear any long living resources.
     */
    void clearServiceData();
}
