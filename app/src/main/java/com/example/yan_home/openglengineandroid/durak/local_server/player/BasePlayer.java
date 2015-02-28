package com.example.yan_home.openglengineandroid.durak.local_server.player;


import com.example.yan_home.openglengineandroid.durak.local_server.cards.Card;
import com.example.yan_home.openglengineandroid.durak.local_server.cards.Pile;
import com.example.yan_home.openglengineandroid.durak.local_server.game.GameSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Yan-Home on 12/30/2014.
 */
public abstract class BasePlayer implements Player {

    protected final GameSession mGameSession;
    protected int mIndexInGame;
    protected int mPileIndex;

    public BasePlayer(int indexInGame, GameSession gameSession, int pileIndex) {
        mIndexInGame = indexInGame;
        mGameSession = gameSession;
        mPileIndex = pileIndex;
    }

    @Override
    public int getGameIndex() {
        return mIndexInGame;
    }

    @Override
    public int getPileIndex() {
        return mPileIndex;
    }

    /**
     * Brings together cards from player hand that can be throwed in.
     *
     * @param allowedRanksToThrowIn all ranks that can be throwed in.
     * @return array of cards.
     */
    public List<Card> getPossibleThrowInCards(Collection<String> allowedRanksToThrowIn) {

        List<Card> retList = new ArrayList<>();

        Pile playerPile = mGameSession.getPilesStack().get(getPileIndex());

        //just simply add every possible rank
        for (Card cardInHand : playerPile.getCardsInPile()) {
            if (allowedRanksToThrowIn.contains(cardInHand.getRank())) {
                retList.add(cardInHand);
            }
        }

        return retList;
    }


}
