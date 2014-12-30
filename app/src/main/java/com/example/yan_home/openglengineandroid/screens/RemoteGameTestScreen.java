package com.example.yan_home.openglengineandroid.screens;

import com.example.yan_home.openglengineandroid.communication.socket.SocketConnectionManager;
import com.example.yan_home.openglengineandroid.entities.cards.Card;
import com.example.yan_home.openglengineandroid.entities.cards.CardsHelper;
import com.example.yan_home.openglengineandroid.input.cards.CardsTouchProcessor;
import com.example.yan_home.openglengineandroid.layouting.CardsLayoutSlot;
import com.example.yan_home.openglengineandroid.layouting.CardsLayouter;
import com.example.yan_home.openglengineandroid.layouting.impl.PlayerCardsLayouter;
import com.example.yan_home.openglengineandroid.layouting.threepoint.ThreePointFanLayouter;
import com.example.yan_home.openglengineandroid.protocol.BaseProtocolMessage;
import com.example.yan_home.openglengineandroid.protocol.messages.CardMovedProtocolMessage;
import com.example.yan_home.openglengineandroid.tweening.CardsTweenAnimator;
import com.google.gson.Gson;
import com.yan.glengine.assets.atlas.YANTextureRegion;
import com.yan.glengine.nodes.YANTexturedNode;
import com.yan.glengine.renderer.YANGLRenderer;
import com.yan.glengine.util.geometry.YANVector2;
import com.yan.glengine.util.math.YANMathUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yan-Home on 10/3/2014.
 */
public class RemoteGameTestScreen extends BaseGameScreen {

    //connection details
    public static final String SERVER_ADDRESS = "192.168.1.100";
    public static final int SERVER_PORT = 7000;

    private static final int CARDS_COUNT = 36;
    private static final int MAX_CARDS_IN_LINE = 8;

    //pile indices (it is hard coded to have 3 players)
    public static final int STOCK_PILE_INDEX = 0;
    public static final int DISCARD_PILE_INDEX = 1;
    public static final int PLAYER_ONE_PILE_INDEX = 2;
    public static final int PLAYER_TWO_PILE_INDEX = 3;
    public static final int PLAYER_THREE_PILE_INDEX = 4;

    //Player hand related
    private CardsLayouter mPlayerCardsLayouter;
    private CardsTouchProcessor mCardsTouchProcessor;

    private ThreePointFanLayouter mThreePointFanLayouterPlayerTwo;
    private ThreePointFanLayouter mThreePointFanLayouterPlayerThree;

    /**
     * Mapping between logical cards representation and cards textures
     */
    private Map<Card, YANTexturedNode> mCardNodesMap;


    private CardsTweenAnimator mCardsTweenAnimator;
    private YANTexturedNode mBackOfCardNode;

    private int mTopCardOnFieldSortingLayer = HIGHEST_SORTING_LAYER;

    /**
     * Mapping between pile index and array of cards in it
     */
    private Map<Integer, ArrayList<Card>> mPileIndexToCardListMap;

    /**
     * Mapping between pile index and position of the pile
     */
    private Map<Integer, YANVector2> mPileIndexToPositionMap;

    /**
     * Maps card to texture for easier change from back to front
     */
    private Map<Card, YANTextureRegion> mCardToTextureMap;

    //cached card dimensions
    private float mCardWidth;
    private float mCardHeight;

    //cached Gson
    private Gson mGson;

    //cached player texture nodes of cards
    private ArrayList<YANTexturedNode> mPlayerOneTextureNodeCards;
    private ArrayList<YANTexturedNode> mPlayerTwoTextureNodeCards;
    private ArrayList<YANTexturedNode> mPlayerThreeTextureNodeCards;


    private ArrayList<YANTexturedNode> mAvatarPlaceHoldersArray;

