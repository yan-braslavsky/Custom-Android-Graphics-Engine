package com.example.yan_home.openglengineandroid.font_test;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Iterator;
import java.util.Vector;

//explanation is here : http://androidblog.reindustries.com/a-real-opengl-es-2-0-2d-tutorial-part-8-rendering-text/
public class TextManager {

    private static final float RI_TEXT_UV_BOX_WIDTH = 0.125f;
    private static final float RI_TEXT_WIDTH = 32.0f;
    private static final float RI_TEXT_SPACE_SIZE = 20f;

    //shader location ids
    public static final String U_MVP_MATRIX = "u_Matrix";
    public static final String V_POSITION = "a_Position";
    public static final String A_TEX_COORD = "a_TextureCoordinates";
    public static final String S_TEXTURE = "u_TextureUnit";

    private FloatBuffer vertexBuffer;
    private FloatBuffer textureBuffer;
    private FloatBuffer colorBuffer;
    private ShortBuffer drawListBuffer;

    private float[] mVecs;
    private float[] mUvs;
    private short[] mIndices;
    private float[] mColors;

    private int index_vecs;
    private int index_indices;
    private int index_uvs;
    private int index_colors;

    private int textureHandleId;

    private static int[] l_size = {
            36, 29, 30, 34, 25, 25, 34, 33,
            11, 20, 31, 24, 48, 35, 39, 29,
            42, 31, 27, 31, 34, 35, 46, 35,
            31, 27, 30, 26, 28, 26, 31, 28,
            28, 28, 29, 29, 14, 24, 30, 18,
            26, 14, 14, 14, 25, 28, 31, 0,
            0, 38, 39, 12, 36, 34, 0, 0,
            0, 38, 0, 0, 0, 0, 0, 0
    };

    private Vector<TextObject> mTxtCollection;

    public TextManager() {
        // Create our container
        mTxtCollection = new Vector<TextObject>();

        // Create the arrays
        mVecs = new float[3 * 10];
        mColors = new float[4 * 10];
        mUvs = new float[2 * 10];
        mIndices = new short[10];

        // init as 0 as default
        textureHandleId = 0;
    }

    public void addText(TextObject obj) {
        // Add text object to our collection
        mTxtCollection.add(obj);
    }

    public void setTextureID(int val) {
        textureHandleId = val;
    }


    public void AddCharRenderInformation(float[] vec, float[] cs, float[] uv, short[] indi) {
        // We need a base value because the object has mIndices related to
        // that object and not to this collection so basically we need to
        // translate the mIndices to align with the vertex location in
        // mVecs array of vectors.
        short base = (short) (index_vecs / 3);

        // We should add the vec, translating the mIndices to our saved vector
        for (int i = 0; i < vec.length; i++) {
            mVecs[index_vecs] = vec[i];
            index_vecs++;
        }

        // We should add the mColors, so we can use the same texture for multiple effects.
        for (int i = 0; i < cs.length; i++) {
            mColors[index_colors] = cs[i];
            index_colors++;
        }

        // We should add the mUvs
        for (int i = 0; i < uv.length; i++) {
            mUvs[index_uvs] = uv[i];
            index_uvs++;
        }

        // We handle the mIndices
        for (int j = 0; j < indi.length; j++) {
            mIndices[index_indices] = (short) (base + indi[j]);
            index_indices++;
        }
    }

    public void PrepareDrawInfo() {
        // Reset the mIndices.
        index_vecs = 0;
        index_indices = 0;
        index_uvs = 0;
        index_colors = 0;

        // Get the total amount of characters
        int charcount = 0;
        for (TextObject txt : mTxtCollection) {
            if (txt != null) {
                if (!(txt.text == null)) {
                    charcount += txt.text.length();
                }
            }
        }

        // Create the arrays we need with the correct size.
        mVecs = null;
        mColors = null;
        mUvs = null;
        mIndices = null;

        mVecs = new float[charcount * 12];
        mColors = new float[charcount * 16];
        mUvs = new float[charcount * 8];
        mIndices = new short[charcount * 6];

    }


    public void PrepareDraw() {
        // Setup all the arrays
        PrepareDrawInfo();

        Iterator<TextObject> it = mTxtCollection.iterator();
        while (it.hasNext()) {
            TextObject txt = it.next();
            if (txt.text != null) {
                convertTextToTriangleInfo(txt);
            }
        }
    }

