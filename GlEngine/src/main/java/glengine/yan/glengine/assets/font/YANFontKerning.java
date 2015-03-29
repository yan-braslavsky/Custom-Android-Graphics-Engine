package glengine.yan.glengine.assets.font;

/**
 * Created by Yan-Home on 1/10/2015.
 */
public class YANFontKerning {

    private int mFirstCharId;
    private int mSecondCharId;
    private int mAmount;

    public YANFontKerning(int firstCharId, int secondCharId, int amount) {
        mFirstCharId = firstCharId;
        mSecondCharId = secondCharId;
        mAmount = amount;
    }

    public int getFirstCharId() {
        return mFirstCharId;
    }

    public int getSecondCharId() {
        return mSecondCharId;
    }

    public int getAmount() {
        return mAmount;
    }
}
