package com.example.yan_home.openglengineandroid.durak.local_server.commands.hooks.notifiers.unicast;


import com.example.yan_home.openglengineandroid.durak.local_server.cards.Card;
import com.example.yan_home.openglengineandroid.durak.local_server.cards.Pile;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.core.AddRemotePlayerCommand;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.hooks.CommandHook;
import com.example.yan_home.openglengineandroid.durak.local_server.communication.connection.SocketClient;
import com.example.yan_home.openglengineandroid.durak.local_server.communication.protocol.messages.GameSetupProtocolMessage;
import com.example.yan_home.openglengineandroid.durak.local_server.player.RemotePlayer;

import java.util.List;

/**
 * Created by Yan-Home on 12/24/2014.
 */
public class RemoteClientsGameSetupUnicastHook implements CommandHook<AddRemotePlayerCommand> {


    @Override
    public Class<AddRemotePlayerCommand> getHookTriggerCommandClass() {
        return AddRemotePlayerCommand.class;
    }

    @Override
    public void onHookTrigger(AddRemotePlayerCommand hookCommand) {

        RemotePlayer addedPlayer = hookCommand.getAddedPlayer();

        //obtain remote client from the added player
        SocketClient client = addedPlayer.getRemoteClient();

        //obtain trump card
        List<Card> cardsInStockPile = hookCommand.getGameSession().findPileByTag(Pile.PileTags.STOCK_PILE_TAG).getCardsInPile();

        //get the trump card
        Card trumpCard = cardsInStockPile.get(0);

        //prepare game setup message
        String jsonMsg = new GameSetupProtocolMessage(addedPlayer.getPileIndex(), trumpCard.getRank(), trumpCard.getSuit()).toJsonString();

        //send game setup message to client
        client.sendMessage(jsonMsg);
    }
}
