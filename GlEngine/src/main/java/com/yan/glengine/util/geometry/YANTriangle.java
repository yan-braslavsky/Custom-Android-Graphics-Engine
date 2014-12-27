package com.yan.glengine.util.geometry;

/**
 * Created by Yan-Home on 12/27/2014.
 */
public class YANTriangle {

    private YANVector2 mTopVertex;
    private YANVector2 mBaseVertexOne;
    private YANVector2 mBaseVertexTwo;

    public YANTriangle(YANVector2 topVertex, YANVector2 baseVertexOne, YANVector2 baseVertexTwo) {
        mTopVertex = topVertex;
        mBaseVertexOne = baseVertexOne;
        mBaseVertexTwo = baseVertexTwo;
    }

    public YANVector2 getTopVertex() {
        return mTopVertex;
    }

    public YANVector2 getBaseVertexOne() {
        return mBaseVertexOne;
    }

    public YANVector2 getBaseVertexTwo() {
        return mBaseVertexTwo;
    }

    public void setTopVertex(YANVector2 topVertex) {
        mTopVertex = topVertex;
    }

    public void setBaseVertexOne(YANVector2 baseVertexOne) {
        mBaseVertexOne = baseVertexOne;
    }

    public void setBaseVertexTwo(YANVector2 baseVertexTwo) {
        mBaseVertexTwo = baseVertexTwo;
    }

    public YANVector2 calculateCenterPointBetweenBaseVertices() {
        float xDistance = Math.abs(mBaseVertexTwo.getX() - mBaseVertexOne.getX());
        float yDistance = Math.abs(mBaseVertexTwo.getY() - mBaseVertexOne.getY());
        float x = xDistance + ((mBaseVertexOne.getX() < mBaseVertexTwo.getX()) ? mBaseVertexOne.getX() : mBaseVertexTwo.getX());
        float y = yDistance + ((mBaseVertexOne.getY() < mBaseVertexTwo.getY()) ? mBaseVertexOne.getY() : mBaseVertexTwo.getY());
        return new YANVector2(x, y);
    }
}