package com.example.yan_home.openglengineandroid.durak.local_server.commands.custom;


import com.example.yan_home.openglengineandroid.durak.local_server.cards.Card;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.BaseSessionCommand;
import com.example.yan_home.openglengineandroid.durak.local_server.player.Player;

/**
 * Created by Yan-Home on 12/21/2014.
 * <p/>
 * Player chooses one card to start the attack with.
 */
public class PlayerAttackRequestCommand extends BaseSessionCommand {

    private int mAttackingPlayerIndex;
    private int mDefendingPlayerIndex;
    private Card mChosenCardForAttack;

    @Override
    public void execute() {
        Player attackingPlayer = getGameSession().getPlayers().get(mAttackingPlayerIndex);

        //get from player the card he would like to attack with
        mChosenCardForAttack = attackingPlayer.getCardForAttack();

    }

    public void setAttackingPlayerIndex(int attackingPlayerIndex) {
        mAttackingPlayerIndex = attackingPlayerIndex;
    }

    public void setDefendingPlayerIndex(int defendingPlayerIndex) {
        mDefendingPlayerIndex = defendingPlayerIndex;
    }

    public Card getChosenCardForAttack() {
        return mChosenCardForAttack;
    }

    public int getDefendingPlayerIndex() {
        return mDefendingPlayerIndex;
    }

    public int getAttackingPlayerIndex() {
        return mAttackingPlayerIndex;
    }
}