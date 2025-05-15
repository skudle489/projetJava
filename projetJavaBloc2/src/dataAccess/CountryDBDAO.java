package dataAccess;

import exceptions.CountryCreationException;
import exceptions.DataAccessException;
import exceptions.GetAllCountryException;
import model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CountryDBDAO {
    private static Connection connection;

    public CountryDBDAO() throws DataAccessException {
        connection = DatabaseConnection.getInstance();
    }

    public ArrayList<Country> getAllCountries() throws GetAllCountryException {

        try {
            String sqlInstruction = "select * from country";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();
            String iso;
            String name;
            ArrayList<Country> countries = new ArrayList<>();
            Country country;

            while (data.next()) {
                iso = data.getString("iso");
                name = data.getString("name");
                country = new Country(iso, name);
                countries.add(country);
            }
            return countries;
        } catch (SQLException | CountryCreationException exception){
            throw new GetAllCountryException("Erreur lors de la récupération des pays");
        }
    }

}
