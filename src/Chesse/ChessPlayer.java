package Chesse;

import TurboNGServer.Game.Game;
import TurboNGServer.Interface.Message;
import TurboNGServer.Lobby.Player;
import TurboNGServer.ServerSettings.ServerResponses;

/**
 * Created by ruijorgeclarateixeira on 16/11/14.
 */

public class ChessPlayer extends Player{

    public ChessPlayer() {
    }

    @Override
    public String executeAction(Message message) {
        if(!message.isValid()) {
            return ServerResponses.ERROR_102_ILLEGAL_FORMAT;
        }

        if(message.getValueOf("action").equals("login")) {
            if(message.getValueOf("username") != null) {
                username = message.getValueOf("username");
                addToOnlinePlayers();
                return "{action:response, type:success, username: " + username + "}";
            }
        }
        else if(message.getValueOf("action").equals("invite")) {
            if(message.getValueOf("target") != null) {
                sendMessageTo(message.getValueOf("target"), "{action:invite, source:" + username + "}");
            }
        }
        return ServerResponses.ERROR_101_ACTION_NOT_FOUND;
    }

    @Override
    public String invite(Game game) {
        return null;
    }

    @Override
    public String getAction() {
        return null;
    }
}
