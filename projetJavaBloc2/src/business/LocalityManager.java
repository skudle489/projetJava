package business;

import dataAccess.LocalityDBDAO;
import exceptions.DataAccessException;
import exceptions.GetAllLocalityWithCountryException;
import model.Locality;

import java.sql.SQLException;
import java.util.ArrayList;

public class LocalityManager {
    private LocalityDBDAO localityDBDAO;
    public LocalityManager() {
        localityDBDAO = new LocalityDBDAO();
    }

    public ArrayList<Locality> getAllLocalityWithCountry(String countryIso) throws GetAllLocalityWithCountryException {
        return localityDBDAO.getAllLocalityWithCountry(countryIso);
    }

}
