package fr.utbm.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Jdbc {

    private static final Logger logger = LoggerFactory.getLogger(Jdbc.class);

    private final String jdbcUrl;
    private final String username;
    private final String password;

    public Jdbc(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("PostgreSQL JDBC driver not found", e);
            throw new RuntimeException("PostgreSQL JDBC driver not found", e);
        }
    }


    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Connection closed.");
            } catch (SQLException e) {
                logger.error("Error closing connection", e);
            }
        }
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            logger.info("Successfully connected to the database.");
        } catch (SQLException e) {
            logger.error("Failed to connect to the database", e);
            throw e;
        }
        return connection;
    }

    public void closeConnection() {
        // You might want to store the connection in the Jdbc class
        // or pass it as an argument to this method.
        logger.warn("closeConnection() called without a connection object.");
    }
}