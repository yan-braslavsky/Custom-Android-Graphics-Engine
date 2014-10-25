package com.example.yan_home.openglengineandroid.tween;

import com.example.yan_home.openglengineandroid.nodes.YANIRenderableNode;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by Yan-Home on 10/25/2014.
 */
public class TweenNodeAccessor implements TweenAccessor<YANIRenderableNode> {

    public static final int POSITION_X = 1;
    public static final int POSITION_Y = 2;
    public static final int POSITION_XY = 3;
    public static final int ROTATION_CW = 4;
    public static final int ROTATION_CCW = 5;

    @Override
    public int getValues(YANIRenderableNode target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case POSITION_X:
                returnValues[0] = target.getPosition().getX();
                return 1;
            case POSITION_Y:
                returnValues[0] = target.getPosition().getY();
                return 1;
            case POSITION_XY:
                returnValues[0] = target.getPosition().getX();
                returnValues[1] = target.getPosition().getY();
                return 2;

            case ROTATION_CW:
                returnValues[0] = target.getRotation();
                return 1;

            case ROTATION_CCW:
                returnValues[0] = target.getRotation();
                return 1;
            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(YANIRenderableNode target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case POSITION_X:
                target.getPosition().setX(newValues[0]);
                break;
            case POSITION_Y:
                target.getPosition().setY(newValues[0]);
                break;
            case POSITION_XY:
                target.getPosition().setX(newValues[0]);
                target.getPosition().setY(newValues[1]);
                break;
            case ROTATION_CW:
                target.setRotation(newValues[0]);
                break;
            case ROTATION_CCW:
                target.setRotation(-newValues[0]);
                break;
            default:
                assert false;
                break;
        }
    }
}
