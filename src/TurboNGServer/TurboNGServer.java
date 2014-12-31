package TurboNGServer;

import TurboNGServer.Networking.ConnectionHandler;
import TurboNGServer.Networking.TurboNGServerSocketFactory;
import TurboNGServer.Player.IPlayerFactory;

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
     * @param port Port that server socket listens from.
     * @param numberOfThreads Size of the thread pool.
     * @param sslConnection True if the socket should use SSL security. False otherwise.
     */
    public TurboNGServer(int port, int numberOfThreads, boolean sslConnection, char[] sslPassword ,String SSLKeysPath) {
        this.port = port;
        this.numberOfThreads = numberOfThreads;
        this.sslConnection = sslConnection;

        try {
            this.serverSocket = TurboNGServerSocketFactory.createNGServerSocket(this.port, this.sslConnection, sslPassword, SSLKeysPath);
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
