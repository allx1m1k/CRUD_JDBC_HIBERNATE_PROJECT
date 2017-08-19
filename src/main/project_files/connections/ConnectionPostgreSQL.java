package main.project_files.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPostgreSQL implements ConnectionToDB {


    /**
     * User's name in PostgreSQL server
     */

//    private final static String USER = "postgres";

    /**
     * User's password in PostgreSQL server
     */

    //  private final static String PASSWORD = "root";

    /**
     * Database's URL
     */

    //private final static String DB_URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
    /**
     * reference of PostgreSQL connection class
     */


    private static ConnectionPostgreSQL connectionPostgreSQL;

    /**
     * Private default constructor
     */

    private ConnectionPostgreSQL() {
    }

    /**
     * This method create instance of this class or return instance if it already exist
     *
     * @return instance of ConnectionPostgreSQL
     */

    public static ConnectionPostgreSQL getInstance() {
        if (connectionPostgreSQL == null) {
            connectionPostgreSQL = new ConnectionPostgreSQL();
        }
        return connectionPostgreSQL;
    }

    /**
     * This method create connection to DB
     *
     * @return connection to DB
     * @throws SQLException in case of connection problems
     */

    @Override
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres",
                "root");

    }

}
