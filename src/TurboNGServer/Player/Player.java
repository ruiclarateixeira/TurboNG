package TurboNGServer.Player;

import TurboNGServer.Game.Game;
import TurboNGServer.Interface.Action;

/**
 * Created by ruijorgeclarateixeira on 29/09/14.
 * This class holds all the player information and performs all players actions that
 * the client sends.
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
     * Game that the user is currently in. Null if not in any game.
     */
    public Game game = null;

    /**
     * Received action to execute.
     * @param action Action o execute.
     */
    public abstract void executeAction(Action action);

    /**
     * Received invitation for a game
     * @param game Game to join or decline.
     * @param source Username of the player that invited.
     */
    public abstract void invite(Game game, String source);

    /**
     * Used in game. Order to get action from user received.
     * @return action sent by the client.
     */
    public abstract Action getAction();

    /**
     * Order to disconnect received.
     */
    public abstract void disconnect();

    /**
     * Instantiate a game. This should instantiate an implementation of game.
     */
    public abstract Game initGame();

    /**
     * Chat message received.
     * @param source Username of the sender.
     * @param message Message received.
     */
    public abstract void chatMessage(String source, String message);

    /**
     *  Add this player to the list of online players.
     */
    public void addToOnlinePlayers() {
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
     * Send an action to another player.
     * @param username Username of the target player.
     * @param action Action to send.
     */
    public void sendActionTo(String username, Action action) {
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
     * Invite another player to the game this is currently in.
     * @param target Username of the player to invite.
     */
    public void inviteToGame(String target) {
        if(this.game != null)
            this.game.invite(this.username, target);
    }

    /**
     * Join given game if not already in game.
     * @param g Game to join.
     */
    public void joinGame(Game g) {
        if(this.game == null)
            g.addPlayer(this);
    }

    /**
     * Create a game and join it if this player is not another game.
     */
    public void createGame() {
        if(this.game == null) {
            this.game = initGame();
            joinGame(this.game);
        }
    }
}
