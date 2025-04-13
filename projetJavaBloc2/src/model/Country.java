package model;

import exceptions.CountryCreationException;

public class Country {
    private String iso;
    private String name;

    public Country(String iso, String name) throws CountryCreationException {
        setIso(iso);
        setName(name);
    }

    public String getIso() {
        return iso;
    }

    public String getName() {
        return name;
    }

    public void setIso(String iso) throws CountryCreationException {
        if (iso == null) {
            throw new CountryCreationException("le code iso ne doit pas etre null");
        } else {
            if (iso.length() == 2) {
                this.iso = iso;
            } else {
                throw new CountryCreationException("Le code iso doit faire 2 caractères.");
            }
        }

    }


    public void setName(String name) throws CountryCreationException {
        if (name == null) {
            throw new CountryCreationException("Le nom du pays ne peut pas etre null");
        } else {
            if (name.isEmpty()){
                throw new CountryCreationException("Le nom du pays ne peut pas etre vide");
            } else {this.name = name;}
        }
    }

    @Override
    public String toString() {
        return iso + " : " + name;
    }

}
