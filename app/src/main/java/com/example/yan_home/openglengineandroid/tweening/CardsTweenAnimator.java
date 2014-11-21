package com.example.yan_home.openglengineandroid.tweening;

import com.example.yan_home.openglengineandroid.layouting.CardsLayoutSlot;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.tween.YANTweenNodeAccessor;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by Yan-Home on 11/21/2014.
 */
public class CardsTweenAnimator {
    private TweenManager mTweenManager;

    public CardsTweenAnimator() {
        mTweenManager = new TweenManager();
    }

    /**
     * Animates values of the card to values of the slot
     */
    public void animateCardToSlot(YANTexturedNode card, CardsLayoutSlot slot) {
        Timeline.createSequence()
                .beginParallel()
                .push(Tween.to(card, YANTweenNodeAccessor.POSITION_X, 0.5f).target(slot.getPosition().getX()))
                .push(Tween.to(card, YANTweenNodeAccessor.POSITION_Y, 0.5f).target(slot.getPosition().getY()))
                .push(Tween.to(card, YANTweenNodeAccessor.ROTATION_CW, 0.5f).target(slot.getRotation()))
                .start(mTweenManager);
    }

    public void animateCardToValues(YANTexturedNode card, float targetXPosition, float targetYPosition, float targetRotation, TweenCallback animationEndCallback) {
        Timeline.createSequence()
                .beginParallel()
                .push(Tween.to(card, YANTweenNodeAccessor.POSITION_X, 0.5f).target(targetXPosition))
                .setCallback(
                        animationEndCallback)
                .push(Tween.to(card, YANTweenNodeAccessor.POSITION_Y, 0.5f).target(targetYPosition))
                .push(Tween.to(card, YANTweenNodeAccessor.ROTATION_CW, 0.5f).target(targetRotation))
                .start(mTweenManager);
    }

    public void update(float deltaTime) {
        mTweenManager.update(deltaTime);
    }
}
