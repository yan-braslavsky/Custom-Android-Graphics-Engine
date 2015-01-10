package com.yan.glengine.assets.font;

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
}
