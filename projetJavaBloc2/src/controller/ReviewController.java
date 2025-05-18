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

    public void addReview(Review review) throws AddReviewException, UpdateReviewException, ReviewException {
        reviewManager.addReview(review);
    }

    public ArrayList<Review> getAllReviews() throws GetAllReviewException {
        return reviewManager.getAllReviews();
    }


    public void deleteReview(int hotel, String customer, LocalDate creationDate) throws ReviewException {
        reviewManager.deleteReview(hotel, customer, creationDate);
    }

    public ArrayList<Review> getAllReviewsByHotel(int hotel) throws GetAllReviewException {
        return reviewManager.getAllReviewsByHotel(hotel);
    }

    public ArrayList<SearchReviewsModel> searchReviewsByRatingAndDates(int starRating, LocalDate startDate, LocalDate endDate) throws ReviewCreationException {
        return reviewManager.searchReviewsByRatingAndDates(starRating, startDate, endDate);
    }

    public ArrayList<Review> getAllReviewsByCustomerAndHotel(String customer, int hotel) throws ReviewException {
        return reviewManager.getAllReviewsByCustomerAndHotel(customer, hotel);
    }
}
