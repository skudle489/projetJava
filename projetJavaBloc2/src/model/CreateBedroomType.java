package model;

import java.util.HashMap;

public class CreateBedroomType {
    private static final HashMap<String, BedroomType> bedroomTypes = new HashMap<>();

    public static BedroomType createBedroomType(String type) {
        BedroomType bedroomType = bedroomTypes.get(type);
        if (bedroomType == null) {
            bedroomType = new BedroomType(type);
            bedroomTypes.put(type, bedroomType);
        }
        return bedroomType;
    }
}
