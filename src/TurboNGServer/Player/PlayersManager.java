package TurboNGServer.Player;

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
        return onlinePLayers.get(username);
    }

    /**
     * Adds player to the online players list.
     * @param player Player to be added.
     */
    public static void addPlayer(Player player) {
        onlinePLayers.put(player.username, player);
    }

    /**
     * Removes player from the online players list.
     * @param username Player to be removed username.
     */
    public static void removePlayer(String username) {
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
