package business;

import dataAccess.BedroomOwnsAmenityDBDAO;
import exceptions.GetAllAmenitiesFromBedroomException;
import model.Amenity;

import java.util.ArrayList;

public class BedroomOwnsAmenityManager {
    private BedroomOwnsAmenityDBDAO bedroomOwnsAmenityDBDAO;
    public BedroomOwnsAmenityManager() {
        bedroomOwnsAmenityDBDAO = new BedroomOwnsAmenityDBDAO();
    }

    public ArrayList<Amenity> getAllAmenitiesFromBedroom(int bedroomNumber, int hotelId) throws GetAllAmenitiesFromBedroomException {
        return bedroomOwnsAmenityDBDAO.getAllAmenitiesFromBedroom(bedroomNumber, hotelId);
    }
}
