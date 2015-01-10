package com.yan.glengine.assets.font;

import java.util.List;

/**
 * Created by Yan-Home on 1/10/2015.
 */
public class YANFont {

    /**
     * This is the name of the true type font.
     */
    private String mFace;

    /**
     * This is the distance in pixels between each line of text.
     */
    private int mLineHeight;

    /**
     * The number of pixels from the absolute top of the line to the base of the characters.
     */
    private int mBase;

    /**
     * The width of the texture, normally used to scale the x pos of the character image.
     */
    private int mScaleWidth;

    /**
     * The height of the texture, normally used to scale the y pos of the character image.
     */
    private int mScaleHeight;

    /**
     * The texture file name.
     */
    private String mTextureFile;

    private List<YANFontChar> mCharsList;

    private List<YANFontKerning> mKerningList;

    public YANFont(String face, int lineHeight, int base, int scaleWidth, int scaleHeight, String textureFile, List<YANFontChar> charsList, List<YANFontKerning> kerningList) {
        mFace = face;
        mLineHeight = lineHeight;
        mBase = base;
        mScaleWidth = scaleWidth;
        mScaleHeight = scaleHeight;
        mTextureFile = textureFile;
        mCharsList = charsList;
        mKerningList = kerningList;
    }

    public String getFace() {
        return mFace;
    }

    public int getLineHeight() {
        return mLineHeight;
    }

    public int getBase() {
        return mBase;
    }

    public int getScaleWidth() {
        return mScaleWidth;
    }

    public int getScaleHeight() {
        return mScaleHeight;
    }

    public String getTextureFile() {
        return mTextureFile;
    }

    public List<YANFontChar> getCharsList() {
        return mCharsList;
    }

    public List<YANFontKerning> getKerningList() {
        return mKerningList;
    }
}
