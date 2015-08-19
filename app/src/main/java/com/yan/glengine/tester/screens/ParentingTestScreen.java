package com.yan.glengine.tester.screens;

import android.support.annotation.NonNull;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import glengine.yan.glengine.assets.YANAssetManager;
import glengine.yan.glengine.nodes.YANBaseNode;
import glengine.yan.glengine.nodes.YANButtonNode;
import glengine.yan.glengine.nodes.YANIParentNode;
import glengine.yan.glengine.nodes.YANTextNode;
import glengine.yan.glengine.nodes.YANTexturedNode;
import glengine.yan.glengine.renderer.YANGLRenderer;
import glengine.yan.glengine.screens.YANIScreen;
import glengine.yan.glengine.service.ServiceLocator;
import glengine.yan.glengine.tween.YANTweenNodeAccessor;
import glengine.yan.glengine.util.geometry.YANRectangle;
import glengine.yan.glengine.util.math.YANMathUtils;

/**
 * Created by Yan-Home on 1/18/2015.
 */
public class ParentingTestScreen extends BaseTestScreen {

    private YANTextNode mTitleText;
    private YANTexturedNode mCard;
    private YANButtonNode mSimpleButton;

    public ParentingTestScreen(YANGLRenderer renderer) {
        super(renderer);
    }

    @Override
    public void onSetActive() {
        super.onSetActive();

        //card will rotate
        Timeline.createSequence()
                .beginParallel()

                        //rotation
                .push(Tween.from(mCard, YANTweenNodeAccessor.ROTATION_Z_CW, 3.0f).target(360))
                .push(Tween.from(mCard, YANTweenNodeAccessor.POSITION_X, 3.0f).target(getSceneSize().getX() / 2))
                .end()
                .beginParallel()
                .push(Tween.to(mCard, YANTweenNodeAccessor.POSITION_X, 3.0f).target(250))
                .push(Tween.from(mCard, YANTweenNodeAccessor.ROTATION_Z_CW, 3.0f).target(0))
                .end()
                        //size
                .beginParallel()
                .push(Tween.from(mCard, YANTweenNodeAccessor.SIZE_Y, 3.0f).target(100))
                .push(Tween.from(mCard, YANTweenNodeAccessor.SIZE_X, 3.0f).target(100))
                .end()
                .beginParallel()
                .push(Tween.to(mCard, YANTweenNodeAccessor.POSITION_X, 3.0f).target(getSceneSize().getX() / 2))
                .push(Tween.from(mCard, YANTweenNodeAccessor.ROTATION_Z_CW, 3.0f).target(0))
                .end()
                .repeat(500, 0.3f)
                .start(mTweenManager);
    }


    @Override
    public void onSetNotActive() {
        super.onSetNotActive();
    }

    @Override
    protected void onLayoutNodes() {
        super.onLayoutNodes();
        mTitleText.setPosition(50, 50);
        mCard.setPosition(getSceneSize().getX() / 2, getSceneSize().getY() / 2);
    }

    @Override
    protected void onAddNodesToScene() {
        super.onAddNodesToScene();

        //add text node
        addNode(mTitleText);
        addNode(mCard);
    }

    @Override
    protected void onChangeNodesSize() {
        super.onChangeNodesSize();
        final float originalCardWidth = mCardsAtlas.getTextureRegion("cards_diamonds_queen.png").getWidth();
        final float originalCardHeight = mCardsAtlas.getTextureRegion("cards_diamonds_queen.png").getHeight();

        float aspectRatio = originalCardWidth / originalCardHeight;
        float newWidth = getSceneSize().getX() * 0.3f;
        float newHeight = newWidth / aspectRatio;

        mCard.setSize(newWidth, newHeight);
        mSimpleButton.setSize(mUiAtlas.getTextureRegion("call_btn_default.png").getWidth() * 3, mUiAtlas.getTextureRegion("call_btn_default.png").getHeight() * 3);
    }

    @Override
    protected void onCreateNodes() {
        super.onCreateNodes();
        //create a text node
        mTitleText = new YANTextNode(ServiceLocator.locateService(YANAssetManager.class).getLoadedFont("standard_font"), "Parenting Test".length());
        mTitleText.setText("Parenting Test");
        mTitleText.setSortingLayer(OVERLAY_SORTING_LAYER);
        mCard = new YANTexturedNode(mCardsAtlas.getTextureRegion("cards_diamonds_queen.png"));

        //we are creating simple button to be attached to the card node
        mSimpleButton = new YANButtonNode(mUiAtlas.getTextureRegion("call_btn_default.png"), mUiAtlas.getTextureRegion("call_btn_pressed.png")) {
            @Override
            public void onParentAttributeChanged(@NonNull final YANBaseNode parentNode, @NonNull final Attribute attribute) {
                super.onParentAttributeChanged(parentNode, attribute);

                switch (attribute) {
                    case SIZE:
                        scaleWithParent(parentNode);
                        break;
                    case SORTING_LAYER:
                        adjustSortingLayerToParent(parentNode);
                        break;
                    case POSITION:
                        clampPositionInParent(parentNode);
                        break;
                    case ROTATION_Y:
                    case ROTATION_Z:
                        adjustRotationToParent(parentNode);
                        break;
                }
            }

            @Override
            public void onAttachedToParentNode(@NonNull final YANBaseNode parentNode) {
                super.onAttachedToParentNode(parentNode);

                //adjust sorting layer to be always on top of the parent
                adjustSortingLayerToParent(parentNode);

                //scale uniformly with parent
                scaleWithParent(parentNode);

                //adjust position to be in parent bounds
                clampPositionInParent(parentNode);

                //setRotation according to parent
                adjustRotationToParent(parentNode);

            }

            private void adjustRotationToParent(final @NonNull YANIParentNode parentNode) {
                this.setRotationY(parentNode.getRotationY());
                this.setRotationZ(parentNode.getRotationZ());
            }

            private void clampPositionInParent(final @NonNull YANIParentNode parentNode) {
                final YANRectangle parentBoundingRect = parentNode.getBoundingRectangle();
                //clamp position within bounds of the parent
                float posX = YANMathUtils.clamp(getPosition().getX(), parentBoundingRect.getLeftTop().getX(), parentBoundingRect.getRightBottom().getX());
                float posY = YANMathUtils.clamp(getPosition().getY(), parentBoundingRect.getLeftTop().getY(), parentBoundingRect.getRightBottom().getY());
                this.setPosition(posX, posY);
            }

            private void adjustSortingLayerToParent(final @NonNull YANIParentNode parentNode) {
                this.setSortingLayer(parentNode.getSortingLayer() + 1);
            }

            private void scaleWithParent(final @NonNull YANIParentNode parentNode) {
                float scaleX = parentNode.getSize().getX() / this.getSize().getX();
                float scaleY = parentNode.getSize().getY() / this.getSize().getY();
                this.setSize(getSize().getX() * scaleX, getSize().getY() * scaleY);
            }
        };


        //we are parenting button to the card
        mCard.addChildNode(mSimpleButton);

        mSimpleButton.setClickListener(new YANButtonNode.YanButtonNodeClickListener() {
            @Override
            public void onButtonClick() {
                //when card is removed button will also be removed
                removeNode(mCard);
            }
        });
    }


    @Override
    protected YANIScreen onSetNextScreen() {
        return new ButtonsTestScreen(getRenderer());
    }

    @Override
    protected YANIScreen onSetPreviousScreen() {
        return null;
    }

    @Override
    public void onUpdate(float deltaTimeSeconds) {
        super.onUpdate(deltaTimeSeconds);
    }
}
