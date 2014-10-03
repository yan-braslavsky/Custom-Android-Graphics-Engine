package com.example.yan_home.openglengineandroid.screens.impl;

import com.example.yan_home.openglengineandroid.R;
import com.example.yan_home.openglengineandroid.assets.YANTexture;
import com.example.yan_home.openglengineandroid.nodes.YANIRenderableNode;
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

    public YANTestScreen() {
        super();
    }

    @Override
    public void onResize(float newWidth, float newHeight) {
        super.onResize(newWidth, newHeight);
        //TODO : handle resize


        float spriteSize = 0.5f;//(getSceneSize().getX() * 0.2f);

        float range = 2;//(getSceneSize().getX() - spriteSize)/6;


        for (YANIRenderableNode sprite : getNodeList()) {
            sprite.setSize(new Vector2(spriteSize, spriteSize));
            sprite.getPosition().setX(MathUtils.randomInRange(-range, range));
            sprite.getPosition().setY(MathUtils.randomInRange(-range, range));
        }
    }

    @Override
    public void onUpdate() {
        //TODO : update nodes state

    }

    @Override
    public void onSetActive() {

        for (int i = 0; i < mImageResources.length; i++) {
            getNodeList().add(new YANTexturedNode(new YANTexture(mImageResources[i])));
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
