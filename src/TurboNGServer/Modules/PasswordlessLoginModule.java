package TurboNGServer.Modules;

import TurboNGServer.Interface.Action;
import TurboNGServer.ModuleManagement.IModule;

/**
 * Created by ruijorgeclarateixeira on 15/01/15.
 */
public class PasswordlessLoginModule implements IModule {
    public PasswordlessLoginModule() {
        System.out.println("Passwordless login instantiated!");
    }

    @Override
    public void action(Action action) {

    }

    @Override
    public String getActionType() {
        return "login";
    }
}
