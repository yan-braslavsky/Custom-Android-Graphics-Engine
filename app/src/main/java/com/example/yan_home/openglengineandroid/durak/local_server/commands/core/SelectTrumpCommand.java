package com.example.yan_home.openglengineandroid.durak.local_server.commands.core;


import com.example.yan_home.openglengineandroid.durak.local_server.cards.Card;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.BaseSessionCommand;
import com.example.yan_home.openglengineandroid.durak.local_server.exceptions.EmptyPileException;

import java.util.List;

/**
 * Created by Yan-Home on 12/21/2014.
 */
public class SelectTrumpCommand extends BaseSessionCommand {

    private int mTrumpPileIndex;

    @Override
    public void execute() {
        List<Card> pile = getGameSession().getPilesStack().get(mTrumpPileIndex).getCardsInPile();
        if (pile.isEmpty()) {
            throw new EmptyPileException(mTrumpPileIndex);
        }

        //set the suit of the bottom card (which is a first card)
        String suit = pile.get(0).getSuit();
        getGameSession().setTrumpSuit(suit);
    }

    public void setTrumpPileIndex(int trumpPileIndex) {
        mTrumpPileIndex = trumpPileIndex;
    }
}
