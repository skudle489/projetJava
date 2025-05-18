package business;

import dataAccess.BedroomTypeDBDAO;
import exceptions.BedroomTypeCreationException;
import model.BedroomType;

import java.util.ArrayList;

public class BedroomTypeManager {
    private BedroomTypeDBDAO bedroomTypeDBDAO;
    public BedroomTypeManager() {
        bedroomTypeDBDAO = new BedroomTypeDBDAO();
    }

    public ArrayList<BedroomType> getAllTypesBedroom() throws BedroomTypeCreationException {
        return bedroomTypeDBDAO.getAllTypesBedroom();
    }
}
