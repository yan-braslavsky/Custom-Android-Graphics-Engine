package com.example.yan_home.openglengineandroid.durak.local_server.validation;


import com.example.yan_home.openglengineandroid.durak.local_server.game.GameSession;

/**
 * Created by Yan-Home on 1/22/2015.
 */
public class GameSessionValidations {

    /**
     * Validates that given pile index exists in provided game session
     *
     * @param pileIndex   index of pile to validate
     * @param gameSession game session to validate pile index in
     * @return true if pile exists , false otherwise
     */
    public static boolean validatePilesExist(int pileIndex, GameSession gameSession) {
        return (pileIndex < 0 || pileIndex >= gameSession.getPilesStack().size()) ? false : true;
    }
}
