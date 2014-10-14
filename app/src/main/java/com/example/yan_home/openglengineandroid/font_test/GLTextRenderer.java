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
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLTextRenderer implements Renderer {

    // Our matrices
    private final float[] mtrxProjection = new float[16];
    private final float[] mtrxView = new float[16];
    private final float[] mtrxProjectionAndView = new float[16];

    // Geometric variables

    public static float uvs[];
    public FloatBuffer uvBuffer;
    public TextManager textManager;

    // Misc
    Context mContext;

    public GLTextRenderer(Context c) {
        mContext = c;
    }

    public void onPause() {
    }

    public void onResume() {
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        // Render the text
        textManager.Draw(mtrxProjectionAndView);
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
        // Create the image information
        SetupImage();
        // Create our texts
        SetupText();

        // Set the clear color
        GLES20.glClearColor(0, 1f, 0, 1f);

        //enable alpha blending
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        // Create the shaders, images
        int vertexShader = ShaderHelperTools.loadShader(GLES20.GL_VERTEX_SHADER, ShaderHelperTools.vs_Image);
        int fragmentShader = ShaderHelperTools.loadShader(GLES20.GL_FRAGMENT_SHADER, ShaderHelperTools.fs_Image);

        // create empty OpenGL ES Program
        // add the vertex shader to program
        // add the fragment shader to program
        // creates OpenGL ES program executables
        ShaderHelperTools.sp_Image = GLES20.glCreateProgram();
        GLES20.glAttachShader(ShaderHelperTools.sp_Image, vertexShader);
        GLES20.glAttachShader(ShaderHelperTools.sp_Image, fragmentShader);
        GLES20.glLinkProgram(ShaderHelperTools.sp_Image);

        // Text shader
        int vshadert = ShaderHelperTools.loadShader(GLES20.GL_VERTEX_SHADER, ShaderHelperTools.vs_Text);
        int fshadert = ShaderHelperTools.loadShader(GLES20.GL_FRAGMENT_SHADER, ShaderHelperTools.fs_Text);

        ShaderHelperTools.sp_Text = GLES20.glCreateProgram();
        GLES20.glAttachShader(ShaderHelperTools.sp_Text, vshadert);
        GLES20.glAttachShader(ShaderHelperTools.sp_Text, fshadert);        // add the fragment shader to program
        GLES20.glLinkProgram(ShaderHelperTools.sp_Text);                  // creates OpenGL ES program executables

        // Set our shader programm
        GLES20.glUseProgram(ShaderHelperTools.sp_Image);
    }

    public void SetupText() {
        // Create our text manager
        textManager = new TextManager();

        // Tell our text manager to use index 1 of textures loaded
        textManager.setTextureID(1);

        // Create our new textobject
        TextObject txt = new TextObject("My Random Message", 10f, 10f);

        // Add it to our manager
        textManager.addText(txt);

        // Prepare the text for rendering
        textManager.PrepareDraw();
    }


    public void SetupImage() {
        // We will use a randomizer for randomizing the textures from texture atlas.
        // This is strictly optional as it only effects the output of our app,
        // Not the actual knowledge.
        Random rnd = new Random();

        // 30 imageo bjects times 4 vertices times (u and v)
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
}
