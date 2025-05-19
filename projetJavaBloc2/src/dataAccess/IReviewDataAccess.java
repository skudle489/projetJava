package dataAccess;

import exceptions.*;
import model.Review;
import model.SearchReviewsModel;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IReviewDataAccess {
    void addReview(Review review) throws AddReviewException;
    ArrayList<Review> getAllReviews() throws GetAllReviewException;
    void updateReview(Review review) throws UpdateReviewException;
    void deleteReviews(String customer) throws DeleteReviewException;
    void deleteReview(int hotel, String customer, LocalDate creationDate) throws ReviewException;
    boolean reviewExists(String customer, int hotel, LocalDate creationDate) throws ReviewException;
    ArrayList<SearchReviewsModel> searchReviewsByRatingAndDates(int starRating, LocalDate startDate, LocalDate endDate) throws ReviewCreationException;


    ArrayList<Review> getAllReviewsByHotel(int hotel) throws GetAllReviewException;
    ArrayList<Review> getAllReviewsByCustomerAndHotel(String customer, int hotel) throws ReviewException;

}
