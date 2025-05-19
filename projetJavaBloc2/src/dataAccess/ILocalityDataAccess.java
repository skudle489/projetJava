package dataAccess;

import exceptions.GetAllLocalityWithCountryException;
import model.Locality;

import java.util.ArrayList;

public interface ILocalityDataAccess {
    ArrayList<Locality> getAllLocalityWithCountry(String countryIso) throws GetAllLocalityWithCountryException;
}
