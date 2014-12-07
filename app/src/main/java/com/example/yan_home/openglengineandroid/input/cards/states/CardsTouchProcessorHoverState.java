package com.example.yan_home.openglengineandroid.input.cards.states;

import com.example.yan_home.openglengineandroid.input.cards.CardsTouchProcessor;
import com.example.yan_home.openglengineandroid.input.cards.CardsTouchProcessorState;
import com.yan.glengine.EngineWrapper;
import com.yan.glengine.input.YANInputManager;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.util.math.YANVector2;

/**
 * Created by Yan-Home on 11/21/2014.
 */
public class CardsTouchProcessorHoverState extends CardsTouchProcessorState {

    private static final float NOT_HOVERED_CARDS_OPACITY = 0.2f;
    YANTexturedNode mHoveredCard;

    public CardsTouchProcessorHoverState(CardsTouchProcessor cardsTouchProcessor) {
        super(cardsTouchProcessor);
    }

    @Override
    public void applyState() {
        //nothing to change
    }

    @Override
    public boolean onTouchUp(float normalizedX, float normalizedY) {

        //move to selected state
        CardsTouchProcessorSelectedState selectedState = new CardsTouchProcessorSelectedState(mCardsTouchProcessor);
        selectedState.setSelectedCard(mHoveredCard);
        mCardsTouchProcessor.setCardsTouchProcessorState(selectedState);
        return true;
    }

    @Override
    public boolean onTouchDrag(float normalizedX, float normalizedY) {
        YANVector2 touchToWorldPoint = YANInputManager.touchToWorld(normalizedX, normalizedY,
                EngineWrapper.getRenderer().getSurfaceSize().getX(), EngineWrapper.getRenderer().getSurfaceSize().getY());

        //find touched card under the touch point
        YANTexturedNode touchedCard = mCardsTouchProcessor.findTouchedCard(touchToWorldPoint);

        //no card touched ?
        if (touchedCard == null)
            return true;

        //we don't want to waste resources on the same card
        if (mHoveredCard == touchedCard)
            return true;

        //cache touched card
        mHoveredCard = touchedCard;

        //now the hover card is changed
        setHoveredCard(touchedCard);
        return true;
    }

    @Override
    public boolean onTouchDown(float normalizedX, float normalizedY) {
        return false;
    }


    public void setHoveredCard(YANTexturedNode hoveredCard) {
        mHoveredCard = hoveredCard;

        //TODO : hide other cards that are not hovered
        for (YANTexturedNode card : mCardsTouchProcessor.getCardNodesArray()) {
            if (card == mHoveredCard) {
                card.setOpacity(1f);
            } else {
                card.setOpacity(NOT_HOVERED_CARDS_OPACITY);
            }
        }
    }
}
