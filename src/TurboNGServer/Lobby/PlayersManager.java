package TurboNGServer.Lobby;

import TurboNGServer.ServerSettings.ServerVariables;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ruijorgeclarateixeira on 21/10/14.
 */
public class PlayersManager {
    public static final ConcurrentHashMap<String, Player> onlinePLayers = new ConcurrentHashMap<>(ServerVariables.NUMBER_OF_THREADS);
}
