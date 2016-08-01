package com.ruiteixeira.turbong.listener;

import com.ruiteixeira.turbong.game.Game;
import com.ruiteixeira.turbong.Interface.Action;
import com.ruiteixeira.turbong.player.Player;
import junit.framework.TestCase;

public class FriendsModuleTest extends TestCase {

    Player player = new FriendsModule() {
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

    public void setUp() throws Exception {
        super.setUp();
        player.username = "rui";
    }

    public void testExecuteAction_NotLoggedIn_False() throws Exception {
        player.username = null;
        assertFalse(player.executeAction(new Action("{type:friends,action:test}")));
    }

    public void testExecuteAction_Null_False() throws Exception {
        assertFalse(player.executeAction(null));
    }

    public void testExecuteAction_InvalidAction_False() throws Exception {
        assertFalse(player.executeAction(new Action("{aasdlfjasdf}")));
    }

    public void testAddFriend_ValidActionWrongFields_True() throws Exception {

        assertTrue(player.executeAction(new Action("{type:friends,action:test}")));
    }

    public void testFriendAdded() throws Exception {

    }

    public void testRemoveFriend() throws Exception {

    }

    public void testFriendRemoved() throws Exception {

    }

    public void testGetFriends() throws Exception {

    }

    public void testSendFriends() throws Exception {

    }

    public void testCreateFriendsTable() throws Exception {

    }
}