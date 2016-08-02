package com.ruiteixeira.turbong.networking;

import com.ruiteixeira.turbong.player.IPlayerFactory;
import com.ruiteixeira.turbong.player.PlayerLobby;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * main.java.main.com.ruiteixeira.turbong.networking.ConnectionHandler
 *
 * This class is given a thread pool and a secure socket to receive connections from.
 * It receives the incoming connections and submits them into the
 * thread pool.
 */
public class ConnectionHandler {
    private static final Logger LOGGER = Logger.getLogger("ConnectionHandler");

    /**
     * Starts the connection handler.
     * @param pool Pool of threads.
     * @param serverSocket Socket the connection handler listens to.
     * @param playerFactory Instance of a player factory.
     */
    public static void Start(ExecutorService pool, ServerSocket serverSocket, IPlayerFactory playerFactory) {
        if (pool == null || serverSocket == null || playerFactory == null) {
            LOGGER.log(Level.SEVERE, "Couldn't start handler");
            return;
        }
        else if (playerFactory.instantiatePlayer() == null) {
            LOGGER.log(Level.SEVERE, "Invalid player Factory");
            return;
        }

        while(true) {
            try {
                Socket clientSocket = serverSocket.accept(); // BLOCKS EXECUTION
                PlayerLobby playerLobby = new PlayerLobby(playerFactory);
                playerLobby.listen(clientSocket);
                pool.submit(playerLobby);
            } catch (InterruptedIOException exception) {
                break; // TODO: Improve on how to stop the server
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error listening to socket connection", e);
            } catch (RuntimeException e) {
                LOGGER.log(Level.SEVERE, "Unidentified exception. Process will continue.", e);
            }
        }
    }
}
