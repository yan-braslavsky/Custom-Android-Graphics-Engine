package com.example.yan_home.openglengineandroid.durak.local_server.commands.custom.startgame;


import com.example.yan_home.openglengineandroid.durak.local_server.commands.BaseSessionCommand;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.composite.PrepareGameSessionCommand;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.composite.StartRoundCommand;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.core.AddBotPlayerCommand;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.core.AddRemotePlayerCommand;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.custom.IdentifyNextRoundPlayersCommand;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.hooks.notifiers.broadcast.PlayerActionAttackBroadcastHook;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.hooks.notifiers.broadcast.PlayerActionRetaliateBroadcastHook;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.hooks.notifiers.broadcast.RemoteClientsCardsMoveBroadcastHook;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.hooks.notifiers.unicast.RemoteClientsGameSetupUnicastHook;
import com.example.yan_home.openglengineandroid.durak.local_server.commands.hooks.notifiers.unicast.RemoteClientsWrongCoverageNotifierUnicastHook;
import com.example.yan_home.openglengineandroid.durak.local_server.communication.connection.LocalClient;
import com.example.yan_home.openglengineandroid.durak.local_server.communication.connection.RemoteClientsManager;

/**
 * Created by Yan-Home on 12/22/2014.
 * <p/>
 * This scenario starts a game with 2 bots and one human players.
 */
public class ScriptedPlayerWithBotsGameStartCommand extends BaseSessionCommand {

    private static final int BOT_PLAYERS_AMOUNT = 2;

    @Override
    public void execute() {

        //in order to keep our structure modular
        //we are using hooks to analyse commands and
        //take desired actions when needed.
        addHooks();

        //clear the game session and put discard and stock piles
        getGameSession().executeCommand(new PrepareGameSessionCommand());

        System.out.println("Waiting for remote player to connect");

        //add remote client
        LocalClient playerClient =  RemoteClientsManager.getInstance().waitForClientToConnect();

        System.out.println("Remote player connected !");

        //get reference to the only client that connected
        AddRemotePlayerCommand addRemotePlayerCommand = new AddRemotePlayerCommand();
        addRemotePlayerCommand.setRemoteClient(playerClient);

        //add human player
        getGameSession().executeCommand(addRemotePlayerCommand);

        //add bot players
        for (int i = 0; i < BOT_PLAYERS_AMOUNT; i++) {
            //add bot player
            getGameSession().executeCommand(new AddBotPlayerCommand());
        }

        //define who attacks and who defends
        IdentifyNextRoundPlayersCommand identifyCommand = new IdentifyNextRoundPlayersCommand();
        getGameSession().executeCommand(identifyCommand);

        //let player attack and the next player by it to defend
        StartRoundCommand startRoundCommand = new StartRoundCommand();
        startRoundCommand.setRoundAttackingPlayerIndex(identifyCommand.getNextRoundAttackerPlayerIndex());
        startRoundCommand.setRoundDefendingPlayerIndex(identifyCommand.getNextRoundDefenderPlayerIndex());
        getGameSession().executeCommand(startRoundCommand);
    }

    private void addHooks() {
        //this post hook notifies all remote clients about cards movement
        getGameSession().addPostHook(new RemoteClientsCardsMoveBroadcastHook());

        //this post hook notifies joined remote player about game setup
        //take in mind that it takes only the information that is available for that
        //point of time
        getGameSession().addPostHook(new RemoteClientsGameSetupUnicastHook());

        //Those pre hooks are used to notify the next player action
        //That allows the client to change the UI state accordingly
        getGameSession().addPreHook(new PlayerActionAttackBroadcastHook());
        getGameSession().addPreHook(new PlayerActionRetaliateBroadcastHook());

        //add hook that will notify active player of wrong retaliation coverage
        getGameSession().addPostHook(new RemoteClientsWrongCoverageNotifierUnicastHook());

    }
}