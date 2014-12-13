package com.example.yan_home.openglengineandroid.input.cards.states;

import com.example.yan_home.openglengineandroid.input.cards.CardsTouchProcessor;
import com.example.yan_home.openglengineandroid.input.cards.CardsTouchProcessorState;
import com.yan.glengine.EngineWrapper;
import com.yan.glengine.input.YANInputManager;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.tasks.YANDelayedTask;
import com.yan.glengine.util.math.YANVector2;


/**
 * Created by Yan-Home on 11/21/2014.
 */
public class CardsTouchProcessorSelectedState extends CardsTouchProcessorState {

    public static final float SELECTED_CARD_SIZE_SCALE = 1.1f;
    public static final int RETURN_TO_DEFAULT_STATE_DELAY_SECONDS = 2;
    public static final float SELECTION_ANIMATION_DURATION = 0.1f;

    private YANTexturedNode mSelectedCard;
    private int originalSortingLayer;
    private float mOriginalYPosition;
    private float mInitialYOffset;
    private YANDelayedTask mDelayedTask;

    public CardsTouchProcessorSelectedState(CardsTouchProcessor cardsTouchProcessor) {
        super(cardsTouchProcessor);
    }

    @Override
    public void applyState() {

        //cache original values
        mOriginalYPosition = mSelectedCard.getPosition().getY();
        originalSortingLayer = mSelectedCard.getSortingLayer();

        //change sorting layer
        mSelectedCard.setSortingLayer(100);

        //make card bigger
        float yOffset = mSelectedCard.getPosition().getY() * 0.1f;
        mCardsTouchProcessor.getCardsTweenAnimator().animateSizeAndPositionXY(mSelectedCard,
                mCardsTouchProcessor.getOriginalCardSize().getX() * SELECTED_CARD_SIZE_SCALE, mCardsTouchProcessor.getOriginalCardSize().getY() * SELECTED_CARD_SIZE_SCALE,
                mSelectedCard.getPosition().getX(), mSelectedCard.getPosition().getY() - yOffset, SELECTION_ANIMATION_DURATION);

        mDelayedTask = new YANDelayedTask(RETURN_TO_DEFAULT_STATE_DELAY_SECONDS, new YANDelayedTask.YANDelayedTaskListener() {
            @Override
            public void onComplete() {
                returnToDefaultState();
            }
        });

        mDelayedTask.start();

    }

    private void returnToDefaultState() {
        mDelayedTask.stop();
        mSelectedCard.setSortingLayer(originalSortingLayer);

        mCardsTouchProcessor.getCardsTweenAnimator().animateSizeAndPositionXY(mSelectedCard,
                mCardsTouchProcessor.getOriginalCardSize().getX(), mCardsTouchProcessor.getOriginalCardSize().getY(),
                mSelectedCard.getPosition().getX(), mOriginalYPosition + mInitialYOffset,
                BACK_IN_PLACE_ANIMATION_DURATION);

        CardsTouchProcessorDefaultState defaultState = new CardsTouchProcessorDefaultState(mCardsTouchProcessor);
        mCardsTouchProcessor.setCardsTouchProcessorState(defaultState);
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

        //if touch down not on the selected card , go back to  default state , else go to drag state
        YANVector2 touchToWorldPoint = YANInputManager.touchToWorld(normalizedX, normalizedY,
                EngineWrapper.getRenderer().getSurfaceSize().getX(), EngineWrapper.getRenderer().getSurfaceSize().getY());

        //find touched card under the touch point
        YANTexturedNode touchedCard = mCardsTouchProcessor.findTouchedCard(touchToWorldPoint);
        if (touchedCard == mSelectedCard) {
            if (mCardsTouchProcessor.getCardsTouchProcessorListener() != null) {
                mDelayedTask.stop();
                CardsTouchProcessorDefaultState defaultState = new CardsTouchProcessorDefaultState(mCardsTouchProcessor);
                mCardsTouchProcessor.setCardsTouchProcessorState(defaultState);
                mCardsTouchProcessor.getCardsTouchProcessorListener().onSelectedCardTap(mSelectedCard);
            }
            return true;
        }


        //else we returning to default state
        returnToDefaultState();
        return true;
    }


    public void setSelectedCard(YANTexturedNode selectedCard) {
        mSelectedCard = selectedCard;
    }

    public void setInitialYOffset(float initialYOffset) {
        mInitialYOffset = initialYOffset;
    }

}
