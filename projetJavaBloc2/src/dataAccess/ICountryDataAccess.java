package dataAccess;

import exceptions.GetAllCountryException;
import model.Country;

import java.util.ArrayList;

public interface ICountryDataAccess {
    ArrayList<Country> getAllCountries() throws GetAllCountryException;

}
