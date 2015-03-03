package TurboNGServer.ListenerModules;

import TurboNGServer.Interface.Action;
import TurboNGServer.Player.Player;

/**
 * Created by ruijorgeclarateixeira on 03/03/15.
 */
public abstract class UsernamePasswordLoginModule extends Player {
    /**
     * Received action to execute.
     *
     * @param action Action o execute.
     * @return True if this method can execute the action. False otherwise.
     */
    @Override
    public boolean executeAction(Action action) {
        return super.executeAction(action);
    }
}
