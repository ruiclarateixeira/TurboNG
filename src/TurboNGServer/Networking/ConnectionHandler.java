package TurboNGServer.Networking;

import DependencyInjection.LobbyDependencyInjector;
import TurboNGServer.Player.PlayerLobby;
import dagger.ObjectGraph;

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
     * Accesses dependency injection graph to instantiate lobby interface.
     * @return Instantiated lobby interface with injected variables.
     */
    public static PlayerLobby initLobbyInterface() {
        ObjectGraph objectGraph;
        objectGraph = ObjectGraph.create(new LobbyDependencyInjector());
        return objectGraph.get(PlayerLobby.class);
    }

    /**
     * Starts the connection handler.
     * @param pool Pool of threads.
     * @param serverSocket Socket the connection handler listens to.
     */
    public static void start(ExecutorService pool, ServerSocket serverSocket) {
        while(serverRunning) {
            try {
                Socket clientSocket = serverSocket.accept(); // BLOCKS EXECUTION
                PlayerLobby playerLobby = initLobbyInterface();
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
