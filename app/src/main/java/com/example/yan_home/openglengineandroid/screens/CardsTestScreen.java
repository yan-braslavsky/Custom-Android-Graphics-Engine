package com.example.yan_home.openglengineandroid.screens;

import com.example.yan_home.openglengineandroid.R;
import com.example.yan_home.openglengineandroid.nodes.CardNode;
import com.yan.glengine.nodes.YANButtonNode;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.screens.YANNodeScreen;
import com.yan.glengine.util.colors.YANColor;
import com.yan.glengine.util.math.YANMathUtils;
import com.yan.glengine.util.math.YANVector2;

import java.util.ArrayList;

import aurelienribon.tweenengine.TweenManager;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class CardsTestScreen extends YANNodeScreen {

    private static final int BG_HEXA_COLOR = 0x9F9E36;
    private static final int CARDS_COUNT = 6;
    private TweenManager mTweenManager;
    private ArrayList<CardNode> mCardNodesArray;
    private CardNode.CardNodeListener mCardNodeListener;
    private YANButtonNode mRepositionCardsNode;

    public CardsTestScreen(YANGLRenderer renderer) {
        super(renderer);

        mCardNodesArray = new ArrayList<>();
        // We need a mTweenManager to handle every tween.
        mTweenManager = new TweenManager();
    }

    @Override
    protected void onAddNodesToScene() {
        for (CardNode cardNode : mCardNodesArray) {
            addNode(cardNode);
        }

        addNode(mRepositionCardsNode);
    }

    @Override
    protected int getAtlasResourceID() {
        return R.raw.ui_atlas;
    }

    @Override
    protected void onLayoutNodes() {

        float rotationAngle = -15;
        YANVector2 rotationOrigin = new YANVector2(getSceneSize().getX() / 2, getSceneSize().getY() * 1.5f);

        for (CardNode cardNode : mCardNodesArray) {
            cardNode.getPosition().setX(getSceneSize().getX() / 2);
            cardNode.getPosition().setY(getSceneSize().getY() - (cardNode.getSize().getY() * 1.5f));
            YANMathUtils.rotatePointAroundOrigin(cardNode.getPosition(), rotationOrigin, rotationAngle);
            cardNode.setRotation(rotationAngle);
            rotationAngle += 5;
        }
    }

    @Override
    protected void onChangeNodesSize() {

        float aspectRatio = mCardNodesArray.get(0).getTextureRegion().getWidth() / mCardNodesArray.get(0).getTextureRegion().getHeight();
        float w = Math.min(getSceneSize().getX(),getSceneSize().getY()) / (float) ((CARDS_COUNT + 2) / 2);
        float h = w / aspectRatio;

        for (CardNode cardNode : mCardNodesArray) {
            cardNode.setSize(new YANVector2(w, h));
        }

        mRepositionCardsNode.setSize(new YANVector2(150, 150));
    }


    @Override
    protected void onCreateNodes() {

        mRepositionCardsNode = new YANButtonNode(getTextureAtlas().getTextureRegion("call_btn_default.png"), getTextureAtlas().getTextureRegion("call_btn_pressed.png"));
        mRepositionCardsNode.setClickListener(new YANButtonNode.YanButtonNodeClickListener() {
            @Override
            public void onButtonClick() {

                for (CardNode cardNode : mCardNodesArray) {
                    pushNodeToFront(cardNode);
                }
                onLayoutNodes();
            }
        });

        mCardNodeListener = new CardNode.CardNodeListener() {
            @Override
            public void onCardPicked(CardNode cardNode) {
                pushNodeToFront(cardNode);
            }

            @Override
            public void onCardHovered(CardNode cardNode) {

            }
        };


        for (int i = 0; i < CARDS_COUNT; i++) {

            String name = "cards_all-0" + (int) (YANMathUtils.randomInRange(1, 4)) + ".png";
            CardNode card = new CardNode(getTextureAtlas().getTextureRegion(name));
            card.setCardNodeListener(mCardNodeListener);

            mCardNodesArray.add(card);
        }
    }


    @Override
    public void onUpdate(float deltaTimeSeconds) {
//        mTweenManager.update(deltaTimeSeconds * 1);
    }

    @Override
    public void onSetActive() {
        super.onSetActive();
        getRenderer().setRendererBackgroundColor(YANColor.createFromHexColor(BG_HEXA_COLOR));

    }

    @Override
    public void onSetNotActive() {
        super.onSetNotActive();
    }
}
