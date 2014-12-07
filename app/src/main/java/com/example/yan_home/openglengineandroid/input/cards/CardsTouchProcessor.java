package com.example.yan_home.openglengineandroid.input.cards;

import com.example.yan_home.openglengineandroid.input.cards.states.CardsTouchProcessorDefaultState;
import com.yan.glengine.EngineWrapper;
import com.yan.glengine.input.YANInputManager;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.util.math.YANVector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Yan-Home on 11/21/2014.
 */
public class CardsTouchProcessor {

    //I assume there gonna be maximum of cards underneath a touch point
    private static final int MAX_CARDS_TO_PROCESS = 15;
    private final YANInputManager.TouchListener mTouchListener;
    private CardsTouchProcessorState mCardsTouchProcessorState;
    private List<YANTexturedNode> mTouchedCards;
    private Comparator<YANTexturedNode> mComparator;

    public CardsTouchProcessor(final ArrayList<YANTexturedNode> cardNodesArray) {

        //starting from a default state
        setCardsTouchProcessorState(new CardsTouchProcessorDefaultState(this));

        //array of cards under touch point
        mTouchedCards = new ArrayList<>(MAX_CARDS_TO_PROCESS);

        //comparator of cards by sorting layer
        mComparator = new Comparator<YANTexturedNode>() {
            @Override
            public int compare(YANTexturedNode lhs, YANTexturedNode rhs) {
                return lhs.getSortingLayer() - rhs.getSortingLayer();
            }
        };

        //touch listener that is added to input processor
        mTouchListener = new YANInputManager.TouchListener() {
            @Override
            public boolean onTouchDown(float normalizedX, float normalizedY) {

                //find touched card under the touch point
                YANTexturedNode touchedCard = findTouchedCard(normalizedX, normalizedY, cardNodesArray);
                if (touchedCard == null)
                    return false;

                //handle touch down on card
                onCardTouchDown(touchedCard);
                return true;
            }

            @Override
            public boolean onTouchUp(float normalizedX, float normalizedY) {
                onCardTouchUp();
                return false;
            }

            @Override
            public boolean onTouchDrag(float normalizedX, float normalizedY) {

                //find touched card under the touch point
                YANTexturedNode touchedCard = findTouchedCard(normalizedX, normalizedY, cardNodesArray);
                if (touchedCard == null)
                    return false;

                //handle touch drag on card
                onCardTouchDrag(touchedCard);
                return true;
            }
        };

    }

    /**
     * Making the touch processor active.It starts listen to touch events
     * and process it on cards.
     */
    public void register() {
        YANInputManager.getInstance().addEventListener(mTouchListener);
    }

    /**
     * Makes touch processor not active.It no longer processes touch events on cards.
     */
    public void unRegister() {
        YANInputManager.getInstance().removeEventListener(mTouchListener);
    }

    private void onCardTouchDown(YANTexturedNode touchedCard) {
        mCardsTouchProcessorState.onCardTouchDown(touchedCard);
    }
    private void onCardTouchUp() {
        mCardsTouchProcessorState.onTouchUp();
    }

    private void onCardTouchDrag(YANTexturedNode touchedCard) {
        mCardsTouchProcessorState.onCardTouchDrag(touchedCard);
    }


    /**
     * Goes over all cards underneath the touch point and puts them into array
     * Returns the touched card or null if there no card was touched
     */
    private YANTexturedNode findTouchedCard(float normalizedX, float normalizedY, ArrayList<YANTexturedNode> cardNodesArray) {
        mTouchedCards.clear();
        YANVector2 touchToWorldPoint = YANInputManager.touchToWorld(normalizedX, normalizedY,
                EngineWrapper.getRenderer().getSurfaceSize().getX(), EngineWrapper.getRenderer().getSurfaceSize().getY());

        //see if one of the cards was touched
        for (int i = cardNodesArray.size() - 1; i >= 0; i--) {
            YANTexturedNode card = cardNodesArray.get(i);
            if (card.getBoundingRectangle().contains(touchToWorldPoint)) {
                mTouchedCards.add(card);
            }
        }

        if (mTouchedCards.isEmpty())
            return null;

        //sort touched cards by layer
        Collections.sort(mTouchedCards, mComparator);
        //the latest card is the one that was touched
        return mTouchedCards.get(mTouchedCards.size() - 1);

    }

    public void setCardsTouchProcessorState(CardsTouchProcessorState cardsTouchProcessorState) {
        mCardsTouchProcessorState = cardsTouchProcessorState;
        mCardsTouchProcessorState.applyState();
    }
}
