package controller;

import business.BedroomTypeManager;
import exceptions.BedroomTypeCreationException;
import model.BedroomType;

import java.util.ArrayList;

public class BedroomTypeController {
    private BedroomTypeManager bedroomTypeManager;
    public BedroomTypeController() {
        bedroomTypeManager = new BedroomTypeManager();
    }

    public ArrayList<BedroomType> getAllTypesBedroom() throws BedroomTypeCreationException {
        return bedroomTypeManager.getAllTypesBedroom();
    }
}
