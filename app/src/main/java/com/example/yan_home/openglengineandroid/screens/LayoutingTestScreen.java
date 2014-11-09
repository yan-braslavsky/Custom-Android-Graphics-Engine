package com.example.yan_home.openglengineandroid.screens;

import com.example.yan_home.openglengineandroid.R;
import com.example.yan_home.openglengineandroid.layouting.CardsLayoutSlot;
import com.example.yan_home.openglengineandroid.layouting.CardsLayouter;
import com.example.yan_home.openglengineandroid.layouting.impl.CardsLayouterImpl;
import com.yan.glengine.nodes.YANButtonNode;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.screens.YANNodeScreen;
import com.yan.glengine.util.colors.YANColor;
import com.yan.glengine.util.math.YANMathUtils;

import java.util.ArrayList;

import aurelienribon.tweenengine.TweenManager;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class LayoutingTestScreen extends YANNodeScreen {

    private static final int BG_HEXA_COLOR = 0x9F9E36;
    private static final int CARDS_COUNT = 36;
    private static final int SCREEN_PADDING = 0;
    private static final int MAX_CARDS_IN_LINE = 8;
    private TweenManager mTweenManager;
    private ArrayList<YANTexturedNode> mCardNodesArray;
    private YANButtonNode mRepositionCardsButton;
    private YANTexturedNode mFence;
    private YANTexturedNode mGlade;
    private float mCardWidth;
    private float mCardHeight;
    private CardsLayouter mCardsLayouter;

    public LayoutingTestScreen(YANGLRenderer renderer) {
        super(renderer);

        mCardNodesArray = new ArrayList<>();
        mTweenManager = new TweenManager();
        mCardsLayouter = new CardsLayouterImpl(CARDS_COUNT);
    }

    @Override
    protected void onAddNodesToScene() {

        addNode(mGlade);
        for (YANTexturedNode cardNode : mCardNodesArray) {
            addNode(cardNode);
        }

        addNode(mRepositionCardsButton);
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

        mFence.setPosition(centerX, centerY);

        //glade
        centerX = (getSceneSize().getX() - mGlade.getSize().getX()) / 2;
        centerY = (getSceneSize().getY() - mGlade.getSize().getY()) / 2;

        mGlade.setPosition(centerX, centerY);

        layoutCards();
    }

    private void layoutCards() {

        mCardsLayouter.setActiveSlotsAmount(mCardNodesArray.size());

        for (int i = 0; i < mCardNodesArray.size(); i++) {
            YANTexturedNode card = mCardNodesArray.get(i);

            //set position
            CardsLayoutSlot slot = mCardsLayouter.getSlotAtPosition(i);
            card.setPosition(slot.getPosition().getX(), slot.getPosition().getY());

            //set rotation
            card.setRotation(slot.getRotation());
        }

        //change position in layers
        for (int i = mCardNodesArray.size() - 1; i >= 0; i--) {
            pushNodeToFront(mCardNodesArray.get(i));
        }

        pushNodeToFront(mFence);

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

        mRepositionCardsButton.setSize(150, 150);

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

        mFence = new YANTexturedNode(getTextureAtlas().getTextureRegion("fence.png"));
        mGlade = new YANTexturedNode(getTextureAtlas().getTextureRegion("glade.png"));

        mRepositionCardsButton = new YANButtonNode(getTextureAtlas().getTextureRegion("call_btn_default.png"), getTextureAtlas().getTextureRegion("call_btn_pressed.png"));
        mRepositionCardsButton.setClickListener(new YANButtonNode.YanButtonNodeClickListener() {
            @Override
            public void onButtonClick() {

                if (mCardNodesArray.isEmpty())
                    return;

                YANTexturedNode cardToRemove = mCardNodesArray.get(0);
                mCardNodesArray.remove(cardToRemove);
                removeNode(cardToRemove);
                layoutCards();
            }
        });


        for (int i = 0; i < CARDS_COUNT; i++) {
            String name = "card_" + (int) (YANMathUtils.randomInRange(1, 4)) + ".png";
            YANTexturedNode card = new YANTexturedNode(getTextureAtlas().getTextureRegion(name));
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
