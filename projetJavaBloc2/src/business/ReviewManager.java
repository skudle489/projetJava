package business;

import dataAccess.HotelDBDAO;
import dataAccess.ReviewDBDAO;
import exceptions.*;
import model.Customer;
import model.Review;
import model.SearchReviewsModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReviewManager {
    private ReviewDBDAO reviewDBDAO;
    private HotelDBDAO hotelDBDAO;
    private CustomerManager customerManager;
    public ReviewManager() {
        reviewDBDAO = new ReviewDBDAO();
        hotelDBDAO = new HotelDBDAO();
        customerManager = new CustomerManager();
    }

    public ArrayList<Review> getAllReviewsByHotel(int hotel) throws GetAllReviewException {
        return reviewDBDAO.getAllReviewsByHotel(hotel);
    }

    public ArrayList<SearchReviewsModel> searchReviewsByRatingAndDates(int starRating, LocalDate startDate, LocalDate endDate) throws SearchReviewModelException, HotelException, GetCustomerException, ReviewCreationException {
        ArrayList<Review> reviews = reviewDBDAO.searchReviewsByRatingAndDates(starRating, startDate, endDate);
        String hotelName;
        int hotelStar;
        Customer customer;

        ArrayList<SearchReviewsModel> searchReviewsModels = new ArrayList<>();

        for (Review review : reviews) {
            hotelName = hotelDBDAO.getHotelName(review.getHotel());
            hotelStar = hotelDBDAO.getHotelStar(review.getHotel());
            customer = customerManager.getCustomer(review.getCustomer());
            searchReviewsModels.add(new SearchReviewsModel(review.getComment(), hotelName, customer.getMailAdress(), hotelStar, customer.getFirstName(), customer.getLastName()));
        }
        return searchReviewsModels;
    }





}
