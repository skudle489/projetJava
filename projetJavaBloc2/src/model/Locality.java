package model;

import exceptions.LocalityCreationException;

public class Locality {
    private String city;
    private Country country;
    private String postalCode;

    public Locality(String city, Country country, String postalCode) throws LocalityCreationException {
        setCity(city);
        setPostalCode(postalCode);
        this.country = country;
    }

    public void setCity(String city) throws LocalityCreationException {
        if (city == null){
            throw new LocalityCreationException("Le nom de la ville ne peut pas etre null");
        } else {
            if (city.isEmpty()){
                throw new LocalityCreationException("Le nom de la ville ne peut pas etre vide");
            } else {
                this.city = city;
            }
        }
    }


    public void setPostalCode(String postalCode) throws LocalityCreationException {
        if (postalCode == null){
            throw new LocalityCreationException("Le code postal ne peut pas etre null");
        } else {
            if (postalCode.isEmpty()) {
                throw new LocalityCreationException("Le code postal ne peut pas etre vide");
            } else {
                this.postalCode = postalCode;
            }
        }
    }

    @Override
    public String toString() {
        return city + " " + postalCode + ", " + country;
    }
}
