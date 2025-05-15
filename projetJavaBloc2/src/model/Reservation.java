package model;

import exceptions.ReservationCreationException;

import java.time.LocalDate;

public class Reservation {
    private LocalDate startDate;
    private LocalDate endDate;
    private String customer;
    private int bedroom;
    private int hotel;

    public Reservation(LocalDate startDate, LocalDate endDate, String customer, int bedroom, int hotel) throws ReservationCreationException {
        setStartDate(startDate);
        setEndDate(endDate);
        this.customer = customer;
        setBedroom(bedroom);
        setHotel(hotel);
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


    public void setStartDate(LocalDate startDate) throws ReservationCreationException {
        if (startDate == null) {
            throw new ReservationCreationException("La date de début de la réservation ne peut etre null");
        } else {
            if (startDate.isBefore(LocalDate.now())) {
                throw new ReservationCreationException("La date de début de la réservation ne peut commencer avant la date actuelle");
            } else {
                if (startDate.isAfter(endDate)) {
                    throw new ReservationCreationException("La date de début de la réservation ne peut commencer après la date de fin de la réservation");
                } else {
                    this.startDate = startDate;
                }
            }
        }
    }

    public void setEndDate(LocalDate endDate) throws ReservationCreationException {
        if (endDate == null) {
            throw new ReservationCreationException("La date de fin de la réservation ne peut etre null");
        } else {
            if (endDate.isBefore(startDate)) {
                throw new ReservationCreationException("La date de fin de la réservation ne peut terminer avant la date de début de la réservation");
            } else {
                if (endDate.isBefore(LocalDate.now())) {
                    throw new ReservationCreationException("La date de fin de la réservation ne peut se terminer avant la date actuelle");
                }
                this.endDate = endDate;
            }
        }
    }
}
