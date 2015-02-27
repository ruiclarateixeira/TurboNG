package TurboNGServer.ListenerModules;

import TurboNGServer.Interface.Action;
import TurboNGServer.Player.Player;
import TurboNGServer.Player.PlayersManager;

/**
 * Created by ruijorgeclarateixeira on 26/01/15.
 * Module that allows player to send and receive messages. Public and private.
 */
public abstract class MessagingModule extends Player {

    /**
     * Chat message received.
     * @param public_message True if the message is public.
     * @param source Username of the sender.
     * @param message Message received.
     */
    public abstract void chatMessage(Boolean public_message, String source, String message);

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
                        sendMessage(action.getValueOf("target"), action.getValueOf("message"));
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
     * Send private message to a single target.
     * @param target Username of the target of the message.
     * @param content Content of the message.
     */
    public void sendMessage(String target, String content) {
        ((MessagingModule) PlayersManager.getPlayer(target)).chatMessage(false, this.username, content);
    }

    /**
     * Send message to all the players except this one
     * @param messageContent Content of the message to be send.
     */
    public void sendMessageToAll(String messageContent) {
        for(Player player : PlayersManager.getAllPlayers()) {
            if(!player.username.equals(this.username))
                ((MessagingModule) player).chatMessage(true, this.username, messageContent);
        }
    }

}