    public RemoteGameTestScreen(YANGLRenderer renderer) {
        super(renderer);
        mCardNodesMap = new HashMap<>(CARDS_COUNT);
        mPileIndexToCardListMap = new HashMap<>(CARDS_COUNT / 2);
        mPileIndexToPositionMap = new HashMap<>(CARDS_COUNT / 2);
        mCardsTweenAnimator = new CardsTweenAnimator();
        mGson = new Gson();

        //array holds avatars for each player
        mAvatarPlaceHoldersArray = new ArrayList<>();

        //init 3 points layouter to create a fan of opponents hands
        mThreePointFanLayouterPlayerTwo = new ThreePointFanLayouter();
        mThreePointFanLayouterPlayerThree = new ThreePointFanLayouter();

        //init player cards layouter
        mPlayerCardsLayouter = new PlayerCardsLayouter(CARDS_COUNT);

        //allocate temp array of texture cards
        mPlayerOneTextureNodeCards = new ArrayList<>(36);
        mPlayerTwoTextureNodeCards = new ArrayList<>(36);
        mPlayerThreeTextureNodeCards = new ArrayList<>(36);
        mCardToTextureMap = new HashMap<>(36);

        //currently we are initializing with empty array , cards will be set every time player pile content changes
        mCardsTouchProcessor = new CardsTouchProcessor(mPlayerOneTextureNodeCards, mCardsTweenAnimator);

        //set listener to handle touches
        mCardsTouchProcessor.setCardsTouchProcessorListener(new CardsTouchProcessor.CardsTouchProcessorListener() {
            @Override
            public void onSelectedCardTap(YANTexturedNode card) {
            }

            @Override
            public void onDraggedCardReleased(YANTexturedNode card) {

                //TODO : send real messages to server when card is released
                layoutPlayerOneCards();
            }
        });
    }


    @Override
    public void onSetActive() {
        super.onSetActive();
        mCardsTouchProcessor.register();
        SocketConnectionManager.getInstance().connectToRemoteServer(SERVER_ADDRESS, SERVER_PORT);
    }

    @Override
    public void onSetNotActive() {
        super.onSetNotActive();
        mCardsTouchProcessor.unRegister();
        SocketConnectionManager.getInstance().disconnectFromRemoteServer();
    }

    @Override
    protected void onAddNodesToScene() {
        super.onAddNodesToScene();

        for (YANTexturedNode texturedNode : mCardNodesMap.values()) {
            addNode(texturedNode);
        }

        for (YANTexturedNode avatar : mAvatarPlaceHoldersArray) {
            addNode(avatar);
        }
    }


    @Override
    protected void onLayoutNodes() {
        super.onLayoutNodes();

        //stock pile
        layoutPile(STOCK_PILE_INDEX, (getSceneSize().getX() - mCardWidth) / 2, 0);

        //discard pile (off the screen)
        layoutPile(DISCARD_PILE_INDEX, -getSceneSize().getX(), getSceneSize().getY() / 2);

        //player one pile (bottom middle)
        layoutPile(PLAYER_ONE_PILE_INDEX, (getSceneSize().getX() - mCardWidth) / 2, getSceneSize().getY() - mCardHeight);

        //player two pile (top right)
        layoutPile(PLAYER_TWO_PILE_INDEX, (getSceneSize().getX() - mCardWidth), 0);

        //player three pile (top left)
        layoutPile(PLAYER_THREE_PILE_INDEX, 0, 0);


        float leftBorderX = getSceneSize().getX() * 0.3f;
        float rightBorderX = getSceneSize().getX() * 0.7f;

        float leftBorderY = getSceneSize().getY() * 0.3f;
        float rightBorderY = getSceneSize().getY() * 0.5f;

        //init "field piles" positions
        for (int i = (PLAYER_THREE_PILE_INDEX + 1); i < CARDS_COUNT / 2; i++) {
            float x = YANMathUtils.randomInRange(leftBorderX, rightBorderX);
            float y = YANMathUtils.randomInRange(leftBorderY, rightBorderY);
            mPileIndexToPositionMap.put(i, new YANVector2(x, y));
        }

        //layout avatars
        float offsetX = getSceneSize().getX() * 0.01f;

        //first player
        YANTexturedNode avatar = mAvatarPlaceHoldersArray.get(0);
        avatar.setAnchorPoint(1f, 1f);
        avatar.setSortingLayer(HIGHEST_SORTING_LAYER + 1);
        avatar.setPosition(getSceneSize().getX() - offsetX, getSceneSize().getY() - offsetX);

        //second player
        float topOffset = getSceneSize().getY() * 0.07f;
        avatar = mAvatarPlaceHoldersArray.get(1);
        avatar.setAnchorPoint(1f, 0f);
        avatar.setSortingLayer(HIGHEST_SORTING_LAYER + 1);
        avatar.setPosition(getSceneSize().getX() - offsetX, topOffset);

        //setup 3 points for player 2
        float fanDistance = getSceneSize().getX() * 0.05f;
        YANVector2 origin = new YANVector2(avatar.getPosition().getX() - avatar.getSize().getX(), avatar.getPosition().getY());
        YANVector2 leftBasis = new YANVector2(origin.getX() /*- fanDistance / 2*/, origin.getY() + fanDistance);
        YANVector2 rightBasis = new YANVector2(origin.getX() - fanDistance, origin.getY() /*+ fanDistance / 2*/);
        mThreePointFanLayouterPlayerTwo.setThreePoints(origin, leftBasis, rightBasis);

        //third player
        avatar = mAvatarPlaceHoldersArray.get(2);
        avatar.setAnchorPoint(0f, 0f);
        avatar.setSortingLayer(HIGHEST_SORTING_LAYER + 1);
        avatar.setPosition(offsetX, topOffset);

        //setup 3 points for player 3
        origin = new YANVector2(avatar.getPosition().getX(), avatar.getPosition().getY());
        rightBasis = new YANVector2(origin.getX() /*- fanDistance / 2*/, origin.getY() + fanDistance);
        leftBasis = new YANVector2(origin.getX() + fanDistance, origin.getY() /*+ fanDistance / 2*/);
        mThreePointFanLayouterPlayerThree.setThreePoints(origin, leftBasis, rightBasis);

    }

