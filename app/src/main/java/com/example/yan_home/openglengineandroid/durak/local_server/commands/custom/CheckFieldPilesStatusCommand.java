package com.example.yan_home.openglengineandroid.durak.local_server.commands.custom;


import com.example.yan_home.openglengineandroid.durak.local_server.cards.Pile;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.BaseSessionCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yan-Home on 12/25/2014.
 *
 * Command will analise filed piles condition and decide whether all field piles
 * are covered or not.
 */
public class CheckFieldPilesStatusCommand extends BaseSessionCommand {


    private boolean mEveryFieldPileCovered;
    private List<Pile> mUncoveredPiles;

    @Override
    public void execute() {

        //allocating new list
        mUncoveredPiles = new ArrayList<>();

        //depending on field piles status , decide what to do next
         mEveryFieldPileCovered = true;

        //filter all field piles
        for (Pile pile : getGameSession().getPilesStack()) {
            if (pile.hasTag(Pile.PileTags.FIELD_PILE)) {

                //check if the pile is covered (has 2 cards)
                if (pile.getCardsInPile().size() < 2) {
                    mEveryFieldPileCovered = false;
                    mUncoveredPiles.add(pile);
                }
            }
        }
    }



    public boolean isEveryFieldPileCovered() {
        return mEveryFieldPileCovered;
    }

    public List<Pile> getUncoveredPiles() {
        return mUncoveredPiles;
    }
}
