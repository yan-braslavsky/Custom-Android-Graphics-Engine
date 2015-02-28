package com.example.yan_home.openglengineandroid.durak.local_server.commands.core;


import com.example.yan_home.openglengineandroid.durak.local_server.cards.Card;
import com.example.yan_home.openglengineandroid.durak.local_server.cards.Pile;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.BaseSessionCommand;
import com.example.yan_home.openglengineandroid.durak.local_server.exceptions.CardNotFoundException;
import com.example.yan_home.openglengineandroid.durak.local_server.exceptions.PileNotFoundException;
import com.example.yan_home.openglengineandroid.durak.local_server.validation.GameSessionValidations;

/**
 * Created by ybra on 19.12.2014.
 */
public class MoveCardFromPileToPileCommand extends BaseSessionCommand {

    //must be the exact instance of the card that should be moved
    private Card mCardToMove;
    private int mFromPileIndex = -1;
    private int mToPileIndex = -1;

    public void setCardToMove(Card mCardToMove) {
        this.mCardToMove = mCardToMove;
    }

    public void setFromPileIndex(int mFromPileIndex) {
        this.mFromPileIndex = mFromPileIndex;
    }

    public void setToPileIndex(int mToPileIndex) {
        this.mToPileIndex = mToPileIndex;
    }

    @Override
    public void execute() {
        validatePilesExist();


        Pile fromPile = getGameSession().getPilesStack().get(mFromPileIndex);
        Pile toPile = getGameSession().getPilesStack().get(mToPileIndex);

        for (Card card : fromPile.getCardsInPile()) {
            if (mCardToMove.equals(card)) {
                fromPile.removeCardFromPile(card);
                toPile.addCardToPile(card);
                return;
            }
        }

        throw new CardNotFoundException(mCardToMove, mFromPileIndex);
    }

    private void validatePilesExist() {
        if (!GameSessionValidations.validatePilesExist(mFromPileIndex, getGameSession()))
            throw new PileNotFoundException(mFromPileIndex);

        if (!GameSessionValidations.validatePilesExist(mToPileIndex, getGameSession()))
            throw new PileNotFoundException(mToPileIndex);
    }


    public Card getCardToMove() {
        return mCardToMove;
    }

    public int getFromPileIndex() {
        return mFromPileIndex;
    }

    public int getToPileIndex() {
        return mToPileIndex;
    }
}
