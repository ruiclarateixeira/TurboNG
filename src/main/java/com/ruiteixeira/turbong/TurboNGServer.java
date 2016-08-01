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
     * TestMain Constructor
     * @param sslPassword Password to the SSL keystore. Null if no SSL connection needed.
     * @param propertiesPath Path to the properties file.
     */
    public TurboNGServer(String propertiesPath, char[] sslPassword) {
        Settings.Load(propertiesPath);

        if(!Settings.validSettings) {
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
     * @param playerFactory Factory that creates an implementation of player.
     */
    public void start(IPlayerFactory playerFactory) {
        try {
            try  {
                Player player = playerFactory.instantiatePlayer();
                if (player == null)
                    throw new RuntimeException("player is null");
            } catch (RuntimeException e) {
                System.out.println("[StartServer] Invalid player Factory");
                return;
            }

            if(!Settings.validSettings) {
                System.err.println("[StartServer] Invalid Settings.");
                return;
            }

            if(serverSocket != null) {
                System.out.println("[StartServer] Starting game server.");
                System.out.println("[StartServer] Socket listening on " + serverSocket.getLocalPort());
                System.out.println("[StartServer] Starting Connection Handler with Thread Pool of size " + Settings.NumberOfThreads);
                ConnectionHandler.Start(initThreadPool(Settings.NumberOfThreads), serverSocket, playerFactory);
            }
            else {
                System.err.println("[StartServer] Socket not created!");
                return;
            }
            System.out.println("[StartServer] Server is running.");
        } catch (RuntimeException e) {
            System.out.println("[StartServer] Uncaught exception! Server will stop!" + "\nException Action" + e.getMessage());
            e.printStackTrace();
        }
    }
}
