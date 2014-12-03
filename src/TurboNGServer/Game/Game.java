package TurboNGServer.Game;

import TurboNGServer.Interface.Action;
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
    private boolean finished = true;

    /**
     * Current players in the game.
     */
    public ArrayList<Player> players = new ArrayList<>();

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
     * Starts the game and runs until finished=false
     */
    public void start() {
        finished = false;
        preGameActions();
        while(!finished) {
            preRoundActions();
            for(Player player : players) {
                player.sendToClient(new Action("{action:gameAction}"));
                preTurnActions(player);
                player.getAction();
                postTurnActions(player);
            }
            postRoundActions();
        }
        postGameActions();
    }
}
