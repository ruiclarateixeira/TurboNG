package IntegrationTesting;

import TurboNGServer.Game.Game;
import TurboNGServer.Interface.Action;
import TurboNGServer.Modules.InviteByUsernamePlayer;
import TurboNGServer.Modules.PasswordlessLoginPlayer;
import TurboNGServer.Player.Player;

/**
 * Created by ruijorgeclarateixeira on 16/11/14.
 * A Chess Player.
 */

public class TestPlayer extends InviteByUsernamePlayer {
    public TestPlayer() {
        System.out.println("NEW TEST PLAYER");
    }

    @Override
    public boolean executeAction(Action action) {
        if(!action.isValid()) {
            sendToClient(Action.ERROR_102_ILLEGAL_FORMAT);
        }

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

    @Override
    public void chatMessage(String source, String message) {

    }
}
