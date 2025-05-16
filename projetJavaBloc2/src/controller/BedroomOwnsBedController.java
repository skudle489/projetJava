package controller;

import business.BedroomOwnsBedManager;
import exceptions.GetAllBedsFromBedroomException;
import model.BedroomOwnsBed;

import java.util.ArrayList;

public class BedroomOwnsBedController {
    private BedroomOwnsBedManager bedroomOwnsBedManager;
    public BedroomOwnsBedController() {
        bedroomOwnsBedManager = new BedroomOwnsBedManager();
    }

    public ArrayList<BedroomOwnsBed> getAllBedsFromBedroom(int bedroom, int hotel) throws GetAllBedsFromBedroomException {
        return bedroomOwnsBedManager.getAllBedsFromBedroom(bedroom, hotel);
    }
}
