/**
 * Created by Yan-Home on 10/3/2014.
 * This node is rendered by using a texture
 */
package com.yan.glengine.nodes;

import com.yan.glengine.assets.atlas.YANTextureRegion;
import com.yan.glengine.programs.YANTextureShaderProgram;

public class YANTexturedNode extends YANBaseNode<YANTextureShaderProgram>{

    private YANTextureRegion mTextureRegion;

    public YANTexturedNode(YANTextureRegion textureRegion) {
        super();
        mTextureRegion = textureRegion;
    }

    public YANTextureRegion getTextureRegion() {
        return mTextureRegion;
    }

    public void setTextureRegion(YANTextureRegion textureRegion) {
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

        return new float[]{
                // Order of coordinates: X, Y, U, V
                // Triangle Fan

                //first vertex (center)
                0f, 0f, mTextureRegion.getU0() + ((mTextureRegion.getU1() - mTextureRegion.getU0()) / 2), mTextureRegion.getV0() + ((mTextureRegion.getV1() - mTextureRegion.getV0()) / 2),

                //second vertex (bottom left)
                -halfWidth, -halfHeight, mTextureRegion.getU0(), mTextureRegion.getV0(),

                //third vertex (top left)
                -halfWidth, halfHeight, mTextureRegion.getU0(), mTextureRegion.getV1(),

                //fourth vertex (top right)
                halfWidth, halfHeight, mTextureRegion.getU1(), mTextureRegion.getV1(),

                //fifth vertex (bottom right)
                halfWidth, -halfHeight, mTextureRegion.getU1(), mTextureRegion.getV0(),

                //sixth vertex (bottom left)
                -halfWidth, -halfHeight, mTextureRegion.getU0(), mTextureRegion.getV0(),
        };
    }



}