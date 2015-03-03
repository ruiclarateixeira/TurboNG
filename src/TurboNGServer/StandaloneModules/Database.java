package TurboNGServer.StandaloneModules;

import java.sql.*;

/**
 * Created by ruijorgeclarateixeira on 03/03/15.
 * SQLite database module.
 */
public class Database {
    /**
     * Returns a database connection.
     * Not Implemented: Get connection from connection pool!
     */
    public static Connection GetConnection() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:db/TurboNGDatabase.sqlite");
        } catch (ClassNotFoundException e) {
            System.out.println("[Database] Could not find the database library!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("[Database] Could not connect to database!");
            e.printStackTrace();
        }
        return  connection;
    }

    /**
     * Executes a statement that modifies the database content.
     * @param strStatement SQL formatted statement.
     */
    public static void ExecuteStatement(String strStatement) {
        try {
            Connection connection = GetConnection();
            Statement stmt = connection.createStatement();
            System.out.println(stmt.executeUpdate(strStatement));
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("[Database] There was a problem executing the statement!");
            e.printStackTrace();
        }
    }

    /**
     * Performs a query to the database. Returns the value of one of the columns of the first row.
     * @param query Query to be performed.
     * @param columnLabel Label of the column to get the result from.
     * @return A string with the result. Null if no results are found or if there are errors during the query.
     */
    public static String GetStringFromFirstRow(String query, String columnLabel) {
        Connection connection = GetConnection();

        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);

            if (!resultSet.isBeforeFirst())
                return null;

            resultSet.next();
            return resultSet.getString(columnLabel);
        } catch (SQLException e) {
            System.out.println("[Database] There was a problem executing the query!");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Executes a query to the database and returns the number of rows returned.
     * @param fromContent Content to be appended to the FROM statement
     * @param whereContent Content to be appended to the WHERE statement
     * @return Number of rows in the result. Returns -1 if there are any errors executing statement.
     */
    public static int RowCountFromWhere(String fromContent, String whereContent) {
        String sql_query = "SELECT COUNT(USERNAME) as total"
                        + " FROM " + fromContent
                        + " WHERE " + whereContent + ";";

        Connection connection = GetConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql_query);
            return resultSet.getInt("total");
        } catch (SQLException e) {
            System.out.println("[Database] There was a problem executing the query!");
            e.printStackTrace();
        }
        return -1;
    }
}