    public void Draw(float[] mvpMatrix) {
        // Set the correct shader for our grid object.
        GLES20.glUseProgram(ShaderHelperTools.sp_Text);

        // The vertex buffer.
        ByteBuffer bb = ByteBuffer.allocateDirect(mVecs.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(mVecs);
        vertexBuffer.position(0);

        // The vertex buffer.
        ByteBuffer bb3 = ByteBuffer.allocateDirect(mColors.length * 4);
        bb3.order(ByteOrder.nativeOrder());
        colorBuffer = bb3.asFloatBuffer();
        colorBuffer.put(mColors);
        colorBuffer.position(0);

        // The texture buffer
        ByteBuffer bb2 = ByteBuffer.allocateDirect(mUvs.length * 4);
        bb2.order(ByteOrder.nativeOrder());
        textureBuffer = bb2.asFloatBuffer();
        textureBuffer.put(mUvs);
        textureBuffer.position(0);

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(mIndices.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(mIndices);
        drawListBuffer.position(0);

        // get handle to vertex shader's vPosition member
        int positionHandle = GLES20.glGetAttribLocation(ShaderHelperTools.sp_Text, V_POSITION);

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(positionHandle);

        // Prepare the background coordinate data
        GLES20.glVertexAttribPointer(positionHandle, 3,
                GLES20.GL_FLOAT, false,
                0, vertexBuffer);

        int texCoordHandle = GLES20.glGetAttribLocation(ShaderHelperTools.sp_Text, A_TEX_COORD);

        // Prepare the texturecoordinates
        GLES20.glVertexAttribPointer(texCoordHandle, 2, GLES20.GL_FLOAT,
                false,
                0, textureBuffer);

        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glEnableVertexAttribArray(texCoordHandle);

        int mColorHandle = GLES20.glGetAttribLocation(ShaderHelperTools.sp_Text, "a_Color");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mColorHandle);

        // Prepare the background coordinate data
        GLES20.glVertexAttribPointer(mColorHandle, 4,
                GLES20.GL_FLOAT, false,
                0, colorBuffer);

        // get handle to shape's transformation matrix
        int mtrxHandle = GLES20.glGetUniformLocation(ShaderHelperTools.sp_Text, U_MVP_MATRIX);

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mtrxHandle, 1, false, mvpMatrix, 0);

        int mSamplerLoc = GLES20.glGetUniformLocation(ShaderHelperTools.sp_Text, S_TEXTURE);

        // Set the sampler texture unit to our selected id
        GLES20.glUniform1i(mSamplerLoc, textureHandleId);

        // Draw the triangle
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, mIndices.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(texCoordHandle);
        GLES20.glDisableVertexAttribArray(mColorHandle);

    }

    private int convertCharToIndex(int c_val) {
        int indx = -1;

        // Retrieve the index
        if (c_val > 64 && c_val < 91) // A-Z
            indx = c_val - 65;
        else if (c_val > 96 && c_val < 123) // a-z
            indx = c_val - 97;
        else if (c_val > 47 && c_val < 58) // 0-9
            indx = c_val - 48 + 26;
        else if (c_val == 43) // +
            indx = 38;
        else if (c_val == 45) // -
            indx = 39;
        else if (c_val == 33) // !
            indx = 36;
        else if (c_val == 63) // ?
            indx = 37;
        else if (c_val == 61) // =
            indx = 40;
        else if (c_val == 58) // :
            indx = 41;
        else if (c_val == 46) // .
            indx = 42;
        else if (c_val == 44) // ,
            indx = 43;
        else if (c_val == 42) // *
            indx = 44;
        else if (c_val == 36) // $
            indx = 45;

        return indx;
    }


    private void convertTextToTriangleInfo(TextObject val) {
        // Get attributes from text object
        float x = val.x;
        float y = val.y;
        String text = val.text;

        // Create
        for (int j = 0; j < text.length(); j++) {
            // get ascii value
            char c = text.charAt(j);
            int c_val = (int) c;

            int indx = convertCharToIndex(c_val);

            if (indx == -1) {
                // unknown character, we will add a space for it to be save.
                x += ((RI_TEXT_SPACE_SIZE));
                continue;
            }

            // Calculate the uv parts
            int row = indx / 8;
            int col = indx % 8;

            float v = row * RI_TEXT_UV_BOX_WIDTH;
            float v2 = v + RI_TEXT_UV_BOX_WIDTH;
            float u = col * RI_TEXT_UV_BOX_WIDTH;
            float u2 = u + RI_TEXT_UV_BOX_WIDTH;

            // Creating the triangle information
            float[] vec = new float[12];
            float[] uv = new float[8];
            float[] colors = new float[16];

            vec[0] = x;
            vec[1] = y + (RI_TEXT_WIDTH);
            vec[2] = 0.99f;
            vec[3] = x;
            vec[4] = y;
            vec[5] = 0.99f;
            vec[6] = x + (RI_TEXT_WIDTH);
            vec[7] = y;
            vec[8] = 0.99f;
            vec[9] = x + (RI_TEXT_WIDTH);
            vec[10] = y + (RI_TEXT_WIDTH);
            vec[11] = 0.99f;

            colors = new float[]
                    {val.color[0], val.color[1], val.color[2], val.color[3],
                            val.color[0], val.color[1], val.color[2], val.color[3],
                            val.color[0], val.color[1], val.color[2], val.color[3],
                            val.color[0], val.color[1], val.color[2], val.color[3]
                    };
            // 0.001f = texture bleeding hack/fix
            uv[0] = u + 0.001f;
            uv[1] = v + 0.001f;
            uv[2] = u + 0.001f;
            uv[3] = v2 - 0.001f;
            uv[4] = u2 - 0.001f;
            uv[5] = v2 - 0.001f;
            uv[6] = u2 - 0.001f;
            uv[7] = v + 0.001f;

            short[] inds = {0, 1, 2, 0, 2, 3};

            // Add our triangle information to our collection for 1 render call.
            AddCharRenderInformation(vec, colors, uv, inds);

            // Calculate the new position
            x += ((l_size[indx] / 2));
        }
    }
}
