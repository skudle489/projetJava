package model;

import exceptions.BedroomCreationException;


import java.time.LocalDate;

public class Bedroom {
    private int bedroomNumber;
    private int nbOfPeople;
    private double bedroomSize;
    private double costPerDay;
    private String description;
    private boolean hasBalcony;
    private LocalDate lastRenovationDate;
    private String bedroomType;
    private int hotel;


    public Bedroom(int bedroomNumber, int nbOfPeople, double bedroomSize, double costPerDay, String description, boolean hasBalcony, LocalDate lastRenovationDate, String bedroomType, int hotel) throws BedroomCreationException {
        setBedroomNumber(bedroomNumber);
        setNbOfPeople(nbOfPeople);
        setBedroomSize(bedroomSize);
        setCostPerDay(costPerDay);
        setDescription(description);
        this.hasBalcony = hasBalcony;
        this.lastRenovationDate = lastRenovationDate;
        this.bedroomType = bedroomType;
        this.hotel = hotel;
    }

    public int getBedroomNumber() {
        return bedroomNumber;
    }

    public int getNbOfPeople() {
        return nbOfPeople;
    }

    public double getBedroomSize() {
        return bedroomSize;
    }

    public double getCostPerDay() {
        return costPerDay;
    }

    public String getDescription() {
        return description;
    }

    public boolean getHasBalcony() {
        return hasBalcony;
    }

    public LocalDate getLastRenovationDate() {
        return lastRenovationDate;
    }

    public String getBedroomType() {
        return bedroomType;
    }

    public int getHotel() {
        return hotel;
    }


    public void setBedroomNumber(int bedroomNumber) throws BedroomCreationException {
        if (bedroomNumber < 0) {
            throw new BedroomCreationException("Le numéro de la chambre de ne etre négatif");
        } else {
            this.bedroomNumber = bedroomNumber;
        }
    }

    public void setNbOfPeople(int nbOfPeople) throws BedroomCreationException {
        if (nbOfPeople <= 0) {
            throw new BedroomCreationException("Le nombre de personnes doit etre strictement supérieur à 1");
        } else {
            this.nbOfPeople = nbOfPeople;
        }
    }

    public void setBedroomSize(double bedroomSize) throws BedroomCreationException {
        if (bedroomSize <= 0) {
            throw new BedroomCreationException("La taille de la chambre doit etre strictement positive");
        } else {
            this.bedroomSize = bedroomSize;
        }
    }

    public void setCostPerDay(double costPerDay) throws BedroomCreationException {
        if (costPerDay <= 0) {
            throw new BedroomCreationException("Le prix par jour de la chambre doit etre strictement positive");
        } else {
            this.costPerDay = costPerDay;
        }
    }

    public void setDescription(String description) throws BedroomCreationException {
        if (description == null || !description.isEmpty()) {
            this.description = description;
        } else {
            throw new BedroomCreationException("La description ne peut etre vide");
        }
    }

    public String getFullDescription() {
        String descriptionText = "Chambre numéro " + bedroomNumber + "\n" +
                "Type : " + bedroomType + "\n" +
                "Capacité : " + nbOfPeople + " personne" + (nbOfPeople > 1 ? "s" : "") + "\n" +
                "Surface : " + bedroomSize + " m²\n" +
                "Prix par jour : " + costPerDay + " euros\n" +
                "Balcon : " + (hasBalcony ? "Oui" : "Non") + "\n" +
                "Dernière rénovation : " + lastRenovationDate + "\n" +
                "Description : " + description + "\n";

        return descriptionText;
    }

    @Override
    public String toString(){
        return "Chambre numéro " + bedroomNumber;
    }
}
