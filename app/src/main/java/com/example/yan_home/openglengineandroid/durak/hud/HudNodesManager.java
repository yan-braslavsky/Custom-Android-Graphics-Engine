package com.example.yan_home.openglengineandroid.durak.hud;

import com.yan.glengine.assets.atlas.YANTextureAtlas;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.nodes.YANTexturedScissorNode;
import com.yan.glengine.util.geometry.YANReadOnlyVector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yan-Home on 1/25/2015.
 */
public class HudNodesManager implements IHudNodesManager {

    /**
     * By default hud will be placed on hud sorting layer and above
     */
    private static final int HUD_SORTING_LAYER = 50;

    private YANTexturedScissorNode mScissorCockNode;
    private ArrayList<YANTexturedNode> mAvatarPlaceHoldersArray;
    private ArrayList<YANTexturedNode> mCockPlaceHoldersArray;

    private int mNumberOfPlayers;
    private float mScissoringCockVisibleStartY;

    public HudNodesManager() {

        //array holds avatars for each player
        mAvatarPlaceHoldersArray = new ArrayList<>();
        mCockPlaceHoldersArray = new ArrayList<>();

        //TODO : should be configurable
        mNumberOfPlayers = 3;
    }

    @Override
    public void createHudNodes(YANTextureAtlas hudAtlas) {

        //add avatar and cock for each players
        mNumberOfPlayers = 3;
        for (int i = 0; i < mNumberOfPlayers; i++) {

            //add an avatar for a player
            mAvatarPlaceHoldersArray.add(new YANTexturedNode(hudAtlas.getTextureRegion("stump.png")));
            mAvatarPlaceHoldersArray.get(i).setSortingLayer(HUD_SORTING_LAYER);

            //add a cock for a player
            mCockPlaceHoldersArray.add(new YANTexturedNode(hudAtlas.getTextureRegion("grey_cock.png")));
            mCockPlaceHoldersArray.get(i).setSortingLayer(HUD_SORTING_LAYER);
        }

        //top left cock is looking the other way
        mCockPlaceHoldersArray.get(2).setRotationY(180);

        //scissor cock node that will be used to present the fill in for cocks
        mScissorCockNode = new YANTexturedScissorNode(hudAtlas.getTextureRegion("yellow_cock.png"));
        mScissorCockNode.setSortingLayer(HUD_SORTING_LAYER + 100);
    }

    @Override
    public void setHudNodesSizes(YANReadOnlyVector2 sceneSize) {
        //set avatars sizes
        YANTexturedNode avatar = mAvatarPlaceHoldersArray.get(0);
        float aspectRatio = avatar.getTextureRegion().getWidth() / avatar.getTextureRegion().getHeight();
        float newWidth = sceneSize.getX() * 0.2f;
        float newHeight = newWidth / aspectRatio;
        for (YANTexturedNode node : mAvatarPlaceHoldersArray) {
            node.setSize(newWidth, newHeight);
        }

        //set cock sizes
        YANTexturedNode cock = mCockPlaceHoldersArray.get(0);
        aspectRatio = cock.getTextureRegion().getWidth() / cock.getTextureRegion().getHeight();
        newWidth = sceneSize.getX() * 0.1f;
        newHeight = newWidth / aspectRatio;
        for (YANTexturedNode node : mCockPlaceHoldersArray) {
            node.setSize(newWidth, newHeight);
        }

        mScissorCockNode.setSize(newWidth, newHeight);
    }

    @Override
    public List<YANTexturedNode> getAllHudNodes() {

        //TODO : change all nodes to sparse array or hash map

        ArrayList<YANTexturedNode> list = new ArrayList<>();

        list.add(mScissorCockNode);
        for (YANTexturedNode node : mAvatarPlaceHoldersArray) {
            list.add(node);
        }

        for (YANTexturedNode node : mCockPlaceHoldersArray) {
            list.add(node);
        }

        return list;
    }

    @Override
    public void layoutNodes(YANReadOnlyVector2 sceneSize) {
        //layout avatars
        float offsetX = sceneSize.getX() * 0.01f;

        //setup avatar for player at left top
        YANTexturedNode avatar = mAvatarPlaceHoldersArray.get(0);
        avatar.setAnchorPoint(1f, 1f);
        avatar.setSortingLayer(HUD_SORTING_LAYER + 1);
        avatar.setPosition(sceneSize.getX() - offsetX, sceneSize.getY() - offsetX);
        mCockPlaceHoldersArray.get(0).setSortingLayer(avatar.getSortingLayer());
        mCockPlaceHoldersArray.get(0).setPosition(avatar.getPosition().getX() - avatar.getSize().getX() / 2 - mCockPlaceHoldersArray.get(0).getSize().getX() / 2,
                avatar.getPosition().getY() - avatar.getSize().getY() - mCockPlaceHoldersArray.get(0).getSize().getY());

        //setup avatar for player at right top
        float topOffset = sceneSize.getY() * 0.07f;
        avatar = mAvatarPlaceHoldersArray.get(1);
        avatar.setAnchorPoint(1f, 0f);
        avatar.setSortingLayer(HUD_SORTING_LAYER + 1);
        avatar.setPosition(sceneSize.getX() - offsetX, topOffset);
        mCockPlaceHoldersArray.get(1).setSortingLayer(avatar.getSortingLayer());
        mCockPlaceHoldersArray.get(1).setPosition(avatar.getPosition().getX() - avatar.getSize().getX() / 2 - mCockPlaceHoldersArray.get(1).getSize().getX() / 2,
                avatar.getPosition().getY() - mCockPlaceHoldersArray.get(1).getSize().getY());


        //third player avatar
        avatar = mAvatarPlaceHoldersArray.get(2);
        avatar.setAnchorPoint(0f, 0f);
        avatar.setSortingLayer(HUD_SORTING_LAYER + 1);
        avatar.setPosition(offsetX, topOffset);
        mCockPlaceHoldersArray.get(2).setSortingLayer(avatar.getSortingLayer());
        mCockPlaceHoldersArray.get(2).setPosition(avatar.getPosition().getX() + mCockPlaceHoldersArray.get(2).getSize().getX() / 2, avatar.getPosition().getY() - mCockPlaceHoldersArray.get(2).getSize().getY());

        //filled in cock by default is out of the screen
        mScissorCockNode.setPosition(-sceneSize.getX(), 0);
    }

    @Override
    public void update(float deltaTimeSeconds) {
        //animate scissoring cock
        mScissorCockNode.setVisibleArea(0, mScissoringCockVisibleStartY, 1, 1);
        mScissoringCockVisibleStartY += 0.001;
        if (mScissoringCockVisibleStartY > 1.0)
            mScissoringCockVisibleStartY = 0.0f;
    }

    @Override
    public void resetCockAnimation(CockPosition position) {

        float rotationAngle = 0;
        YANReadOnlyVector2 newCockPosition = null;

        switch (position) {
            case BOTTOM_RIGHT:
                newCockPosition = mCockPlaceHoldersArray.get(0).getPosition();
                break;
            case TOP_RIGHT:
                newCockPosition = mCockPlaceHoldersArray.get(1).getPosition();
                break;
            case TOP_LEFT:
                newCockPosition = mCockPlaceHoldersArray.get(2).getPosition();
                rotationAngle = 180;
                break;
        }

        mScissorCockNode.setPosition(newCockPosition.getX(), newCockPosition.getY());
        mScissorCockNode.setRotationY(rotationAngle);

        //start animating the cock down
        mScissoringCockVisibleStartY = 1;
    }

    @Override
    public void disableCockAnimation() {

    }

}
