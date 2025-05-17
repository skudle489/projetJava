package business;

import dataAccess.HotelDBDAO;
import exceptions.GetAllHotelsException;
import exceptions.GetAllReviewException;
import exceptions.HotelException;
import model.Hotel;
import model.Review;

import java.util.ArrayList;

public class HotelManager {
    private HotelDBDAO hotelDBDAO;
    private ReviewManager reviewManager;
    public HotelManager() {
        hotelDBDAO = new HotelDBDAO();
        reviewManager = new ReviewManager();
    }

    public ArrayList<Hotel> getAllHotels() throws GetAllHotelsException {
        return hotelDBDAO.getAllHotels();
    }

    public String getHotelName(int id) throws HotelException {
        return hotelDBDAO.getHotelName(id);
    }

    public double getAverageStarsReviewsByHotel(int hotelId) throws GetAllReviewException {
        ArrayList<Review> reviews = reviewManager.getAllReviewsByHotel(hotelId);
        double totalStarsReviews = 0;

        for (Review review : reviews) {
            totalStarsReviews += review.getStar();
        }

        return totalStarsReviews / reviews.size();
    }
}
