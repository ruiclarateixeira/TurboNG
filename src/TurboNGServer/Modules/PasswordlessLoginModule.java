package TurboNGServer.Modules;

import TurboNGServer.Interface.Action;
import TurboNGServer.Player.Player;
import TurboNGServer.Player.PlayersManager;

import javax.management.openmbean.KeyAlreadyExistsException;

/**
 * Created by ruijorgeclarateixeira on 15/01/15.
 * Password-less login module.
 */
public abstract class PasswordlessLoginModule extends Player {
    @Override
    public boolean executeAction(Action action) {
        if(super.executeAction(action))
            return true;
        else if (action.getValueOf("type") == null || action.getValueOf("action") == null || !action.getValueOf("type").equals("login"))
            return false;

        if (action.getValueOf("action").equals("login")) {
            if (action.getValueOf("username") != null) {
                username = action.getValueOf("username");
                try {
                    addToOnlinePlayers();
                    sendToClient(new Action("{type:login, action:successful, username:" + this.username + "}"));
                    for (Player player : PlayersManager.getAllPlayers()) {
                        if (!player.username.equals(username))
                            player.sendToClient(new Action("{type:lobby, action:new_online, username:" + this.username + "}"));
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (KeyAlreadyExistsException e) {
                    sendToClient(new Action("{type:login, action:username_exists}"));
                }
            }
        }
        else if (action.getValueOf("action").equals("logout")) {
            sendToClient(new Action("{type:login, action:logout}"));
            PlayersManager.removePlayer(this.username);
            this.disconnect();
        }

        return true;
    }
}
