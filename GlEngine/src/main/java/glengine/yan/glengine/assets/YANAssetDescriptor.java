package glengine.yan.glengine.assets;

/**
 * Created by Yan-Home on 1/10/2015.
 * <p/>
 * Used to contain an information about an asset.
 */
public class YANAssetDescriptor {

    /**
     * Assets loading will depend on their type
     */
    public enum YANAssetType {
        TEXTURE_ATLAS, FONT
    }

    private YANAssetType mType;
    private String mPathToAsset;
    private String mAssetExtension;
    private String mAssetName;

    /**
     * @param type           type of the asset
     * @param pathToAsset    full path to the asset starting from an asset folder as a root
     * @param assetName      name of the asset without a file extension
     * @param assetExtension file extension of the asset
     */
    public YANAssetDescriptor(YANAssetType type, String pathToAsset, String assetName, String assetExtension) {
        mType = type;
        mPathToAsset = pathToAsset;
        mAssetExtension = assetExtension;
        mAssetName = assetName;
    }

    public YANAssetType getType() {
        return mType;
    }

    public String getPathToAsset() {
        return mPathToAsset;
    }

    public String getAssetExtension() {
        return mAssetExtension;
    }

    public String getAssetName() {
        return mAssetName;
    }
}
