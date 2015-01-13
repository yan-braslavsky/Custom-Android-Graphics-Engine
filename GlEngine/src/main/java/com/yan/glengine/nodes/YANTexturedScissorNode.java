/**
 * Created by Yan-Home on 10/3/2014.
 * This node is rendered by using a texture
 */
package com.yan.glengine.nodes;

import android.opengl.GLES20;

import com.yan.glengine.assets.atlas.YANAtlasTextureRegion;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.util.geometry.YANRectangle;
import com.yan.glengine.util.geometry.YANVector2;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDisable;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glScissor;

/**
 * This is an extension of a regular {@link YANTexturedNode} with
 * an ability to be scissored .
 */
public class YANTexturedScissorNode extends YANTexturedNode {

    /**
     * The rectangle that will be specified in normalized coordinates
     * relative to node size with a range of 0.0 to 1.0
     */
    YANRectangle mScissoringRectangle;

    public YANTexturedScissorNode(YANAtlasTextureRegion textureRegion) {
        super(textureRegion);

        //init the visible rectangle to occupy the entire area  (not scissoring anything)
        mScissoringRectangle = new YANRectangle(new YANVector2(0, 0), new YANVector2(1f, 1f));
    }

    @Override
    public void render(YANGLRenderer renderer) {

        //TODO : consider matrices for the following calculations !

        //optimization , enable scissoring only if scissoring area is defined
        boolean isGoingToScissor = !(mScissoringRectangle.getLeftTop().getX() == 0
                && mScissoringRectangle.getLeftTop().getY() == 0
                && mScissoringRectangle.getRightBottom().getX() == 1f
                && mScissoringRectangle.getRightBottom().getY() == 1f);

        if (isGoingToScissor) {
            //we are going to use a scissoring information
            glEnable(GLES20.GL_SCISSOR_TEST);

            //TODO : cache scissoring values ?

            //the scissoring rectangle coordinates specified in a device window coordinates where [0,0]
            //is a bottom left corner of a viewport (and the scene in current implementation)

            //top left position of the node in View Port coordinate system
            float topLeftX_VPCoord = getPosition().getX();
            float topLeftY_VPcoord = renderer.getSurfaceSize().getY() - getPosition().getY();

            //TODO : offset top left according to anchor point
            // ...

            //the amount of x offset from left to scissoring rectangle right
            float deltaX = (mScissoringRectangle.getRightBottom().getX() - mScissoringRectangle.getLeftTop().getX()) * getSize().getX();

            //the amount of y offset from top down to scissoring rectangle bottom
            float deltaY = (mScissoringRectangle.getRightBottom().getY() - mScissoringRectangle.getLeftTop().getY()) * getSize().getY();


            //calculate xy start coordinates for scissoring in ViewPort coordinates
            int xStartTopLeft_VPCoord = (int) (topLeftX_VPCoord + (mScissoringRectangle.getLeftTop().getX() * getSize().getX()));
            int yStartTopLeft_VPCoord = (int) (topLeftY_VPcoord - (mScissoringRectangle.getLeftTop().getY() * getSize().getY()));

            //calculate xy end coordinates for scissoring in ViewPort coordinates
            int xStartBottomRight_VPCoord = (int) (xStartTopLeft_VPCoord + deltaX);
            int yStartBottomRight_VPCoord = (int) (yStartTopLeft_VPCoord - deltaY);

            //summarise to scissoring rectangle in viewPort coordinates
            int x = xStartTopLeft_VPCoord;
            int y = yStartBottomRight_VPCoord;
            int width = (int) deltaX;
            int height = (int) deltaY;

            glScissor(x, y, width, height);
        }

        //render the actual scissored node
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);

        if (isGoingToScissor) {
            glDisable(GLES20.GL_SCISSOR_TEST);
        }
    }

    /**
     * Defines a rectangular area of the node that will be visible , all the rest will be scissored.
     * Parameters represents normalized coordinates of the node with a range of 0.0 to 1.0 .
     *
     * @param xStart the start of the  rectangle relative to anchor point in x axis.
     * @param yStart the start of the  rectangle relative to anchor point in y axis.
     * @param xEnd   the end of the  rectangle relative to anchor point in x axis.
     * @param yEnd   the end of the  rectangle relative to anchor point in y axis.
     */
    public void setVisibleArea(float xStart, float yStart, float xEnd, float yEnd) {

        //validate values
        if (xStart < 0 || yStart < 0 || xEnd < 0 || yEnd < 0 || xStart > 1 || yStart > 1 || xEnd > 1 || yEnd > 1) {
            throw new RuntimeException("Visible rectangle values should be between 0.0 and 1.0");
        }

        //validate values relationships
        if (xStart > xEnd || yStart > yEnd) {
            throw new RuntimeException("Visible rectangle should be defined from top to bottom");
        }

        mScissoringRectangle.getLeftTop().setXY(xStart, yStart);
        mScissoringRectangle.getRightBottom().setXY(xEnd, yEnd);
    }

}