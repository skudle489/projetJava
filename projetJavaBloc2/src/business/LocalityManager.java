package business;

import dataAccess.ILocalityDataAccess;
import dataAccess.LocalityDBDAO;
import exceptions.GetAllLocalityWithCountryException;
import model.Locality;

import java.util.ArrayList;

public class LocalityManager {
    private ILocalityDataAccess localityDBDAO;
    public LocalityManager() {
        localityDBDAO = new LocalityDBDAO();
    }

    public ArrayList<Locality> getAllLocalityWithCountry(String countryIso) throws GetAllLocalityWithCountryException {
        return localityDBDAO.getAllLocalityWithCountry(countryIso);
    }

}
