// Code generated by dagger-compiler.  Do not edit.
package DependencyInjection;

import dagger.internal.BindingsGroup;
import dagger.internal.ModuleAdapter;
import dagger.internal.ProvidesBinding;
import javax.inject.Provider;

/**
 * A manager of modules and provides adapters allowing for proper linking and
 * instance provision of types served by {@code @Provides} methods.
 */
public final class LobbyDependencyInjector$$ModuleAdapter extends ModuleAdapter<LobbyDependencyInjector> {
  private static final String[] INJECTS = { "members/TurboNGServer.Interface.LobbyInterface", };
  private static final Class<?>[] STATIC_INJECTIONS = { };
  private static final Class<?>[] INCLUDES = { };

  public LobbyDependencyInjector$$ModuleAdapter() {
    super(DependencyInjection.LobbyDependencyInjector.class, INJECTS, STATIC_INJECTIONS, false /*overrides*/, INCLUDES, true /*complete*/, false /*library*/);
  }

  @Override
  public LobbyDependencyInjector newModule() {
    return new DependencyInjection.LobbyDependencyInjector();
  }

  /**
   * Used internally obtain dependency information, such as for cyclical
   * graph detection.
   */
  @Override
  public void getBindings(BindingsGroup bindings, LobbyDependencyInjector module) {
    bindings.contributeProvidesBinding("TurboNGServer.Lobby.Player", new ProvideUserProvidesAdapter(module));
    bindings.contributeProvidesBinding("TurboNGServer.Lobby.NewsFeed", new ProvideNewsFeedProvidesAdapter(module));
  }

  /**
   * A {@code Binding<TurboNGServer.Lobby.Player>} implementation which satisfies
   * Dagger's infrastructure requirements including:
   *
   * Being a {@code Provider<TurboNGServer.Lobby.Player>} and handling creation and
   * preparation of object instances.
   */
  public static final class ProvideUserProvidesAdapter extends ProvidesBinding<TurboNGServer.Lobby.Player>
      implements Provider<TurboNGServer.Lobby.Player> {
    private final LobbyDependencyInjector module;

    public ProvideUserProvidesAdapter(LobbyDependencyInjector module) {
      super("TurboNGServer.Lobby.Player", NOT_SINGLETON, "DependencyInjection.LobbyDependencyInjector", "provideUser");
      this.module = module;
      setLibrary(false);
    }

    /**
     * Returns the fully provisioned instance satisfying the contract for
     * {@code Provider<TurboNGServer.Lobby.Player>}.
     */
    @Override
    public TurboNGServer.Lobby.Player get() {
      return module.provideUser();
    }
  }

  /**
   * A {@code Binding<TurboNGServer.Lobby.NewsFeed>} implementation which satisfies
   * Dagger's infrastructure requirements including:
   *
   * Being a {@code Provider<TurboNGServer.Lobby.NewsFeed>} and handling creation and
   * preparation of object instances.
   */
  public static final class ProvideNewsFeedProvidesAdapter extends ProvidesBinding<TurboNGServer.Lobby.NewsFeed>
      implements Provider<TurboNGServer.Lobby.NewsFeed> {
    private final LobbyDependencyInjector module;

    public ProvideNewsFeedProvidesAdapter(LobbyDependencyInjector module) {
      super("TurboNGServer.Lobby.NewsFeed", NOT_SINGLETON, "DependencyInjection.LobbyDependencyInjector", "provideNewsFeed");
      this.module = module;
      setLibrary(false);
    }

    /**
     * Returns the fully provisioned instance satisfying the contract for
     * {@code Provider<TurboNGServer.Lobby.NewsFeed>}.
     */
    @Override
    public TurboNGServer.Lobby.NewsFeed get() {
      return module.provideNewsFeed();
    }
  }
}