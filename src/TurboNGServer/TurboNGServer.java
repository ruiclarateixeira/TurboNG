package TurboNGServer;

import TurboNGServer.Networking.ConnectionHandler;
import TurboNGServer.Networking.TurboNGServerSocketFactory;
import TurboNGServer.ServerSettings.ServerVariables;

import javax.net.ssl.SSLServerSocket;
import java.util.concurrent.Executors;

/**
 * Created by ruijorgeclarateixeira on 27/09/14.
 * This is the main instance of the server
 * It starts a ConnectionHandler that will receive the incoming connections
 */
public class TurboNGServer {
    SSLServerSocket serverSocket;
    int port;
    int numberOfThreads;
    boolean sslConnection;

    private TurboNGServer(int port, int numberOfThreads, boolean sslConnection) {
        this.port = port;
        this.numberOfThreads = numberOfThreads;
        this.sslConnection = sslConnection;
    }

    public static TurboNGServer createInstance(int port, int numberOfThreads, boolean withSSLConnection) {
        return new TurboNGServer(port, numberOfThreads, withSSLConnection);
    }

    public void start() {
        System.out.println("Starting game server.");
        try {
            System.out.println("Creating listening socket.");
            serverSocket = TurboNGServerSocketFactory.createNGServerSocket(port, sslConnection);

            if(serverSocket != null)
                System.out.println("Listening on port " + port);
            else
                throw new RuntimeException("Could not create the listening socket!");

            System.out.println("Starting Connection Handler with Thread Pool of size " + numberOfThreads);
            ConnectionHandler.start(Executors.newFixedThreadPool(numberOfThreads), serverSocket);

            System.out.println("Server is running.");
        } catch (RuntimeException e) {
            ServerVariables.errorLogger.severe("Uncaught exception! Server will stop!"
                    + "\nException Message" +  e.getMessage());
            e.printStackTrace();
        }
    }
}
