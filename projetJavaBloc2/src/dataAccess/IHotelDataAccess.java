package dataAccess;

import exceptions.GetAllHotelsException;
import exceptions.HotelException;
import model.Hotel;

import java.util.ArrayList;

public interface IHotelDataAccess {
    public ArrayList<Hotel> getAllHotels() throws GetAllHotelsException;
    public String getHotelName(int id) throws HotelException;
    public int getHotelStar(int id) throws HotelException;

}
