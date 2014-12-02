package TurboNGServerTests.DependencyInjection;

import Chesse.ChessPlayer;
import TurboNGServer.Interface.LobbyInterface;
import TurboNGServer.Lobby.NewsFeed;
import TurboNGServer.Lobby.Player;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ruijorgeclarateixeira on 02/12/14.
 */
@Module(
        injects = LobbyInterface.class
)
public class LobbyInterfaceDITest {
    @Provides
    Player providePlayer() {
        return new TestPlayer();
    }

    @Provides
    NewsFeed provideNewsFeed() {
        return null;
    }
}