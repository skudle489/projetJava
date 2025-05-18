package dataAccess;

import exceptions.*;
import model.Reservation;
import model.ReservationInvoiceModel;

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

    public ArrayList<Reservation> getAllReservationsCustomer(String customer) throws ReservationException {

        try {
            String sqlInstruction = "select * from reservation where customer = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, customer);
            ResultSet data = preparedStatement.executeQuery();


            LocalDate startDate;
            LocalDate endDate;

            int bedroom;
            int hotel;

            ArrayList<Reservation> reservations = new ArrayList<>();
            while (data.next()) {
                startDate = data.getDate("start_date").toLocalDate();
                endDate = data.getDate("end_date").toLocalDate();
                bedroom = data.getInt("bedroom_number");
                hotel = data.getInt("hotel");
                reservations.add(new Reservation(startDate, endDate, customer, bedroom, hotel));
            }
            return reservations;

        } catch (SQLException | ReservationCreationException | CustomerCreationException exception) {
            throw new ReservationException("Erreur lors de la lecture de tous les réservations client " + exception.getMessage());
        }
    }



    public ArrayList<ReservationInvoiceModel> reservationInvoice(String mailAddress) throws ReservationException {
        String sqlInstruction = "SELECT r.start_date, r.end_date, b.nb_of_people, b.bedroom_size, b.cost_per_day, h.name FROM reservation AS r INNER JOIN bedroom AS b ON r.bedroom_number = b.bedroom_number INNER JOIN hotel AS h ON b.hotel = h.id WHERE r.customer = ?";

        ArrayList<ReservationInvoiceModel> reservationInvoices = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, mailAddress);
            ResultSet data = preparedStatement.executeQuery();

            while (data.next()) {
                String hotelName = data.getString("name");
                LocalDate startDateReservation = data.getDate("start_date").toLocalDate();
                LocalDate endDateReservation = data.getDate("end_date").toLocalDate();
                int nbOfPeopleInBedroom = data.getInt("nb_of_people");
                double bedroomSize = data.getDouble("bedroom_size");
                double costPerDay = data.getDouble("cost_per_day");

                reservationInvoices.add(new ReservationInvoiceModel(hotelName, startDateReservation, endDateReservation, nbOfPeopleInBedroom, bedroomSize, costPerDay));
            }

            return reservationInvoices;

        } catch (SQLException e) {
            throw new ReservationException("Erreur : impossible de récupérer les factures de réservation" + e.getMessage());
        }
    }





}
