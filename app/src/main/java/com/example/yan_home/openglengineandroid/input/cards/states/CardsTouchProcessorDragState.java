package com.example.yan_home.openglengineandroid.input.cards.states;

import com.example.yan_home.openglengineandroid.input.cards.CardsTouchProcessor;
import com.example.yan_home.openglengineandroid.input.cards.CardsTouchProcessorState;

/**
 * Created by Yan-Home on 11/21/2014.
 */
public class CardsTouchProcessorDragState extends CardsTouchProcessorState {

    public CardsTouchProcessorDragState(CardsTouchProcessor cardsTouchProcessor) {
        super(cardsTouchProcessor);
    }

    @Override
    public void applyState() {

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


}
