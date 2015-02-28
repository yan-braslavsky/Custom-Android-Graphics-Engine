package com.example.yan_home.openglengineandroid.durak.local_server.commands.core;


import com.example.yan_home.openglengineandroid.durak.local_server.commands.BaseSessionCommand;

/**
 * Created by Yan-Home on 12/22/2014.
 * <p/>
 * This command discards any state changes on the game , and resets the game
 * to its default initial state.
 */
public class ResetGameDataCommand extends BaseSessionCommand {
    @Override
    public void execute() {
        //clear all players
        getGameSession().getPlayers().clear();

        //clear all pending commands
        getGameSession().getPendingCommandsQueue().clear();

        //clear all executed commands
        getGameSession().getExecutedCommandsStack().clear();

        //clear all piles
        getGameSession().getPilesStack().clear();

        //set trump to null
        getGameSession().setTrumpSuit(null);
    }
}
