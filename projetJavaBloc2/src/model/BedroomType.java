package model;

import exceptions.BedroomTypeCreationException;

public class BedroomType {
    private String label;

    public BedroomType(String label) throws BedroomTypeCreationException {
        setLabel(label);
    }



    public void setLabel(String label) throws BedroomTypeCreationException {
        if (label == null || label.equals("")){
            throw new BedroomTypeCreationException("Le label ne peut etre null ou vide");
        } else {
            this.label = label;
        }
    }
}
