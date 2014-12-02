package TurboNGServer.Networking;

import DependencyInjection.LobbyDependencyInjector;
import TurboNGServer.Interface.LobbyInterface;
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
    public static boolean serverRunning = true;

    public static LobbyInterface createLobbyInterface(InputStream in, OutputStream out) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        ObjectGraph objectGraph;
        objectGraph = ObjectGraph.create(new LobbyDependencyInjector());
        LobbyInterface lobbyInterface = objectGraph.get(LobbyInterface.class);
        lobbyInterface.setReaderAndWriter(reader, writer);
        return lobbyInterface;
    }

    public static void start(ExecutorService pool, ServerSocket serverSocket) {
        while(serverRunning) {
            try {
                Socket clientSocket = serverSocket.accept(); // BLOCKS EXECUTION
                pool.submit(createLobbyInterface(clientSocket.getInputStream(), clientSocket.getOutputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (RuntimeException e) {
                e.printStackTrace();
                System.err.println("Unidentified exception. TurboNGServer.Networking.ConnectionHandler will continue.");
            }
        }
    }
}
