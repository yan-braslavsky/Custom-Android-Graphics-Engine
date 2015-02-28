package com.example.yan_home.openglengineandroid.durak.local_server.commands.hooks.notifiers.unicast;


import com.example.yan_home.openglengineandroid.durak.local_server.commands.control.RetaliationValidationControlCommand;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.hooks.CommandHook;
import com.example.yan_home.openglengineandroid.durak.local_server.communication.connection.SocketClient;
import com.example.yan_home.openglengineandroid.durak.local_server.communication.protocol.data.CardData;
import com.example.yan_home.openglengineandroid.durak.local_server.communication.protocol.data.RetaliationSetData;
import com.example.yan_home.openglengineandroid.durak.local_server.communication.protocol.messages.RetaliationInvalidProtocolMessage;
import com.example.yan_home.openglengineandroid.durak.local_server.player.Player;
import com.example.yan_home.openglengineandroid.durak.local_server.player.RemotePlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yan-Home on 12/24/2014.
 */
public class RemoteClientsWrongCoverageNotifierUnicastHook implements CommandHook<RetaliationValidationControlCommand> {

    @Override
    public Class<RetaliationValidationControlCommand> getHookTriggerCommandClass() {
        return RetaliationValidationControlCommand.class;
    }

    @Override
    public void onHookTrigger(RetaliationValidationControlCommand hookCommand) {

        //obtain retaliated player
        Player retaliatedPlayer = hookCommand.getRetaliatedPlayer();

        //obtain remote client from the player
        SocketClient client;
        if (retaliatedPlayer instanceof RemotePlayer) {
            client = ((RemotePlayer) retaliatedPlayer).getRemoteClient();
        } else {
            //in case the player is not a remote player , we have nothing to do
            return;
        }

        //adapt data to protocol message
        List<RetaliationSetData> retaliationSetDataList = new ArrayList<>();
        for (RetaliationValidationControlCommand.ValidationDetails validationDetails : hookCommand.getFailedValidationsList()) {

            //add validation data to the array list
            retaliationSetDataList.add(new RetaliationSetData(new CardData(validationDetails.getCoveredCard().getRank(), validationDetails.getCoveredCard().getSuit()),
                    new CardData(validationDetails.getCoveringCard().getRank(), validationDetails.getCoveringCard().getSuit())));
        }

        //prepare json message
        String jsonMsg = new RetaliationInvalidProtocolMessage(retaliationSetDataList).toJsonString();

        //send message to client
        client.sendMessage(jsonMsg);
    }
}
