package TurboNGServer.Player;

import TurboNGServer.Interface.Action;
import TurboNGServer.ServerSettings.ServerResponses;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * Created by ruijorgeclarateixeira on 13/10/14.
 * This class is the main interface between the player class and the client.
 */
public class PlayerLobby implements Callable<Void> {
    public BufferedReader bufReader = null;
    public BufferedWriter bufWriter = null;

    Player player;

    public PlayerLobby(IPlayerFactory playerFactory) {
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
     * message to be executed by the Player.
     * @return computed result
     */
    @Override
    public Void call() {
        player.setPlayerLobby(this);

        if(bufReader == null || bufWriter == null) {
            System.out.println("No socket given to PlayerLobby.");
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
            }
        }
    }


    /**
     * Send an action to this lobby's player.
     * @param action Action to be sent.
     */
    public void sendToPlayer(Action action) {
        if(action.isValid()) {
            player.executeAction(action);
        }
        else {
            sendToClient(new Action(ServerResponses.ERROR_102_ILLEGAL_FORMAT));
        }
    }

    /**
     * Send an action to the client.
     * @param action Action to be sent.
     */
    public void sendToClient(Action action) {
        try {
            if(action.isValid()) {
                bufWriter.write(action.toString());
                bufWriter.newLine();
                bufWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
