package com.example.yan_home.openglengineandroid.durak.local_server.communication.protocol.messages;


import com.example.yan_home.openglengineandroid.durak.local_server.communication.protocol.BaseProtocolMessage;
import com.example.yan_home.openglengineandroid.durak.local_server.communication.protocol.data.CardData;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Yan-Home on 12/24/2014.
 */
public class CardMovedProtocolMessage extends BaseProtocolMessage<CardMovedProtocolMessage.ProtocolMessageData> {

    public static final String MESSAGE_NAME = "cardMoved";

    public CardMovedProtocolMessage(String cardRank, String cardSuit, int movedFromPile, int movedToPile) {
        super();
        setMessageName(MESSAGE_NAME);
        setMessageData(new ProtocolMessageData(new CardData(cardRank, cardSuit), movedFromPile, movedToPile));
    }

    public static class ProtocolMessageData {

        @SerializedName("movedCard")
        CardData mMovedCard;
        @SerializedName("fromPileIndex")
        int mFromPileIndex;
        @SerializedName("toPileIndex")
        int mToPileIndex;

        public ProtocolMessageData(CardData movedCard, int fromPileIndex, int toPileIndex) {
            mMovedCard = movedCard;
            mFromPileIndex = fromPileIndex;
            mToPileIndex = toPileIndex;
        }
    }
}
