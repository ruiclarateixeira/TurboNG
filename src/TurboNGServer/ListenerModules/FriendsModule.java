package TurboNGServer.ListenerModules;

import TurboNGServer.Interface.Action;
import TurboNGServer.Player.Player;
import TurboNGServer.StandaloneModules.Database;

import java.util.ArrayList;

/**
 * Created by ruijorgeclarateixeira on 08/03/15.
 * Friends listener modules. Allows user to keep a list of special users. (Friends)
 * Requires a database to store friends relationship.
 */
public abstract class FriendsModule extends Player {
    /**
     * Received action to execute.
     *
     * @param action Action o execute.
     * @return True if this method can execute the action. False otherwise.
     */
    @Override
    public boolean executeAction(Action action) {
        if (action == null)
            return false;

        if(super.executeAction(action))
            return true;
        else if (this.username == null) {
            sendToClient(new Action("{type:friends,action:friend_not_added,message:'Not logged in'}"));
            return false;
        }
        else if (action.getValueOf("type") == null
                || action.getValueOf("action") == null
                || !action.getValueOf("type").equals("friends"))
            return false;

        switch (action.getValueOf("action")) {
            case "add_friend":
                if (action.getValueOf("username") != null
                        && action.getValueOf("username").matches(usernameRegex)) {
                    addFriend(action.getValueOf("username"));
                } else {
                    sendToClient(new Action("{type:friends,action:friend_not_added,message:'Invalid friend username'}"));
                }
                break;
            case "remove_friend":
                if (action.getValueOf("username") != null && action.getValueOf("username").matches(usernameRegex)) {
                    removeFriend(action.getValueOf("username"));
                } else {
                    sendToClient(new Action("{type:friends,action:friend_not_removed,message:'Invalid friend username'}"));
                }
                break;
            case "show_friends":
                ArrayList<String> friends = getFriends();
                sendFriends(friends);
                break;
            default:
                sendToClient(new Action("{type:friends,action:not_recognized}"));
        }
        return true;
    }

    /**
     * Add friendship to database if it doesn't exist.
     * @param friendUsername Username of required friend.
     */
    public void addFriend(String friendUsername) {
        int noOfRows = Database.RowCountFromWhere("FRIENDS", "PLAYER_USERNAME = '"
                                                + this.username
                                                + "' AND FRIEND_USERNAME = '"
                                                + friendUsername + "'");

        if (noOfRows == 0) {
            String sql_statement = "INSERT INTO FRIENDS (PLAYER_USERNAME,FRIEND_USERNAME)" +
                    " VALUES ('" + this.username + "','" + friendUsername + "');";
            Database.ExecuteStatement(sql_statement);
            friendAdded(friendUsername);
        }
    }

    /**
     * Called when a new friend is successfully added.
     * @param friendUsername Username of friend added
     */
    public void friendAdded(String friendUsername) {
        sendToClient(new Action("{type:friends,action:friend_added,username:" + friendUsername + "}"));
    }

    /**
     * Remove friendship from database.
     * @param friendUsername Username of friend to remove
     */
    public void removeFriend(String friendUsername) {
        int noOfRows = Database.RowCountFromWhere("FRIENDS", "PLAYER_USERNAME = '"
                + this.username
                + "' AND FRIEND_USERNAME = '"
                + friendUsername + "'");

        if (noOfRows > 0) {
            String sql_statement = "DELETE FROM FRIENDS WHERE PLAYER_USERNAME = '"
                    + this.username + "' AND FRIEND_USERNAME = '" + friendUsername + "';";
            Database.ExecuteStatement(sql_statement);
            friendRemoved(friendUsername);
        }
    }

    /**
     * Called when a friend is successfully removed
     * @param friendUsername Username of player successfully removed.
     */
    public void friendRemoved(String friendUsername) {
        sendToClient(new Action("{type:friends,action:friend_removed,username:" + friendUsername + "}"));
    }

    /**
     * Gets friends from database
     * @return List of friends' usernames
     */
    public ArrayList<String> getFriends() {
        return Database.GetStringsFromColumn("FRIENDS", "FRIEND_USERNAME", "PLAYER_USERNAME = '" + this.username + "'");
    }

    /**
     * Send friends list to client
     * @param friends List of friends' usernames
     */
    public void sendFriends(ArrayList<String> friends) {
        String friendsStr = "[";
        for (String friendUsername : friends) {
            friendsStr += (friendUsername + ",");
        }
        if (friendsStr.length() > 1) {
            friendsStr = friendsStr.substring(0, friendsStr.length()-1);
        }
        friendsStr += "]";
        System.out.println("{type:friends,action:show_friends,friends:" + friendsStr + "}");
        sendToClient(new Action("{type:friends,action:show_friends,friends:" + friendsStr + "}"));
    }

    /**
     * Creates a friends table to be used by this class. Uses sqlite syntax query.
     */
    public static void CreateFriendsTable() {
        String sql_statement = "CREATE TABLE FRIENDS " +
                                "(ROWID             INTEGER     PRIMARY KEY     AUTOINCREMENT," +
                                " PLAYER_USERNAME   TEXT        NOT NULL, " +
                                " FRIEND_USERNAME   TEXT        NOT NULL)";
        Database.ExecuteStatement(sql_statement);
    }
}
