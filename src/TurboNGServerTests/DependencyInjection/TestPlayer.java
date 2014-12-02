package TurboNGServerTests.DependencyInjection;

import TurboNGServer.Game.Game;
import TurboNGServer.Interface.Action;
import TurboNGServer.Player.Player;

/**
 * Created by ruijorgeclarateixeira on 02/12/14.
 */
public class TestPlayer extends Player {
    @Override
    public void executeAction(Action message) {
        sendToClient(message);
    }

    @Override
    public void invite(Game game, String source) {

    }

    @Override
    public Action getAction() {
        return null;
    }

    @Override
    public void disconnect() {

    }

    @Override
    public Game initGame() {
        return null;
    }

    @Override
    public void chatMessage(String source, String message) {

    }
}
