package controller;

import business.ReviewManager;
import exceptions.GetAllReviewException;
import model.Review;

import java.util.ArrayList;

public class ReviewController {
    private ReviewManager reviewManager;
    public ReviewController() {
        reviewManager = new ReviewManager();
    }

    public ArrayList<Review> getAllReviewsByHotel(int hotel) throws GetAllReviewException {
        return reviewManager.getAllReviewsByHotel(hotel);
    }
}
