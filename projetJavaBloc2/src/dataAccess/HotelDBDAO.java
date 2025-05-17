package dataAccess;

import exceptions.*;
import model.Hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HotelDBDAO {
    private Connection connection;

    public HotelDBDAO() throws DataAccessException {
        connection = DatabaseConnection.getInstance();
    }

    public ArrayList<Hotel> getAllHotels() throws GetAllHotelsException {
        try {
            String sqlInstruction = "select * from hotel";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();
            int id;
            String street;
            int streetNumber;
            int stars;
            String city;
            String postalCode;
            String countryIso;
            String name;
            ArrayList<Hotel> hotels = new ArrayList<>();

            while (data.next()) {
                id = data.getInt("id");
                street = data.getString("street");
                streetNumber = data.getInt("street_number");
                stars = data.getInt("stars");
                name = data.getString("name");
                city = data.getString("city");
                postalCode = data.getString("postal_code");
                countryIso = data.getString("country");
                hotels.add(new Hotel(id, street, streetNumber, stars, city, postalCode, countryIso, name));
            }
            return hotels;
        } catch (SQLException | HotelCreationException exception){
            throw new GetAllHotelsException("Erreur lors de la lecture de tous les hotels");
        }
    }

    public String getHotelName(int id) throws HotelException {
        try {
            String sqlInstruction = "select name from hotel where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, id);
            ResultSet data = preparedStatement.executeQuery();
            data.next();
            return data.getString("name");


        } catch (SQLException exception){
            throw new HotelException("Erreur lors de la recherche du nom d'hotel " + exception.getMessage());
        }
    }


    public int getHotelStar(int id) throws HotelException {
        try {
            String sqlInstruction = "select stars from hotel where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, id);
            ResultSet data = preparedStatement.executeQuery();
            data.next();
            return data.getInt("stars");


        } catch (SQLException exception){
            throw new HotelException("Erreur lors de la recherche de l'hotel " + exception.getMessage());
        }
    }

}
