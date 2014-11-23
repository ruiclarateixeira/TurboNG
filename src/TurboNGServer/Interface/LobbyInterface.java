package TurboNGServer.Interface;

import TurboNGServer.Lobby.NewsFeed;
import TurboNGServer.Lobby.Player;
import TurboNGServer.ServerSettings.ServerResponses;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Created by ruijorgeclarateixeira on 13/10/14.
 * This class is the main interface with the client.
 */
public class LobbyInterface implements Callable<Void> {
    BufferedReader bufReader;
    BufferedWriter bufWriter;
    @Inject Player player;
    @Inject NewsFeed newsFeed;

    public void setReaderAndWriter(BufferedReader givenReader, BufferedWriter givenWriter) {
        bufReader = givenReader;
        bufWriter = givenWriter;
    }

    /**
     * This is method runs an infinite loop that waits for player input and does basic validation. It then sends the
     * message to be executed by the right class.
     * @return computed result
     */
    @Override
    public Void call() {
        player.setLobbyInterface(this);
        while (true) {
            try {
                String input = bufReader.readLine();

                if(input == null) {
                    player.disconnect();
                    return null; // Stop execution
                }

                Message inputMessage = new Message(input);
                if(inputMessage.isValid()) {
                    sendMessage(player.executeAction(inputMessage));
                }
                else {
                    sendMessage(ServerResponses.ERROR_102_ILLEGAL_FORMAT);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Send a message to the client.
     * @param message Message to be sent.
     */
    public void sendMessage(String message) {
        try {
            bufWriter.write(message);
            bufWriter.newLine();
            bufWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
