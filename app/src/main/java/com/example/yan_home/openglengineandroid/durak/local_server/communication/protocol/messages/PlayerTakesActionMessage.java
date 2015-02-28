package com.example.yan_home.openglengineandroid.durak.local_server.communication.protocol.messages;


import com.example.yan_home.openglengineandroid.durak.local_server.communication.protocol.BaseProtocolMessage;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Yan-Home on 12/24/2014.
 */
public class PlayerTakesActionMessage extends BaseProtocolMessage<PlayerTakesActionMessage.ProtocolMessageData> {

    public static final String MESSAGE_NAME = "playerTakesAction";

    /**
     * Defines an action that represents what player is currently doing
     */
    public enum PlayerAction {
        ATTACK, RETALIATION, THROW_IN
    }

    public PlayerTakesActionMessage(int playerIndex, PlayerAction action) {
        super();
        setMessageName(MESSAGE_NAME);
        setMessageData(new ProtocolMessageData(playerIndex, action.name()));
    }

    public static class ProtocolMessageData {

        @SerializedName("playerIndex")
        int mPlayerIndex;
        @SerializedName("action")
        String mAction;

        public ProtocolMessageData(int playerIndex, String action) {
            mPlayerIndex = playerIndex;
            mAction = action;
        }

        public int getPlayerIndex() {
            return mPlayerIndex;
        }

        public String getAction() {
            return mAction;
        }
    }
}
