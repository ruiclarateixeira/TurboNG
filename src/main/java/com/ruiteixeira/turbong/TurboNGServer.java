package com.ruiteixeira.turbong;

import com.ruiteixeira.turbong.networking.ConnectionHandler;
import com.ruiteixeira.turbong.networking.TurboNGServerSocketFactory;
import com.ruiteixeira.turbong.player.IPlayerFactory;
import com.ruiteixeira.turbong.player.Player;
import com.ruiteixeira.turbong.settings.Settings;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the main instance of the server
 * It starts a ConnectionHandler that will receive the incoming connections
 */
public class TurboNGServer {
    private static final Logger LOGGER = Logger.getLogger("TurboNG");
    private final Settings settings;

    /**
     * Socket from which the server listens for new client connections.
     */
    private ServerSocket serverSocket;

    /**
     * Main Constructor
     * @param sslPassword Password to the SSL keystore. Null if no SSL connection needed.
     * @param settings to be used
     */
    public TurboNGServer(Settings settings, char[] sslPassword) {
        this.settings = settings;
        try {
            this.serverSocket = TurboNGServerSocketFactory.createNGServerSocket(settings, sslPassword);
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
     * @param playerFactory Factory that creates an implementation of player.
     */
    public void start(IPlayerFactory playerFactory) {
        try {
            try  {
                Player player = playerFactory.instantiatePlayer();
                if (player == null)
                    throw new RuntimeException("player is null");
            } catch (RuntimeException e) {
                LOGGER.log(Level.SEVERE, "Invalid player Factory");
                return;
            }

            if(!settings.isValid()) {
                LOGGER.log(Level.SEVERE, "Invalid Settings.");
                return;
            }

            if(serverSocket != null) {
                LOGGER.log(Level.INFO, "Starting game server.");
                LOGGER.log(Level.INFO, "Socket listening on " + serverSocket.getLocalPort());
                LOGGER.log(Level.INFO, "Starting Connection Handler with Thread Pool of size " + settings.getNumberOfThreads());
                ConnectionHandler.Start(initThreadPool(settings.getNumberOfThreads()), serverSocket, playerFactory);
            }
            else {
                LOGGER.log(Level.SEVERE, "Socket not created!");
                return;
            }
            LOGGER.log(Level.INFO, "Server is running.");
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Uncaught exception! Server will stop!" + "\nException Action" + e.getMessage());
            e.printStackTrace();
        }
    }
}
