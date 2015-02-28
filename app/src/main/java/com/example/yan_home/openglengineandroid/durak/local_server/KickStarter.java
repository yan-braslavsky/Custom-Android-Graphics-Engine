package com.example.yan_home.openglengineandroid.durak.local_server;


import com.example.yan_home.openglengineandroid.durak.local_server.commands.custom.startgame.ScriptedPlayerWithBotsGameStartCommand;
import com.example.yan_home.openglengineandroid.durak.local_server.game.GameRules;
import com.example.yan_home.openglengineandroid.durak.local_server.game.GameSession;

/**
 * Created by ybra on 19.12.2014.
 * <p/>
 * Temporary class that binds logic together
 * Later planned to be removed and replaced by more sophisticated system,
 */
public class KickStarter {
    private final GameSession mGameSession;

    public KickStarter() {
        mGameSession = new GameSession();
        mGameSession.setGameRules(new GameRules());
        mGameSession.addCommand(new ScriptedPlayerWithBotsGameStartCommand());
    }

    public void start() {
        mGameSession.startSession();
    }
}