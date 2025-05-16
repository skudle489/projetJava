package business;

import dataAccess.ReservationDBDAO;
import exceptions.DeleteReservationException;
import exceptions.IsRoomReservedException;
import exceptions.ReservationException;
import model.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationManager {
    private ReservationDBDAO reservationDBDAO;
    public ReservationManager() {
        reservationDBDAO = new ReservationDBDAO();
    }


    public void deleteReservation(String customer) throws DeleteReservationException {
        reservationDBDAO.deleteReservation(customer);
    }

    public boolean isRoomReserved(int bedroom, int hotel, LocalDate date) throws IsRoomReservedException {
        return reservationDBDAO.isRoomReserved(bedroom, hotel, date);
    }

    public ArrayList<LocalDate> getAllReservedDatesFrom(int bedroom, int hotel, LocalDate startDate) throws ReservationException {
        return reservationDBDAO.getAllReservedDatesFrom(bedroom, hotel, startDate);
    }

    public void addReservation(Reservation reservation) throws ReservationException {
        reservationDBDAO.addReservation(reservation);
    }

    /*public ArrayList<LocalDate> getAllAvailableDatesFrom(int bedroom, int hotel, LocalDate startDate) throws ReservationException {
        ArrayList<LocalDate> availableDates = new ArrayList<>();
        ArrayList<LocalDate> reservedDates = getAllReservedDatesFrom(bedroom, hotel, startDate);

        LocalDate endDate = startDate.plusMonths(6);
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (!reservedDates.contains(date)) {
                availableDates.add(date);
            }
        }
        return availableDates;
    }*/

    public ArrayList<LocalDate> getAvailableDatesFrom(int bedroom, int hotel, LocalDate startDate) throws IsRoomReservedException {
        ArrayList<LocalDate> availableDates = new ArrayList<>();
        LocalDate endDate = startDate.plusMonths(6);  // borne fixe

        while (!isRoomReserved(bedroom, hotel, startDate) && !startDate.isAfter(endDate)) {
            availableDates.add(startDate);
            startDate = startDate.plusDays(1);
        }

        return availableDates;
    }

}
