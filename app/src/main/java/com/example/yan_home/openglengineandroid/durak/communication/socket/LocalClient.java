package com.example.yan_home.openglengineandroid.durak.communication.socket;

/**
 * Created by Yan-Home on 12/24/2014.
 */
public class LocalClient implements SocketClient {

    @Override
    public void sendMessage(String msg) {
        SharedLocalMessageQueue.getInstance().insertMessageForServerQueue(msg);
    }

    @Override
    public String readMessage() {
        return SharedLocalMessageQueue.getInstance().getMessageForClientQueue();
    }

    @Override
    public void disconnect() {
        SharedLocalMessageQueue.getInstance().clearForServerQueue();
    }
}
