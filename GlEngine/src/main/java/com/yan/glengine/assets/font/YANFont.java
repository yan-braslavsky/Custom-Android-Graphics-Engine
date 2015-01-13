package com.yan.glengine.assets.font;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Map<Integer, YANFontChar> mCharsMap;


    /**
     * Used to easily access kerning values
     */
    private Map<Integer, Map<Integer, Integer>> mKerningsTable;

    public YANFont(String face, int lineHeight, int base, int scaleWidth, int scaleHeight, String textureFile, Map<Integer, YANFontChar> charsMap, List<YANFontKerning> kerningList) {
        mFace = face;
        mLineHeight = lineHeight;
        mBase = base;
        mScaleWidth = scaleWidth;
        mScaleHeight = scaleHeight;
        mTextureFile = textureFile;
        mCharsMap = charsMap;

        //allocate kerning table
        mKerningsTable = new HashMap<>();

        //load kerning table
        loadKerningTable(kerningList);

    }

    private void loadKerningTable(List<YANFontKerning> kerningList) {
        for (YANFontKerning kerning : kerningList) {
            Map<Integer, Integer> followingCharIds;
            if (!mKerningsTable.containsKey(kerning.getFirstCharId())) {
                followingCharIds = new HashMap<>();
                mKerningsTable.put(kerning.getFirstCharId(), followingCharIds);
            } else {
                followingCharIds = mKerningsTable.get(kerning.getFirstCharId());
            }

            followingCharIds.put(kerning.getSecondCharId(), kerning.getAmount());
        }
    }

    public int getKerningValueForChars(int firstCharId, int secondCharId) {
        int value = 0;
        if (mKerningsTable.get(firstCharId) != null && mKerningsTable.get(firstCharId).get(secondCharId) != null) {
            value = mKerningsTable.get(firstCharId).get(secondCharId);
        }
        return value;
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

    public String getGlyphImageFilePath() {
        return mTextureFile;
    }

    public Map<Integer, YANFontChar> getCharsMap() {
        return mCharsMap;
    }

}
