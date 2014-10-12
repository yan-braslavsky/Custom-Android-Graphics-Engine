package com.example.yan_home.openglengineandroid.font_test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLUtils;
import android.opengl.Matrix;

import com.example.yan_home.openglengineandroid.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLRenderer implements Renderer {

    // Our matrices
    private final float[] mtrxProjection = new float[16];
    private final float[] mtrxView = new float[16];
    private final float[] mtrxProjectionAndView = new float[16];

    // Geometric variables
    public static float vertices[];
    public static short indices[];
    public static float uvs[];
    public FloatBuffer vertexBuffer;
    public ShortBuffer drawListBuffer;
    public FloatBuffer uvBuffer;
    public TextManager tm;

    float screenScaleUniform = 1.0f;
    float screenScaleX = 1.0f;
    float screenScaleY = 1.0f;
    float screenWidthInPixels = 320.0f;
    float screenHeightInPixels = 480.0f;

    // Misc
    Context mContext;
    long mLastTime;

    public GLRenderer(Context c) {
        mContext = c;
        mLastTime = System.currentTimeMillis() + 100;
    }

    public void onPause() {
    }

    public void onResume() {
    }

    @Override
    public void onDrawFrame(GL10 unused) {

        // Render our example
        Render(mtrxProjectionAndView);

        // Render the text
        if (tm != null)
            tm.Draw(mtrxProjectionAndView);
    }

    private void Render(float[] m) {
        // Set our shaderprogram to image shader
        GLES20.glUseProgram(riGraphicTools.shaderProgram_Image);

        // clear Screen and Depth Buffer, we have set the clear color as black.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // get handle to vertex shader's vPosition member and add vertices
        int mPositionHandle = GLES20.glGetAttribLocation(riGraphicTools.shaderProgram_Image, "vPosition");
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Get handle to texture coordinates location and load the texture uvs
        int mTexCoordLoc = GLES20.glGetAttribLocation(riGraphicTools.shaderProgram_Image, "a_texCoord");
        GLES20.glVertexAttribPointer(mTexCoordLoc, 2, GLES20.GL_FLOAT, false, 0, uvBuffer);
        GLES20.glEnableVertexAttribArray(mTexCoordLoc);

        // Get handle to shape's transformation matrix and add our matrix
        int mtrxhandle = GLES20.glGetUniformLocation(riGraphicTools.shaderProgram_Image, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mtrxhandle, 1, false, m, 0);

        // Get handle to textures locations
        int mSamplerLoc = GLES20.glGetUniformLocation(riGraphicTools.shaderProgram_Image, "s_texture");

        // Set the sampler texture unit to 0, where we have saved the texture.
        GLES20.glUniform1i(mSamplerLoc, 0);

        // Draw the triangle
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTexCoordLoc);

    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        // Redo the Viewport, making it fullscreen.
        GLES20.glViewport(0, 0, width, height);

        // Clear our matrices
        for (int i = 0; i < 16; i++) {
            mtrxProjection[i] = 0.0f;
            mtrxView[i] = 0.0f;
            mtrxProjectionAndView[i] = 0.0f;
        }

        // Setup our screen width and height for normal sprite translation.
        Matrix.orthoM(mtrxProjection, 0, 0f, width, 0.0f, height, 0, 50);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mtrxView, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mtrxProjectionAndView, 0, mtrxProjection, 0, mtrxView, 0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        // Setup our scaling system
        SetupScaling();
        // Create the triangles
        SetupTriangle();
        // Create the image information
        SetupImage();
        // Create our texts
        SetupText();

        // Set the clear color to black
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1);

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        // Create the shaders, images
        int vertexShader = riGraphicTools.loadShader(GLES20.GL_VERTEX_SHADER, riGraphicTools.vs_Image);
        int fragmentShader = riGraphicTools.loadShader(GLES20.GL_FRAGMENT_SHADER, riGraphicTools.fs_Image);

        riGraphicTools.shaderProgram_Image = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        GLES20.glAttachShader(riGraphicTools.shaderProgram_Image, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(riGraphicTools.shaderProgram_Image, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(riGraphicTools.shaderProgram_Image);                  // creates OpenGL ES program executables

        // Text shader
        int vshadert = riGraphicTools.loadShader(GLES20.GL_VERTEX_SHADER, riGraphicTools.vs_Text);
        int fshadert = riGraphicTools.loadShader(GLES20.GL_FRAGMENT_SHADER, riGraphicTools.fs_Text);

        riGraphicTools.shaderProgram_Text = GLES20.glCreateProgram();
        GLES20.glAttachShader(riGraphicTools.shaderProgram_Text, vshadert);
        GLES20.glAttachShader(riGraphicTools.shaderProgram_Text, fshadert);        // add the fragment shader to program
        GLES20.glLinkProgram(riGraphicTools.shaderProgram_Text);                  // creates OpenGL ES program executables

        // Set our shader programm
        GLES20.glUseProgram(riGraphicTools.shaderProgram_Image);
    }

    public void SetupText() {
        // Create our text manager
        tm = new TextManager();

        // Tell our text manager to use index 1 of textures loaded
        tm.setTextureID(1);

        // Pass the uniform scale
        tm.setUniformscale(screenScaleUniform);

        // Create our new textobject
        TextObject txt = new TextObject("hello world", 10f, 10f);

        // Add it to our manager
        tm.addText(txt);

        // Prepare the text for rendering
        tm.PrepareDraw();
    }

    public void SetupScaling() {
        // The screen resolutions
        screenWidthInPixels = (mContext.getResources().getDisplayMetrics().widthPixels);
        screenHeightInPixels = (mContext.getResources().getDisplayMetrics().heightPixels);

        // Orientation is assumed portrait
        screenScaleX = screenWidthInPixels / 320.0f;
        screenScaleY = screenHeightInPixels / 480.0f;

        // Get our uniform scaler
        if (screenScaleX > screenScaleY)
            screenScaleUniform = screenScaleY;
        else
            screenScaleUniform = screenScaleX;
    }

    public void SetupImage() {
        // We will use a randomizer for randomizing the textures from texture atlas.
        // This is strictly optional as it only effects the output of our app,
        // Not the actual knowledge.
        Random rnd = new Random();

        // 30 imageobjects times 4 vertices times (u and v)
        uvs = new float[30 * 4 * 2];

        // We will make 30 randomly textures objects
        for (int i = 0; i < 30; i++) {
            int random_u_offset = rnd.nextInt(2);
            int random_v_offset = rnd.nextInt(2);

            // Adding the UV's using the offsets
            uvs[(i * 8) + 0] = random_u_offset * 0.5f;
            uvs[(i * 8) + 1] = random_v_offset * 0.5f;
            uvs[(i * 8) + 2] = random_u_offset * 0.5f;
            uvs[(i * 8) + 3] = (random_v_offset + 1) * 0.5f;
            uvs[(i * 8) + 4] = (random_u_offset + 1) * 0.5f;
            uvs[(i * 8) + 5] = (random_v_offset + 1) * 0.5f;
            uvs[(i * 8) + 6] = (random_u_offset + 1) * 0.5f;
            uvs[(i * 8) + 7] = random_v_offset * 0.5f;
        }

        // The texture buffer
        ByteBuffer bb = ByteBuffer.allocateDirect(uvs.length * 4);
        bb.order(ByteOrder.nativeOrder());
        uvBuffer = bb.asFloatBuffer();
        uvBuffer.put(uvs);
        uvBuffer.position(0);

        // Generate Textures, if more needed, alter these numbers.
        int[] texturenames = new int[2];
        GLES20.glGenTextures(2, texturenames, 0);

        // Temporary create a bitmap
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.font);

        // Bind texture to texturename
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texturenames[0]);

        // Set filtering
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        // Load the bitmap into the bound texture.
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);

        // We are done using the bitmap so we should recycle it.
        bmp.recycle();

        // Again for the text texture
        bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.font);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + 1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texturenames[1]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);
        bmp.recycle();
    }

    public void SetupTriangle() {
        // We will need a randomizer
        Random rnd = new Random();

        // Our collection of vertices
        vertices = new float[30 * 4 * 3];

        // Create the vertex data
        for (int i = 0; i < 30; i++) {
            int offset_x = rnd.nextInt((int) screenWidthInPixels);
            int offset_y = rnd.nextInt((int) screenHeightInPixels);

            // Create the 2D parts of our 3D vertices, others are default 0.0f
            vertices[(i * 12) + 0] = offset_x;
            vertices[(i * 12) + 1] = offset_y + (30.0f * screenScaleUniform);
            vertices[(i * 12) + 2] = 0f;
            vertices[(i * 12) + 3] = offset_x;
            vertices[(i * 12) + 4] = offset_y;
            vertices[(i * 12) + 5] = 0f;
            vertices[(i * 12) + 6] = offset_x + (30.0f * screenScaleUniform);
            vertices[(i * 12) + 7] = offset_y;
            vertices[(i * 12) + 8] = 0f;
            vertices[(i * 12) + 9] = offset_x + (30.0f * screenScaleUniform);
            vertices[(i * 12) + 10] = offset_y + (30.0f * screenScaleUniform);
            vertices[(i * 12) + 11] = 0f;
        }

        // The indices for all textured quads
        indices = new short[30 * 6];
        int last = 0;
        for (int i = 0; i < 30; i++) {
            // We need to set the new indices for the new quad
            indices[(i * 6) + 0] = (short) (last + 0);
            indices[(i * 6) + 1] = (short) (last + 1);
            indices[(i * 6) + 2] = (short) (last + 2);
            indices[(i * 6) + 3] = (short) (last + 0);
            indices[(i * 6) + 4] = (short) (last + 2);
            indices[(i * 6) + 5] = (short) (last + 3);

            // Our indices are connected to the vertices so we need to keep them
            // in the correct order.
            // normal quad = 0,1,2,0,2,3 so the next one will be 4,5,6,4,6,7
            last = last + 4;
        }

        // The vertex buffer.
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(indices.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(indices);
        drawListBuffer.position(0);
    }
}
