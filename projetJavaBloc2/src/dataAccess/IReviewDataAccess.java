package dataAccess;

import exceptions.ReviewCreationException;
import model.Review;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface IReviewDataAccess {
    void addReview(Review review) throws SQLException;
    ArrayList<Review> getAllReviews() throws SQLException, ReviewCreationException;
    void updateReview(Review review) throws SQLException;
    void deleteReview(int hotel, String customer, LocalDate creationDate) throws SQLException;

}
