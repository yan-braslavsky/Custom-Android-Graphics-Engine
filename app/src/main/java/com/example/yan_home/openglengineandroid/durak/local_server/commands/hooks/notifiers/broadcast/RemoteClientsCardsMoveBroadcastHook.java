package com.example.yan_home.openglengineandroid.durak.local_server.commands.hooks.notifiers.broadcast;


import com.example.yan_home.openglengineandroid.durak.local_server.commands.core.MoveCardFromPileToPileCommand;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.hooks.CommandHook;
import com.example.yan_home.openglengineandroid.durak.local_server.communication.connection.RemoteClientsManager;
import com.example.yan_home.openglengineandroid.durak.local_server.communication.connection.SocketClient;
import com.example.yan_home.openglengineandroid.durak.local_server.communication.protocol.messages.CardMovedProtocolMessage;

/**
 * Created by Yan-Home on 12/24/2014.
 */
public class RemoteClientsCardsMoveBroadcastHook implements CommandHook<MoveCardFromPileToPileCommand> {

    @Override
    public Class<MoveCardFromPileToPileCommand> getHookTriggerCommandClass() {
        return MoveCardFromPileToPileCommand.class;
    }

    @Override
    public void onHookTrigger(MoveCardFromPileToPileCommand hookCommand) {
        String jsonMsg = new CardMovedProtocolMessage(hookCommand.getCardToMove().getRank(), hookCommand.getCardToMove().getSuit(), hookCommand.getFromPileIndex(), hookCommand.getToPileIndex()).toJsonString();

        for (SocketClient client : RemoteClientsManager.getInstance().getRemoteClients()) {
            client.sendMessage(jsonMsg);
        }

        //TODO : imitate waiting
        try {
            Thread.sleep(600);
//            Thread.sleep(1600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
