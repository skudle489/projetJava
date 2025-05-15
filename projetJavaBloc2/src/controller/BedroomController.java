package controller;

import business.BedroomManager;
import exceptions.BedroomCreationException;
import model.Bedroom;

import java.util.ArrayList;

public class BedroomController {
    private BedroomManager bedroomManager;
    public BedroomController() {
        bedroomManager = new BedroomManager();
    }

    public ArrayList<Bedroom> getBedroomsFromHotel(int hotelID) throws BedroomCreationException {
        return bedroomManager.getBedroomsFromHotel(hotelID);
    }
}
