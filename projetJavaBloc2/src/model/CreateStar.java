package model;

import exceptions.StarCreationException;

import java.util.HashMap;

public class CreateStar {
    private static final HashMap<Integer, Star> stars = new HashMap<>();

    public static Star getStar(int starNumber) throws StarCreationException {
        Star star = stars.get(starNumber);
        if (star == null){
            star = new Star(starNumber);
            stars.put(starNumber, star);
        }
        return star;
    }

}
