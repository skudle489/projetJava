package controller;

import business.ReviewManager;
import exceptions.AddReviewException;
import model.Review;

import java.sql.SQLException;

public class ReviewController {
    private ReviewManager reviewManager;
    public ReviewController() {
        reviewManager = new ReviewManager();
    }

    public void addReview(Review review) throws AddReviewException {
        reviewManager.addReview(review);
    }

}
