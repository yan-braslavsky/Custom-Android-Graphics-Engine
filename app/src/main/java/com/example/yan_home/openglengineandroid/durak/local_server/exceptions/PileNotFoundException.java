package com.example.yan_home.openglengineandroid.durak.local_server.exceptions;

/**
 * Created by Yan-Home on 12/21/2014.
 */
public class PileNotFoundException extends RuntimeException {
    public PileNotFoundException(int pileIndex) {
        super("Pile with index : " + pileIndex + " is not found in the game session");
    }
}