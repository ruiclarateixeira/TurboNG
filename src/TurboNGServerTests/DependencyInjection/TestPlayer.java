package TurboNGServerTests.DependencyInjection;

import TurboNGServer.Game.Game;
import TurboNGServer.Interface.Action;
import TurboNGServer.Lobby.Player;

/**
 * Created by ruijorgeclarateixeira on 02/12/14.
 */
public class TestPlayer extends Player {
    @Override
    public void executeAction(Action message) {

    }

    @Override
    public String invite(Game game) {
        return null;
    }

    @Override
    public String getAction() {
        return null;
    }

    @Override
    public void disconnect() {

    }

    @Override
    public void chatMessage(String source, String message) {

    }
}
