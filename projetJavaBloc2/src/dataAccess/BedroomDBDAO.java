package dataAccess;

import exceptions.BedroomCreationException;
import exceptions.DataAccessException;
import model.Bedroom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BedroomDBDAO {
    private Connection connection;

    public BedroomDBDAO() throws DataAccessException {
        connection = DatabaseConnection.getInstance();
    }

    public ArrayList<Bedroom> getBedroomsFromHotel(int hotelID) throws BedroomCreationException {

        try {
            String sqlInstruction = "SELECT * FROM bedroom WHERE hotel = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, hotelID);
            ResultSet data = preparedStatement.executeQuery();

            int bedroomNumber;
            int nbOfPeople;
            double bedroomSize;
            double costPerDay;
            String description;
            boolean hasBalcony;
            LocalDate lastRenovationDate;
            String bedroomType;
            int hotel;
            ArrayList<Bedroom> bedrooms = new ArrayList<>();


            while (data.next()) {
                bedroomNumber = data.getInt("bedroom_number");
                nbOfPeople = data.getInt("nb_of_people");
                bedroomSize = data.getFloat("bedroom_size");
                costPerDay = data.getFloat("cost_per_day");
                description = data.getString("description");
                hasBalcony = data.getBoolean("has_balcony");
                lastRenovationDate = data.getDate("last_renovation_date").toLocalDate();
                bedroomType = data.getString("bedroom_type");
                hotel = data.getInt("hotel");
                bedrooms.add(new Bedroom(bedroomNumber, nbOfPeople, bedroomSize, costPerDay, description, hasBalcony, lastRenovationDate, bedroomType, hotel));
            }
            return bedrooms;
        } catch (SQLException exception){
            throw new BedroomCreationException("Erreur lors de la récupération des chambres de l'hotel " + exception.getMessage());
        }
    }



    public Bedroom getBedroom(int bedroomNumber, int hotel) throws BedroomCreationException {
        try {

            String sqlInstruction = "SELECT * FROM bedroom WHERE bedroom_number = ? and hotel = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, bedroomNumber);
            preparedStatement.setInt(2, hotel);
            ResultSet data = preparedStatement.executeQuery();
            data.next();

            int nbOfPeople;
            double bedroomSize;
            double costPerDay;
            String description;
            boolean hasBalcony;
            LocalDate lastRenovationDate;
            String bedroomType;

            nbOfPeople = data.getInt("nb_of_people");
            bedroomSize = data.getFloat("bedroom_size");
            costPerDay = data.getFloat("cost_per_day");
            description = data.getString("description");
            hasBalcony = data.getBoolean("has_balcony");
            lastRenovationDate = data.getDate("last_renovation_date").toLocalDate();
            bedroomType = data.getString("bedroom_type");
            return new Bedroom(bedroomNumber, nbOfPeople, bedroomSize, costPerDay, description, hasBalcony, lastRenovationDate, bedroomType, hotel);
        } catch (SQLException exception) {
            throw new BedroomCreationException("Erreur lors de la lecture du lit " + exception.getMessage());
        }
    }
}
