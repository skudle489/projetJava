package business;

import dataAccess.ReservationDBDAO;
import exceptions.*;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ReservationManager {
    private ReservationDBDAO reservationDBDAO;
    private BedroomOwnsAmenityManager bedroomOwnsAmenityManager;
    private BedroomOwnsBedManager bedroomOwnsBedManager;
    private BedroomManager bedroomManager;
    public ReservationManager() {
        reservationDBDAO = new ReservationDBDAO();
        bedroomOwnsAmenityManager = new BedroomOwnsAmenityManager();
        bedroomOwnsBedManager = new BedroomOwnsBedManager();
        bedroomManager = new BedroomManager();

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

    public ArrayList<Reservation> getAllReservationsCustomer(String customer) throws ReservationException {
        return reservationDBDAO.getAllReservationsCustomer(customer);
    }

    public String getReservationInvoiceCustomer(Reservation reservation) throws ReservationException, GetAllAmenitiesFromBedroomException, GetAllBedsFromBedroomException, BedroomCreationException {

        StringBuilder description = new StringBuilder();

        Bedroom bedroom = bedroomManager.getBedroom(reservation.getBedroom(), reservation.getHotel());

        String bedroomDescription = bedroom.getFullDescription();

        description.append(bedroomDescription);

        ArrayList<Amenity> amenities = bedroomOwnsAmenityManager.getAllAmenitiesFromBedroom(reservation.getBedroom(), reservation.getHotel());

        description.append("Accomodités : ");

        if (amenities != null) {
            for (Amenity amenity : amenities) {
                description.append(amenity.getLabel()).append(", ");
            }

            description.setLength(description.length() - 2);

        }

        description.append("\n");

        ArrayList<BedroomOwnsBed> bedroomOwnsBeds = bedroomOwnsBedManager.getAllBedsFromBedroom(reservation.getBedroom(), reservation.getHotel());

        description.append("Type de lit(s) : ");

        if (bedroomOwnsBeds != null) {
            for (BedroomOwnsBed bed : bedroomOwnsBeds) {
                description.append(bed.getBed()).append(", ");
            }

            description.setLength(description.length() - 2);
        }

        description.append("\nPrix total : ");

        long days = ChronoUnit.DAYS.between(reservation.getStartDate(), reservation.getEndDate());
        if (days == 0) days = 1;

        int totalPrice = (int) (days * bedroom.getCostPerDay());

        description.append(totalPrice).append(" euros");
        return description.toString();
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
        LocalDate endDate = startDate.plusMonths(6);

        while (!isRoomReserved(bedroom, hotel, startDate) && !startDate.isAfter(endDate)) {
            availableDates.add(startDate);
            startDate = startDate.plusDays(1);
        }

        return availableDates;
    }

}
