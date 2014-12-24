package com.example.yan_home.openglengineandroid.communication.socket;

/**
 * Created by Yan-Home on 12/24/2014.
 */
public interface SocketClient {

    void sendMessage(String msg);
    String readMessage();
    void disconnect();
}
