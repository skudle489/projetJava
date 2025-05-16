package business;

import dataAccess.BedroomOwnsBedDBDAO;
import exceptions.GetAllBedsFromBedroomException;
import model.Bed;
import model.BedroomOwnsBed;

import java.util.ArrayList;

public class BedroomOwnsBedManager {
    private BedroomOwnsBedDBDAO bedroomOwnsBedDBDAO;
    public BedroomOwnsBedManager() {
        bedroomOwnsBedDBDAO = new BedroomOwnsBedDBDAO();
    }

    public ArrayList<BedroomOwnsBed> getAllBedsFromBedroom(int bedroom, int hotel) throws GetAllBedsFromBedroomException {
        return bedroomOwnsBedDBDAO.getAllBedsFromBedroom(bedroom, hotel);
    }
}
