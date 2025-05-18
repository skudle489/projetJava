package model;

import java.time.LocalDate;

public class ReservationInvoiceModel {
    private String hotelName;
    private LocalDate startDateReservation;
    private LocalDate endDateReservation;
    private int nbOfPeopleInBedroom;
    private double bedroomSize;
    private double costPerDay;


    public ReservationInvoiceModel(String hotelName, LocalDate startDateReservation, LocalDate endDateReservation, int nbOfPeopleInBedroom, double bedroomSize, double costPerDay) {
        this.hotelName = hotelName;
        this.startDateReservation = startDateReservation;
        this.endDateReservation = endDateReservation;
        this.nbOfPeopleInBedroom = nbOfPeopleInBedroom;
        this.bedroomSize = bedroomSize;
        this.costPerDay = costPerDay;
    }


    public String getHotelName() {
        return hotelName;
    }

    public LocalDate getStartDateReservation() {
        return startDateReservation;
    }

    public LocalDate getEndDateReservation() {
        return endDateReservation;
    }

    public int getNbOfPeopleInBedroom() {
        return nbOfPeopleInBedroom;
    }

    public double getBedroomSize() {
        return bedroomSize;
    }

    public double getCostPerDay() {
        return costPerDay;
    }

    @Override
    public String toString() {
        return "Facture de réservation : " + "\n" +
                hotelName + "\n" +
                "Début de réservation = " + startDateReservation + "\n" +
                "Fin de réservation = " + endDateReservation + "\n" +
                "Nombre de personnes = " + nbOfPeopleInBedroom + "\n" +
                "Taille de la chambre = " + bedroomSize + " m²\n" +
                "Coût par jour = " + costPerDay + " €";
    }

}
