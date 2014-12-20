package TurboNGServer.Game;

import TurboNGServer.Player.Player;

import java.util.ArrayList;

/**
 * Created by ruijorgeclarateixeira on 27/10/14.
 * This class provides the skeleton of a game with all the game
 * related methods that are important for the inner workings of the
 * framework.
 */
public abstract class Game {
    /**
     * Current state of the game. Initialized as true since the game
     * is not running.
     */
    private boolean running = false;

    /**
     * Current players in the game.
     */
    public ArrayList<Player> players = new ArrayList<>();

    /**
     * Current player's index
     */
    private int currentPlayer = 0;

    /**
     * Called when trying to add a player to the game.
     * @param player Player to be added.
     */
    public abstract void join(Player player);

    /**
     * Called when a player invites another.
     * @param source Inviting.
     * @param target Invited.
     */
    public abstract void invite(String source, String target);

    /**
     * Called just before the game loop starts.
     */
    public abstract void preGameActions();

    /**
     * Called before each round.
     */
    public abstract void preRoundActions();

    /**
     * Called after each round.
     */
    public abstract void postRoundActions();

    /**
     * Called before every turn.
     * @param player Player who is starting their turn after these actions.
     */
    public abstract void preTurnActions(Player player);

    /**
     * Called after every turn.
     * @param player Player who just finished their turn.
     */
    public abstract void postTurnActions(Player player);
    public abstract void postGameActions();

    /**
     * Start new round
     */
    public void startRound() {
        currentPlayer = 0;
        preRoundActions();
        startTurn();
    }

    /**
     * Start new turn
     */
    public void startTurn() {
        if(currentPlayer < this.players.size()) {
            preTurnActions(this.players.get(currentPlayer));
            this.players.get(currentPlayer).turn();
        }
    }

    /**
     * End Round
     */
    public void endRound() {
        postRoundActions();
        startRound();
    }

    /**
     * End the current player's turn
     */
    public void endTurn() {
        postTurnActions(players.get(currentPlayer));

        currentPlayer++;
        if(players.size() >= currentPlayer)
            endRound();
    }

    /**
     * End game
     */
    public void end() {
        postGameActions();
        running = false;
    }

    /**
     * Starts the game if it's not running already
     */
    public void start() {
        if(this.running)
            return;

        this.running = true;
        preGameActions();

        startRound();
    }
}
