package com.ruiteixeira.turbong.player;

/**
 * Created by ruijorgeclarateixeira on 08/12/14.
 * This is a representation of a player factory. Developer needs to
 * implement this interface and pass an instance of the implementation
 * to the server process.
 */
public interface IPlayerFactory {
    /**
     * Returns an instance of server. The implementation should return the
     * implementation of Play of the developer.
     * @return An instance of player.
     */
    public abstract Player instantiatePlayer();
}
