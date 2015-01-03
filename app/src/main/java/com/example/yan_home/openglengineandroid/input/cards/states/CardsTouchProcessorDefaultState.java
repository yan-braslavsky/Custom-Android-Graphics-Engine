package com.example.yan_home.openglengineandroid.input.cards.states;

import com.example.yan_home.openglengineandroid.input.cards.CardsTouchProcessor;
import com.example.yan_home.openglengineandroid.input.cards.CardsTouchProcessorState;
import com.example.yan_home.openglengineandroid.nodes.CardNode;
import com.yan.glengine.EngineWrapper;
import com.yan.glengine.input.YANInputManager;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.util.geometry.YANVector2;

/**
 * Created by Yan-Home on 11/21/2014.
 */
public class CardsTouchProcessorDefaultState extends CardsTouchProcessorState {


    public CardsTouchProcessorDefaultState(CardsTouchProcessor cardsTouchProcessor) {
        super(cardsTouchProcessor);
    }

    @Override
    public void applyState() {
        //Make all the cards at their regular alpha , position and size
        for (YANTexturedNode card : mCardsTouchProcessor.getCardNodesArray()) {
            mCardsTouchProcessor.getCardsTweenAnimator().animateCardToAlpha(card, 1f, BACK_IN_PLACE_ANIMATION_DURATION);
        }
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
        YANVector2 touchToWorldPoint = YANInputManager.touchToWorld(normalizedX, normalizedY,
                EngineWrapper.getRenderer().getSurfaceSize().getX(), EngineWrapper.getRenderer().getSurfaceSize().getY());

        //find touched card under the touch point
        CardNode touchedCard = mCardsTouchProcessor.findTouchedCard(touchToWorldPoint);
        if (touchedCard == null)
            return false;

        //move to hover state
        CardsTouchProcessorHoverState hoverState = new CardsTouchProcessorHoverState(mCardsTouchProcessor);
        hoverState.setHoveredCard(touchedCard);
        mCardsTouchProcessor.setCardsTouchProcessorState(hoverState);
        return true;

    }


}
