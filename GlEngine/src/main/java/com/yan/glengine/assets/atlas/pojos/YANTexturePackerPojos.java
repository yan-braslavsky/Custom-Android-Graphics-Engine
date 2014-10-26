package com.yan.glengine.assets.atlas.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Yan-Home on 10/24/2014.
 */
public class YANTexturePackerPojos {

    public static class WrappingObject {

        @SerializedName("frames")
        private List<Frame> mFramesList;

        @SerializedName("meta")
        private MetaData mMetaData;

        public List<Frame> getFramesList() {
            return mFramesList;
        }

        public MetaData getMetaData() {
            return mMetaData;
        }
    }

    public static class MetaData {

        @SerializedName("image")
        private String mAtlasImageFileName;
        @SerializedName("size")
        private AtlasImageSize mAtlasImageSize;

        public String getAtlasImageFileName() {
            return mAtlasImageFileName;
        }

        public AtlasImageSize getAtlasImageSize() {
            return mAtlasImageSize;
        }
    }

    public static class AtlasImageSize {
        @SerializedName("w")
        float w;
        @SerializedName("h")
        float h;

        public float getW() {
            return w;
        }

        public float getH() {
            return h;
        }
    }

    public static class Frame {

        @SerializedName("filename")
        private String mTextureFileName;
        @SerializedName("frame")
        private FrameData mFrameData;

        public String getTextureFileName() {
            return mTextureFileName;
        }

        public FrameData getFrameData() {
            return mFrameData;
        }
    }

    public static class FrameData {

        @SerializedName("x")
        float x;
        @SerializedName("y")
        float y;
        @SerializedName("w")
        float w;
        @SerializedName("h")
        float h;

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getW() {
            return w;
        }

        public float getH() {
            return h;
        }
    }
}
