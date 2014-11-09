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

        //for now we will only implement line layout
        float yPosition = mBaseYPosition;

        //the step will change according to logic
        int step = calculateStep();
        float yDeltaBetweenRows = mCardHeight / 4;
        int i = 0;
        CardsLayoutStrategy strategy;
        while (i < mActiveSlotsAmount) {
            int start = i;
            int end = Math.min(i + step, mActiveSlotsAmount);

            //strategy will change depending on amount of cards in line
            int cardsInLine = end - start;
            boolean isLineStrategy = cardsInLine == 2;
            if (isLineStrategy)
                strategy = mLineStrategy;
            else
                strategy = mFanStrategy;

            strategy.init(mBaseXPosition, yPosition, mMaxAvailableWidth, mCardWidth, mCardHeight);
            List<CardsLayouterSlotImpl> subList = mSlots.subList(start, end);
            strategy.layoutRowOfSlots(subList);

            if(!isLineStrategy){
                //reorder cards inside subList
                reorderInsideSubList(subList);
            }

            yPosition -= yDeltaBetweenRows;
            i += step;
        }
    }

    private void reorderInsideSubList(List<CardsLayouterSlotImpl> subList) {

//        ArrayList<CardsLayouterSlotImpl> tmpList = new ArrayList<>();
//        if(subList.size() == 11){
//            tmpList.add(0,subList.get(10));
//            tmpList.add(1,subList.get(8));
//            tmpList.add(2,subList.get(6));
//            tmpList.add(3,subList.get(4));
//            tmpList.add(4,subList.get(2));
//            tmpList.add(5,subList.get(0));
//            tmpList.add(6,subList.get(1));
//            tmpList.add(7,subList.get(3));
//            tmpList.add(8,subList.get(5));
//            tmpList.add(9,subList.get(7));
//            tmpList.add(10,subList.get(9));
//
//            subList.clear();
//
//            for (CardsLayouterSlotImpl slot : tmpList) {
//                subList.add(slot);
//            }
//        }


    }

    private int calculateStep() {
        //the step is defined according to  logic
        return (mActiveSlotsAmount > 21) ? 11 : (mActiveSlotsAmount > 14) ? 9 : 7;
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
