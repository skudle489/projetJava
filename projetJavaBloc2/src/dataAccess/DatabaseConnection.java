package dataAccess;

import exceptions.DataAccessException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    public static Connection getInstance() throws DataAccessException {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotel", "root", "root");
            }
            return connection;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
