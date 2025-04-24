package model;

import java.util.HashMap;

public class CreateAmenity {
    private static final HashMap<String, Amenity> amenities = new HashMap<>();

    public static Amenity getAmenity(String amenityName) {
        Amenity amenity = amenities.get(amenityName);
        if (amenity == null) {
            amenity = new Amenity(amenityName);
            amenities.put(amenityName, amenity);
        }
        return amenity;
    }
}
