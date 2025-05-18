package dataAccess;

import exceptions.GetAllCountryException;
import model.Country;

import java.util.ArrayList;

public interface ICountryDataAccess {
    public ArrayList<Country> getAllCountries() throws GetAllCountryException;

}
