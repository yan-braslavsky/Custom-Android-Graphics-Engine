package com.example.yan_home.openglengineandroid.screens;

import com.example.yan_home.openglengineandroid.entities.cards.Card;
import com.example.yan_home.openglengineandroid.entities.cards.CardsHelper;
import com.example.yan_home.openglengineandroid.layouting.impl.CardsLayouterSlotImpl;
import com.example.yan_home.openglengineandroid.layouting.threepoint.ThreePointFanLayouter;
import com.example.yan_home.openglengineandroid.layouting.threepoint.ThreePointLayouter;
import com.example.yan_home.openglengineandroid.tweening.CardsTweenAnimator;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.util.geometry.YANVector2;
import com.yan.glengine.util.math.YANMathUtils;

import java.util.ArrayList;
import java.util.List;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class ThreePointLayoutingTestScreen extends BaseGameScreen {

    private static final int CARDS_COUNT = 6;
    private static final int MAX_CARDS_IN_LINE = /*8*/10;

    private ArrayList<YANTexturedNode> mCardNodesArray;
    private ArrayList<YANTexturedNode> mNodesToRemove;
    private CardsTweenAnimator mCardsTweenAnimator;
    private ThreePointLayouter mThreePointLayouter;
    private List<CardsLayouterSlotImpl> mSlots;

    public ThreePointLayoutingTestScreen(YANGLRenderer renderer) {
        super(renderer);
        mCardNodesArray = new ArrayList<>(CARDS_COUNT);
        mNodesToRemove = new ArrayList<>();
        mCardsTweenAnimator = new CardsTweenAnimator();
        mThreePointLayouter = new ThreePointFanLayouter();
        mSlots = new ArrayList<>(CARDS_COUNT);

        for (int i = 0; i < CARDS_COUNT; i++) {
            mSlots.add(new CardsLayouterSlotImpl());
        }
    }


    @Override
    public void onSetActive() {
        super.onSetActive();
    }

    @Override
    public void onSetNotActive() {
        super.onSetNotActive();
    }

    @Override
    protected void onAddNodesToScene() {
        super.onAddNodesToScene();
        getNodeList().addAll(mCardNodesArray);
    }

    protected void onRemoveCardButtonClicked() {
        if (mCardNodesArray.isEmpty())
            return;

        //removing a random card from the hand
        final YANTexturedNode cardToRemove = mCardNodesArray.get((int) YANMathUtils.randomInRange(0, mCardNodesArray.size() - 1));
        removeCardFromHand(cardToRemove);
    }

    private void removeCardFromHand(final YANTexturedNode cardToRemove) {
        mCardNodesArray.remove(cardToRemove);
        float targetRotation = YANMathUtils.randomInRange(0, 360);

        float targetXPosition = getSceneSize().getX() / 2;
        float targetYPosition = 200;
        TweenCallback animationEndCallback = new TweenCallback() {
            @Override
            public void onEvent(int i, BaseTween<?> baseTween) {
                cardToRemove.setSortingLayer(1);
            }
        };

        mCardsTweenAnimator.animateCardToValues(cardToRemove, targetXPosition, targetYPosition, targetRotation, animationEndCallback);
        mNodesToRemove.add(cardToRemove);
        layoutCards();
    }


    @Override
    protected void onLayoutNodes() {
        super.onLayoutNodes();
        layoutCards();
    }

    private void layoutCards() {

        //TODO : uncomment original coordinates
//        YANVector2 originPoint = new YANVector2(getSceneSize().getX() / 2, getSceneSize().getY());
//        YANVector2 leftBasis = new YANVector2(0, getSceneSize().getY() / 2);
//        YANVector2 righBasis = new YANVector2(getSceneSize().getX(), getSceneSize().getY() / 2);

//        YANVector2 originPoint = new YANVector2(getSceneSize().getX() / 2, getSceneSize().getY() / 2 + getSceneSize().getY() / 6);
//        YANVector2 leftBasis = new YANVector2(getSceneSize().getX() / 4, getSceneSize().getY() / 2);
//        YANVector2 righBasis = new YANVector2(getSceneSize().getX() - (getSceneSize().getX() / 4), getSceneSize().getY() / 2);

        float length = getSceneSize().getX() / 8;
        float offset = getSceneSize().getX() / 16;
        float w = getSceneSize().getX();
        YANVector2 originPoint = new YANVector2(w - offset, 0);
        YANVector2 leftBasis = new YANVector2(w - offset , length );
        YANVector2 righBasis = new YANVector2(w - length - offset, 0);

//        YANVector2 originPoint = new YANVector2(0, 0);
//        YANVector2 leftBasis = new YANVector2(1, 1);
//        YANVector2 righBasis = new YANVector2(-1, 1);

        mThreePointLayouter.setThreePoints(originPoint, leftBasis, righBasis);
        mThreePointLayouter.layoutRowOfSlots(mSlots);

        for (int i = 0; i < mSlots.size(); i++) {
            CardsLayouterSlotImpl slot = mSlots.get(i);
            YANTexturedNode node = mCardNodesArray.get(i);

            //set slot values to node
            node.setPosition(slot.getPosition().getX(), slot.getPosition().getY());
            node.setRotation(slot.getRotation());
        }
    }


    @Override
    protected void onChangeNodesSize() {
        super.onChangeNodesSize();

        if (mCardNodesArray.isEmpty())
            return;

        //cards
        float aspectRatio = mCardNodesArray.get(0).getTextureRegion().getWidth() / mCardNodesArray.get(0).getTextureRegion().getHeight();
        float cardWidth = Math.min(getSceneSize().getX(), getSceneSize().getY()) / (float) ((MAX_CARDS_IN_LINE) / 2);
        float cardHeight = cardWidth / aspectRatio;

        for (YANTexturedNode texturedNode : mCardNodesArray) {
            texturedNode.setAnchorPoint(0.5f, 0);
        }

        //TODO : for testing point rotations
//        float cardWidth = 40;
//        float cardHeight = 40;

        for (YANTexturedNode cardNode : mCardNodesArray) {
            cardNode.setSize(cardWidth, cardHeight);
        }

    }

    protected void onResetLayoutButtonClicked() {
        for (YANTexturedNode node : mNodesToRemove) {
            removeNode(node);
        }

        mNodesToRemove.clear();

        for (YANTexturedNode node : mCardNodesArray) {
            removeNode(node);
        }

        mCardNodesArray.clear();
        initCardsArray();
        for (YANTexturedNode node : mCardNodesArray) {
            addNode(node);
        }
        onChangeNodesSize();
        onLayoutNodes();
    }

    @Override
    protected void onCreateNodes() {
        super.onCreateNodes();
        initCardsArray();
    }

    private void initCardsArray() {
        ArrayList<Card> cardEntities = CardsHelper.create36Deck();

        //TODO : uncomment real cards loading
        for (Card cardEntity : cardEntities) {
            String name = "cards_back.png";
            YANTexturedNode card = new YANTexturedNode(mAtlas.getTextureRegion(name));
            mCardNodesArray.add(card);
        }
//        for (Card cardEntity : cardEntities) {
//            String name = "cards_" + cardEntity.getSuit() + "_" + cardEntity.getRank() + ".png";
//            YANTexturedNode card = new YANTexturedNode(mAtlas.getTextureRegion(name));
//            mCardNodesArray.add(card);
//        }
    }

    @Override
    public void onUpdate(float deltaTimeSeconds) {
        super.onUpdate(deltaTimeSeconds);
        mCardsTweenAnimator.update(deltaTimeSeconds * 1);
    }

}
