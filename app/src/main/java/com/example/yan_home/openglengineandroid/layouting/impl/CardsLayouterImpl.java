package com.example.yan_home.openglengineandroid.layouting.impl;

import com.example.yan_home.openglengineandroid.layouting.CardsLayoutSlot;
import com.example.yan_home.openglengineandroid.layouting.CardsLayoutStrategy;
import com.example.yan_home.openglengineandroid.layouting.CardsLayouter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yan-Home on 11/8/2014.
 */
public class CardsLayouterImpl implements CardsLayouter {

    private int mActiveSlotsAmount;
    private ArrayList<CardsLayouterSlotImpl> mSlots;

    //layouting strategies used for delegations of actual calculations
    private CardsLayoutStrategyFan mFanStrategy;
    private CardsLayoutStrategyLine mLineStrategy;

    //metrics for calculations
    private float mCardWidth;
    private float mCardHeight;
    private float mMaxAvailableWidth;
    private float mMaxAvailibleHeight;
    private float mBaseXPosition;
    private float mBaseYPosition;

    //maximum amount of cards that can fit in one row
    //without getting on on top of another
    private int mMaxFullCardsInLine;


    //external data that required
    public CardsLayouterImpl(int maxSlotsAmount) {
        mFanStrategy = new CardsLayoutStrategyFan();
        mLineStrategy = new CardsLayoutStrategyLine();
        mSlots = new ArrayList<>(maxSlotsAmount);
        for (int i = 0; i < maxSlotsAmount; i++) {
            mSlots.add(new CardsLayouterSlotImpl());
        }
    }

    @Override
    public void setActiveSlotsAmount(int amount) {
        mActiveSlotsAmount = amount;
        recalculateSlotsData();
    }

    @Override
    public void init(float cardWidth, float cardHeight, float maxAvailableWidth, float maxAvailableHeight, float baseXPosition, float baseYPosition) {
        mCardWidth = cardWidth;
        mCardHeight = cardHeight;
        mMaxAvailableWidth = maxAvailableWidth;
        mMaxAvailibleHeight = maxAvailableHeight;
        mBaseXPosition = baseXPosition;
        mBaseYPosition = baseYPosition;

        //calculate max cards in line
        mMaxFullCardsInLine = (int) Math.floor(maxAvailableWidth / cardWidth);
    }

    private void recalculateSlotsData() {
        //here we decide how cards should be layed out

        //for now we will only implement line layout
        float yPosition = mBaseYPosition;

        //we will allow cards to overlap
        //up until half of the width
        int step = /*mMaxFullCardsInLine * 2*/ 7;
        float yDeltaBetweenRows = mCardHeight / 4;
        int i = 0;
        CardsLayoutStrategy strategy;
        while (i < mActiveSlotsAmount) {
            int start = i;
            int end = Math.min(i + step, mActiveSlotsAmount);

            //strategy will change depending on amount of cards in line
            int cardsInLine = end - start;
            if (cardsInLine == 2)
                strategy = mLineStrategy;
            else
                strategy = mFanStrategy;

            strategy.init(mBaseXPosition, yPosition, mMaxAvailableWidth, mCardWidth, mCardHeight);
            strategy.layoutRowOfSlots(mSlots.subList(start, end));

            yPosition -= yDeltaBetweenRows;
            i += step;
        }
    }

    @Override
    public CardsLayoutSlot getSlotAtPosition(int position) {
        if (mActiveSlotsAmount < position) {
            throw new RuntimeException("There is only " + mActiveSlotsAmount + " active slots , cannot provide slot at position " + position);
        }

        return mSlots.get(position);
    }

    @Override
    public List<List<Integer>> getSequences() {
        throw new UnsupportedOperationException();
    }
}
