package TurboNGServer;

import TurboNGServer.Networking.ConnectionHandler;
import TurboNGServer.Networking.TurboNGServerSocketFactory;

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
     * Main Constructor
     * @param port Port that server socket listens from.
     * @param numberOfThreads Size of the thread pool.
     * @param sslConnection True if the socket should use SSL security. False otherwise.
     */
    private TurboNGServer(int port, int numberOfThreads, boolean sslConnection) {
        this.port = port;
        this.numberOfThreads = numberOfThreads;
        this.sslConnection = sslConnection;
    }

    /**
     * Instantiates an object of this class.
     * @param port Port that server socket listens from.
     * @param numberOfThreads Size of the thread pool.
     * @param withSSLConnection True if the socket should use SSL security. False otherwise.
     */
    public static TurboNGServer createInstance(int port, int numberOfThreads, boolean withSSLConnection) {
        return new TurboNGServer(port, numberOfThreads, withSSLConnection);
    }

    /**
     * Initializes server socket.
     * @param rPort Port that server socket listens from.
     * @param withSSLConnection True if the socket should use SSL security. False otherwise.
     * @return  ServerSocket or SSLServerSocket depending on required security.
     */
    private ServerSocket initSocket(int rPort, boolean withSSLConnection) {
        ServerSocket socket = null;
        try {
            socket = TurboNGServerSocketFactory.createNGServerSocket(rPort, withSSLConnection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
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
     */
    public void start() {
        System.out.println("Starting game server.");
        try {
            System.out.println("Creating listening socket.");
            serverSocket =  initSocket(port, sslConnection);
            if(serverSocket != null) {
                System.out.println("Socket listening on " + serverSocket.getLocalPort());
                System.out.println("Starting Connection Handler with Thread Pool of size " + numberOfThreads);
                ConnectionHandler.start(initThreadPool(numberOfThreads), serverSocket);
            }
            System.out.println("Server is running.");
        } catch (RuntimeException e) {
            System.out.println("Uncaught exception! Server will stop!" + "\nException Action" + e.getMessage());
            e.printStackTrace();
        }
    }
}