    private void layoutPile(int pileIndex, float x, float y) {
        ArrayList<Card> cardsInStockPile = mPileIndexToCardListMap.get(pileIndex);
        for (Card card : cardsInStockPile) {
            YANTexturedNode cardTexturedNode = mCardNodesMap.get(card);
            cardTexturedNode.setPosition(x, y);
            cardTexturedNode.setRotation(90);
        }

        //init pile position
        mPileIndexToPositionMap.put(pileIndex, new YANVector2(x, y));
    }


    @Override
    protected void onChangeNodesSize() {
        super.onChangeNodesSize();

        //cards
        float aspectRatio = mBackOfCardNode.getTextureRegion().getWidth() / mBackOfCardNode.getTextureRegion().getHeight();
        mCardWidth = Math.min(getSceneSize().getX(), getSceneSize().getY()) / (float) ((MAX_CARDS_IN_LINE) / 2);
        mCardHeight = mCardWidth / aspectRatio;

        //set size of a card for touch processor
        mCardsTouchProcessor.setOriginalCardSize(mCardWidth, mCardHeight);

        //set size for each card
        for (YANTexturedNode node : mCardNodesMap.values()) {
            node.setSize(mCardWidth, mCardHeight);
        }

        //init the player cards layouter
        mPlayerCardsLayouter.init(mCardWidth, mCardHeight,
                //maximum available width
                getSceneSize().getX(),
                //base x position ( center )
                getSceneSize().getX() / 2,
                //base y position
                getSceneSize().getY() - mFence.getSize().getY() / 2);

        //set avatars sizes
        YANTexturedNode avatar = mAvatarPlaceHoldersArray.get(0);
        aspectRatio = avatar.getTextureRegion().getWidth() / avatar.getTextureRegion().getHeight();
        float newWidth = getSceneSize().getX() * 0.2f;
        float newHeight = newWidth / aspectRatio;
        for (YANTexturedNode node : mAvatarPlaceHoldersArray) {
            node.setSize(newWidth, newHeight);
        }
    }

    @Override
    protected void onCreateNodes() {
        super.onCreateNodes();
        mBackOfCardNode = new YANTexturedNode(mAtlas.getTextureRegion("cards_back.png"));
        initCardsMap();

        //add 3 avatars for 3 players
        for (int i = 0; i < 3; i++) {
            mAvatarPlaceHoldersArray.add(new YANTexturedNode(mAtlas.getTextureRegion("stump.png")));
        }
    }

