package TurboNGServer.ModuleManagement;

import TurboNGServer.Interface.Action;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by ruijorgeclarateixeira on 11/01/15.
 */
public class ListenerModulesManager {
    private static HashMap<String,IModule> listenerModules = new HashMap<>();
    public static void load() {
        System.out.println("[ListenerModules] Loading modules");
        try {
            Properties properties = new Properties();
            FileInputStream lmConfigFile = new FileInputStream("/Users/ruijorgeclarateixeira/Development/TurboNGServer/src/TurboNGServer/ModuleManagement/listenermodules.properties");
            properties.load(lmConfigFile);

            for(String propertyName : properties.stringPropertyNames()) {
                Class Module = Class.forName((String)properties.get(propertyName));
                IModule module = (IModule) Module.newInstance();
                listenerModules.put(module.getActionType(),module);
            }

            lmConfigFile.close();
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static String action(Action action) {
        return null;
    }
}
