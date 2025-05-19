package business;

import dataAccess.IReservationDataAccess;
import dataAccess.ReservationDBDAO;
import exceptions.*;
import model.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ReservationManager {
    private IReservationDataAccess reservationDBDAO;
    private BedroomOwnsAmenityManager bedroomOwnsAmenityManager;
    private BedroomOwnsBedManager bedroomOwnsBedManager;
    private BedroomManager bedroomManager;
    public ReservationManager() {
        reservationDBDAO = new ReservationDBDAO();
        bedroomOwnsAmenityManager = new BedroomOwnsAmenityManager();
        bedroomOwnsBedManager = new BedroomOwnsBedManager();
        bedroomManager = new BedroomManager();

    }

    public boolean isRoomReserved(int bedroom, int hotel, LocalDate date) throws IsRoomReservedException {
        return reservationDBDAO.isRoomReserved(bedroom, hotel, date);
    }


    public void addReservation(Reservation reservation) throws ReservationException {
        if (reservation.getStartDate().isBefore(LocalDate.now())){
            throw new ReservationException("La date de début de réservation ne peut pas etre dans le passé");
        }

        if (reservation.getEndDate().isBefore(LocalDate.now())){
            throw new ReservationException("La date de fin de réservation ne peut se terminer avant la date actuelle");
        }

        if (reservation.getStartDate().isAfter(reservation.getEndDate())){
            throw new ReservationException("La date de début de réservation doit se terminer avant la date de fin");
        }

        reservationDBDAO.addReservation(reservation);
    }

    public ArrayList<Reservation> getAllReservationsCustomer(String customer) throws ReservationException {
        return reservationDBDAO.getAllReservationsCustomer(customer);
    }

    public double totalPrice(int daysCount, double pricePerDay){
        return daysCount * pricePerDay;
    }


    public String getReservationInvoiceCustomer(Reservation reservation) throws GetAllAmenitiesFromBedroomException, GetAllBedsFromBedroomException, BedroomCreationException {

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

        double totalPrice = totalPrice((int) days, bedroom.getCostPerDay());

        description.append(totalPrice).append(" euros");
        return description.toString();
    }


    public ArrayList<LocalDate> getAvailableDatesFrom(int bedroom, int hotel, LocalDate startDate) throws IsRoomReservedException {
        ArrayList<LocalDate> availableDates = new ArrayList<>();
        LocalDate endDate = startDate.plusMonths(6);

        while (!isRoomReserved(bedroom, hotel, startDate) && !startDate.isAfter(endDate)) {
            availableDates.add(startDate);
            startDate = startDate.plusDays(1);
        }

        return availableDates;
    }

    public ArrayList<ReservationInvoiceModel> reservationInvoice (String mailAddress) throws ReservationException {
        return reservationDBDAO.reservationInvoice(mailAddress);
    }

}
