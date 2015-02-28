package com.example.yan_home.openglengineandroid.durak.local_server.commands.core;


import com.example.yan_home.openglengineandroid.durak.local_server.cards.Pile;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.BaseSessionCommand;

import java.util.Collections;

/**
 * Created by ybra on 19.12.2014.
 */
public class ShufflePileAtPositionCommand extends BaseSessionCommand {

    private int mPilePosition;


    public void setPilePosition(int pilePosition) {
        mPilePosition = pilePosition;
    }

    @Override
    public void execute() {
        Pile pile = getGameSession().getPilesStack().get(mPilePosition);
        if (pile == null) {
            throw new RuntimeException("pile at index " + mPilePosition + " is not found");
        }
        Collections.shuffle(pile.getCardsInPile());
    }
}
