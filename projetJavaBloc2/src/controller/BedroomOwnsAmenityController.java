package controller;

import business.BedroomOwnsAmenityManager;
import exceptions.GetAllAmenitiesFromBedroomException;
import model.Amenity;

import java.util.ArrayList;

public class BedroomOwnsAmenityController {
    private BedroomOwnsAmenityManager bedroomOwnsAmenityManager;
    public BedroomOwnsAmenityController() {
        bedroomOwnsAmenityManager = new BedroomOwnsAmenityManager();
    }

    public ArrayList<Amenity> getAllAmenitiesFromBedroom(int bedroomNumber, int hotelId) throws GetAllAmenitiesFromBedroomException {
        return bedroomOwnsAmenityManager.getAllAmenitiesFromBedroom(bedroomNumber, hotelId);
    }
}
