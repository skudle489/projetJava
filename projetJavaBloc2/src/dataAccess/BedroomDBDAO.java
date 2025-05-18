package dataAccess;

import exceptions.BedroomCreationException;
import exceptions.DataAccessException;
import model.Bedroom;
import model.BedroomInformationsModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BedroomDBDAO implements IBedroomDataAccess {
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
            java.sql.Date lastRenovationSQLDate;
            LocalDate lastRenovationDate;
            String bedroomType;
            int hotel;
            ArrayList<Bedroom> bedrooms = new ArrayList<>();


            while (data.next()) {
                lastRenovationDate = null;
                bedroomNumber = data.getInt("bedroom_number");
                nbOfPeople = data.getInt("nb_of_people");
                bedroomSize = data.getFloat("bedroom_size");
                costPerDay = data.getFloat("cost_per_day");
                description = data.getString("description");
                hasBalcony = data.getBoolean("has_balcony");
                lastRenovationSQLDate = data.getDate("last_renovation_date");
                if (lastRenovationSQLDate != null) {
                    lastRenovationDate = lastRenovationSQLDate.toLocalDate();
                }
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
            java.sql.Date lastRenovationSQLDate;
            LocalDate lastRenovationDate = null;
            String bedroomType;

            nbOfPeople = data.getInt("nb_of_people");
            bedroomSize = data.getFloat("bedroom_size");
            costPerDay = data.getFloat("cost_per_day");
            description = data.getString("description");
            hasBalcony = data.getBoolean("has_balcony");
            lastRenovationSQLDate = data.getDate("last_renovation_date");
            if (lastRenovationSQLDate != null) {
                lastRenovationDate = lastRenovationSQLDate.toLocalDate();
            }
            bedroomType = data.getString("bedroom_type");
            return new Bedroom(bedroomNumber, nbOfPeople, bedroomSize, costPerDay, description, hasBalcony, lastRenovationDate, bedroomType, hotel);
        } catch (SQLException exception) {
            throw new BedroomCreationException("Erreur lors de la lecture du lit " + exception.getMessage());
        }
    }

    public ArrayList<BedroomInformationsModel> getAllBedroomsInformationsByType(String bedroomType) throws BedroomCreationException {
        try {
            String sqlInstruction = "SELECT b.bedroom_number, b.cost_per_day, h.name, h.street_number, h.street, l.city, c.name AS country_name " +
                    "FROM bedroom AS b " +
                    "INNER JOIN hotel AS h ON b.hotel = h.id " +
                    "INNER JOIN locality AS l ON h.city = l.city AND h.postal_code = l.postal_code AND h.country = l.country " +
                    "INNER JOIN country AS c ON l.country = c.iso " +
                    "WHERE b.bedroom_type = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, bedroomType);
            ResultSet data = preparedStatement.executeQuery();

            ArrayList<BedroomInformationsModel> bedroomsInformations = new ArrayList<>();

            while (data.next()) {
                int bedroomNumber = data.getInt("bedroom_number");
                double costPerDay = data.getDouble("cost_per_day");
                String hotelName = data.getString("name");
                int streetNumber = data.getInt("street_number");
                String street = data.getString("street");
                String city = data.getString("city");
                String countryName = data.getString("country_name");

                bedroomsInformations.add(new BedroomInformationsModel(
                        bedroomNumber, costPerDay, hotelName, streetNumber, street, city, countryName
                ));
            }

            return bedroomsInformations;
        } catch (SQLException exception) {
            throw new BedroomCreationException("Erreur lors de la récupération des chambres de tous les hôtels : " + exception.getMessage());
        }
    }
}
