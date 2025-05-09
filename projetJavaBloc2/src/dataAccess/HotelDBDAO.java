package dataAccess;

import model.Hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HotelDBDAO {
    private static Connection connection;

    public HotelDBDAO() throws SQLException {
        connection = DatabaseConnection.getInstance();
    }

    /*public Hotel getHotel(int id) throws SQLException {
        String sqlInstruction = "select * from hotel where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

    }*/

}
