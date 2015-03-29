package glengine.yan.glengine.assets;

/**
 * Created by Yan-Home on 1/10/2015.
 *
 * @param <T> class of the asset that will be loaded
 */
public interface YANAssetLoader<T> {
    T loadAsset(YANAssetDescriptor assetDescriptor);
}
