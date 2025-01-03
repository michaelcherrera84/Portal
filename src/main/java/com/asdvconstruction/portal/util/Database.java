package com.asdvconstruction.portal.util;

import com.asdvconstruction.portal.model.User;
import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Michael C. Herrera
 */
public class Database {

    /**
     * Opens a database connection.
     *
     * @param user database user
     * @return a database connection
     * @throws SQLException if there is a database access error or other errors
     */
    public static Connection connection(User user) throws SQLException {

        // Load the Driver. This is required for the DriverManager to connect to the database.
        @SuppressWarnings("unused") Driver driver = new Driver();
        String ip = "localhost";
        String database = "ASDV_Construction";
        String url = "jdbc:mysql://" + ip + ":8889/" + database + "?allowPublicKeyRetrieval=true&useSSL=false";

        return DriverManager.getConnection(url, user.getUsername(), user.getPassword());
    }

    /**
     * Closes a database connection.
     *
     * @param connection the connection
     * @throws SQLException if there is a database access error or other errors
     */
    @SuppressWarnings("unused")
    public static void closeConnection(Connection connection) throws SQLException {

        connection.close();
    }
}
