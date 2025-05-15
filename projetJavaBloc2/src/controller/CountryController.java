package controller;

import business.CountryManager;
import exceptions.CountryCreationException;
import exceptions.GetAllCountryException;
import model.Country;

import java.util.ArrayList;

public class CountryController {
    private CountryManager countryManager;

    public CountryController() {
        countryManager = new CountryManager();
    }

    public ArrayList<Country> getAllCountries() throws GetAllCountryException {
        return countryManager.getAllCountries();
    }
}
