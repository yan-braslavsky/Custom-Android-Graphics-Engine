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
    public static final int MAX_CARDS_TO_PROCESS = 15;
    private final YANInputManager.TouchListener mTouchListener;
    private CardsTouchProcessorState mCardsTouchProcessorState;
    private List<YANTexturedNode> mTouchedCards;
    private Comparator<YANTexturedNode> mComparator;


    public CardsTouchProcessor(final ArrayList<YANTexturedNode> cardNodesArray) {

        mCardsTouchProcessorState = new CardsTouchProcessorDefaultState();
        mTouchedCards = new ArrayList<>(MAX_CARDS_TO_PROCESS);
        mComparator = new Comparator<YANTexturedNode>() {
            @Override
            public int compare(YANTexturedNode lhs, YANTexturedNode rhs) {
                return lhs.getSortingLayer() - rhs.getSortingLayer();
            }
        };

        mTouchListener = new YANInputManager.TouchListener() {
            @Override
            public boolean onTouchDown(float normalizedX, float normalizedY) {

                YANTexturedNode touchedCard = findTouchedCard(normalizedX, normalizedY, cardNodesArray);
                if (touchedCard == null)
                    return false;

                onCardTouchDown(touchedCard);
                return true;
            }

            @Override
            public boolean onTouchUp(float normalizedX, float normalizedY) {
                CardsTouchProcessor.this.onTouchUp();
                return false;
            }

            @Override
            public boolean onTouchDrag(float normalizedX, float normalizedY) {
                YANTexturedNode touchedCard = findTouchedCard(normalizedX, normalizedY, cardNodesArray);
                if (touchedCard == null)
                    return false;

                onCardTouchDrag(touchedCard);
                return true;
            }
        };

    }

    public void register() {
        YANInputManager.getInstance().addEventListener(mTouchListener);
    }

    public void unRegister() {
        YANInputManager.getInstance().removeEventListener(mTouchListener);
    }

    private void onTouchUp() {
        mCardsTouchProcessorState.onTouchUp();
    }

    private void onCardTouchDrag(YANTexturedNode touchedCard) {
        mCardsTouchProcessorState.onCardTouchDrag(touchedCard);
    }

    private void onCardTouchDown(YANTexturedNode touchedCard) {
        //TODO : here we actually will process the touched card according to state
        mCardsTouchProcessorState.onCardTouchDown(touchedCard);

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
}
