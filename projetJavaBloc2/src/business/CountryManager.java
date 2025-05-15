package business;

import dataAccess.CountryDBDAO;
import exceptions.CountryCreationException;
import exceptions.DataAccessException;
import exceptions.GetAllCountryException;
import model.Country;

import java.sql.SQLException;
import java.util.ArrayList;

public class CountryManager {
    private CountryDBDAO countryDBDAO;

    public CountryManager() {
        try {
            countryDBDAO = new CountryDBDAO();
        } catch (SQLException exception){
            throw new DataAccessException("Erreur d'acces à la base de donnée");
        }
    }


    public ArrayList<Country> getAllCountries() throws GetAllCountryException {
        return countryDBDAO.getAllCountries();
    }
}
