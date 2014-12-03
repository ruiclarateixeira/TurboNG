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
    private boolean finished = false;
    public ArrayList<Player> players = new ArrayList<>();

    public abstract Game createGame();

    public abstract void join(Player player);
    public abstract void invite(String source, String target);

    public abstract void preGameActions();
    public abstract void preRoundActions();
    public abstract void postRoundActions();
    public abstract void preTurnActions(Player player);
    public abstract void postTurnActions(Player player);
    public abstract void postGameActions();

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
