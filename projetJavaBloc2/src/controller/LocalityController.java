package controller;

import business.LocalityManager;
import exceptions.GetAllLocalityWithCountryException;
import model.Locality;

import java.sql.SQLException;
import java.util.ArrayList;

public class LocalityController {
    private LocalityManager localityManager;
    public LocalityController() {
        localityManager = new LocalityManager();
    }

    public ArrayList<Locality> getAllLocalityWithCountry(String countryIso) throws GetAllLocalityWithCountryException {
        return localityManager.getAllLocalityWithCountry(countryIso);
    }
}
