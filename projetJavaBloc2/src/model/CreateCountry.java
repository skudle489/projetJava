package model;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateCountry {
    private static final HashMap<ArrayList<String>, Country> countries = new HashMap<>();

    public static Country getCountry(String iso, String countryName) {
        ArrayList<String> parameters = new ArrayList<>();
        Country country = countries.get(parameters);
        if (country == null) {
            country = new Country(iso, countryName);
            countries.put(parameters, country);
        }
        return country;
    }
}
