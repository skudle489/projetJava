package dataAccess;

import model.Country;
import model.Locality;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocalityDBDAO {
    private static Connection connection;

    public LocalityDBDAO() throws SQLException {
        connection = DatabaseConnection.getInstance();
    }

    /*public Locality getLocality(String city, String postalCode, String iso) throws SQLException {
        String sqlInstruction = "select * from locality where city = ? and postal_code = ? and iso = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
        preparedStatement.setString(1, city);
        preparedStatement.setString(2, postalCode);
        preparedStatement.setString(3, iso);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        //Country country =

    }*/
}
