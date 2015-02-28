package com.example.yan_home.openglengineandroid.durak.local_server.commands.core;


import com.example.yan_home.openglengineandroid.durak.local_server.cards.Card;
import com.example.yan_home.openglengineandroid.durak.local_server.cards.Pile;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.BaseSessionCommand;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.custom.AddPileCommand;
import com.example.yan_home.openglengineandroid.durak.local_server.player.BotPlayer;

import java.util.ArrayList;

/**
 * Created by Yan-Home on 12/22/2014.
 */
public class AddBotPlayerCommand extends BaseSessionCommand {
    @Override
    public void execute() {

        //add players
        //associate pile index with player
        BotPlayer player = new BotPlayer(getGameSession().getPlayers().size(), getGameSession(),getGameSession().getPilesStack().size());

        //add players
        getGameSession().getPlayers().add(player);

        //first we creating a pile for a player
        AddPileCommand addPileCommand = new AddPileCommand();

        //create pile and tag it as player pile
        Pile pile = new Pile();
        pile.addTag(Pile.PileTags.PLAYER_PILE_TAG);

        addPileCommand.setPile(pile);
        addPileCommand.setCards(new ArrayList<Card>());
        getGameSession().executeCommand(addPileCommand);
    }
}