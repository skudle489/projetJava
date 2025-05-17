package business;

import dataAccess.ReviewDBDAO;
import exceptions.GetAllReviewException;
import model.Review;

import java.util.ArrayList;

public class ReviewManager {
    private ReviewDBDAO reviewDBDAO;
    public ReviewManager() {
        reviewDBDAO = new ReviewDBDAO();
    }

    public ArrayList<Review> getAllReviewsByHotel(int hotel) throws GetAllReviewException {
        return reviewDBDAO.getAllReviewsByHotel(hotel);
    }
}
