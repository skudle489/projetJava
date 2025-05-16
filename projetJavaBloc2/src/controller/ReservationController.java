package controller;

import business.ReservationManager;
import exceptions.IsRoomReservedException;
import exceptions.ReservationException;
import model.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationController {
    private ReservationManager reservationManager;
    public ReservationController() {
        reservationManager = new ReservationManager();
    }

    public boolean isRoomReserved(int bedroom, int hotel, LocalDate date) throws IsRoomReservedException {
        return reservationManager.isRoomReserved(bedroom, hotel, date);
    }


    public ArrayList<LocalDate> getAvailableDatesFrom(int bedroom, int hotel, LocalDate startDate) throws IsRoomReservedException {
        return reservationManager.getAvailableDatesFrom(bedroom, hotel, startDate);
    }

    public void addReservation(Reservation reservation) throws ReservationException {
        reservationManager.addReservation(reservation);
    }


}
