package dataAccess;

import exceptions.*;
import model.Review;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface IReviewDataAccess {
    void addReview(Review review) throws AddReviewException;
    ArrayList<Review> getAllReviews() throws GetAllReviewException;
    void updateReview(Review review) throws UpdateReviewException;
    void deleteReviews(String customer) throws DeleteReviewException;

}
