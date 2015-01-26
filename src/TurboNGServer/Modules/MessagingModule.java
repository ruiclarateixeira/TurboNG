package TurboNGServer.Modules;

import TurboNGServer.Interface.Action;
import TurboNGServer.Player.Player;
import TurboNGServer.Player.PlayersManager;

/**
 * Created by ruijorgeclarateixeira on 26/01/15.
 * Module that allows player to send and receive messages. Public and private.
 */
public abstract class MessagingModule extends InviteByUsernameModule {

    /**
     * Chat message received.
     * @param source Username of the sender.
     * @param message Message received.
     */
    public abstract void chatMessage(String source, String message);

    @Override
    public boolean executeAction(Action action) {
        if(super.executeAction(action)) {
            return true;
        }

        if(action.isValid()
                && action.getValueOf("type") != null
                && action.getValueOf("type").equals("message")) {
            switch (action.getValueOf("action")) {
                case "private":
                    if(action.getValueOf("target") != null && action.getValueOf("message") != null) {
                        ((MessagingModule) PlayersManager.getPlayer(username)).chatMessage(this.username,
                                                                                            action.getValueOf("message"));
                    }
                    break;
                case "public":
                    sendMessageToAll(action.getValueOf("message"));
                    break;
            }
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Send message to all the players except this one
     * @param messageContent Content of the message to be send.
     */
    public void sendMessageToAll(String messageContent) {
        for(Player player : PlayersManager.getAllPlayers()) {
            if(!player.username.equals(this.username))
                ((MessagingModule) player).chatMessage(this.username, messageContent);
        }
    }

}
