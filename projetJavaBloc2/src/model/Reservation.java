package model;

import exceptions.ReservationCreationException;

import java.time.LocalDate;

public class Reservation {
    private LocalDate startDate;
    private LocalDate endDate;
    private Customer customer;
    private Bedroom bedroom;

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
