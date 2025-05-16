package model;

import exceptions.BedCreationException;

public class Bed {
    private String type;

    public Bed(String type) throws BedCreationException {
        setType(type);
    }



    public void setType(String type) throws BedCreationException {
        if (type == null || type.equals("")){
            throw new BedCreationException("le type de la chambre ne peut etre null ou vide");
        } else {
            this.type = type;
        }
    }

    public String getType() {
        return type;
    }
}
