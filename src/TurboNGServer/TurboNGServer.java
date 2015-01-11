package TurboNGServer;

import TurboNGServer.Networking.ConnectionHandler;
import TurboNGServer.Networking.TurboNGServerSocketFactory;
import TurboNGServer.Player.IPlayerFactory;
import TurboNGServer.ServerSettings.Settings;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ruijorgeclarateixeira on 27/09/14.
 * This is the main instance of the server
 * It starts a ConnectionHandler that will receive the incoming connections
 */
public class TurboNGServer {
    /**
     * Socket from which the server listens for new client connections.
     */
    ServerSocket serverSocket;

    /**
     * Server socket listening port.
     */
    int port;

    /**
     * Number of threads in the thread pool.
     */
    int numberOfThreads;

    /**
     * Security required. True if ssl connection required. false otherwise.
     */
    boolean sslConnection;

    /**
     * TestMain Constructor
     * @param sslPassword Password to the SSL keystore. Null if no SSL connection needed.
     * @param propertiesPath Path to the properties file.
     */
    public TurboNGServer(String propertiesPath, char[] sslPassword) {
        Settings.Load(propertiesPath);

        if(!Settings.validSettings) {
            System.err.println("Invalid settings in config.properties!\nServer not started!");
            return;
        }

        try {
            this.serverSocket = TurboNGServerSocketFactory.createNGServerSocket(sslPassword);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes and returns thread pool of given size.
     * @param threadNo Number of threads in the pool
     * @return Thread pool.
     */
    private ExecutorService initThreadPool(int threadNo) {
        return Executors.newFixedThreadPool(threadNo);
    }

    /**
     * Starts server
     * @param playerFactory Factory that creates an implementation of Player.
     */
    public void start(IPlayerFactory playerFactory) {
        try {
            if(serverSocket != null) {
                System.out.println("Starting game server.");
                System.out.println("Socket listening on " + serverSocket.getLocalPort());
                System.out.println("Starting Connection Handler with Thread Pool of size " + numberOfThreads);
                ConnectionHandler.start(initThreadPool(numberOfThreads), serverSocket, playerFactory);
            }
            else {
                System.err.println("Socket not created!");
                return;
            }
            System.out.println("Server is running.");
        } catch (RuntimeException e) {
            System.out.println("Uncaught exception! Server will stop!" + "\nException Action" + e.getMessage());
            e.printStackTrace();
        }
    }
}
