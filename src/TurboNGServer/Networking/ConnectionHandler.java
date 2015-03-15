package TurboNGServer.Networking;

import TurboNGServer.Player.IPlayerFactory;
import TurboNGServer.Player.PlayerLobby;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * Created by ruijorgeclarateixeira on 27/09/14.
 *
 * TurboNGServer.Networking.ConnectionHandler
 *
 * This class is given a thread pool and a secure socket to receive connections from.
 * It receives the incoming connections and submits them into the
 * thread pool.
 */
public class ConnectionHandler {
    /**
     * Current state of the server.
     */
    public static boolean serverRunning = true;

    /**
     * Starts the connection handler.
     * @param pool Pool of threads.
     * @param serverSocket Socket the connection handler listens to.
     * @param playerFactory Instance of a player factory.
     */
    public static void Start(ExecutorService pool, ServerSocket serverSocket, IPlayerFactory playerFactory) {
        if (pool == null || serverSocket == null || playerFactory == null) {
            System.err.println("[ConnectionHandler] Couldn't start handler");
            return;
        }
        else if (playerFactory.instantiatePlayer() == null) {
            System.err.println("[ConnectionHandler] Invalid Player Factory");
            return;
        }

        while(serverRunning) {
            try {
                Socket clientSocket = serverSocket.accept(); // BLOCKS EXECUTION
                PlayerLobby playerLobby = new PlayerLobby(playerFactory);
                playerLobby.listen(clientSocket);
                pool.submit(playerLobby);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (RuntimeException e) {
                e.printStackTrace();
                System.err.println("Unidentified exception in TurboNGServer.Networking.ConnectionHandler. Process will continue.");
            }
        }
    }
}
