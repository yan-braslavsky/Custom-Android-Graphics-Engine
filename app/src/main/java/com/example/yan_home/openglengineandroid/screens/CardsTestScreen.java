package com.example.yan_home.openglengineandroid.screens;

import com.example.yan_home.openglengineandroid.R;
import com.example.yan_home.openglengineandroid.nodes.CardNode;
import com.yan.glengine.nodes.YANButtonNode;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.screens.YANNodeScreen;
import com.yan.glengine.tween.YANTweenNodeAccessor;
import com.yan.glengine.util.colors.YANColor;
import com.yan.glengine.util.math.YANMathUtils;
import com.yan.glengine.util.math.YANVector2;

import java.util.ArrayList;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class CardsTestScreen extends YANNodeScreen {

    private static final int BG_HEXA_COLOR = 0x9F9E36;
    private static final int CARDS_COUNT = 10;
    private static final int MAX_CARDS_IN_LINE = 8;
    private TweenManager mTweenManager;
    private ArrayList<CardNode> mCardNodesArray;
    private CardNode.CardNodeListener mCardNodeListener;
    private YANButtonNode mRepositionCardsNode;
    private YANTexturedNode mFence;
    private YANTexturedNode mGlade;
    private float mCardWidth;
    private float mCardheight;

    public CardsTestScreen(YANGLRenderer renderer) {
        super(renderer);

        mCardNodesArray = new ArrayList<>();
        // We need a mTweenManager to handle every tween.
        mTweenManager = new TweenManager();
    }

    @Override
    protected void onAddNodesToScene() {

        addNode(mGlade);
        for (CardNode cardNode : mCardNodesArray) {
            addNode(cardNode);
        }

        addNode(mRepositionCardsNode);
        addNode(mFence);
    }

    @Override
    protected int getAtlasResourceID() {
        return R.raw.ui_atlas;
    }

    @Override
    protected void onLayoutNodes() {

        //fence
        float centerX = (getSceneSize().getX() - mFence.getSize().getX()) / 2;
        float centerY = (getSceneSize().getY() - mFence.getSize().getY());
        mFence.getPosition().setX(centerX);
        mFence.getPosition().setY(centerY);

        //glade
        centerX = (getSceneSize().getX() - mGlade.getSize().getX()) / 2;
        centerY = (getSceneSize().getY() - mGlade.getSize().getY()) / 2;
        mGlade.getPosition().setX(centerX);
        mGlade.getPosition().setY(centerY);

        positionCardsInFan();
//        positionCardsInLine();
    }

    private void positionCardsInLine() {

        float xStartPosition = 0;
        float yStartPosition = getSceneSize().getY() - mCardNodesArray.get(0).getSize().getY() * 1.3f;
        float distanceBetweenCards = mCardNodesArray.get(0).getSize().getX() / 2;

        for (int i = 0; i < mCardNodesArray.size(); i++) {
            CardNode card = mCardNodesArray.get(i);
            card.getPosition().setX(xStartPosition + (i * distanceBetweenCards));
            card.getPosition().setY(yStartPosition);
        }

    }

    private void positionCardsInFan() {
        float xCenterPosition = (getSceneSize().getX() - mCardNodesArray.get(0).getSize().getX()) / 2;
        float cardHeigth = mCardNodesArray.get(0).getSize().getY();
        float yStartPosition = getSceneSize().getY() - cardHeigth * 1.3f;

        float yRotationPoint = getSceneSize().getY() * 6;
        double tanFi = (yRotationPoint - yStartPosition) / xCenterPosition;
        double fi = Math.atan(tanFi);

        float fanAngle = (float) (90 - Math.toDegrees(fi));
        float angleStep = fanAngle / CARDS_COUNT;
        float currentRotation = -fanAngle + angleStep;

        YANVector2 rotationOrigin = new YANVector2(xCenterPosition, yRotationPoint);

        for (CardNode cardNode : mCardNodesArray) {

            //card will be rotated around origin starting from the center bottom
            cardNode.getPosition().setX(xCenterPosition);
            cardNode.getPosition().setY(yStartPosition);

            //rotate the card
            YANMathUtils.rotatePointAroundOrigin(cardNode.getPosition(), rotationOrigin, currentRotation);
            cardNode.setRotation(currentRotation);

            currentRotation += (angleStep * 2) /*+ angleStep * 0.2f*/;
        }
    }

    @Override
    protected void onChangeNodesSize() {
        float aspectRatio = 0;

        //fence
        aspectRatio = mFence.getTextureRegion().getWidth() / mFence.getTextureRegion().getHeight();
        mFence.setSize(new YANVector2(getSceneSize().getX(), getSceneSize().getX() / aspectRatio));

        //glade
        aspectRatio = mGlade.getTextureRegion().getWidth() / mGlade.getTextureRegion().getHeight();
        float gladeWidth = Math.min(getSceneSize().getX(), getSceneSize().getY()) * 0.9f;
        mGlade.setSize(new YANVector2(gladeWidth, gladeWidth / aspectRatio));

        //cards
        aspectRatio = mCardNodesArray.get(0).getTextureRegion().getWidth() / mCardNodesArray.get(0).getTextureRegion().getHeight();
        mCardWidth = Math.min(getSceneSize().getX(), getSceneSize().getY()) / (float) ((MAX_CARDS_IN_LINE) / 2);
        mCardheight = mCardWidth / aspectRatio;

        for (CardNode cardNode : mCardNodesArray) {
            cardNode.setSize(new YANVector2(mCardWidth, mCardheight));
        }

        mRepositionCardsNode.setSize(new YANVector2(150, 150));
    }


    @Override
    protected void onCreateNodes() {

        mFence = new YANTexturedNode(getTextureAtlas().getTextureRegion("fence.png"));
        mGlade = new YANTexturedNode(getTextureAtlas().getTextureRegion("glade.png"));


        mRepositionCardsNode = new YANButtonNode(getTextureAtlas().getTextureRegion("call_btn_default.png"), getTextureAtlas().getTextureRegion("call_btn_pressed.png"));
        mRepositionCardsNode.setClickListener(new YANButtonNode.YanButtonNodeClickListener() {
            @Override
            public void onButtonClick() {

                for (CardNode cardNode : mCardNodesArray) {
//                    pushNodeToFront(cardNode);
                }
                onLayoutNodes();
            }
        });

        mCardNodeListener = new CardNode.CardNodeListener() {
            @Override
            public void onCardPicked(CardNode cardNode) {

//                pushNodeToFront(cardNode);
                Timeline.createSequence()
                        .push(Tween.to(cardNode, YANTweenNodeAccessor.ROTATION_CW, 0.5f).target(0))
                        .start(mTweenManager);

                makeCardBiggerWithAnimation(cardNode);

            }

            @Override
            public void onCardReleased(CardNode cardNode) {
                makeCardSmallerWithAnimation(cardNode);
            }

            @Override
            public void onCardHovered(CardNode cardNode) {
                makeCardBiggerWithAnimation(cardNode);
            }

            @Override
            public void onCardHoverEnd(CardNode cardNode) {
                makeCardSmallerWithAnimation(cardNode);
            }
        };


        for (int i = 0; i < CARDS_COUNT; i++) {

            String name = "card_" + (int) (YANMathUtils.randomInRange(1, 4)) + ".png";
            CardNode card = new CardNode(getTextureAtlas().getTextureRegion(name));
            card.setCardNodeListener(mCardNodeListener);

            mCardNodesArray.add(card);
        }
    }

    private void makeCardSmallerWithAnimation(CardNode cardNode) {
        Timeline.createSequence().beginParallel()
                .push(Tween.to(cardNode, YANTweenNodeAccessor.SIZE_X, 0.1f).target(mCardWidth))
                .push(Tween.to(cardNode, YANTweenNodeAccessor.SIZE_Y, 0.1f).target(mCardheight))
                .start(mTweenManager);
    }

    private void makeCardBiggerWithAnimation(CardNode cardNode) {
        Timeline.createSequence().beginParallel()
                .push(Tween.to(cardNode, YANTweenNodeAccessor.SIZE_X, 0.15f).target(mCardWidth * 1.2f))
                .push(Tween.to(cardNode, YANTweenNodeAccessor.SIZE_Y, 0.15f).target(mCardheight * 1.2f))
                .start(mTweenManager);
    }


    @Override
    public void onUpdate(float deltaTimeSeconds) {
        mTweenManager.update(deltaTimeSeconds * 1);
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
