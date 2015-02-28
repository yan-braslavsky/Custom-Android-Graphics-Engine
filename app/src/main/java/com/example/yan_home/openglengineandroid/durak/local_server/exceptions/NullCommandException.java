package com.example.yan_home.openglengineandroid.durak.local_server.exceptions;

/**
 * Created by Yan-Home on 12/21/2014.
 */
public class NullCommandException extends RuntimeException {
    public NullCommandException() {
        super("The command is null !");
    }
}