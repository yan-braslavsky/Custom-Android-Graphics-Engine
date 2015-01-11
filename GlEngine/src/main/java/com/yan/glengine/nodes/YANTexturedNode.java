/**
 * Created by Yan-Home on 10/3/2014.
 * This node is rendered by using a texture
 */
package com.yan.glengine.nodes;

import com.yan.glengine.assets.atlas.YANAtlasTextureRegion;
import com.yan.glengine.programs.YANTextureShaderProgram;

public class YANTexturedNode extends YANBaseNode<YANTextureShaderProgram> {

    private static final int VALUES_PER_VERTEX = POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT;
    private static final int VERTICES_COUNT = 6;
    private YANAtlasTextureRegion mTextureRegion;

    //vertex data is a cached float array with a fixed size.
    //that array size is not subject to change , it will always have only position and
    //texture coordinates data.
    private float[] mVertexData;

    public YANTexturedNode(YANAtlasTextureRegion textureRegion) {
        super();
        mTextureRegion = textureRegion;
        mVertexData = new float[VALUES_PER_VERTEX * VERTICES_COUNT];
    }

    public YANAtlasTextureRegion getTextureRegion() {
        return mTextureRegion;
    }

    public void setTextureRegion(YANAtlasTextureRegion textureRegion) {
        mTextureRegion = textureRegion;
        recalculateDimensions();
    }

    @Override
    public void bindData(YANTextureShaderProgram shaderProgram) {
        vertexArray.setVertexAttribPointer(
                0,
                shaderProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);

        vertexArray.setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                shaderProgram.getTextureCoordinatesAttributeLocation(),
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE);
    }

    @Override
    protected float[] createVertexData() {
        float halfWidth = getSize().getX() / 2f;
        float halfHeight = getSize().getY() / 2f;

        // Order of coordinates: X, Y, U, V
        // Triangle Fan

        //first vertex (center)
        mVertexData[0] = 0f;
        mVertexData[1] = 0f;
        mVertexData[2] = mTextureRegion.getU0() + ((mTextureRegion.getU1() - mTextureRegion.getU0()) / 2);
        mVertexData[3] = mTextureRegion.getV0() + ((mTextureRegion.getV1() - mTextureRegion.getV0()) / 2);

        //second vertex (bottom left)
        mVertexData[4] = -halfWidth;
        mVertexData[5] = -halfHeight;
        mVertexData[6] = mTextureRegion.getU0();
        mVertexData[7] = mTextureRegion.getV0();

        //third vertex (top left)
        mVertexData[8] = -halfWidth;
        mVertexData[9] = halfHeight;
        mVertexData[10] = mTextureRegion.getU0();
        mVertexData[11] = mTextureRegion.getV1();

        //fourth vertex (top right)
        mVertexData[12] = halfWidth;
        mVertexData[13] = halfHeight;
        mVertexData[14] = mTextureRegion.getU1();
        mVertexData[15] = mTextureRegion.getV1();

        //fifth vertex (bottom right)
        mVertexData[16] = halfWidth;
        mVertexData[17] = -halfHeight;
        mVertexData[18] = mTextureRegion.getU1();
        mVertexData[19] = mTextureRegion.getV0();

        //sixth vertex (bottom left)
        mVertexData[20] = -halfWidth;
        mVertexData[21] = -halfHeight;
        mVertexData[22] = mTextureRegion.getU0();
        mVertexData[23] = mTextureRegion.getV0();

        return mVertexData;

    }


}