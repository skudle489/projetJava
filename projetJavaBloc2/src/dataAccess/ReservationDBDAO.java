package dataAccess;

import exceptions.*;
import model.Reservation;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationDBDAO {
    private static Connection connection;

    public ReservationDBDAO() throws DataAccessException {
        connection = DatabaseConnection.getInstance();
    }

    public void addReservation(Reservation reservation) throws ReservationException {

        try {
            String sqlInstruction = "insert into reservation (start_date, end_date, customer, bedroom_number, hotel) values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setDate(1, java.sql.Date.valueOf(reservation.getStartDate()));
            preparedStatement.setDate(2, java.sql.Date.valueOf(reservation.getEndDate()));
            preparedStatement.setString(3, reservation.getCustomer());
            preparedStatement.setInt(4, reservation.getBedroom());
            preparedStatement.setInt(5, reservation.getHotel());
            preparedStatement.executeUpdate();
        } catch (SQLException exception){
            System.out.println(exception.getMessage());
            throw new ReservationException("Erreur lors de l'ajout de la réservation " + exception.getMessage());
        }
    }

    public void deleteReservation(String customer) throws DeleteReservationException {
        try {
            String sqlInstruction = "delete from reservation where customer = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, customer);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new DeleteReservationException("Erreur lors de la supression des reservations " + exception.getMessage());
        }
    }

    public boolean isRoomReserved(int bedroom, int hotel, LocalDate date) throws IsRoomReservedException {

        try {
            String sqlInstruction = "select count(*) FROM reservation WHERE bedroom_number = ? and hotel = ? AND ? BETWEEN start_date AND end_date";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, bedroom);
            preparedStatement.setInt(2, hotel);
            preparedStatement.setDate(3, java.sql.Date.valueOf(date));

            ResultSet data = preparedStatement.executeQuery();
            data.next();
            int count = data.getInt(1);
            return count > 0;


        } catch (SQLException exception){
            throw new IsRoomReservedException("Erreur lors du test pour savoir si une data est libre pour la réservation " + exception.getMessage());
        }

    }

    public ArrayList<LocalDate> getAllReservedDatesFrom(int bedroom, int hotel, LocalDate startDate) throws ReservationException {

        try {
            ArrayList<LocalDate> reservedDates = new ArrayList<>();


            String sqlInstruction = "SELECT start_date, end_date FROM reservation " +
                    "WHERE bedroom_number = ? AND hotel = ? AND end_date >= ? ORDER BY start_date DESC";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, bedroom);
            preparedStatement.setInt(2, hotel);
            preparedStatement.setDate(3, java.sql.Date.valueOf(startDate));
            ResultSet data = preparedStatement.executeQuery();

            while (data.next()) {
                LocalDate start = data.getDate("start_date").toLocalDate();
                LocalDate end = data.getDate("end_date").toLocalDate();

                for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
                    reservedDates.add(date);
                }
            }

            return reservedDates;


        } catch (SQLException exception) {
            throw new ReservationException("Erreur lors de l'accès aux date reéservées " + exception.getMessage());
        }
    }




}
