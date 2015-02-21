package com.yan.glengine.util.geometry;

/**
 * Created by Yan-Home on 10/4/2014.
 */
public class YANRectangle {
    private final YANVector2 leftTop;
    private final YANVector2 rightBottom;

    public YANRectangle(YANVector2 leftTop, YANVector2 rightBottom) {
        this.leftTop = leftTop;
        this.rightBottom = rightBottom;
    }

    public YANVector2 getLeftTop() {
        return leftTop;
    }

    public YANVector2 getRightBottom() {
        return rightBottom;
    }

//    public boolean contains(float x, float y) {
//        boolean isXinBounds = x > leftTop.getX() && x < rightBottom.getX();
//        boolean isYinBounds = y > leftTop.getY() && y < rightBottom.getY();
//        return isXinBounds && isYinBounds;
//    }

    public boolean contains(YANVector2 point) {
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

    public boolean contains(YANRectangle boundingRectangle) {

        //this rectangle params
        float thisX = leftTop.getX();
        float thisY = leftTop.getY();
        float thisWidth = rightBottom.getX() - leftTop.getX();
        float thisHeight = rightBottom.getY() - leftTop.getY();

        //other rectangle params
        float otherX = boundingRectangle.getLeftTop().getX();
        float otherY = boundingRectangle.getLeftTop().getY();
        float otherWidth = boundingRectangle.getRightBottom().getX() - boundingRectangle.getLeftTop().getX();
        float otherHeight = boundingRectangle.getRightBottom().getY() - boundingRectangle.getLeftTop().getY();

        if (thisX < otherX + otherWidth &&
                thisX + thisWidth > otherX &&
                thisY < otherY + otherHeight &&
                thisHeight + thisY > otherY) {
            return true;
        }

        return false;
    }
}
