package com.example.yan_home.openglengineandroid.screens;

import com.example.yan_home.openglengineandroid.R;
import com.example.yan_home.openglengineandroid.nodes.INode;
import com.example.yan_home.openglengineandroid.objects.TexturedNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class BaseScreen implements IScreen {

    private List<INode> mNodeList;

    public BaseScreen() {
        mNodeList = new ArrayList<INode>();
    }

    @Override
    public void onResize(float newWidth, float newHeight) {
        //TODO : handle resize
    }

    @Override
    public void onUpdate() {
        //TODO : update nodes state

//        for (INode iNode : mNodeList) {
//            iNode.getPosition().setX(iNode.getPosition().getX() + 0.001f);
//        }
    }

    @Override
    public void onSetActive() {

        mNodeList.add(new TexturedNode(R.drawable.air_hockey_surface));
        mNodeList.add(new TexturedNode(R.drawable.air_hockey_surface));

        mNodeList.get(0).getPosition().setX(1);
        mNodeList.get(1).getPosition().setX(-1);
    }

    @Override
    public List<INode> getNodeList() {
        return mNodeList;
    }

    @Override
    public void onSetNotActive() {
        //TODO : stop all ongoing tasks and remove resources
    }
}
