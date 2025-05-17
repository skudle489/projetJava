package controller;

import business.ReviewManager;
import exceptions.*;
import model.Review;
import model.SearchReviewsModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReviewController {
    private ReviewManager reviewManager;
    public ReviewController() {
        reviewManager = new ReviewManager();
    }

    public ArrayList<Review> getAllReviewsByHotel(int hotel) throws GetAllReviewException {
        return reviewManager.getAllReviewsByHotel(hotel);
    }

    public ArrayList<SearchReviewsModel> searchReviewsByRatingAndDates(int starRating, LocalDate startDate, LocalDate endDate) throws SearchReviewModelException, HotelException, GetCustomerException, ReviewCreationException {
        return reviewManager.searchReviewsByRatingAndDates(starRating, startDate, endDate);
    }
}
