package controller;

import business.ReservationManager;
import exceptions.*;
import model.Reservation;
import model.ReservationInvoiceModel;

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

    public ArrayList<Reservation> getAllReservationsCustomer(String customer) throws ReservationException {
        return reservationManager.getAllReservationsCustomer(customer);
    }

    public String getReservationInvoiceCustomer(Reservation reservation) throws GetAllAmenitiesFromBedroomException, GetAllBedsFromBedroomException, BedroomCreationException {
        return reservationManager.getReservationInvoiceCustomer(reservation);
    }

    public ArrayList<ReservationInvoiceModel> reservationInvoice (String mailAddress) throws ReservationException {
        return reservationManager.reservationInvoice(mailAddress);
    }



}
