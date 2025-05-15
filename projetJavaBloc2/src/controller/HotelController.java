package controller;

import business.CustomerManager;
import business.HotelManager;
import exceptions.GetAllCustomersException;
import exceptions.GetAllHotelsException;
import model.Customer;
import model.Hotel;

import java.util.ArrayList;

public class HotelController {
    private HotelManager hotelManager;

    public HotelController() {
        hotelManager = new HotelManager();
    }

    public ArrayList<Hotel> getAllHotels() throws GetAllHotelsException {
        return hotelManager.getAllHotels();

    }
}
