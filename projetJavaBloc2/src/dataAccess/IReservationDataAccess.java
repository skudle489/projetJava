package dataAccess;

import exceptions.DeleteReservationException;
import exceptions.IsRoomReservedException;
import exceptions.ReservationException;
import model.Reservation;
import model.ReservationInvoiceModel;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IReservationDataAccess {
    public void addReservation(Reservation reservation) throws ReservationException;
    public void deleteReservation(String customer) throws DeleteReservationException;
    public boolean isRoomReserved(int bedroom, int hotel, LocalDate date) throws IsRoomReservedException;
    public ArrayList<LocalDate> getAllReservedDatesFrom(int bedroom, int hotel, LocalDate startDate) throws ReservationException;
    public ArrayList<Reservation> getAllReservationsCustomer(String customer) throws ReservationException;
    public ArrayList<ReservationInvoiceModel> reservationInvoice(String mailAddress) throws ReservationException;

}
