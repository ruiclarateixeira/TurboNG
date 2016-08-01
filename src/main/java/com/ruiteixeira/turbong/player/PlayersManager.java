package com.ruiteixeira.turbong.player;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ruijorgeclarateixeira on 21/10/14.
 * This class holds all the players that are currently online.
 */
public class PlayersManager {
    /**
     * Stores the currently online players.
     */
    private static ConcurrentHashMap<String, Player> onlinePLayers = onlinePLayers = new ConcurrentHashMap<>(50);

    /**
     * Get the required player.
     * @param username Username of the required player.
     * @return Required player.
     */
    public static Player getPlayer(String username) {
        if (username == null)
            return null;
        return onlinePLayers.get(username);
    }

    /**
     * Adds player to the online players list.
     * @param player player to be added.
     */
    public static void addPlayer(Player player) throws KeyAlreadyExistsException, NullPointerException {
        if(player == null || player.username == null) {
            throw new NullPointerException("Username cannot be null!");
        }

        if(onlinePLayers.containsKey(player.username)) {
            throw new KeyAlreadyExistsException("Username already exists!");
        }
        onlinePLayers.put(player.username, player);
    }

    /**
     * Removes player from the online players list.
     * @param username player to be removed username.
     */
    public static void removePlayer(String username) {
        if (username == null)
            return;
        onlinePLayers.remove(username);
    }

    /**
     * Returns all online players.
     * @return List of all online players.
     */
    public static ArrayList<Player> getAllPlayers() {
        return new ArrayList<>(onlinePLayers.values());
    }
}
