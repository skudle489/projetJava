package dataAccess;


import exceptions.BedroomTypeCreationException;
import exceptions.DataAccessException;
import model.BedroomType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class BedroomTypeDBDAO implements IBedroomTypeDataAccess {
    private Connection connection;

    public BedroomTypeDBDAO() throws DataAccessException {
        connection = DatabaseConnection.getInstance();
    }

    public ArrayList<BedroomType> getAllTypesBedroom() throws BedroomTypeCreationException {
        try {
            String sqlInstruction = "SELECT * FROM bedroom_type";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();

            String label;

            ArrayList<BedroomType> labelsTypesBedroom = new ArrayList<>();

            while (data.next()) {
                label = data.getString("label");
                labelsTypesBedroom.add(new BedroomType(label));
            }
            return  labelsTypesBedroom;
        } catch (SQLException exception) {
            throw new BedroomTypeCreationException("Erreur lors de la récupération des types de chambre existante " + exception.getMessage());

        }

    }
}