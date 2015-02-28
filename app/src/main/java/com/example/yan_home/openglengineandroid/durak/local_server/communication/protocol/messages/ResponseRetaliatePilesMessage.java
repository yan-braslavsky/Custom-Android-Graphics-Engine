package com.example.yan_home.openglengineandroid.durak.local_server.communication.protocol.messages;


import com.example.yan_home.openglengineandroid.durak.local_server.cards.Pile;
import com.example.yan_home.openglengineandroid.durak.local_server.communication.protocol.BaseProtocolMessage;
import com.example.yan_home.openglengineandroid.durak.local_server.communication.protocol.data.CardData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Yan-Home on 12/24/2014.
 */
public class ResponseRetaliatePilesMessage extends BaseProtocolMessage<ResponseRetaliatePilesMessage.ProtocolMessageData> {

    public static final String MESSAGE_NAME = "responseRetaliatePilesMessage";

    public ResponseRetaliatePilesMessage(List<Pile> pilesAfterRetaliation) {
        super();
        setMessageName(MESSAGE_NAME);
        List<List<CardData>> piles = convertCardDataList(pilesAfterRetaliation);
        setMessageData(new ProtocolMessageData(piles));
    }

    private List<List<CardData>> convertCardDataList(List<Pile> pilesAfterRetaliation) {
        throw new UnsupportedOperationException();
    }

    public static class ProtocolMessageData {
        @SerializedName("pilesAfterRetaliation")
        List<List<CardData>> mPilesAfterRetaliation;
        public ProtocolMessageData(List<List<CardData>> piles) {
            mPilesAfterRetaliation = piles;
        }
        public List<List<CardData>> getPilesAfterRetaliation() {
            return mPilesAfterRetaliation;
        }
    }
}