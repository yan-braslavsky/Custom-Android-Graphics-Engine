package com.example.yan_home.openglengineandroid.durak.local_server.commands;


import com.example.yan_home.openglengineandroid.durak.local_server.game.GameSession;

/**
 * Created by ybra on 19.12.2014.
 *
 * Defines the command that will be executed on game session
 */
public interface SessionCommand {

    public void setGameSession(GameSession gameSession);
    public void execute();
}
