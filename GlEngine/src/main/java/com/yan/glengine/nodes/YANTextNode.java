package com.yan.glengine.nodes;

import com.yan.glengine.assets.YANTextureRegion;
import com.yan.glengine.assets.font.YANFont;
import com.yan.glengine.assets.font.YANFontChar;
import com.yan.glengine.programs.YANTextShaderProgram;

/**
 * Created by Yan-Home on 10/26/2014.
 */
public class YANTextNode extends YANBaseNode<YANTextShaderProgram> {

    private static final int VALUES_PER_VERTEX = POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT;
    private static final int VERTICES_COUNT = 6;

    /**
     * Text that will be rendered
     */
    private String mText;

    /**
     * Font that is used to draw text
     */
    private YANFont mFont;

    /**
     * vertex data is a cached float array with a fixed size.
     * that array changing when the text is changing , it will always have only position and texture coordinates data.
     */
    private float[] mVertexData;


    public YANTextNode(YANFont font) {
        mFont = font;
        mVertexData = new float[VALUES_PER_VERTEX * VERTICES_COUNT];
    }

    @Override
    protected float[] createVertexData() {

        //TODO : here we are creating a mesh with indices and texture coordinates for the entire text
        //that way we can draw it in one draw call

        float halfWidth = getSize().getX() / 2f;
        float halfHeight = getSize().getY() / 2f;

        YANTextureRegion sampleTextureRegion = null;

        for (YANFontChar aChar : mFont.getCharsList()) {

            //We are looking for "@" character
            if (aChar.getID() == 64) {
                sampleTextureRegion = aChar.getYANTextureRegion();
            }
        }

        //we just taking a random character to try the rendering

//        YANTextureRegion sampleTextureRegion = new YANTextureRegion(0,1,0,1);//mFont.getCharsList().get(2).getYANTextureRegion();
//        YANTextureRegion sampleTextureRegion = new YANTextureRegion(0,0.1015625f,0,0.1015625f);//mFont.getCharsList().get(2).getYANTextureRegion();

        // Order of coordinates: X, Y, U, V
        // Triangle Fan

        //first vertex (center)
        mVertexData[0] = 0f;
        mVertexData[1] = 0f;

        mVertexData[2] = sampleTextureRegion.getU0() + ((sampleTextureRegion.getU1() - sampleTextureRegion.getU0()) / 2);
        mVertexData[3] = sampleTextureRegion.getV0() + ((sampleTextureRegion.getV1() - sampleTextureRegion.getV0()) / 2);

        //second vertex (bottom left)
        mVertexData[4] = -halfWidth;
        mVertexData[5] = -halfHeight;
        mVertexData[6] = sampleTextureRegion.getU0();
        mVertexData[7] = sampleTextureRegion.getV0();

        //third vertex (top left)
        mVertexData[8] = -halfWidth;
        mVertexData[9] = halfHeight;
        mVertexData[10] = sampleTextureRegion.getU0();
        mVertexData[11] = sampleTextureRegion.getV1();

        //fourth vertex (top right)
        mVertexData[12] = halfWidth;
        mVertexData[13] = halfHeight;
        mVertexData[14] = sampleTextureRegion.getU1();
        mVertexData[15] = sampleTextureRegion.getV1();

        //fifth vertex (bottom right)
        mVertexData[16] = halfWidth;
        mVertexData[17] = -halfHeight;
        mVertexData[18] = sampleTextureRegion.getU1();
        mVertexData[19] = sampleTextureRegion.getV0();

        //sixth vertex (bottom left)
        mVertexData[20] = -halfWidth;
        mVertexData[21] = -halfHeight;
        mVertexData[22] = sampleTextureRegion.getU0();
        mVertexData[23] = sampleTextureRegion.getV0();

        return mVertexData;
    }


    @Override
    public void bindData(YANTextShaderProgram shaderProgram) {
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

        //TODO : maybe color ?
    }

    public void setText(String text) {
        mText = text;

        //every time text changes , data must be recalculated
        recalculateDimensions();
    }

    public void setFont(YANFont font) {
        mFont = font;
    }

    public YANFont getFont() {
        return mFont;
    }
}
