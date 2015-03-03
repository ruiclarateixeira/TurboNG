package TurboNGServer.ListenerModules;

import TurboNGServer.Interface.Action;
import TurboNGServer.Player.Player;
import TurboNGServer.StandaloneModules.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ruijorgeclarateixeira on 03/03/15.
 * Username needs to be alphanumeric.
 */
public abstract class UsernamePasswordLoginModule extends Player {
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
                    sendToClient(new Action("{type:login,action:login_successful,username:" + this.username + "}"));
                }
                else {
                    sendToClient(new Action("{type:login,action:login_unsuccessful,message:'Wrong Password'}"));
                }
            }
        }
    }

    /**
     * Executes a register action.
     * @param action Register Action
     */
    private void register(Action action) {
        /**** Check username against more strict rules with regex ****/
        if (action.getValueOf("username") != null && action.getValueOf("password") != null
                && !action.getValueOf("username").equals("") && !action.getValueOf("username").equals("")) {
            if (!IsPlayerRegistered(action.getValueOf("username"))) {
                RegisterPlayer(action.getValueOf("username"), action.getValueOf("password"));
                sendToClient(new Action("{type:login,action:register_successful, username:"
                        + action.getValueOf("username") + " }"));
            }
            else {
                sendToClient(new Action("{type:register,action:register_unsuccessful,message:'Username in use'}"));
            }
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
