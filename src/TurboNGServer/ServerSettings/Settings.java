package TurboNGServer.ServerSettings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by ruijorgeclarateixeira on 10/01/15.
 */
public class Settings {
    public static boolean SSL;

    public static void Load() {
        try {
            Properties properties = new Properties();
            FileInputStream in = new FileInputStream("config.properties");
            properties.load(in);
                SSL = Boolean.parseBoolean((String)properties.get("SSL"));
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
