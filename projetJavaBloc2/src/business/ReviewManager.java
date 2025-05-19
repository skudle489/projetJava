package business;

import dataAccess.HotelDBDAO;
import dataAccess.IReviewDataAccess;
import dataAccess.ReviewDBDAO;
import exceptions.*;
import model.Customer;
import model.Review;
import model.SearchReviewsModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReviewManager {
    private IReviewDataAccess reviewDBDAO;

    public ReviewManager() {
        reviewDBDAO = new ReviewDBDAO();
    }

    public boolean reviewExists(String customer, int hotel, LocalDate creationDate) throws ReviewException {
        return reviewDBDAO.reviewExists(customer,hotel,creationDate);
    }

    public boolean isLastVisitDateValid(LocalDate lastVisitDate) {
        return !lastVisitDate.isAfter(LocalDate.now());
    }

    public void addReview(Review review) throws AddReviewException, ReviewException, UpdateReviewException {

        LocalDate lastVisitDate = review.getLastVisitDateHotelCountry();

        if (lastVisitDate != null && !isLastVisitDateValid(lastVisitDate)) {
            throw new AddReviewException("La date de dernière visite du pays de l'hôtel ne peut être dans le futur.");
        }

        if (reviewDBDAO.reviewExists(review.getCustomer(), review.getHotel(), review.getCreationDate())) {
            reviewDBDAO.updateReview(review);
        } else {
            reviewDBDAO.addReview(review);
        }
    }


    public ArrayList<Review> getAllReviews() throws GetAllReviewException {
        return reviewDBDAO.getAllReviews();
    }

    public void deleteReview(int hotel, String customer, LocalDate creationDate) throws ReviewException {
        reviewDBDAO.deleteReview(hotel, customer, creationDate);
    }

    public ArrayList<Review> getAllReviewsByHotel(int hotel) throws GetAllReviewException {
        return reviewDBDAO.getAllReviewsByHotel(hotel);
    }

    public ArrayList<SearchReviewsModel> searchReviewsByRatingAndDates(int starRating, LocalDate startDate, LocalDate endDate) throws ReviewCreationException {
        return reviewDBDAO.searchReviewsByRatingAndDates(starRating, startDate, endDate);
    }

    public ArrayList<Review> getAllReviewsByCustomerAndHotel(String customer, int hotel) throws ReviewException {

        return reviewDBDAO.getAllReviewsByCustomerAndHotel(customer, hotel);
    }





}
