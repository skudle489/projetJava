package model;

import exceptions.CustomerCreationException;
import exceptions.ReservationCreationException;
import utils.CustomerFormValidator;

import java.time.LocalDate;

public class Reservation {
    private LocalDate startDate;
    private LocalDate endDate;
    private String customer;
    private int bedroom;
    private int hotel;

    public Reservation(LocalDate startDate, LocalDate endDate, String customer, int bedroom, int hotel) throws ReservationCreationException, CustomerCreationException {
        this.startDate = startDate;
        this.endDate = endDate;

        validateStartDate();
        validateEndDate();


        setCustomer(customer);
        setBedroom(bedroom);
        setHotel(hotel);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getCustomer() {
        return customer;
    }

    public int getBedroom() {
        return bedroom;
    }

    public int getHotel() {
        return hotel;
    }


    public void setCustomer(String customer) throws CustomerCreationException {
        CustomerFormValidator.validateMailAdress(customer);
        this.customer = customer;
    }

    public void setHotel(int hotel) throws ReservationCreationException {
        if (hotel < 0){
            throw new ReservationCreationException("Le numéro d'hotel doit etre positif");
        } else {
            this.hotel = hotel;
        }
    }

    public void setBedroom(int bedroom) throws ReservationCreationException {
        if (bedroom < 0){
            throw new ReservationCreationException("Le numéro de la chambre ne peut etre négatif");
        } else {
            this.bedroom = bedroom;
        }
    }


    public void validateStartDate() throws ReservationCreationException {
        if (startDate == null) {
            throw new ReservationCreationException("La date de début de la réservation ne peut etre null");
        }
    }


    public void validateEndDate() throws ReservationCreationException {
        if (endDate == null) {
            throw new ReservationCreationException("La date de fin de la réservation ne peut etre null");
        } else {
            if (endDate.isBefore(startDate)) {
                throw new ReservationCreationException("La date de fin de la réservation ne peut terminer avant la date de début de la réservation");
            }
        }
    }

    public String toString() {
        return "hotel " + hotel + " chambre " + bedroom + " le " + startDate + " au " + endDate;
    }

}


