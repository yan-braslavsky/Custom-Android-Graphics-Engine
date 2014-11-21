package com.example.yan_home.openglengineandroid.screens;

import com.example.yan_home.openglengineandroid.R;
import com.example.yan_home.openglengineandroid.layouting.CardsLayoutSlot;
import com.example.yan_home.openglengineandroid.layouting.CardsLayouter;
import com.example.yan_home.openglengineandroid.layouting.impl.CardsLayouterImpl;
import com.example.yan_home.openglengineandroid.tweening.CardsTweenAnimator;
import com.yan.glengine.assets.YANAssetManager;
import com.yan.glengine.assets.atlas.YANTextureAtlas;
import com.yan.glengine.nodes.YANButtonNode;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.screens.YANNodeScreen;
import com.yan.glengine.util.colors.YANColor;
import com.yan.glengine.util.math.YANMathUtils;

import java.util.ArrayList;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class LayoutingTestScreen extends YANNodeScreen {

    private static final int BG_HEXA_COLOR = 0x9F9E36;
    private static final int CARDS_COUNT = 36;
    private static final int SCREEN_PADDING = 0;
    private static final int MAX_CARDS_IN_LINE = 8;

    private ArrayList<YANTexturedNode> mCardNodesArray;
    private ArrayList<YANTexturedNode> mNodesToRemove;
    private YANTextureAtlas mAtlas;
    private YANButtonNode mRemoveCardButton;
    private YANButtonNode mResetLayoutButton;
    private CardsTweenAnimator mCardsTweenAnimator;
    private YANTexturedNode mFence;
    private YANTexturedNode mGlade;
    private float mCardWidth;
    private float mCardHeight;
    private CardsLayouter mCardsLayouter;

    public LayoutingTestScreen(YANGLRenderer renderer) {
        super(renderer);
        mCardNodesArray = new ArrayList<>();
        mNodesToRemove = new ArrayList<>();

        mCardsLayouter = new CardsLayouterImpl(CARDS_COUNT);
        mAtlas = YANAssetManager.getInstance().getLoadedAtlas(R.raw.ui_atlas);
        mCardsTweenAnimator = new CardsTweenAnimator();
    }

    @Override
    protected void onAddNodesToScene() {

        //add cards
        for (YANTexturedNode yanTexturedNode : mCardNodesArray) {
            addNode(yanTexturedNode);
        }

        //add all the other nodes
        addNode(mGlade);
        addNode(mFence);
        addNode(mRemoveCardButton);
        addNode(mResetLayoutButton);
    }


    @Override
    protected void onLayoutNodes() {

        //fence
        float centerX = (getSceneSize().getX() - mFence.getSize().getX()) / 2;
        float centerY = (getSceneSize().getY() - mFence.getSize().getY());
        mFence.setPosition(centerX, centerY);

        //glade
        centerX = (getSceneSize().getX() - mGlade.getSize().getX()) / 2;
        centerY = (getSceneSize().getY() - mGlade.getSize().getY()) / 2;
        mGlade.setPosition(centerX, centerY);

        //buttons
        mResetLayoutButton.setPosition(getSceneSize().getX() - mResetLayoutButton.getSize().getX(), 0);
        mRemoveCardButton.setPosition(0, 0);

        layoutCards();
    }

    private void layoutCards() {

        mCardsLayouter.setActiveSlotsAmount(mCardNodesArray.size());

        //each index in cards array corresponds to slot index
        for (int i = 0; i < mCardNodesArray.size(); i++) {
            YANTexturedNode card = mCardNodesArray.get(i);
            CardsLayoutSlot slot = mCardsLayouter.getSlotAtPosition(i);
            card.setSortingLayer(slot.getSortingLayer());
            mCardsTweenAnimator.animateCardToSlot(card, slot);
        }
    }


    @Override
    protected void onChangeNodesSize() {
        float aspectRatio;

        //fence
        aspectRatio = mFence.getTextureRegion().getWidth() / mFence.getTextureRegion().getHeight();
        mFence.setSize(getSceneSize().getX(), getSceneSize().getX() / aspectRatio);

        //glade
        aspectRatio = mGlade.getTextureRegion().getWidth() / mGlade.getTextureRegion().getHeight();
        float gladeWidth = Math.min(getSceneSize().getX(), getSceneSize().getY()) * 0.9f;
        mGlade.setSize(gladeWidth, gladeWidth / aspectRatio);

        if (mCardNodesArray.isEmpty())
            return;

        //cards
        aspectRatio = mCardNodesArray.get(0).getTextureRegion().getWidth() / mCardNodesArray.get(0).getTextureRegion().getHeight();
        mCardWidth = Math.min(getSceneSize().getX(), getSceneSize().getY()) / (float) ((MAX_CARDS_IN_LINE) / 2);
        mCardHeight = mCardWidth / aspectRatio;

        for (YANTexturedNode cardNode : mCardNodesArray) {
            cardNode.setSize(mCardWidth, mCardHeight);
        }

        mRemoveCardButton.setSize(150, 150);
        mResetLayoutButton.setSize(150, 150);

        //init the layouter
        mCardsLayouter.init(mCardWidth, mCardHeight,
                //maximum available width
                getSceneSize().getX() - (SCREEN_PADDING * 2),
                //maximum available height
                getSceneSize().getY(),
                //base x position ( center )
                getSceneSize().getX() / 2,
                //base y position
                getSceneSize().getY() - mFence.getSize().getY() / 2);
    }


    @Override
    protected void onCreateNodes() {

        mFence = new YANTexturedNode(mAtlas.getTextureRegion("fence.png"));

        //fence is on top of cards
        mFence.setSortingLayer(50);
        mGlade = new YANTexturedNode(mAtlas.getTextureRegion("glade.png"));

        mRemoveCardButton = new YANButtonNode(mAtlas.getTextureRegion("call_btn_default.png"), mAtlas.getTextureRegion("call_btn_pressed.png"));
        mRemoveCardButton.setClickListener(new YANButtonNode.YanButtonNodeClickListener() {
            @Override
            public void onButtonClick() {

                if (mCardNodesArray.isEmpty())
                    return;

                //removing a random card from the hand
                final YANTexturedNode cardToRemove = mCardNodesArray.get((int) YANMathUtils.randomInRange(0, mCardNodesArray.size() - 1));
                mCardNodesArray.remove(cardToRemove);
                float targetRotation = YANMathUtils.randomInRange(0, 360);

                float targetXPosition = getSceneSize().getX() / 2;
                float targetYPosition = mCardHeight;
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
        });

        mResetLayoutButton = new YANButtonNode(mAtlas.getTextureRegion("call_btn_default.png"), mAtlas.getTextureRegion("call_btn_pressed.png"));
        mResetLayoutButton.setClickListener(new YANButtonNode.YanButtonNodeClickListener() {
            @Override
            public void onButtonClick() {

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
        });

        initCardsArray();
    }

    private void initCardsArray() {
        for (int i = 0; i < CARDS_COUNT; i++) {
            String name = "card_" + (i + 1) + ".png";
            YANTexturedNode card = new YANTexturedNode(mAtlas.getTextureRegion(name));
            mCardNodesArray.add(card);
        }
    }

    @Override
    public void onUpdate(float deltaTimeSeconds) {
        super.onUpdate(deltaTimeSeconds);
        mCardsTweenAnimator.update(deltaTimeSeconds * 1);

    }

    @Override
    public void onSetActive() {
        super.onSetActive();
        getRenderer().setRendererBackgroundColor(YANColor.createFromHexColor(BG_HEXA_COLOR));
        YANAssetManager.getInstance().loadTexture(mAtlas.getAtlasImageResourceID());
    }

    @Override
    public void onSetNotActive() {
        super.onSetNotActive();
        YANAssetManager.getInstance().unloadTexture(mAtlas.getAtlasImageResourceID());
    }
}