    private void initCardsMap() {
        ArrayList<Card> cardEntities = CardsHelper.create36Deck();
        for (Card card : cardEntities) {
            String name = "cards_" + card.getSuit() + "_" + card.getRank() + ".png";
            YANTexturedNode texturedNode = new YANTexturedNode(mAtlas.getTextureRegion(name));
            mCardNodesMap.put(card, texturedNode);
            mCardToTextureMap.put(card, texturedNode.getTextureRegion());

            //hide the card
            texturedNode.setTextureRegion(mBackOfCardNode.getTextureRegion());
        }

        //put everything in the stock pile
        mPileIndexToCardListMap.put(STOCK_PILE_INDEX, cardEntities);

        //init rest of a piles
        mPileIndexToCardListMap.put(DISCARD_PILE_INDEX, new ArrayList<Card>(CARDS_COUNT));
        mPileIndexToCardListMap.put(PLAYER_ONE_PILE_INDEX, new ArrayList<Card>(CARDS_COUNT));
        mPileIndexToCardListMap.put(PLAYER_TWO_PILE_INDEX, new ArrayList<Card>(CARDS_COUNT));
        mPileIndexToCardListMap.put(PLAYER_THREE_PILE_INDEX, new ArrayList<Card>(CARDS_COUNT));

        //init "field piles" ( can be no more than 2 cards)
        for (int i = (PLAYER_THREE_PILE_INDEX + 1); i < CARDS_COUNT / 2; i++) {
            mPileIndexToCardListMap.put(i, new ArrayList<Card>(2));
        }

    }

    @Override
    public void onUpdate(float deltaTimeSeconds) {
        super.onUpdate(deltaTimeSeconds);

        //read messages from remote socket server
        readMessageFromServer();
        mCardsTweenAnimator.update(deltaTimeSeconds * 1);
    }

    private void readMessageFromServer() {
        if (SocketConnectionManager.getInstance().isConnected()) {
            String msg = SocketConnectionManager.getInstance().readMessageFromRemoteServer();
            if (msg != null) {
                handleServerMessage(msg);
            }
        }
    }

    private void handleServerMessage(String msg) {

        BaseProtocolMessage message = mGson.fromJson(msg, CardMovedProtocolMessage.class);

        //TODO : this is not an efficient way to handle messages
        if (message.getMessageName().equals(CardMovedProtocolMessage.MESSAGE_NAME)) {
            CardMovedProtocolMessage cardMovedMessage = mGson.fromJson(msg, CardMovedProtocolMessage.class);
            handleCardMoveMessage(cardMovedMessage);
        }
    }

