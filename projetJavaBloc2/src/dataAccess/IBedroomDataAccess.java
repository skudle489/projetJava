package dataAccess;

import exceptions.BedroomCreationException;
import model.Bedroom;
import model.BedroomInformationsModel;

import java.util.ArrayList;

public interface IBedroomDataAccess {
    ArrayList<Bedroom> getBedroomsFromHotel(int hotelID) throws BedroomCreationException;
    Bedroom getBedroom(int bedroomNumber, int hotel) throws BedroomCreationException;
    ArrayList<BedroomInformationsModel> getAllBedroomsInformationsByType(String bedroomType) throws BedroomCreationException;

}
