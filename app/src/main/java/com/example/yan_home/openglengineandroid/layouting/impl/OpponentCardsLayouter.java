package com.example.yan_home.openglengineandroid.layouting.impl;

import com.example.yan_home.openglengineandroid.layouting.CardsLayoutSlot;
import com.example.yan_home.openglengineandroid.layouting.CardsLayouter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yan-Home on 11/8/2014.
 */
public class OpponentCardsLayouter implements CardsLayouter {

    public static final int BASE_SORTING_LAYER = 5;
    private int mActiveSlotsAmount;
    private ArrayList<CardsLayouterSlotImpl> mSlots;

    //layouting strategies used for delegations of actual calculations
    private CardsLayoutStrategyFan mFanStrategy;
    private CardsLayoutStrategyLine mLineStrategy;

    //metrics for calculations
    private float mCardWidth;
    private float mCardHeight;
    private float mMaxAvailableWidth;
    private float mBaseXPosition;
    private float mBaseYPosition;


    private List<List<CardsLayouterSlotImpl>> mLinesOfSlots;

    //external data that required
    public OpponentCardsLayouter(int maxSlotsAmount) {
        mFanStrategy = new CardsLayoutStrategyFan();
        mLineStrategy = new CardsLayoutStrategyLine();
        mSlots = new ArrayList<>(maxSlotsAmount);
        for (int i = 0; i < maxSlotsAmount; i++) {
            mSlots.add(new CardsLayouterSlotImpl());
        }

        //allocate list of slots
        mLinesOfSlots = new ArrayList<>();

    }

    @Override
    public void setActiveSlotsAmount(int amount) {
        mActiveSlotsAmount = amount;
        recalculateSlotsData();
    }

    @Override
    public void init(float cardWidth, float cardHeight, float maxAvailableWidth,  float baseXPosition, float baseYPosition) {
        mCardWidth = cardWidth;
        mCardHeight = cardHeight;
        mMaxAvailableWidth = maxAvailableWidth;
        mBaseXPosition = baseXPosition;
        mBaseYPosition = baseYPosition;
    }

    private void recalculateSlotsData() {
        mLinesOfSlots.clear();


        mFanStrategy.init(mBaseXPosition, mBaseYPosition, mMaxAvailableWidth, mCardWidth, mCardHeight);

        //TODO : remove
//        tempInit();


        List<CardsLayouterSlotImpl> slotsSubsist = mSlots.subList(0, mActiveSlotsAmount);
        mFanStrategy.layoutRowOfSlots(slotsSubsist);
        mLinesOfSlots.add(slotsSubsist);
        calculateSortingLayer();
    }

//    private void tempInit() {
//        YANVector2 rotatedPointOne = new YANVector2(0,200);
//        YANVector2 rotatedPointTwo = new YANVector2(0,200);
//
//        YANVector2 origin = new YANVector2(0,0);
//        YANMathUtils.rotatePointAroundOrigin(rotatedPointOne, origin, -70);
//        YANMathUtils.rotatePointAroundOrigin(rotatedPointTwo, origin,-20);
//
//        mFanStrategy.initFan(new YANTriangle(origin,rotatedPointOne,rotatedPointTwo) , mCardWidth, mCardHeight);
//    }

    private void calculateSortingLayer() {
        // 1 is the bottom line
        //used to order slots by z order
        int sortingLayer = BASE_SORTING_LAYER;
        for (int i = getLinesOfSlots().size() - 1; i >= 0; i--) {
            List<CardsLayouterSlotImpl> currentLine = getLinesOfSlots().get(i);
            for (CardsLayouterSlotImpl slot : currentLine) {
                slot.setSortingLayer(sortingLayer);
                sortingLayer++;
            }
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
    public List<List<CardsLayouterSlotImpl>> getLinesOfSlots() {
        return mLinesOfSlots;
    }
}