    private void handleCardMoveMessage(CardMovedProtocolMessage cardMovedMessage) {

        //extract data
        Card movedCard = new Card(cardMovedMessage.getMessageData().getMovedCard().getRank(), cardMovedMessage.getMessageData().getMovedCard().getSuit());
        int fromPile = cardMovedMessage.getMessageData().getFromPileIndex();
        int toPile = cardMovedMessage.getMessageData().getToPileIndex();

        //execute the move
        moveCardFromPileToPile(movedCard, fromPile, toPile);

        //check if card goes to or from player 1 pile
        if (toPile == PLAYER_ONE_PILE_INDEX || fromPile == PLAYER_ONE_PILE_INDEX) {

            if (toPile == PLAYER_ONE_PILE_INDEX) {
                mPlayerOneTextureNodeCards.add(mCardNodesMap.get(movedCard));
            } else {
                mPlayerOneTextureNodeCards.remove(mCardNodesMap.get(movedCard));
            }

            layoutPlayerOneCards();
        }

//        //player 2
//        else if (toPile == PLAYER_TWO_PILE_INDEX || fromPile == PLAYER_TWO_PILE_INDEX) {
//            if (toPile == PLAYER_TWO_PILE_INDEX) {
//                mPlayerTwoTextureNodeCards.add(mCardNodesMap.get(movedCard));
//            } else {
//                mPlayerTwoTextureNodeCards.remove(mCardNodesMap.get(movedCard));
//            }
//
//            //TODO : cache slots for efficiency
//            List<CardsLayouterSlotImpl> slots = new ArrayList<>(mPlayerTwoTextureNodeCards.size());
//            for (int i = 0; i < mPlayerTwoTextureNodeCards.size(); i++) {
//                slots.add(new CardsLayouterSlotImpl());
//            }
//
//            //layout the slots
//            mThreePointFanLayouterPlayerTwo.layoutRowOfSlots(slots);
//
//            //make the layouting
//            for (int i = 0; i < slots.size(); i++) {
//                CardsLayouterSlotImpl slot = slots.get(i);
//                YANTexturedNode node = mPlayerTwoTextureNodeCards.get(i);
//                //make the animation
//                mCardsTweenAnimator.animateCardToValues(node, slot.getPosition().getX(), slot.getPosition().getY(), mCardWidth * 0.7f, mCardHeight * 0.7f, slot.getRotation(), null);
//            }
//        }

//        //player 3
//        else if (toPile == PLAYER_THREE_PILE_INDEX || fromPile == PLAYER_THREE_PILE_INDEX) {
//            if (toPile == PLAYER_THREE_PILE_INDEX) {
//                mPlayerThreeTextureNodeCards.add(mCardNodesMap.get(movedCard));
//            } else {
//                mPlayerThreeTextureNodeCards.remove(mCardNodesMap.get(movedCard));
//            }
//
//            List<CardsLayouterSlotImpl> slots = new ArrayList<>(mPlayerThreeTextureNodeCards.size());
//            for (int i = 0; i < mPlayerThreeTextureNodeCards.size(); i++) {
//                slots.add(new CardsLayouterSlotImpl());
//            }
//
//            //layout the slots
//            mThreePointFanLayouterPlayerThree.layoutRowOfSlots(slots);
//
//            //make the layouting
//            for (int i = 0; i < slots.size(); i++) {
//                CardsLayouterSlotImpl slot = slots.get(i);
//                YANTexturedNode node = mPlayerThreeTextureNodeCards.get(i);
//                //make the animation
//                mCardsTweenAnimator.animateCardToValues(node, slot.getPosition().getX(), slot.getPosition().getY(), slot.getRotation(), null);
//                mCardsTweenAnimator.animateSize(node, mCardWidth * 0.7f, mCardHeight * 0.7f, 0.5f);
//            }
//        }

        else {
            //set the sorting layer higher
            mTopCardOnFieldSortingLayer++;
            mCardNodesMap.get(movedCard).setSortingLayer(mTopCardOnFieldSortingLayer);
        }
    }

    private void layoutPlayerOneCards() {
        //update layouter to recalculate positions
        mPlayerCardsLayouter.setActiveSlotsAmount(mPlayerOneTextureNodeCards.size());

        //each index in nodes array corresponds to slot index
        for (int i = 0; i < mPlayerOneTextureNodeCards.size(); i++) {
            YANTexturedNode card = mPlayerOneTextureNodeCards.get(i);
            CardsLayoutSlot slot = mPlayerCardsLayouter.getSlotAtPosition(i);
            card.setSortingLayer(slot.getSortingLayer());
            mCardsTweenAnimator.animateCardToSlot(card, slot);
        }
    }

    private void moveCardFromPileToPile(Card movedCard, int fromPile, int toPile) {

        //remove card entity from its current pile
        mPileIndexToCardListMap.get(fromPile).remove(movedCard);

        //add card entity to its new pile
        mPileIndexToCardListMap.get(toPile).add(movedCard);

        //find node
        YANTexturedNode txtrNode = mCardNodesMap.get(movedCard);

        //find destination position
        float destX = mPileIndexToPositionMap.get(toPile).getX();
        float destY = mPileIndexToPositionMap.get(toPile).getY();
        float destRotation = YANMathUtils.randomInRange(-70, 70);

        //make the animation
        mCardsTweenAnimator.animateCardToValues(txtrNode, destX, destY, destRotation, null);
        mCardsTweenAnimator.animateSize(txtrNode, mCardWidth, mCardHeight, 0.5f);

        if (fromPile == PLAYER_ONE_PILE_INDEX || toPile == PLAYER_ONE_PILE_INDEX || toPile > PLAYER_THREE_PILE_INDEX || toPile == DISCARD_PILE_INDEX) {
            //show the card
            txtrNode.setTextureRegion(mCardToTextureMap.get(movedCard));
        } else {
            //hide the card
            txtrNode.setTextureRegion(mBackOfCardNode.getTextureRegion());
        }

    }

}