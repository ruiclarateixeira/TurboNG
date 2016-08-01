package com.ruiteixeira.turbong.player;

import com.ruiteixeira.turbong.Interface.Action;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * Created by ruijorgeclarateixeira on 13/10/14.
 * This class is the main interface between the player class and the client.
 */
public class PlayerLobby implements Callable<Void> {
    /**
     * Input from the client.
     */
    public BufferedReader bufReader = null;

    /**
     * Output to the client.
     */
    public BufferedWriter bufWriter = null;

    /**
     * player that is in the lobby
     */
    Player player;

    public PlayerLobby(IPlayerFactory playerFactory) {
        if (playerFactory == null) {
            System.err.println("[PlayerLobby] Null player Factory.");
            return;
        }
        player = playerFactory.instantiatePlayer();
    }

    /**
     *
     * @return current player in lobby.
     */
    public Player getPlayer() {
        return player;
    }


    /**
     * Gives interface the socket to listen from.
     * @param socket Socket to listen from.
     * @throws IOException
     */
    public void listen(Socket socket) throws IOException {
        bufReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bufWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    /**
     * This is method runs an infinite loop that waits for player input and does basic validation. It then sends the
     * message to be executed by the player.
     * @return computed result
     */
    @Override
    public Void call() {
        try {
            player.setPlayerLobby(this);
        } catch (RuntimeException e) {
            return null;
        }

        if(bufReader == null || bufWriter == null) {
            return null;
        }

        while (true) {
            try {
                String input = bufReader.readLine();

                if(input == null) {
                    player.disconnect();
                    return null; // Stop execution
                }
                sendToPlayer(new Action(input));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }


    /**
     * Send an action to this lobby's player.
     * @param action Action to be sent.
     */
    public void sendToPlayer(Action action) {
        if(action != null && action.isValid()) {
            try {
                player.executeAction(action);
            } catch (RuntimeException e) {
                System.err.println("[ExecuteAction] Error executing action!");
                e.printStackTrace();
            }
        }
        else {
            sendToClient(Action.ERROR_102_ILLEGAL_FORMAT);
        }
    }

    /**
     * Send an action to the client.
     * @param action Action to be sent.
     */
    public void sendToClient(Action action) {
        try {
            if(action != null && action.isValid()) {
                bufWriter.write(action.toString());
                bufWriter.newLine();
                bufWriter.flush();
            }
        } catch (IOException e) {
            System.err.println("[PlayerLobby] Could not write to socket");
        } catch (NullPointerException e) {
            System.err.println("[PlayerLobby] Writer is null");
        }
    }
}
