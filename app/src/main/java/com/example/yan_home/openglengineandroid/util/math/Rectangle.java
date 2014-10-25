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

//    public boolean contains(float x, float y) {
//        boolean isXinBounds = x > leftTop.getX() && x < rightBottom.getX();
//        boolean isYinBounds = y > leftTop.getY() && y < rightBottom.getY();
//        return isXinBounds && isYinBounds;
//    }

    public boolean contains(Vector2 point) {
        return contains(point.getX(), point.getY());
    }

    public boolean contains(float x, float y) {
        float ex, ey, fx, fy;

        float ax = leftTop.getX();
        float ay = leftTop.getY();
        float bx = rightBottom.getX();
        float by = leftTop.getY();
        float dx = leftTop.getX();
        float dy = rightBottom.getY();

        ex = bx - ax;
        ey = by - ay;
        fx = dx - ax;
        fy = dy - ay;

        if ((x - ax) * ex + (y - ay) * ey < 0.0) return false;
        if ((x - bx) * ex + (y - by) * ey > 0.0) return false;
        if ((x - ax) * fx + (y - ay) * fy < 0.0) return false;
        if ((x - dx) * fx + (y - dy) * fy > 0.0) return false;

        return true;
    }

}
