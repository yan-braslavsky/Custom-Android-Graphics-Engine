package glengine.yan.glengine.util.helpers;

import android.opengl.Matrix;

import glengine.yan.glengine.nodes.YANIRenderableNode;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANMatrixHelper {

    // matricies that used for vertex calculations
    public static final float[] projectionMatrix = new float[16];
    public static final float[] modelMatrix = new float[16];
    public static final float[] viewMatrix = new float[16];
    public static final float[] viewProjectionMatrix = new float[16];
    public static final float[] invertedViewProjectionMatrix = new float[16];
    public static final float[] modelViewProjectionMatrix = new float[16];

    public static final void positionObjectInScene(float x, float y) {
        Matrix.setIdentityM(YANMatrixHelper.modelMatrix, 0);
        Matrix.translateM(YANMatrixHelper.modelMatrix, 0, x, y, 0);
        Matrix.multiplyMM(YANMatrixHelper.modelViewProjectionMatrix, 0, YANMatrixHelper.viewProjectionMatrix, 0,
                YANMatrixHelper.modelMatrix, 0);
    }

    public static void positionObjectInScene(YANIRenderableNode iNode) {

        float x = iNode.getPosition().getX() + (iNode.getSize().getX() / 2) - (iNode.getAnchorPoint().getX() * (iNode.getSize().getX()));
        float y = (iNode.getPosition().getY() + (iNode.getSize().getY() / 2) - (iNode.getAnchorPoint().getY() * (iNode.getSize().getY())));

        //on z plane we translate everything to the back in order to avoid clipping of rotated objects around Y axis
        float z = -500;

        Matrix.setIdentityM(YANMatrixHelper.modelMatrix, 0);
        Matrix.translateM(YANMatrixHelper.modelMatrix, 0, x, y, z);

        //Rotation around Y axis
        if (iNode.getRotationY() != 0) {
            Matrix.rotateM(YANMatrixHelper.modelMatrix, 0, iNode.getRotationY(), 0, 1, 0);
        }

        //Rotation around Z axis
        if (iNode.getRotationZ() != 0) {
            Matrix.rotateM(YANMatrixHelper.modelMatrix, 0, iNode.getRotationZ(), 0, 0, 1);
        }

        Matrix.multiplyMM(YANMatrixHelper.modelViewProjectionMatrix, 0, YANMatrixHelper.viewProjectionMatrix, 0,
                YANMatrixHelper.modelMatrix, 0);
    }
}
