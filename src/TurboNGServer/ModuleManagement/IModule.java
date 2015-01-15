package TurboNGServer.ModuleManagement;

import TurboNGServer.Interface.Action;

/**
 * Created by ruijorgeclarateixeira on 11/01/15.
 * Represents a module. It is notified when the required action is
 */
public interface IModule {
    ListenerModulesManager manager = null;
    /**
     * Called when an action of the correct type is received.
     * @param action Action received.
     */
    public abstract void action(Action action);

    /**
     * Returns the type of action this module listens to.
     * @return Type of action.
     */
    public abstract String getActionType();
}
