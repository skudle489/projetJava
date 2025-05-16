package business;

import dataAccess.BedroomDBDAO;
import exceptions.BedroomCreationException;
import model.Bedroom;

import java.util.ArrayList;

public class BedroomManager {
    private BedroomDBDAO bedroomDBDAO;

    public BedroomManager() {
        bedroomDBDAO = new BedroomDBDAO();
    }

    public ArrayList<Bedroom> getBedroomsFromHotel(int hotelID) throws BedroomCreationException {
        return bedroomDBDAO.getBedroomsFromHotel(hotelID);
    }

    public Bedroom getBedroom(int bedroomNumber, int hotel) throws BedroomCreationException {
        return bedroomDBDAO.getBedroom(bedroomNumber, hotel);
    }
}
