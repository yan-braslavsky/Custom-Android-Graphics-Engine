package com.example.yan_home.openglengineandroid.durak.local_server.commands;


import com.example.yan_home.openglengineandroid.durak.local_server.game.GameSession;

/**
 * Created by ybra on 19.12.2014.
 * <p/>
 * Base implementation of the SessionCommand
 */
public abstract class BaseSessionCommand implements SessionCommand {
    private GameSession mGameSession;

    @Override
    public void setGameSession(GameSession gameSession)
    {
        mGameSession = gameSession;
    }

    public GameSession getGameSession() {
        return mGameSession;
    }
}
