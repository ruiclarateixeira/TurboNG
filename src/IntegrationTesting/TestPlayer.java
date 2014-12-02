package IntegrationTesting;

import TurboNGServer.Game.Game;
import TurboNGServer.Interface.Action;
import TurboNGServer.Player.Player;
import TurboNGServer.ServerSettings.ServerResponses;

/**
 * Created by ruijorgeclarateixeira on 16/11/14.
 * A Chess Player.
 */

public class TestPlayer extends Player{
    public TestPlayer() {
    }

    @Override
    public void executeAction(Action action) {
        if(!action.isValid()) {
            sendToClient(new Action(ServerResponses.ERROR_102_ILLEGAL_FORMAT));
        }

        switch (action.getValueOf("action")) {
            case  ("login"):
                System.out.println("Login");
                if (action.getValueOf("username") != null) {
                    username = action.getValueOf("username");
                    addToOnlinePlayers();
                    sendToClient(new Action("{action:loginSuccessful}"));
                }
                break;
            case ("createGame"):
                System.out.println("createGame");
                createGame();
                break;
            case ("invite"):
                System.out.println("Invite");
                if(action.getValueOf("target") != null) {
                   inviteToGame(action.getValueOf("target"));
                }
                break;
            default:
                sendToClient(new Action(ServerResponses.ERROR_101_ACTION_NOT_FOUND));
        }
    }

    @Override
    public void invite(Game game, String source) {
        sendToClient(new Action("{action:invite, source: " + source + "}"));
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
        return new TestGame();
    }

    @Override
    public void chatMessage(String source, String message) {

    }
}
