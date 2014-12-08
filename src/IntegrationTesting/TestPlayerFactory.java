package IntegrationTesting;

import TurboNGServer.Player.IPlayerFactory;
import TurboNGServer.Player.Player;

/**
 * Created by ruijorgeclarateixeira on 08/12/14.
 */
public class TestPlayerFactory implements IPlayerFactory {
    @Override
    public Player instantiatePlayer() {
        return new TestPlayer();
    }
}
