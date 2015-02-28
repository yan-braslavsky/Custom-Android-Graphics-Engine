package com.example.yan_home.openglengineandroid.durak.local_server.commands.custom;


import com.example.yan_home.openglengineandroid.durak.local_server.cards.Card;
import com.example.yan_home.openglengineandroid.durak.local_server.cards.Pile;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.BaseSessionCommand;

/**
 * Created by ybra on 19.12.2014.
 * <p/>
 * This is a test command .
 */
public class LogPilesStatusCommand extends BaseSessionCommand {

    @Override
    public void execute() {

        System.out.println("===================================================================================================================================\n");
        System.out.println("Selected trump suit is : " + getGameSession().getTrumpSuit());
        for (int i = 0; i < getGameSession().getPilesStack().size(); i++) {
            Pile pile = getGameSession().getPilesStack().get(i);

            System.out.println("************************************************************************\n");
            System.out.println("Pile index : " + i + " cards count = " + pile.getCardsInPile().size());
            System.out.println("--------------------------------------");
            for (Card card : pile.getCardsInPile()) {
                System.out.println("Rank : " + card.getRank() + " Suit : " + card.getSuit());
            }
        }


    }
}
