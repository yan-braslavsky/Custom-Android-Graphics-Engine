package com.example.yan_home.openglengineandroid.screens.impl;

import com.example.yan_home.openglengineandroid.R;
import com.example.yan_home.openglengineandroid.assets.YANTexture;
import com.example.yan_home.openglengineandroid.nodes.YANTexturedNode;
import com.example.yan_home.openglengineandroid.screens.YANBaseScreen;
import com.example.yan_home.openglengineandroid.util.math.MathUtils;
import com.example.yan_home.openglengineandroid.util.math.Vector2;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANTestScreen extends YANBaseScreen {


    private static int[] mImageResources = {
            R.drawable.basketball,
            R.drawable.billiardball,
            R.drawable.bowlingball,
            R.drawable.football,
            R.drawable.golfball,
            R.drawable.tennisball,
            R.drawable.volleyball
    };

    @Override
    public void onResize(float newWidth, float newHeight) {
        //TODO : handle resize
    }

    @Override
    public void onUpdate() {
        //TODO : update nodes state


        getNodeList().get(0).setSize(new Vector2(getNodeList().get(0).getSize().getX() - 0.001f, getNodeList().get(0).getSize().getY() - 0.001f));

//        for (INode iNode : mNodeList) {
//            iNode.getPosition().setX(iNode.getPosition().getX() + 0.001f);
//        }
    }

    @Override
    public void onSetActive() {

        float range = 1.5f;

        for (int i = 0; i < mImageResources.length; i++) {
            YANTexturedNode sprite = new YANTexturedNode(new YANTexture(mImageResources[i]));
            sprite.setSize(new Vector2(0.5f,0.5f));

            sprite.getPosition().setX(MathUtils.randomInRange(-range, range));
            sprite.getPosition().setY(MathUtils.randomInRange(-range, range));
            getNodeList().add(sprite);
        }


//        mNodeList.add(new TexturedNode(R.drawable.air_hockey_surface));

//        mNodeList.get(0).getPosition().setX(1);
//        mNodeList.get(1).getPosition().setX(-1);
    }

    @Override
    public void onSetNotActive() {
        //TODO : stop all ongoing tasks and remove resources
    }
}
