package TurboNGServerTests.DependencyInjection;

import TurboNGServer.Player.PlayerLobby;
import TurboNGServer.Player.Player;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ruijorgeclarateixeira on 02/12/14.
 */
@Module(
        injects = PlayerLobby.class,
        library = true
)
public class LobbyInterfaceDITest {
    @Provides
    Player providePlayer() {
        return new TestPlayer();
    }
}