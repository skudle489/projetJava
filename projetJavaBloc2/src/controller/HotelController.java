package controller;

import business.HotelManager;
import exceptions.GetAllHotelsException;
import model.Hotel;

import java.util.ArrayList;

public class HotelController {
    private HotelManager hotelManager;
    public HotelController(){
        hotelManager = new HotelManager();
    }

    public ArrayList<Hotel> getAllHotels() throws GetAllHotelsException {
        return hotelManager.getAllHotels();
    }
}
