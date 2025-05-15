package dataAccess;

import exceptions.DeleteReviewException;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReservationDBDAO {
    private static Connection connection;

    public ReservationDBDAO() throws SQLException {
        connection = DatabaseConnection.getInstance();
    }

    public void deleteReservation(String customer) throws DeleteReviewException {
        try {
            String sqlInstruction = "delete from reservation where customer = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, customer);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new DeleteReviewException("Erreur lors de la supression de la reservation");
        }
    }


}
