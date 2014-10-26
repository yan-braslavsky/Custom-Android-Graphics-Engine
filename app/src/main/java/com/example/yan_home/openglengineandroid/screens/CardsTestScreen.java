package com.example.yan_home.openglengineandroid.screens;

import com.example.yan_home.openglengineandroid.R;
import com.example.yan_home.openglengineandroid.nodes.CardNode;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.screens.YANNodeScreen;
import com.yan.glengine.util.math.YANVector2;

import aurelienribon.tweenengine.TweenManager;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class CardsTestScreen extends YANNodeScreen {

    private TweenManager mTweenManager;
    private CardNode mCard1;
    private CardNode mCard2;
    private CardNode mCard3;
    private CardNode mCard4;

    public CardsTestScreen(YANGLRenderer renderer) {
        super(renderer);
    }

    @Override
    protected void onAddNodesToScene() {
        addNode(mCard1);
        addNode(mCard2);
        addNode(mCard3);
        addNode(mCard4);
    }

    @Override
    protected int getAtlasResourceID() {
        return R.raw.ui_atlas;
    }

    @Override
    protected void onLayoutNodes() {

        float yPosition = getSceneSize().getY() - mCard4.getSize().getY();

        mCard1.getPosition().setX(0);
        mCard1.getPosition().setY(yPosition);

        mCard2.getPosition().setX(mCard1.getPosition().getX() + mCard2.getSize().getX());
        mCard2.getPosition().setY(yPosition);

        mCard3.getPosition().setX(mCard2.getPosition().getX() + mCard3.getSize().getX());
        mCard3.getPosition().setY(yPosition);

        mCard4.getPosition().setX(mCard3.getPosition().getX() + mCard4.getSize().getX());
        mCard4.getPosition().setY(yPosition);

    }

    @Override
    protected void onChangeNodesSize() {
        float w = mCard1.getTextureRegion().getWidth() * 2;
        float h = mCard1.getTextureRegion().getHeight() * 2;

        mCard1.setSize(new YANVector2(w, h));
        mCard2.setSize(new YANVector2(w, h));
        mCard3.setSize(new YANVector2(w, h));
        mCard4.setSize(new YANVector2(w, h));
    }


    @Override
    protected void onCreateNodes() {

        // We need a mTweenManager to handle every tween.
        mTweenManager = new TweenManager();

        mCard1 = new CardNode(getTextureAtlas().getTextureRegion("cards_all-01.png"));
        mCard2 = new CardNode(getTextureAtlas().getTextureRegion("cards_all-02.png"));
        mCard3 = new CardNode(getTextureAtlas().getTextureRegion("cards_all-03.png"));
        mCard4 = new CardNode(getTextureAtlas().getTextureRegion("cards_all-04.png"));

    }


    @Override
    public void onUpdate(float deltaTimeSeconds) {
//        mTweenManager.update(deltaTimeSeconds * 1);
    }

    @Override
    public void onSetActive() {
        super.onSetActive();


    }

    @Override
    public void onSetNotActive() {
        super.onSetNotActive();
    }
}
