package dataAccess;

import exceptions.DeleteReservationException;
import exceptions.IsRoomReservedException;
import exceptions.ReservationException;
import model.Reservation;
import model.ReservationInvoiceModel;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IReservationDataAccess {
    void addReservation(Reservation reservation) throws ReservationException;
    void deleteReservation(String customer) throws DeleteReservationException;
    boolean isRoomReserved(int bedroom, int hotel, LocalDate date) throws IsRoomReservedException;
    ArrayList<Reservation> getAllReservationsCustomer(String customer) throws ReservationException;
    ArrayList<ReservationInvoiceModel> reservationInvoice(String mailAddress) throws ReservationException;

}
