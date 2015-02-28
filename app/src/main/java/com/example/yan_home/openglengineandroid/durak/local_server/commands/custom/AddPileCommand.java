package com.example.yan_home.openglengineandroid.durak.local_server.commands.custom;


import com.example.yan_home.openglengineandroid.durak.local_server.cards.Card;
import com.example.yan_home.openglengineandroid.durak.local_server.cards.Pile;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.BaseSessionCommand;

import java.util.Collection;

/**
 * Created by ybra on 19.12.2014.
 * <p/>
 * Adds given pile fills it with given cards and pushes it into the
 * Stack of piles in game session.
 * <p/>
 * Can be used for stock pile initialization , or users hands initialization.
 */
public class AddPileCommand extends BaseSessionCommand {

    private Pile mPile;
    private Collection<Card> mCardsCollection;

    public void setPile(Pile pile) {
        mPile = pile;
    }

    public void setCards(Collection<Card> cardsCollection) {
        mCardsCollection = cardsCollection;
    }

    @Override
    public void execute() {
        for (Card card : mCardsCollection) {
            mPile.addCardToPile(card);
        }

        //add initialized pile to the session stack of piles
        getGameSession().getPilesStack().add(mPile);
    }
}
