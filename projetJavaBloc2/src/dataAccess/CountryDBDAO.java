package dataAccess;

import exceptions.CountryCreationException;
import model.Country;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDBDAO {
    private static Connection connection;

    public CountryDBDAO() throws SQLException {
        connection = DatabaseConnection.getInstance();
    }

    public Country getCountry(String iso) throws SQLException, CountryCreationException {
        String sqlInstruction = "select * from country where iso = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
        preparedStatement.setString(1, iso);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new Country(resultSet.getString("iso"), resultSet.getString("name"));
    }

}
