package com.example.yan_home.openglengineandroid.util.math;

/**
 * Created by Yan-Home on 10/4/2014.
 */
public class Rectangle {
    private final Vector2 leftTop;
    private final Vector2 rightBottom;

    public Rectangle(Vector2 leftTop, Vector2 rightBottom) {
        this.leftTop = leftTop;
        this.rightBottom = rightBottom;
    }

    public Vector2 getLeftTop() {
        return leftTop;
    }

    public Vector2 getRightBottom() {
        return rightBottom;
    }

    public boolean contains(float x, float y) {
        boolean isXinBounds = x > leftTop.getX() && x < rightBottom.getX();
        boolean isYinBounds = y < leftTop.getY() && y > rightBottom.getY();
        return isXinBounds && isYinBounds;
    }

    public boolean contains(Vector2 point) {
        return contains(point.getX(), point.getY());
    }

}
