package com.example.yan_home.openglengineandroid.durak.screens;

import com.example.yan_home.openglengineandroid.durak.communication.game_server.GameServerCommunicator;
import com.example.yan_home.openglengineandroid.durak.communication.game_server.IGameServerConnector;
import com.example.yan_home.openglengineandroid.durak.entities.cards.Card;
import com.example.yan_home.openglengineandroid.durak.input.cards.CardsTouchProcessor;
import com.example.yan_home.openglengineandroid.durak.layouting.CardsLayoutSlot;
import com.example.yan_home.openglengineandroid.durak.layouting.CardsLayouter;
import com.example.yan_home.openglengineandroid.durak.layouting.impl.CardsLayouterSlotImpl;
import com.example.yan_home.openglengineandroid.durak.layouting.impl.PlayerCardsLayouter;
import com.example.yan_home.openglengineandroid.durak.layouting.threepoint.ThreePointFanLayouter;
import com.example.yan_home.openglengineandroid.durak.nodes.CardNode;
import com.example.yan_home.openglengineandroid.durak.protocol.BaseProtocolMessage;
import com.example.yan_home.openglengineandroid.durak.protocol.data.CardData;
import com.example.yan_home.openglengineandroid.durak.protocol.messages.CardMovedProtocolMessage;
import com.example.yan_home.openglengineandroid.durak.protocol.messages.GameSetupProtocolMessage;
import com.example.yan_home.openglengineandroid.durak.protocol.messages.PlayerTakesActionMessage;
import com.example.yan_home.openglengineandroid.durak.protocol.messages.RequestCardForAttackMessage;
import com.example.yan_home.openglengineandroid.durak.protocol.messages.RequestRetaliatePilesMessage;
import com.example.yan_home.openglengineandroid.durak.protocol.messages.ResponseCardForAttackMessage;
import com.example.yan_home.openglengineandroid.durak.protocol.messages.ResponseRetaliatePilesMessage;
import com.example.yan_home.openglengineandroid.durak.protocol.messages.RetaliationInvalidProtocolMessage;
import com.example.yan_home.openglengineandroid.durak.screen_fragments.cards.CardsScreenFragment;
import com.example.yan_home.openglengineandroid.durak.screen_fragments.cards.ICardsScreenFragment;
import com.example.yan_home.openglengineandroid.durak.screen_fragments.hud.HudScreenFragment;
import com.example.yan_home.openglengineandroid.durak.screen_fragments.hud.IHudScreenFragment;
import com.example.yan_home.openglengineandroid.durak.tweening.CardsTweenAnimator;
import com.yan.glengine.nodes.YANButtonNode;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.util.YANLogger;
import com.yan.glengine.util.geometry.YANVector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class RemoteGameScreen extends BaseGameScreen {

    public static final float CARD_SCALE_AMOUNT_OPPONENT = 0.6f;

    //Players hand related
    private CardsLayouter mPlayerCardsLayouter;
    private CardsTouchProcessor mCardsTouchProcessor;

    //opponent hand related
    private ThreePointFanLayouter mThreePointFanLayouterTopRightPlayer;
    private ThreePointFanLayouter mThreePointFanLayouterTopLeft;
    private CardsTweenAnimator mCardsTweenAnimator;
    private IGameServerConnector mGameServerConnector;
    private IHudScreenFragment mHudNodesManager;
    private ICardsScreenFragment mCardsScreenFragment;
    private boolean mCardForAttackRequested;
    private boolean mRequestedRetaliation;

    public RemoteGameScreen(YANGLRenderer renderer) {
        super(renderer);

        mHudNodesManager = new HudScreenFragment();
        mHudNodesManager.setNodeNodeAttachmentChangeListener(new IHudScreenFragment.INodeAttachmentChangeListener() {
            @Override
            public void onNodeVisibilityChanged(YANTexturedNode node, boolean isVisible) {
                if (isVisible) {
                    addNode(node);
                } else {
                    removeNode(node);
                }
            }
        });


        //TODO : inject game server connector
        mGameServerConnector = new GameServerCommunicator();
        mGameServerConnector.setListener(new IGameServerConnector.IGameServerCommunicatorListener() {
            @Override
            public void handleServerMessage(BaseProtocolMessage serverMessage) {

                //TODO : this is not an efficient way to handle messages
                if (serverMessage.getMessageName().equals(CardMovedProtocolMessage.MESSAGE_NAME)) {
                    handleCardMoveMessage((CardMovedProtocolMessage) serverMessage);
                } else if (serverMessage.getMessageName().equals(RequestCardForAttackMessage.MESSAGE_NAME)) {
                    handleRequestCardForAttackMessage((RequestCardForAttackMessage) serverMessage);
                } else if (serverMessage.getMessageName().equals(RequestRetaliatePilesMessage.MESSAGE_NAME)) {
                    handleRequestRetaliatePilesMessage((RequestRetaliatePilesMessage) serverMessage);
                } else if (serverMessage.getMessageName().equals(GameSetupProtocolMessage.MESSAGE_NAME)) {
                    handleGameSetupMessage((GameSetupProtocolMessage) serverMessage);
                } else if (serverMessage.getMessageName().equals(PlayerTakesActionMessage.MESSAGE_NAME)) {
                    handlePlayerTakesActionMessage((PlayerTakesActionMessage) serverMessage);
                } else if (serverMessage.getMessageName().equals(RetaliationInvalidProtocolMessage.MESSAGE_NAME)) {
                    handleInvalidRetaliationMessage((RetaliationInvalidProtocolMessage) serverMessage);
                }
            }
        });

        mCardsTweenAnimator = new CardsTweenAnimator();
        mCardsScreenFragment = new CardsScreenFragment(mCardsTweenAnimator);
        mCardsScreenFragment.setCardMovementListener(new ICardsScreenFragment.ICardMovementListener() {
            @Override
            public void onCardMovesToBottomPlayerPile() {
                layoutBottomPlayerCards();
            }

            @Override
            public void onCardMovesToTopRightPlayerPile() {
                layoutTopRightPlayerCards();
            }

            @Override
            public void onCardMovesToTopLeftPlayerPile() {
                layoutTopLeftPlayerCards();
            }

            @Override
            public void onCardMovesToFieldPile() {
            }
        });

        //init 3 points layouter to create a fan of opponents hands
        mThreePointFanLayouterTopRightPlayer = new ThreePointFanLayouter(2);
        mThreePointFanLayouterTopLeft = new ThreePointFanLayouter(2);

        //init player cards layouter
        mPlayerCardsLayouter = new PlayerCardsLayouter(mCardsScreenFragment.getTotalCardsAmount());

        //currently we are initializing with empty array , cards will be set every time player pile content changes
        mCardsTouchProcessor = new CardsTouchProcessor(mCardsScreenFragment.getBottomPlayerCardNodes()/*mPlayerOneCardNodes*/, mCardsTweenAnimator);

        //set listener to handle touches
        mCardsTouchProcessor.setCardsTouchProcessorListener(new CardsTouchProcessor.CardsTouchProcessorListener() {
            @Override
            public void onSelectedCardTap(CardNode cardNode) {
                layoutBottomPlayerCards();
            }

            @Override
            public void onDraggedCardReleased(CardNode cardNode) {
                if (mCardForAttackRequested) {
                    mCardForAttackRequested = false;

                    ResponseCardForAttackMessage responseCardForAttackMessage = new ResponseCardForAttackMessage(cardNode.getCard());
                    mGameServerConnector.sentMessageToServer(responseCardForAttackMessage);

                } else if (mRequestedRetaliation) {

                    //clamp to be released far from hand cards
                    if (cardNode.getPosition().getY() > getSceneSize().getY() * 0.7) {
                        layoutBottomPlayerCards();
                        return;
                    }

                    mRequestedRetaliation = false;
                    mHudNodesManager.setTakeButtonAttachedToScreen(false);

                    //TODO : For now we are "assuming" there is only one field pile always
                    List<List<Card>> list = new ArrayList<>();
                    Card cardOnFiled = mCardsScreenFragment.getCardsInPileWithIndex(5).iterator().next();
                    List<Card> innerList = new ArrayList<>();
                    innerList.add(cardNode.getCard());
                    innerList.add(cardOnFiled);
                    list.add(innerList);
                    ResponseRetaliatePilesMessage responseRetaliatePilesMessage = new ResponseRetaliatePilesMessage(list);
                    mGameServerConnector.sentMessageToServer(responseRetaliatePilesMessage);
                } else {
                    layoutBottomPlayerCards();
                }
            }
        });
    }


    @Override
    public void onSetActive() {
        super.onSetActive();
        mCardsTouchProcessor.register();
        mGameServerConnector.connect();
    }

    @Override
    public void onSetNotActive() {
        super.onSetNotActive();
        mCardsTouchProcessor.unRegister();
        mGameServerConnector.disconnect();
    }

    @Override
    protected void onAddNodesToScene() {
        super.onAddNodesToScene();

        //add card nodes
        for (YANTexturedNode cardNode : mCardsScreenFragment.getFragmentNodes()) {
            addNode(cardNode);
        }

        for (YANTexturedNode hudNode : mHudNodesManager.getFragmentNodes()) {
            addNode(hudNode);
        }

        mHudNodesManager.setBitoButtonAttachedToScreen(false);
        mHudNodesManager.setTakeButtonAttachedToScreen(false);
    }


    @Override
    protected void onLayoutNodes() {
        super.onLayoutNodes();

        mHudNodesManager.layoutNodes(getSceneSize());

        //layout avatars
        float offsetX = getSceneSize().getX() * 0.01f;
        float topOffset = getSceneSize().getY() * 0.07f;

        float aspectRatio = mUiAtlas.getTextureRegion("stump.png").getWidth() / mUiAtlas.getTextureRegion("stump.png").getHeight();
        float avatarWidth = getSceneSize().getX() * 0.2f;
        float avatarHeight = avatarWidth / aspectRatio;
        YANVector2 avatarSize = new YANVector2(avatarWidth, avatarHeight);

        //setup 3 points for player at right top
        float fanDistance = getSceneSize().getX() * 0.05f;

        YANVector2 pos = new YANVector2(getSceneSize().getX() - offsetX, topOffset);
        YANVector2 origin = new YANVector2(pos.getX() - avatarSize.getX(), pos.getY());
        YANVector2 leftBasis = new YANVector2(origin.getX(), origin.getY() + fanDistance);
        YANVector2 rightBasis = new YANVector2(origin.getX() - fanDistance, origin.getY());
        mThreePointFanLayouterTopRightPlayer.setThreePoints(origin, leftBasis, rightBasis);

        pos.setXY(offsetX, topOffset);

        //setup 3 points for player at left top
        origin = new YANVector2(pos.getX() /*+ avatar.getSize().getX()*/, pos.getY());
        leftBasis = new YANVector2(origin.getX() + fanDistance, origin.getY());
        rightBasis = new YANVector2(origin.getX(), origin.getY() + fanDistance);
        mThreePointFanLayouterTopLeft.setThreePoints(origin, leftBasis, rightBasis);

        //swap direction
        mThreePointFanLayouterTopLeft.setDirection(ThreePointFanLayouter.LayoutDirection.RTL);
    }


    @Override
    protected void onChangeNodesSize() {
        super.onChangeNodesSize();

        mCardsScreenFragment.setNodesSizes(getSceneSize());

        //set size of a card for touch processor
        mCardsTouchProcessor.setOriginalCardSize(/*mCardWidth*/mCardsScreenFragment.getCardNodeWidth(), mCardsScreenFragment.getCardNodeHeight());

        //init the player cards layouter
        mPlayerCardsLayouter.init(mCardsScreenFragment.getCardNodeWidth()/*mCardWidth*/, mCardsScreenFragment.getCardNodeHeight(),
                //maximum available width
                getSceneSize().getX(),
                //base x position ( center )
                getSceneSize().getX() / 2,
                //base y position
                getSceneSize().getY() - mFence.getSize().getY() / 2);

        mHudNodesManager.setNodesSizes(getSceneSize());
    }

    @Override
    protected void onCreateNodes() {
        super.onCreateNodes();
        mHudNodesManager.createNodes(mUiAtlas);
        mCardsScreenFragment.createNodes(mCardsAtlas);

        mHudNodesManager.setTakeButtonClickListener(new YANButtonNode.YanButtonNodeClickListener() {
            @Override
            public void onButtonClick() {

                YANLogger.log("Take Button Clicked");
                //TODO : here we are taking a cardNode
                //remove the flags
                if (mRequestedRetaliation) {
                    mRequestedRetaliation = false;

                    ResponseRetaliatePilesMessage responseRetaliatePilesMessage = new ResponseRetaliatePilesMessage(new ArrayList<List<Card>>());
                    mGameServerConnector.sentMessageToServer(responseRetaliatePilesMessage);

                    mHudNodesManager.setTakeButtonAttachedToScreen(false);
                }
            }
        });

        mHudNodesManager.setBitoButtonClickListener(new YANButtonNode.YanButtonNodeClickListener() {
            @Override
            public void onButtonClick() {
                //TODO : change the way retaliation is implemented
                YANLogger.log("Bito Button Clicked");
            }
        });


    }

    @Override
    public void onUpdate(float deltaTimeSeconds) {
        super.onUpdate(deltaTimeSeconds);
        mGameServerConnector.update();
        mCardsTweenAnimator.update(deltaTimeSeconds * 1);
        mHudNodesManager.update(deltaTimeSeconds);
        mCardsScreenFragment.update(deltaTimeSeconds);
    }


    private void handleInvalidRetaliationMessage(RetaliationInvalidProtocolMessage retaliationInvalidProtocolMessage) {
        //TODO : as long as we have only one pile we can just relayout player one piles.Later we will have to
        //search what should get back to player hand and what is not
        layoutBottomPlayerCards();
    }

    private void handlePlayerTakesActionMessage(PlayerTakesActionMessage playerTakesActionMessage) {
        int actionPlayerIndex = playerTakesActionMessage.getMessageData().getPlayerIndex();

        //since we don't have reference to players indexes in the game
        //we translating the player index to pile index
        int actionPlayerPileIndex = (actionPlayerIndex + 2) % 5;
        @IHudScreenFragment.HudNode int cockPosition = IHudScreenFragment.COCK_BOTTOM_RIGHT_INDEX;
        if (actionPlayerPileIndex == mCardsScreenFragment.getBottomPlayerPileIndex()) {
            cockPosition = IHudScreenFragment.COCK_BOTTOM_RIGHT_INDEX;
        } else if (actionPlayerPileIndex == mCardsScreenFragment.getTopRightPlayerPileIndex()) {
            cockPosition = IHudScreenFragment.COCK_TOP_RIGHT_INDEX;
        } else if (actionPlayerPileIndex == mCardsScreenFragment.getTopLeftPlayerPileIndex()) {
            cockPosition = IHudScreenFragment.COCK_TOP_LEFT_INDEX;
        }
        mHudNodesManager.resetCockAnimation(cockPosition);
    }

    private void handleGameSetupMessage(GameSetupProtocolMessage gameSetupProtocolMessage) {

        //TODO : get rid of the statics and make it more generic
        int bottomPlayerPileIndex = gameSetupProtocolMessage.getMessageData().getMyPileIndex();
        int topPlayerToTheRightPileIndex = (bottomPlayerPileIndex + 1) % 5;
        int topLeftPlayerToTheLeftPileIndex = (bottomPlayerPileIndex + 2) % 5;

        //TODO :load all pile indexes from server
        mCardsScreenFragment.setPilesIndexes(0, 1, bottomPlayerPileIndex, topPlayerToTheRightPileIndex, topLeftPlayerToTheLeftPileIndex);

        //extract trump card
        CardData cardData = gameSetupProtocolMessage.getMessageData().getTrumpCard();
        mCardsScreenFragment.setTrumpCard(new Card(cardData.getRank(), cardData.getSuit()));
        mCardsScreenFragment.layoutNodes(getSceneSize());

    }

    private void handleRequestRetaliatePilesMessage(RequestRetaliatePilesMessage requestRetaliatePilesMessage) {
        mRequestedRetaliation = true;

        //in that case we want the hud to present us with option to take the card
        mHudNodesManager.setTakeButtonAttachedToScreen(true);
    }

    private void handleRequestCardForAttackMessage(RequestCardForAttackMessage requestCardForAttackMessage) {
        mCardForAttackRequested = true;
    }

    private void handleCardMoveMessage(CardMovedProtocolMessage cardMovedMessage) {
        //extract data
        Card movedCard = new Card(cardMovedMessage.getMessageData().getMovedCard().getRank(), cardMovedMessage.getMessageData().getMovedCard().getSuit());
        int fromPile = cardMovedMessage.getMessageData().getFromPileIndex();
        int toPile = cardMovedMessage.getMessageData().getToPileIndex();

        //execute the move
        mCardsScreenFragment.moveCardFromPileToPile(movedCard, fromPile, toPile);
    }

    private void layoutTopLeftPlayerCards() {

        List<CardsLayouterSlotImpl> slots = new ArrayList<>(mCardsScreenFragment.getTopLeftPlayerCardNodes().size());
        for (int i = 0; i < mCardsScreenFragment.getTopLeftPlayerCardNodes().size(); i++) {
            slots.add(new CardsLayouterSlotImpl());
        }

        //layout the slots
        mThreePointFanLayouterTopLeft.layoutRowOfSlots(slots);

        //make the layouting
        for (int i = 0; i < slots.size(); i++) {
            CardsLayouterSlotImpl slot = slots.get(i);
            YANTexturedNode node = mCardsScreenFragment.getTopLeftPlayerCardNodes().get(i);
            node.setSortingLayer(slot.getSortingLayer());
            //make the animation
            mCardsTweenAnimator.animateCardToValues(node, slot.getPosition().getX(), slot.getPosition().getY(), slot.getRotation(), null);
            mCardsTweenAnimator.animateSize(node, mCardsScreenFragment.getCardNodeWidth() * CARD_SCALE_AMOUNT_OPPONENT, mCardsScreenFragment.getCardNodeHeight() * CARD_SCALE_AMOUNT_OPPONENT, 0.5f);
        }
    }

    private void layoutTopRightPlayerCards() {
        List<CardsLayouterSlotImpl> slots = new ArrayList<>(mCardsScreenFragment.getTopRightPlayerCardNodes().size());
        for (int i = 0; i < mCardsScreenFragment.getTopRightPlayerCardNodes().size(); i++) {
            slots.add(new CardsLayouterSlotImpl());
        }

        //layout the slots
        mThreePointFanLayouterTopRightPlayer.layoutRowOfSlots(slots);

        //make the layouting
        for (int i = 0; i < slots.size(); i++) {
            CardsLayouterSlotImpl slot = slots.get(i);
            YANTexturedNode node = mCardsScreenFragment.getTopRightPlayerCardNodes().get(i);
            node.setSortingLayer(slot.getSortingLayer());
            //make the animation
            mCardsTweenAnimator.animateCardToValues(node, slot.getPosition().getX(), slot.getPosition().getY(), slot.getRotation(), null);
            mCardsTweenAnimator.animateSize(node, mCardsScreenFragment.getCardNodeWidth() * CARD_SCALE_AMOUNT_OPPONENT, mCardsScreenFragment.getCardNodeHeight() * CARD_SCALE_AMOUNT_OPPONENT, 0.5f);
        }
    }

    private void layoutBottomPlayerCards() {
        //update layouter to recalculate positions
        mPlayerCardsLayouter.setActiveSlotsAmount(mCardsScreenFragment.getBottomPlayerCardNodes().size());

        //each index in nodes array corresponds to slot index
        for (int i = 0; i < mCardsScreenFragment.getBottomPlayerCardNodes().size(); i++) {
            CardNode card = mCardsScreenFragment.getBottomPlayerCardNodes().get(i);
            CardsLayoutSlot slot = mPlayerCardsLayouter.getSlotAtPosition(i);
            card.setSortingLayer(slot.getSortingLayer());
            mCardsTweenAnimator.animateCardToSlot(card, slot);
        }
    }
}