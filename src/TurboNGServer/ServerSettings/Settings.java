package TurboNGServer.ServerSettings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by ruijorgeclarateixeira on 10/01/15.
 */
public class Settings {
    public static boolean validSettings = false;
    public static boolean SSL;
    public static int NumberOfThreads, ListeningPort;
    public static String SSLKeysPath;

    public static void Load(String propertiesPath) {
        try {
            Properties properties = new Properties();
            FileInputStream in = new FileInputStream(propertiesPath);
            properties.load(in);
                ListeningPort = Integer.parseInt((String) properties.get("PORT"));
                NumberOfThreads = Integer.parseInt((String) properties.get("THREADS"));

                SSL = Boolean.parseBoolean((String)properties.get("SSL"));

                if(SSL)
                    SSLKeysPath = (String)properties.get("KEYSTORE_PATH");
            in.close();
            validSettings = true;
        } catch (FileNotFoundException e) {
            System.err.println("File " + propertiesPath + " not found.");
            validSettings = false;
        } catch (IOException e) {
            e.printStackTrace();
            validSettings = false;
        }
    }
}
