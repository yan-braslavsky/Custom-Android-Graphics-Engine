package com.example.yan_home.openglengineandroid.assets;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANTexture {

    private int mTextureResourceID;

    public YANTexture(int textureResourceID) {
        this.mTextureResourceID = textureResourceID;
    }

    public int getTextureResourceID() {
        return mTextureResourceID;
    }

    @Override
    public int hashCode() {
        return mTextureResourceID;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof YANTexture))
            return false;
        YANTexture tex = (YANTexture) o;
        return tex.getTextureResourceID() == mTextureResourceID;
    }
}
