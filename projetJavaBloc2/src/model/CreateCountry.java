package model;

import exceptions.CountryCreationException;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateCountry {
    private static final HashMap<String, Country> countries = new HashMap<>();

    public static Country getCountry(String iso, String countryName) throws CountryCreationException {
        String key = iso + countryName;
        Country country = countries.get(key);
        if (country == null) {
            country = new Country(iso, countryName);
            countries.put(key, country);
        }

        return country;
    }
}