package model;

import exceptions.HotelCreationException;

public class Hotel {
    private int id;
    private String street;
    private int streetNumber;
    private int stars;
    private String city;
    private String postalCode;
    private String countryIso;
    private String name;

    public Hotel(int id, String street, int streetNumber, int stars, String city, String postalCode, String countryIso, String name) throws HotelCreationException {
        this.id = id;
        setStreet(street);
        setStreetNumber(streetNumber);
        setStars(stars);
        this.city = city;
        this.postalCode = postalCode;
        this.countryIso = countryIso;
        this.name = name;
    }

    public void setStreet(String street) throws HotelCreationException {
        if (street == null) {
            throw new HotelCreationException("La rue ne peut pas etre null");
        } else {
            if (street.isEmpty()) {
                throw new HotelCreationException("La rue ne peut pas etre vide");
            } else {
                this.street = street;
            }
        }
    }

    public void setStreetNumber(int streetNumber) throws HotelCreationException {
        if (streetNumber < 0) {
            throw new HotelCreationException("Le numéro de la rue ne peut pas etre négatif");
        } else {
            this.streetNumber = streetNumber;
        }
    }

    public void setStars(int stars) throws HotelCreationException {
        if (stars < 0 || stars > 5) {
            throw new HotelCreationException("Le nombre d'étoiles doit etre entre 0 et 5");
        } else {
            this.stars = stars;
        }
    }

    @Override
    public String toString() {
        return name + " à " + stars + " étoile" + (stars == 0 ? "":"s") + " situé à : " + street + " " + streetNumber + ", " + city + " " + postalCode + " " + countryIso;
    }

    public int getId() {
        return id;
    }
}
