package business;

import dataAccess.HotelDBDAO;
import exceptions.GetAllHotelsException;
import exceptions.HotelException;
import model.Hotel;

import java.util.ArrayList;

public class HotelManager {
    private HotelDBDAO hotelDBDAO;
    public HotelManager() {
        hotelDBDAO = new HotelDBDAO();
    }

    public ArrayList<Hotel> getAllHotels() throws GetAllHotelsException {
        return hotelDBDAO.getAllHotels();
    }

    public String getHotelName(int id) throws HotelException {
        return hotelDBDAO.getHotelName(id);
    }
}
