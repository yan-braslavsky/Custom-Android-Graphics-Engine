package com.example.yan_home.openglengineandroid.durak.local_server.commands.custom;


import com.example.yan_home.openglengineandroid.durak.local_server.cards.Card;
import com.example.yan_home.openglengineandroid.durak.local_server.cards.Pile;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.BaseSessionCommand;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.composite.MoveCardsListFromPileToPile;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Yan-Home on 12/23/2014.
 * <p/>
 * moves all the cards from field piles to another pile
 * and removes references for field piles from game session.
 */
public class MoveAllFieldPilesCardsCommand extends BaseSessionCommand {
    private int toPileIndex;

    public void setToPileIndex(int toPileIndex) {
        this.toPileIndex = toPileIndex;
    }

    @Override
    public void execute() {
        for (Pile pile : getGameSession().getPilesStack()) {
            if (pile.hasTag(Pile.PileTags.FIELD_PILE)) {
                issueMoveCardListCommand(pile.getCardsInPile(), toPileIndex, getGameSession().getPilesStack().indexOf(pile));
            }
        }

        //remove all field piles from game session
        Iterator<Pile> iterator = getGameSession().getPilesStack().iterator();
        while (iterator.hasNext()) {
            Pile pile = iterator.next();
            if (pile.hasTag(Pile.PileTags.FIELD_PILE)) {
                iterator.remove();
            }
        }
    }

    private void issueMoveCardListCommand(List<Card> cardListToBeMoved, int toPileIndex, int fromPileIndex) {
        MoveCardsListFromPileToPile moveCardFromPileToPileCommand = new MoveCardsListFromPileToPile();
        moveCardFromPileToPileCommand.setCardList(cardListToBeMoved);
        moveCardFromPileToPileCommand.setToPileIndex(toPileIndex);
        moveCardFromPileToPileCommand.setFromPileIndex(fromPileIndex);
        getGameSession().executeCommand(moveCardFromPileToPileCommand);
    }

    public int getToPileIndex() {
        return toPileIndex;
    }
}
