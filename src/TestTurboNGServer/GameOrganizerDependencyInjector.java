package TestTurboNGServer;

import TurboNGServer.Game.Game;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ruijorgeclarateixeira on 27/10/14.
 */
@Module (
        injects = Game.class
)
public class GameOrganizerDependencyInjector {
    @Provides
    Game provideGame() {
        return null;
    }
}
