package model;

import exceptions.AmenityCreationException;

public class Amenity {
    private String label;

    public Amenity(String label) throws AmenityCreationException {
        setLabel(label);
    }

    public void setLabel(String label) throws AmenityCreationException {
        if (label == null || label.equals("")){
            throw new AmenityCreationException("Le label de la commodité ne peut etre vide ou null");
        } else {
            this.label = label;
        }
    }
}
