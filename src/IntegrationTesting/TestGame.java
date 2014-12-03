package IntegrationTesting;

import TurboNGServer.Game.Game;
import TurboNGServer.Player.Player;
import TurboNGServer.Player.PlayersManager;

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
    public void join(Player player) {
        System.out.println("Player Joined! " + player.username);
        players.add(player);
    }

    @Override
    public void invite(String source, String target) {
        PlayersManager.getPlayer(target).gameInviteReceived(this, source);
    }

    @Override
    public void preGameActions() {

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

    @Override
    public void postGameActions() {

    }
}
