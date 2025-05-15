package business;

import dataAccess.HotelDBDAO;
import dataAccess.ReviewDBDAO;
import exceptions.AddReviewException;
import exceptions.DataAccessException;
import model.Review;

import java.sql.SQLException;

public class ReviewManager {
    private ReviewDBDAO reviewDBDAO;
    public ReviewManager() {
        try {
            reviewDBDAO = new ReviewDBDAO();
        } catch (Exception e) {
            throw new DataAccessException("Erreur de base de donnée");
        }
    }

    public void addReview(Review review) throws AddReviewException {
        reviewDBDAO.addReview(review);
    }


}
