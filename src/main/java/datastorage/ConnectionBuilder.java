package datastorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class for managing the {@link Connection} to the database.
 * Follows a Singleton pattern.
 */
public class ConnectionBuilder {
    // region Fields
    private static Connection conn;
    // endregion

    // region Constructor

    /**
     * private constructor for a {@link ConnectionBuilder} object
     */
    private ConnectionBuilder() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            System.out.println("Working Directory = " + System.getProperty("user.dir"));

            conn = DriverManager.getConnection("jdbc:hsqldb:db/nursingHomeDB;user=SA;password=SA");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Treiberklasse konnte nicht gefunden werden!");
        } catch (SQLException e) {
            System.out.println("Verbindung zur Datenbank konnte nicht aufgebaut werden!");
            e.printStackTrace();
        }
    }
    // endregion

    // region Methods

    /**
     * Gets the active {@link Connection}.
     * Creates a {@link ConnectionBuilder} object if necessary
     * @return the active connection
     */
    public static Connection getConnection() {
        if (conn == null) {
            new ConnectionBuilder();
        }
        return conn;
    }

    /**
     * closes the active connection to the database
     */
    public static void closeConnection() {
        try {
            if(conn != null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // endregion
}
