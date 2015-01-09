package com.example.yan_home.openglengineandroid.protocol.messages;

import com.example.yan_home.openglengineandroid.protocol.BaseProtocolMessage;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Yan-Home on 12/24/2014.
 */
public class GameSetupProtocolMessage extends BaseProtocolMessage<GameSetupProtocolMessage.ProtocolMessageData> {

    public static final String MESSAGE_NAME = "gameSetup";

    /**
     * @param playerPileIndex index of a pile that belongs to relieving player
     */
    public GameSetupProtocolMessage(int playerPileIndex) {
        super();
        setMessageName(MESSAGE_NAME);
        setMessageData(new ProtocolMessageData(playerPileIndex));
    }

    public static class ProtocolMessageData {

        //TODO : in future perhaps we will need more information

        @SerializedName("myPileIndex")
        int mMyPileIndex;

        public ProtocolMessageData(int myPileIndex) {
            mMyPileIndex = myPileIndex;
        }

        public int getMyPileIndex() {
            return mMyPileIndex;
        }
    }
}
