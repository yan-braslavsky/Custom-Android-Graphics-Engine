package com.example.yan_home.openglengineandroid.durak.local_server.commands.core;


import com.example.yan_home.openglengineandroid.durak.local_server.cards.Card;
import com.example.yan_home.openglengineandroid.durak.local_server.cards.Pile;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.BaseSessionCommand;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.custom.AddPileCommand;
import com.example.yan_home.openglengineandroid.durak.local_server.communication.connection.LocalClient;
import com.example.yan_home.openglengineandroid.durak.local_server.communication.connection.SocketClient;
import com.example.yan_home.openglengineandroid.durak.local_server.player.RemotePlayer;

import java.util.ArrayList;

/**
 * Created by Yan-Home on 12/22/2014.
 */
public class AddRemotePlayerCommand extends BaseSessionCommand {

    private SocketClient mRemoteClient;
    private RemotePlayer mAddedPlayer;

    @Override
    public void execute() {

        //add players
        //associate pile index with player
        mAddedPlayer = new RemotePlayer(getGameSession().getPlayers().size(), getGameSession(), getGameSession().getPilesStack().size(), mRemoteClient);

        //add players
        getGameSession().getPlayers().add(mAddedPlayer);

        //first we creating a pile for a player
        AddPileCommand addPileCommand = new AddPileCommand();

        //create pile and tag it as player pile
        Pile pile = new Pile();
        pile.addTag(Pile.PileTags.PLAYER_PILE_TAG);

        addPileCommand.setPile(pile);
        addPileCommand.setCards(new ArrayList<Card>());
        getGameSession().executeCommand(addPileCommand);
    }

    public void setRemoteClient(LocalClient remoteClient) {
        mRemoteClient = remoteClient;
    }

    public SocketClient getRemoteClient() {
        return mRemoteClient;
    }

    public RemotePlayer getAddedPlayer() {
        return mAddedPlayer;
    }
}