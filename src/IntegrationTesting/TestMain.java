package IntegrationTesting;

import TurboNGServer.Game.Game;
import TurboNGServer.Interface.Action;
import TurboNGServer.ListenerModules.UsernamePasswordLoginModule;
import TurboNGServer.Player.IPlayerFactory;
import TurboNGServer.Player.Player;
import TurboNGServer.TurboNGServer;

/**
 * Created by ruijorgeclarateixeira on 16/11/14.
 */
public class TestMain {
    public static void main(String[] args) {
        UsernamePasswordLoginModule player = new UsernamePasswordLoginModule() {
            @Override
            public void sendToClient(Action action) {
                System.out.println(action);
            }

            @Override
            public void gameInviteReceived(Game game, String source) {

            }

            @Override
            public void gameStarted(Game requestingGame) {

            }

            @Override
            public void disconnect() {

            }

            @Override
            public Game initGame() {
                return null;
            }

            @Override
            public void turn() {

            }
        };
        UsernamePasswordLoginModule player1 = new UsernamePasswordLoginModule() {
            @Override
            public void sendToClient(Action action) {
                System.out.println(action);
            }

            @Override
            public void gameInviteReceived(Game game, String source) {

            }

            @Override
            public void gameStarted(Game requestingGame) {

            }

            @Override
            public void disconnect() {

            }

            @Override
            public Game initGame() {
                return null;
            }

            @Override
            public void turn() {

            }
        };
        System.out.println(player.executeAction(new Action("{type:login,action:login,username:rui,password:password}")));
        System.out.println(player1.executeAction(new Action("{type:login,action:login,username:rui,password:password}")));
    }
}
