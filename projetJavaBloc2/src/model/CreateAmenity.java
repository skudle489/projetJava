package model;

import exceptions.AmenityCreationException;

import java.util.HashMap;

public class CreateAmenity {
    private static final HashMap<String, Amenity> amenities = new HashMap<>();

    public static Amenity getAmenity(String amenityName) throws AmenityCreationException {
        Amenity amenity = amenities.get(amenityName);
        if (amenity == null) {
            amenity = new Amenity(amenityName);
            amenities.put(amenityName, amenity);
        }
        return amenity;
    }
}
