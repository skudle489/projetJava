package dataAccess;

import exceptions.AmenityCreationException;
import exceptions.GetAllAmenitiesFromBedroomException;
import model.Amenity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BedroomOwnsAmenityDBDAO {
    private Connection connection;

    public BedroomOwnsAmenityDBDAO() {
        connection = DatabaseConnection.getInstance();
    }

    public ArrayList<Amenity> getAllAmenitiesFromBedroom(int bedroomNumber, int hotelId) throws GetAllAmenitiesFromBedroomException {
        try {

            String sqlInstruction = "select * from bedroom_owns_amenity where bedroom_number = ? and hotel = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, bedroomNumber);
            preparedStatement.setInt(2, hotelId);
            ResultSet resultSet = preparedStatement.executeQuery();

            String amenity;
            ArrayList<Amenity> amenities = new ArrayList<>();

            while (resultSet.next()) {
                amenity = resultSet.getString("amenity");
                amenities.add(new Amenity(amenity));
            }
            return amenities;
        } catch (SQLException | AmenityCreationException exception){
            throw new GetAllAmenitiesFromBedroomException("Erreur de lecture de tous les commodités d'une chambre " + exception.getMessage());
        }
    }
}
