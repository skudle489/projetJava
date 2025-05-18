package business;

import dataAccess.BedroomOwnsAmenityDBDAO;
import dataAccess.IBedroomOwnsAmenityDataAccess;
import exceptions.GetAllAmenitiesFromBedroomException;
import model.Amenity;

import java.util.ArrayList;

public class BedroomOwnsAmenityManager {
    private IBedroomOwnsAmenityDataAccess bedroomOwnsAmenityDBDAO;
    public BedroomOwnsAmenityManager() {
        bedroomOwnsAmenityDBDAO = new BedroomOwnsAmenityDBDAO();
    }

    public ArrayList<Amenity> getAllAmenitiesFromBedroom(int bedroomNumber, int hotelId) throws GetAllAmenitiesFromBedroomException {
        return bedroomOwnsAmenityDBDAO.getAllAmenitiesFromBedroom(bedroomNumber, hotelId);
    }
}
