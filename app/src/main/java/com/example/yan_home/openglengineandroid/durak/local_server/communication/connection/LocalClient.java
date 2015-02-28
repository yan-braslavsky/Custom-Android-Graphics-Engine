package com.example.yan_home.openglengineandroid.durak.local_server.communication.connection;


import com.example.yan_home.openglengineandroid.durak.communication.socket.SharedLocalMessageQueue;

/**
 * Created by Yan-Home on 12/24/2014.
 */
public class LocalClient implements SocketClient {

    @Override
    public void sendMessage(String msg) {
        //we are sending to client , that is why inserting into client queue
        SharedLocalMessageQueue.getInstance().insertMessageForClientQueue(msg);
    }

    @Override
    public String readMessage() {
        //reading from queue that intended for server
        return SharedLocalMessageQueue.getInstance().getMessageForServerQueue();
    }

    @Override
    public void disconnect() {
        SharedLocalMessageQueue.getInstance().clearForClientQueue();
    }
}
