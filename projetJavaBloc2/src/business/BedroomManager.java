package business;

import dataAccess.BedroomDBDAO;
import dataAccess.IBedroomDataAccess;
import exceptions.BedroomCreationException;
import model.Bedroom;
import model.BedroomInformationsModel;

import java.util.ArrayList;

public class BedroomManager {
    private IBedroomDataAccess bedroomDBDAO;

    public BedroomManager() {
        bedroomDBDAO = new BedroomDBDAO();
    }

    public ArrayList<Bedroom> getBedroomsFromHotel(int hotelID) throws BedroomCreationException {
        return bedroomDBDAO.getBedroomsFromHotel(hotelID);
    }

    public Bedroom getBedroom(int bedroomNumber, int hotel) throws BedroomCreationException {
        return bedroomDBDAO.getBedroom(bedroomNumber, hotel);
    }

    public ArrayList<BedroomInformationsModel> getAllBedroomsInformationsByType(String bedroomType) throws BedroomCreationException {
        return bedroomDBDAO.getAllBedroomsInformationsByType(bedroomType);
    }
}

