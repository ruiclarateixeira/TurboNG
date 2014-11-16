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

    public static void main(String[] args) {
        SSLServerSocket serverSocket;
        System.out.println("Starting game server.");
        try {
            System.out.println("Creating listening socket.");
            serverSocket = TurboNGServerSocketFactory.createNGServerSocket(ServerVariables.LISTENING_PORT, true);

            if(serverSocket != null)
                System.out.println("Listening on port " + ServerVariables.LISTENING_PORT);
            else
                throw new RuntimeException("Could not create the listening socket!");

            System.out.println("Starting Connection Handler with Thread Pool of size " + ServerVariables.NUMBER_OF_THREADS);
            ConnectionHandler.start(Executors.newFixedThreadPool(ServerVariables.NUMBER_OF_THREADS), serverSocket);

            System.out.println("Server is running.");
        } catch (RuntimeException e) {
            ServerVariables.errorLogger.severe("Uncaught exception! Server will stop!"
                                                + "\nException Message" +  e.getMessage());
            e.printStackTrace();
        }
    }
}
