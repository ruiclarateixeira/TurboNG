package TurboNGServer.Modules;

import TurboNGServer.Interface.Action;
import TurboNGServer.Player.Player;

/**
 * Created by ruijorgeclarateixeira on 15/01/15.
 */
public abstract class PasswordlessLoginPlayer extends Player {
    @Override
    public boolean executeAction(Action action) {
        if(super.executeAction(action)) {
            if (action.getValueOf("action").equals("login")) {
                if (action.getValueOf("username") != null) {
                    username = action.getValueOf("username");
                    try {
                        addToOnlinePlayers();
                        sendToClient(new Action("{action:loginSuccessful}"));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }
                return true;
            }
            else
                return false;
        }
        else {
            return true;
        }
    }
}
