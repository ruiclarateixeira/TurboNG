package IntegrationTesting;

import TurboNGServer.Game.Game;
import TurboNGServer.Player.Player;

/**
 * Created by ruijorgeclarateixeira on 02/12/14.
 * A Chess game.
 */
public class TestGame extends Game {

    public TestGame() {
        System.out.println("Instantiated Chess Game");
    }

    @Override
    public Game createGame() {
        return null;
    }

    @Override
    public void addPlayer(Player player) {
        players.add(player);
    }

    @Override
    public void invite(String source, String target) {

    }

    @Override
    public void preRoundActions() {

    }

    @Override
    public void postRoundActions() {

    }

    @Override
    public void preTurnActions(Player player) {

    }

    @Override
    public void postTurnActions(Player player) {

    }
}
