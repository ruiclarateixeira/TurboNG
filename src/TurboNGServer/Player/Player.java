package TurboNGServer.Player;

import TurboNGServer.Game.Game;
import TurboNGServer.Interface.Action;

import javax.management.openmbean.KeyAlreadyExistsException;

/**
 * Created by ruijorgeclarateixeira on 29/09/14.
 * This class provides the skeleton of a player with all the player
 * related methods that are important for the inner workings of the
 * framework.
*/
public abstract class Player {
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
     * Used in game. Order to get action from user received.
     * @return action sent by the client.
     */
    public abstract void getAction();

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
     * Chat message received.
     * @param source Username of the sender.
     * @param message Message received.
     */
    public abstract void chatMessage(String source, String message);

    /**
     * Received action to execute.
     * @param action Action o execute.
     * @return True if this method can execute the action. False otherwise.
     */
    public boolean executeAction(Action action) {
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
        playerLobby.sendToClient(action);
    }

    /**
     * Send message to another player.
     * @param username Username of the target player.
     * @param message Message to send.
     */
    public void sendMessageTo(String username, String message) {
        if(PlayersManager.getPlayer(username).username != null)
            PlayersManager.getPlayer(username).chatMessage(this.username, message);
    }

    /**
     * Send message to all the players except this one
     * @param messageContent Content of the message to be send.
     */
    public void sendMessageToAll(String messageContent) {
        for(Player player : PlayersManager.getAllPlayers()) {
            if(!player.username.equals(this.username))
               player.chatMessage(this.username, messageContent);
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
}
