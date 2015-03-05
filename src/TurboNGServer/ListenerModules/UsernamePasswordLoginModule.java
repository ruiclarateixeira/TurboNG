package TurboNGServer.ListenerModules;

import TurboNGServer.Interface.Action;
import TurboNGServer.Player.Player;
import TurboNGServer.StandaloneModules.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ruijorgeclarateixeira on 03/03/15.
 * Username and Password Login Listener Module.
 * Default:
 * Username: Between 3 and 16 characters accepts alphanumeric characters, hyphens and underscores
 * Password: Between 6 and 18 characters accepts alphanumeric characters, hyphens and underscores
 */
public abstract class UsernamePasswordLoginModule extends Player {
    /**
     * Regex username is matched against on registering.
     */
    public static String usernameRegex = "^[a-z0-9_-]{3,16}$";

    /**
     * Regex password is matched against on registering.
     */
    public static String passwordRegex = "^[a-z0-9_-]{6,18}$";

    /**
     * Received action to execute.
     *
     * @param action Action o execute.
     * @return True if this method can execute the action. False otherwise.
     */
    @Override
    public boolean executeAction(Action action) {
        if (super.executeAction(action))
            return true;

        if (action.getValueOf("type") != null && action.getValueOf("type").equals("login")) {
            switch (action.getValueOf("action")) {
                case "login":
                    login(action);
                    break;
                case "register":
                    register(action);
                    break;
                case "unregister":
                    break;
                default:
                    sendToClient(Action.ERROR_101_ACTION_NOT_FOUND);
            }
            return true;
        }
        return false;
    }

    /**
     * Called when the user successfully logs in. The log in action is sent as parameter.
     * @param action Successful login action.
     */
    public void loggedIn(Action action) {
        sendToClient(new Action("{type:login,action:login_successful,username:" + this.username + "}"));
    }

    /**
     * Called when the user successfully registers.
     * @param action Successful register action.
     */
    public void registered(Action action) {
        sendToClient(new Action("{type:login,action:register_successful, username:"
                + action.getValueOf("username") + " }"));
    }

    /**
     * Executes a login action.
     * @param action Login Action
     */
    private void login(Action action) {
        if (action.getValueOf("username") != null && action.getValueOf("password") != null) {
            // Check if already logged in
            if (this.username != null) {
                sendToClient(new Action("{type:login,action:login_unsuccessful,message:'Already Logged In'}"));
                return;
            }

            if (IsPlayerRegistered(action.getValueOf("username"))) {
                if (action.getValueOf("password").equals(GetPasswordHashFor(action.getValueOf("username")))) {
                    this.username = action.getValueOf("username");
                    loggedIn(action);
                }
                else {
                    sendToClient(new Action("{type:login,action:login_unsuccessful,message:'Wrong Password'}"));
                }
            } else {
                sendToClient(new Action("{type:login,action:login_unsuccessful,message:'Player not registered!'}"));
            }
        }
    }

    /**
     * Executes a register action.
     * Username: Between 3 and 16 characters accepts alphanumeric characters, hyphens and underscores
     * Password: Between 6 and 18 characters accepts alphanumeric characters, hyphens and underscores
     * @param action Register Action
     * Regular expressions taken from:
     */
    private void register(Action action) {
        if (action.getValueOf("username") != null
                && action.getValueOf("password") != null
                && !action.getValueOf("username").matches(usernameRegex)
                && !action.getValueOf("password").matches(passwordRegex)) {
            if (!IsPlayerRegistered(action.getValueOf("username"))) {
                RegisterPlayer(action.getValueOf("username"), action.getValueOf("password"));
                registered(action);
            }
            else {
                sendToClient(new Action("{type:login,action:register_unsuccessful,message:'Username in use'}"));
            }
        } else {
            sendToClient(new Action("{type:login,action:register_unsuccessful,message:" +
                                    "'Username and Password are not valid'}"));
        }
    }

    /**
     * Creates the table for players needed to store the login credentials
     */
    public static void CreatePlayersDBTable() {
        String sql_statement = "CREATE TABLE PLAYERS " +
                "(ROWID INTEGER PRIMARY KEY     AUTOINCREMENT," +
                " USERNAME           TEXT    NOT NULL, " +
                " PASSWORD       NVARCHAR(128))";
        Database.ExecuteStatement(sql_statement);
    }

    /**
     * Inserts player into database
     */
    private static void RegisterPlayer(String username, String password) {
        // HASH PASSWORD

        String sql_statement = "INSERT INTO PLAYERS (USERNAME,PASSWORD)" +
                                " VALUES ('" + username + "','" +
                                            password + "');";
        Database.ExecuteStatement(sql_statement);
    }

    private static boolean IsPlayerRegistered(String username) {
        int numberOfPlayers = Database.RowCountFromWhere("PLAYERS", "USERNAME='" + username + "'");
        return numberOfPlayers > 0;
    }

    private static String GetPasswordHashFor(String username) {
        String sql_query = "SELECT PASSWORD" +
                " FROM PLAYERS" +
                " WHERE USERNAME = '" + username + "';";

        return Database.GetStringFromFirstRow(sql_query, "PASSWORD");
    }
}
