package dataAccess;

import exceptions.DataAccessException;
import exceptions.GetAllLocalityWithCountryException;
import exceptions.LocalityCreationException;
import model.Locality;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LocalityDBDAO {
    private static Connection connection;

    public LocalityDBDAO() throws DataAccessException {
        connection = DatabaseConnection.getInstance();
    }

    public ArrayList<Locality> getAllLocalityWithCountry(String countryIso) throws GetAllLocalityWithCountryException {
        try {
            ArrayList<Locality> localities = new ArrayList<>();
            String sqlInstruciton = "select * from locality where country = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruciton);
            preparedStatement.setString(1, countryIso);

            ResultSet resultSet = preparedStatement.executeQuery();
            String postalCode;
            String city;

            while (resultSet.next()) {
                postalCode = resultSet.getString("postal_code");
                city = resultSet.getString("city");
                localities.add(new Locality(city, countryIso, postalCode));
            }
            return localities;

        } catch (SQLException | LocalityCreationException exception){
            throw new GetAllLocalityWithCountryException("Erreur lors de la lecture des localités avec un pays");
        }
    }

}
