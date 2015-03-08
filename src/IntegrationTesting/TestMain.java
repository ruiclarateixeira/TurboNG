package IntegrationTesting;

import TurboNGServer.Player.IPlayerFactory;
import TurboNGServer.Player.Player;
import TurboNGServer.TurboNGServer;

/**
 * Created by ruijorgeclarateixeira on 16/11/14.
 */
public class TestMain {
    public static void main(String[] args) {
        TurboNGServer gameServer = new TurboNGServer("/Users/ruijorgeclarateixeira/Development/TurboNGServer/src/IntegrationTesting/config.properties", null);
        gameServer.start(new IPlayerFactory() {
            @Override
            public Player instantiatePlayer() {
                return new TestPlayer("Rui");
            }
        });
    }
}
