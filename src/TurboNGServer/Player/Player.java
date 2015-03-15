package TurboNGServer.Player;

import TurboNGServer.Game.Game;
import TurboNGServer.Interface.Action;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;

/**
 * Created by ruijorgeclarateixeira on 29/09/14.
 * This class provides the skeleton of a player with all the player
 * related methods that are important for the inner workings of the
 * framework.
*/
public abstract class Player {
    /**
     * Regex username is matched against on registering.
     */
    public static String usernameRegex = "^[a-z0-9_-]{3,16}$";
    /**
     * Player's username. Every player needs to have a string username.
     */
    public String username = null;

    /**
     * Lobby interface used to connect with the client.
     */
    public PlayerLobby playerLobby = null;

    /**
     * Game that the user is currently in. Null if not in any game. This
     * should be an implementation of the abstract class Game.
     */
    public Game game = null;

    /**
     * Received invitation for a game
     * @param game Game to join or decline.
     * @param source Username of the player that invited.
     */
    public abstract void gameInviteReceived(Game game, String source);

    /**
     * Called when the game the player is in starts.
     * @param requestingGame Game that is starting.
     */
    public abstract void gameStarted(Game requestingGame);

    /**
     * Order to disconnect received.
     */
    public abstract void disconnect();

    /**
     * Instantiate a game. This should instantiate an implementation of game.
     */
    public abstract Game initGame();

    /**
     * Called when it's the player's turn
     */
    public abstract void turn();

    /**
     * Received action to execute.
     * @param action Action o execute.
     * @return True if this method can execute the action. False otherwise.
     */
    public boolean executeAction(Action action) {
        if (action == null)
            return false;

        if(action.getValueOf("action") != null) {
            if(action.getValueOf("action").equals("showonline")) {
                ArrayList<Player> players = PlayersManager.getAllPlayers();
                String playerString = "[";
                for (Player player : players)
                    playerString += player.username + ",";
                playerString = playerString.substring(0, playerString.length() - 1) + "]";
                sendToClient(new Action("{type:lobby, action:show_online, players:" + playerString + "}"));
                return true;
            }
            else
                return false;
        }
        return false;
    }

    /**
     *  Add this player to the list of online players.
     */
    public void addToOnlinePlayers() throws NullPointerException, KeyAlreadyExistsException {
        if(username != null) {
            PlayersManager.addPlayer(this);
        }
    }

    /**
     * Set lobby interface for this player.
     * @param gPlayerLobby Lobby interface for this player.
     */
    public void setPlayerLobby(PlayerLobby gPlayerLobby) {
        this.playerLobby = gPlayerLobby;
    }

    /**
     * Send action to client.
     * @param action Action to send.
     */
    public void sendToClient(Action action) {
        if (playerLobby != null && action != null) {
            playerLobby.sendToClient(action);
        }
    }

    /**
     * Create a game and join it if this player is not another game.
     */
    public void createGame() {
        if(this.game == null) {
            this.game = initGame();
            this.game.join(this);
        }
    }

    /**
     * Returns a json representation of the player.
     * @return Json representation of th player.
     */
    public String toJsonString() {
        return "{username:" + this.username + "}";
    }
}
