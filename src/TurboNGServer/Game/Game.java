package TurboNGServer.Game;

import TurboNGServer.Interface.Action;
import TurboNGServer.Player.Player;

import java.util.ArrayList;

/**
 * Created by ruijorgeclarateixeira on 27/10/14.
 */
public abstract class Game {
    private boolean finished = false;
    public ArrayList<Player> players = new ArrayList<>();

    public abstract Game createGame();

    public abstract void addPlayer(Player player);
    public abstract void invite(String source, String target);

    public abstract void preRoundActions();
    public abstract void postRoundActions();
    public abstract void preTurnActions(Player player);
    public abstract void postTurnActions(Player player);

    public void startGame() {
        finished = false;
        //while(!finished) {
            //preRoundActions();
            for(Player player : players) {
                player.sendToClient(new Action("{action:login}"));
                //preTurnActions(player);
                //player.getAction();
                //postTurnActions(player);
            }
            //postRoundActions();
        //}
    }
}
