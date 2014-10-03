package com.example.yan_home.openglengineandroid.screens;

import com.example.yan_home.openglengineandroid.R;
import com.example.yan_home.openglengineandroid.nodes.YANIRenderableNode;
import com.example.yan_home.openglengineandroid.nodes.YANTexturedNode;
import com.example.yan_home.openglengineandroid.util.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class YANBaseScreen implements YANIScreen {

    private List<YANIRenderableNode> mNodeList;

    public YANBaseScreen() {
        mNodeList = new ArrayList<YANIRenderableNode>();
    }

    @Override
    public void onResize(float newWidth, float newHeight) {
        //TODO : handle resize
    }

    @Override
    public void onUpdate() {
        //TODO : update nodes state


        mNodeList.get(0).setSize(new Vector2(mNodeList.get(0).getSize().getX() - 0.001f,mNodeList.get(0).getSize().getY() - 0.001f));

//        for (INode iNode : mNodeList) {
//            iNode.getPosition().setX(iNode.getPosition().getX() + 0.001f);
//        }
    }

    @Override
    public void onSetActive() {

        mNodeList.add(new YANTexturedNode(R.drawable.basketball));
//        mNodeList.add(new TexturedNode(R.drawable.air_hockey_surface));

//        mNodeList.get(0).getPosition().setX(1);
//        mNodeList.get(1).getPosition().setX(-1);
    }

    @Override
    public List<YANIRenderableNode> getNodeList() {
        return mNodeList;
    }

    @Override
    public void onSetNotActive() {
        //TODO : stop all ongoing tasks and remove resources
    }
}
