package IntegrationTesting;

import TurboNGServer.Game.Game;
import TurboNGServer.Interface.Action;
import TurboNGServer.ListenerModules.MessagingModule;
import TurboNGServer.ListenerModules.UsernamePasswordLoginModule;

/**
 * Created by ruijorgeclarateixeira on 16/11/14.
 * A Chess Player.
 */

public class TestPlayer extends UsernamePasswordLoginModule {
    public TestPlayer() {
        System.out.println("NEW TEST PLAYER");
    }

    @Override
    public boolean executeAction(Action action) {
        if(super.executeAction(action))
            return true;

        System.out.println(action);

        switch (action.getValueOf("action")) {
            case ("createGame"):
                System.out.println("createGame");
                createGame();
                break;
            case ("startGame"):
                if(this.game != null) {
                    this.game.start();
                }
                break;
            default:
                sendToClient(Action.ERROR_101_ACTION_NOT_FOUND);
        }
        return true;
    }

    @Override
    public void gameInviteReceived(Game game, String source) {
        game.join(this);
    }

    @Override
    public void gameStarted(Game requestingGame) {

    }

    @Override
    public void getAction() {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public Game initGame() {
        return new TestGame();
    }

    @Override
    public void turn() {

    }
}
