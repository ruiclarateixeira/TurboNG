package DependencyInjection;

import IntegrationTesting.TestPlayer;
import TurboNGServer.Player.PlayerLobby;
import TurboNGServer.Player.Player;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ruijorgeclarateixeira on 15/10/14.
 * Dependency injector Modules
 */

@Module (
        injects = PlayerLobby.class,
        library = true
)
public class LobbyDependencyInjector {
    @Provides
    Player provideUser() {
        return new TestPlayer();
    }
}

