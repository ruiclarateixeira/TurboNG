package IntegrationTesting;

import TurboNGServer.ModuleManagement.ListenerModulesManager;

/**
 * Created by ruijorgeclarateixeira on 16/11/14.
 */
public class TestMain {
    public static void main(String[] args) {
        //TurboNGServer gameServer = new TurboNGServer("/Users/ruijorgeclarateixeira/Development/TurboNGServer/src/TurboNGServer/ServerSettings/config.properties", null);
        //gameServer.start(new TestPlayerFactory());
        ListenerModulesManager.load();
    }
}
