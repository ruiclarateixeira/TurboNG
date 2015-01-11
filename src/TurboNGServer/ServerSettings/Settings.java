package TurboNGServer.ServerSettings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by ruijorgeclarateixeira on 10/01/15.
 */
public class Settings {
    public static boolean SSL;
    public static int NumberOfThreads, ListeningPort;
    public static String SSLKeysPath;

    public static void Load() {
        try {
            Properties properties = new Properties();
            FileInputStream in = new FileInputStream("config.properties");
            properties.load(in);
                ListeningPort = Integer.parseInt((String) properties.get("PORT"));
                NumberOfThreads = Integer.parseInt((String) properties.get("THREADS"));

                SSL = Boolean.parseBoolean((String)properties.get("SSL"));

                if(SSL)
                    SSLKeysPath = (String)properties.get("KEYSTORE_PATH");
            in.close();
        } catch (FileNotFoundException e) {
            System.err.println("File config.properties not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
