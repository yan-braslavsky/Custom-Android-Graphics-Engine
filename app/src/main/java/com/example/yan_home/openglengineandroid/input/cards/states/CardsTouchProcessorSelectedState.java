package com.example.yan_home.openglengineandroid.input.cards.states;

import com.example.yan_home.openglengineandroid.input.cards.CardsTouchProcessor;
import com.example.yan_home.openglengineandroid.input.cards.CardsTouchProcessorState;
import com.yan.glengine.nodes.YANTexturedNode;


/**
 * Created by Yan-Home on 11/21/2014.
 */
public class CardsTouchProcessorSelectedState extends CardsTouchProcessorState {

    public static final float SELECTED_CARD_SIZE_SCALE = 1.1f;
    public static final int RETURN_TO_DEFAULT_STATE_DELAY = 2000;
    private YANTexturedNode mSelectedCard;

    public CardsTouchProcessorSelectedState(CardsTouchProcessor cardsTouchProcessor) {
        super(cardsTouchProcessor);
    }

    @Override
    public void applyState() {
        //TODO : Implement
        mSelectedCard.setSize(mCardsTouchProcessor.getOriginalCardSize().getX() * SELECTED_CARD_SIZE_SCALE, mCardsTouchProcessor.getOriginalCardSize().getY() * SELECTED_CARD_SIZE_SCALE);
        final float yOffset = mSelectedCard.getPosition().getY() * 0.1f;
        mSelectedCard.setPosition(mSelectedCard.getPosition().getX(), mSelectedCard.getPosition().getY() - yOffset);
        final int originalSortingLayer = mSelectedCard.getSortingLayer();
        mSelectedCard.setSortingLayer(100);

        //TODO : implement using a new delayed system
        //return to default state after few seconds of inactivity
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                YANLogger.log("CALLED RUNNABLE !!");
//
//                mSelectedCard.setSortingLayer(originalSortingLayer);
//                mSelectedCard.setPosition(mSelectedCard.getPosition().getX(), mSelectedCard.getPosition().getY() + yOffset);
//                mSelectedCard.setSize(mCardsTouchProcessor.getOriginalCardSize().getX(), mCardsTouchProcessor.getOriginalCardSize().getY());
//            }
//        }, RETURN_TO_DEFAULT_STATE_DELAY);
    }

    @Override
    public boolean onTouchUp(float normalizedX, float normalizedY) {
        return false;
    }

    @Override
    public boolean onTouchDrag(float normalizedX, float normalizedY) {
        return false;
    }

    @Override
    public boolean onTouchDown(float normalizedX, float normalizedY) {
        return false;
    }


    public void setSelectedCard(YANTexturedNode selectedCard) {
        mSelectedCard = selectedCard;
    }

}
