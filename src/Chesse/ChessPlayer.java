package Chesse;

import TurboNGServer.Game.Game;
import TurboNGServer.Interface.Action;
import TurboNGServer.Player.Player;
import TurboNGServer.ServerSettings.ServerResponses;

/**
 * Created by ruijorgeclarateixeira on 16/11/14.
 * A Chess Player.
 */

public class ChessPlayer extends Player{

    public ChessPlayer() {
    }

    @Override
    public void executeAction(Action action) {
        if(!action.isValid()) {
            sendToClient(new Action(ServerResponses.ERROR_102_ILLEGAL_FORMAT));
        }

        switch (action.getValueOf("action")) {
            case  ("login"):
                if (action.getValueOf("username") != null) {
                    username = action.getValueOf("username");
                    addToOnlinePlayers();
                    sendToClient(new Action("{action:loginSuccessful}"));
                }
                break;
            case ("userList"):
                break;
            case ("chat"):
                if(action.getValueOf("target") != null) {
                    sendMessageTo(action.getValueOf("target"), action.getValueOf("message"));
                }
                break;
            case ("exit"):

                break;
            default:
                sendToClient(new Action(ServerResponses.ERROR_101_ACTION_NOT_FOUND));
        }
    }

    @Override
    public void invite(Game game) {

    }

    @Override
    public Action getAction() {
        return null;
    }

    @Override
    public void disconnect() {

    }

    @Override
    public void chatMessage(String source, String message) {

    }
}
