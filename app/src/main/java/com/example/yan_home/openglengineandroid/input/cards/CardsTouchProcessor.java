package com.example.yan_home.openglengineandroid.input.cards;

import com.example.yan_home.openglengineandroid.input.cards.states.CardsTouchProcessorDefaultState;
import com.example.yan_home.openglengineandroid.tweening.CardsTweenAnimator;
import com.yan.glengine.input.YANInputManager;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.util.geometry.YANReadOnlyVector2;
import com.yan.glengine.util.geometry.YANVector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Yan-Home on 11/21/2014.
 */
public class CardsTouchProcessor {

    public interface CardsTouchProcessorListener{
        void onSelectedCardTap(YANTexturedNode card);
        void onDraggedCardReleased(YANTexturedNode card);
    }

    //I assume there gonna be maximum of cards underneath a touch point
    private static final int MAX_CARDS_TO_PROCESS = 6;
    private final YANInputManager.TouchListener mTouchListener;
    private final ArrayList<YANTexturedNode> mCardNodesArray;
    private final CardsTweenAnimator mCardsTweenAnimator;
    private  YANReadOnlyVector2 mOriginalCardSize;
    private CardsTouchProcessorState mCardsTouchProcessorState;
    private List<YANTexturedNode> mTouchedCards;
    private Comparator<YANTexturedNode> mComparator;
    private CardsTouchProcessorListener mCardsTouchProcessorListener;

    public CardsTouchProcessor(final ArrayList<YANTexturedNode> cardNodesArray, CardsTweenAnimator cardsTweenAnimator) {

        //cache reference to tween animator
        mCardsTweenAnimator = cardsTweenAnimator;

        //cache the reference to array of cards in hand
        mCardNodesArray = cardNodesArray;

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
                return mCardsTouchProcessorState.onTouchDown(normalizedX, normalizedY);
            }

            @Override
            public boolean onTouchUp(float normalizedX, float normalizedY) {
                return mCardsTouchProcessorState.onTouchUp(normalizedX, normalizedY);
            }

            @Override
            public boolean onTouchDrag(float normalizedX, float normalizedY) {
                return mCardsTouchProcessorState.onTouchDrag(normalizedX, normalizedY);
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

    /**
     * Goes over all cards underneath the touch point and puts them into array
     * Returns the touched card or null if there no card was touched
     */
    public YANTexturedNode findTouchedCard(YANVector2 touchToWorldPoint) {
        mTouchedCards.clear();

        //see if one of the cards was touched
        for (int i = mCardNodesArray.size() - 1; i >= 0; i--) {
            YANTexturedNode card = mCardNodesArray.get(i);
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

    public ArrayList<YANTexturedNode> getCardNodesArray() {
        return mCardNodesArray;
    }

    public CardsTweenAnimator getCardsTweenAnimator() {
        return mCardsTweenAnimator;
    }

    public YANReadOnlyVector2 getOriginalCardSize() {
        return mOriginalCardSize;
    }

    public void setOriginalCardSize(float cardWidth,float cardHeight) {
        mOriginalCardSize = new YANVector2(cardWidth,cardHeight);
    }

    public CardsTouchProcessorListener getCardsTouchProcessorListener() {
        return mCardsTouchProcessorListener;
    }

    public void setCardsTouchProcessorListener(CardsTouchProcessorListener cardsTouchProcessorListener) {
        mCardsTouchProcessorListener = cardsTouchProcessorListener;
    }
}
