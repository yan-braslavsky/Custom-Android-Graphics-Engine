package com.example.yan_home.openglengineandroid.font_test;

public class TextObject {

    public String text;
    public float x;
    public float y;
    public float[] color;

    public TextObject(String txt, float xcoord, float ycoord) {
        text = txt;
        x = xcoord;
        y = ycoord;
        color = new float[]{1f, 1f, 1f, 1.0f};
    }

}
