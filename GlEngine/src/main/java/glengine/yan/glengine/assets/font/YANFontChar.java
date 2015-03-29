package glengine.yan.glengine.assets.font;

import glengine.yan.glengine.assets.YANTextureRegion;

/**
 * Created by Yan-Home on 1/10/2015.
 */
public class YANFontChar {

    private int mID;
    private int mX;
    private int mY;
    private int mWidth;
    private int mHeight;
    private int mXOffset;
    private int mYOffseet;
    private int mXAdvance;
    private YANTextureRegion mYANTextureRegion;
    private YANFont mFont;

    public YANFontChar(int ID, int x, int y, int width, int height, int XOffset, int YOffseet, int XAdvance) {
        mID = ID;
        mX = x;
        mY = y;
        mWidth = width;
        mHeight = height;
        mXOffset = XOffset;
        mYOffseet = YOffseet;
        mXAdvance = XAdvance;
    }

    private void calculateTextureRegion() {
        float u0 = (float) mX / (float) mFont.getScaleWidth();
        float u1 = ((float) mX + (float) mWidth) / (float) mFont.getScaleWidth();
        float v0 = (float) mY / (float) mFont.getScaleHeight();
        float v1 = (((float) mY + (float) mHeight) / (float) mFont.getScaleHeight());

        mYANTextureRegion = new YANTextureRegion(u0, u1, v0, v1);
    }

    public int getID() {
        return mID;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public int getXOffset() {
        return mXOffset;
    }

    public int getYOffseet() {
        return mYOffseet;
    }

    public int getXAdvance() {
        return mXAdvance;
    }

    public YANTextureRegion getYANTextureRegion() {
        return mYANTextureRegion;
    }

    public void setFont(YANFont font) {
        mFont = font;
        calculateTextureRegion();
    }

    public YANFont getFont() {
        return mFont;
    }
}
