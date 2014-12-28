package com.example.yan_home.openglengineandroid.layouting.threepoint;


import com.example.yan_home.openglengineandroid.layouting.impl.CardsLayouterSlotImpl;
import com.yan.glengine.util.geometry.YANVector2;
import com.yan.glengine.util.math.YANMathUtils;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.List;

/**
 * Created by Yan-Home on 12/28/2014.
 */
public class ThreePointFanLayouter implements ThreePointLayouter {
    private YANVector2 mOriginPoint;
    private YANVector2 mLeftBasis;
    private YANVector2 mRightBasis;

    private YANVector2 mNormalizedOriginPoint;
    private YANVector2 mNormalizedLeftBasis;
    private YANVector2 mNormalizedRightBasis;
    private float mNormalizedFanAngle;

    private RealMatrix mAffineMappingMatrix;


    @Override
    public void setThreePoints(YANVector2 originPoint, YANVector2 leftBasis, YANVector2 rightBasis) {
        mOriginPoint = originPoint;
        mLeftBasis = leftBasis;
        mRightBasis = rightBasis;

        //create normalized points that we are layouting to
        initNormalizedPoints();

        //create the affine matrix that will be used to map points
        //from normalized triangle to given triangle
        mAffineMappingMatrix = findAffineMatrixUsingSourceAndDestinationPoints();

    }

    //taken from :
    //http://stackoverflow.com/questions/21270892/generate-affinetransform-from-3-points
    private RealMatrix findAffineMatrixUsingSourceAndDestinationPoints() {

        //AffineMatrix * sourceTriangleMatrix = DestinationTriangleMatrix
        //AffinMatrix = DestinationTriangleMatrix * Inversion(sourceTriangleMatrix)

        //create matrix of the normalized points (source triangle)
        double[][] normalizedTriangleMatrixData = {

                //first row contains data for normalized triangle : nTopX,nLeftX,nRightX
                {mNormalizedOriginPoint.getX(), mNormalizedLeftBasis.getX(), mNormalizedRightBasis.getX()},

                //first row contains data for normalized triangle : nTopY,nLeftY,nRightY
                {mNormalizedOriginPoint.getY(), mNormalizedLeftBasis.getY(), mNormalizedRightBasis.getY()},

                //third row is an extention to 3D coordinates
                {1, 1, 1},
        };

        //create normalized triangle matrix
        RealMatrix normalizedTriangleMatrix = MatrixUtils.createRealMatrix(normalizedTriangleMatrixData);


        //create matrix data of the destination triangle
        double[][] destinationTriangleMatrixData = {

                //first row contains data for normalized triangle : nTopX,nLeftX,nRightX
                {mOriginPoint.getX(), mLeftBasis.getX(), mRightBasis.getX()},

                //first row contains data for normalized triangle : nTopY,nLeftY,nRightY
                {mOriginPoint.getY(), mLeftBasis.getY(), mRightBasis.getY()},
        };

        //create normalized triangle matrix
        RealMatrix destinationTriangleMatrix = MatrixUtils.createRealMatrix(destinationTriangleMatrixData);

        // Invert source matrix , using LU decomposition
        RealMatrix sourceTriangleMatrixInverse = new LUDecomposition(normalizedTriangleMatrix).getSolver().getInverse();

        // Now multiply destinationTriangleMatrix by sourceTriangleMatrixInverse
        RealMatrix affineMatrix = destinationTriangleMatrix.multiply(sourceTriangleMatrixInverse);

        return affineMatrix;
    }

    private void initNormalizedPoints() {
        mNormalizedOriginPoint = new YANVector2(0, 0);
        mNormalizedLeftBasis = new YANVector2(1, 1);
        mNormalizedRightBasis = new YANVector2(-1, 1);

        //calculate the angle between 2 basis meridians
        float opposite = Math.abs(mNormalizedLeftBasis.getX() - mNormalizedRightBasis.getX()) / 2;
        float adjacent = Math.abs(mNormalizedOriginPoint.getY() - mNormalizedLeftBasis.getY());
        mNormalizedFanAngle = (float) Math.toDegrees(Math.atan(opposite / adjacent) * 2);
    }

    @Override
    public void layoutRowOfSlots(List<CardsLayouterSlotImpl> slots) {

        //we are are rotating left basis counter clockwise half the fan angle
        //to reach the centered highest point
        YANVector2 startingPosition = new YANVector2(mNormalizedLeftBasis);

        float angleStep = mNormalizedFanAngle / (slots.size() - 1);

        //rotate slots
        for (int i = 0; i < slots.size(); i++) {
            CardsLayouterSlotImpl slot = slots.get(i);

            //set slot to initial position and rotation
            slot.setPosition(startingPosition.getX(), startingPosition.getY());
            slot.setRotation(angleStep * i - (mNormalizedFanAngle / 2));

            YANMathUtils.rotatePointAroundOrigin(slot.getPosition(), mNormalizedOriginPoint, angleStep * i);

            //Map back ...
            //create vector for slot point
            double[][] pointData = {
                    {slot.getPosition().getX()},
                    {slot.getPosition().getY()},
                    {1}
            };
            RealMatrix slotNormalizedMatrix = MatrixUtils.createRealMatrix(pointData);
            RealMatrix slotMappedBackMatrix = mAffineMappingMatrix.multiply(slotNormalizedMatrix);

            double[] column = slotMappedBackMatrix.getColumn(0);
            slot.setPosition((float) column[0], (float) column[1]);
        }
    }


}
