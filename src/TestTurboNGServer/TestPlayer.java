package TestTurboNGServer;

import TurboNGServer.Game.Game;
import TurboNGServer.Interface.Message;
import TurboNGServer.Lobby.Player;

/**
 * Created by ruijorgeclarateixeira on 15/10/14.
 * This class creates a dummy player.
 */
public class TestPlayer extends Player {

    public TestPlayer() {
        System.out.println("Creating Test Player");
        this.addToOnlinePlayers();
    }

    @Override
    public String executeAction(Message message) {
        if(message.isValid() && message.getValueOf("command").equals("login")) {
            this.username = message.getValueOf("username");
            addToOnlinePlayers();
            System.out.println("User connected with username " + this.username);
            return "User connected with username " + this.username;
        }
        return "This is a response from the user";
    }

    @Override
    public String invite(Game game) {
        return "Yes";
    }

    @Override
    public String getAction() {
        return null;
    }
}
