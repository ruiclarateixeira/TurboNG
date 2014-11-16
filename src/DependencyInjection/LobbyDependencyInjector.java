package DependencyInjection;

import Chesse.ChessPlayer;
import TurboNGServer.Interface.LobbyInterface;
import TurboNGServer.Lobby.NewsFeed;
import TurboNGServer.Lobby.Player;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ruijorgeclarateixeira on 15/10/14.
 * Dependency injector Modules
 */
@Module (
        injects = LobbyInterface.class
)
public class LobbyDependencyInjector {
    @Provides
    Player provideUser() {
        return new ChessPlayer();
    }

    @Provides
    NewsFeed provideNewsFeed() {
        return null;
    }
}
