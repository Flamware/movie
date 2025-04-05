package fr.utbm.conf;
import java.sql.Connection;
import java.sql.SQLException;

public class Jdbc {
    Connection connection;
    private String url;
    private String username;
    private String password;

    public Jdbc(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.apache.derby.jdbc.ClientDriver");
                connection = java.sql.DriverManager.getConnection(url, username, password);
                connection.setAutoCommit(false);
                System.out.println("Connected to the database");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {

                }
            }
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}