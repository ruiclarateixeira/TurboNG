package TurboNGServer.Lobby;

import TurboNGServer.Game.Game;
import TurboNGServer.Interface.Action;
import TurboNGServer.Interface.LobbyInterface;

/**
 * Created by ruijorgeclarateixeira on 29/09/14.
 * This class holds all the user information and performs all user action that
 * the client sends.
*/
public abstract class Player {
    public String username = null;
    public LobbyInterface lobbyInterface = null;

    public abstract void executeAction(Action message);
    public abstract String invite(Game game);
    public abstract String getAction();
    public abstract void disconnect();
    public abstract void chatMessage(String source, String message);

    public void addToOnlinePlayers() {
        if(username != null) {
            PlayersManager.onlinePLayers.put(username, this);
        }
    }

    public void setLobbyInterface(LobbyInterface gLobbyInterface) {
        this.lobbyInterface = gLobbyInterface;
    }

    public void sendToClient(Action action) {
        lobbyInterface.sendToClient(action);
    }

    public void sendActionTo(String target, Action action) {
        if(action.isValid() && target != null) {
            PlayersManager.onlinePLayers.get(username);
        }
    }

    public void sendMessageTo(String username, String message) {
        if(PlayersManager.onlinePLayers.get(username) != null)
            PlayersManager.onlinePLayers.get(username).chatMessage(this.username, message);
    }
}
