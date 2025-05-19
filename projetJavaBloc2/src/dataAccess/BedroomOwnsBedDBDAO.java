package dataAccess;

import exceptions.GetAllBedsFromBedroomException;
import model.BedroomOwnsBed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BedroomOwnsBedDBDAO implements IBedroomOwnsBedDataAccess {
    private Connection connection;
    public BedroomOwnsBedDBDAO() {
        connection = DatabaseConnection.getInstance();
    }

    public ArrayList<BedroomOwnsBed> getAllBedsFromBedroom(int bedroom, int hotel) throws GetAllBedsFromBedroomException {
        try {
            String sqlInstruction = "select * from bedroom_owns_bed where bedroom_number = ? and hotel = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, bedroom);
            preparedStatement.setInt(2, hotel);
            ResultSet data = preparedStatement.executeQuery();

            ArrayList<BedroomOwnsBed> beds = new ArrayList<>();

            String bed;

            while (data.next()) {
                bedroom = data.getInt("bedroom_number");
                bed = data.getString("bed");
                hotel = data.getInt("hotel");
                beds.add(new BedroomOwnsBed(bedroom, bed, hotel));
            }

            return beds;

        } catch (SQLException exception){
            throw new GetAllBedsFromBedroomException("Erreur lors de la récupération des lits de la chambre " + exception.getMessage());
        }
    }



}
