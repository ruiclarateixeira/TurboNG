package TurboNGServer.Game;

import TurboNGServer.Player.Player;

import java.util.ArrayList;

/**
 * Created by ruijorgeclarateixeira on 27/10/14.
 */
public abstract class Game {
    private boolean finished = false;
    private ArrayList<Player> players = new ArrayList<>();

    public abstract String addPlayer(Player player);
    public abstract String acceptInvite(Player player);
    public abstract void invite(String username);

    public abstract void preRoundActions();
    public abstract void postRoundActions();
    public abstract void preTurnActions(Player player);
    public abstract void postTurnActions(Player player);

    public void startGame() {
        finished = false;
        while(!finished) {
            preRoundActions();
            for(Player player : players) {
                preTurnActions(player);
                player.getAction();
                postTurnActions(player);
            }
            postRoundActions();
        }
    }
}
