package dataAccess;

import exceptions.BedroomCreationException;
import model.Bedroom;
import model.BedroomInformationsModel;

import java.util.ArrayList;

public interface IBedroomDataAccess {
    public ArrayList<Bedroom> getBedroomsFromHotel(int hotelID) throws BedroomCreationException;
    public Bedroom getBedroom(int bedroomNumber, int hotel) throws BedroomCreationException;
    public ArrayList<BedroomInformationsModel> getAllBedroomsInformationsByType(String bedroomType) throws BedroomCreationException;

}
