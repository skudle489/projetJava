package dataAccess;

import exceptions.GetAllHotelsException;
import exceptions.HotelException;
import model.Hotel;

import java.util.ArrayList;

public interface IHotelDataAccess {
    ArrayList<Hotel> getAllHotels() throws GetAllHotelsException;
    String getHotelName(int id) throws HotelException;

}
