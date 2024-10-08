package o2technologies_utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {

    private static final String CONNECTION_URL = "jdbc:sqlserver://10.0.2.34:1433;Database=MailScan_Dev;User=mailscan;Password=MailScan@343260;encrypt=true;trustServerCertificate=true";

    // Static block to load the driver class
    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  // Proper logging should be used in real scenarios
        }
    }

    public static ResultSet executeQuery(String query) throws SQLException {
        Connection connection = DriverManager.getConnection(CONNECTION_URL);
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static void closeResources(Connection connection, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();  // Proper logging should be used in real scenarios
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();  // Proper logging should be used in real scenarios
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();  // Proper logging should be used in real scenarios
            }
        }
    }
}
