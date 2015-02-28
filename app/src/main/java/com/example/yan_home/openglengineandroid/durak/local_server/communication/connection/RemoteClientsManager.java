package com.example.yan_home.openglengineandroid.durak.local_server.communication.connection;


import java.util.ArrayList;

/**
 * Created by Yan-Home on 12/24/2014.
 * <p/>
 * Implemented as a singleton.
 * Remote clients manager manages connected peers
 */
public class RemoteClientsManager {

    private static final RemoteClientsManager INSTANCE = new RemoteClientsManager();
    private ArrayList<LocalClient> mRemoteClients;


    public static final RemoteClientsManager getInstance() {
        return INSTANCE;
    }

    private RemoteClientsManager() {
        mRemoteClients = new ArrayList<>();
    }

    /**
     * Blocks until remote client connects via socket
     *
     * @throws java.io.IOException
     */
    public LocalClient waitForClientToConnect() {
        LocalClient remoteClient =  new LocalClient();
        mRemoteClients.add(remoteClient);
        return remoteClient;
    }

    public ArrayList<LocalClient> getRemoteClients() {
        return mRemoteClients;
    }
}
