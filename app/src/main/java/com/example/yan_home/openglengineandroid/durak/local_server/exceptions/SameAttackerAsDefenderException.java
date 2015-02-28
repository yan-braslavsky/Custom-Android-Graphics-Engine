package com.example.yan_home.openglengineandroid.durak.local_server.exceptions;

/**
 * Created by Yan-Home on 12/21/2014.
 */
public class SameAttackerAsDefenderException extends RuntimeException {


    public SameAttackerAsDefenderException(int attackerIndex, int defenderIndex) {
        super("index of attacker is " + attackerIndex + " the same as index of defender " + defenderIndex);
    }
}