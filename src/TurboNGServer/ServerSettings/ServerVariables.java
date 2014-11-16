package TurboNGServer.ServerSettings;

import java.util.logging.Logger;

/**
 * Created by ruijorgeclarateixeira on 30/09/14.
 * This class contains static definitions used by the server when it's running
 */
public class ServerVariables {
    public static final Logger requestLogger = Logger.getLogger("requests");
    public static final Logger errorLogger = Logger.getLogger("errors");
    public static final int LISTENING_PORT = 8080;
    public static final int NUMBER_OF_THREADS = 50; // Number of threads in the thread pool
    public static final String PUBLIC_KEYS_PATH = "/Users/ruijorgeclarateixeira/Development/TurboNGServer/sslkeys/turbong.keys";
}
