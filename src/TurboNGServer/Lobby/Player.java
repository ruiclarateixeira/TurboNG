package TurboNGServer.Lobby;

import TurboNGServer.Game.Game;
import TurboNGServer.Interface.LobbyInterface;
import TurboNGServer.Interface.Message;

/**
 * Created by ruijorgeclarateixeira on 29/09/14.
 * This class holds all the user information and performs all user action that
 * the client sends.
*/
public abstract class Player {
    public String username = null;
    public LobbyInterface lobbyInterface = null;

    public abstract String executeAction(Message message);
    public abstract String invite(Game game);
    public abstract String getAction();
    public abstract void disconnect();

    public void addToOnlinePlayers() {
        if(username != null) {
            PlayersManager.onlinePLayers.put(username, this);
        }
    }

    public void setLobbyInterface(LobbyInterface gLobbyInterface) {
        this.lobbyInterface = gLobbyInterface;
    }

    public void sendMessage(String message) {
        lobbyInterface.sendMessage(message);
    }

    public void sendMessageTo(String username, String message) {
        if(PlayersManager.onlinePLayers.get(username) != null)
            PlayersManager.onlinePLayers.get(username).sendMessage(message);
    }
}
