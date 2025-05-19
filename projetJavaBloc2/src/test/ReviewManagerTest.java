package test;

import business.ReviewManager;
import exceptions.ReviewException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReviewManagerTest {
    private ReviewManager reviewManager;
    @BeforeEach
    void setUp() {
        reviewManager = new ReviewManager();
    }

    @Test
    void isLastVisitDateValid() {
        assertTrue(reviewManager.isLastVisitDateValid(LocalDate.now()));
    }

    @Test
    void reviewExists() throws ReviewException {
        assertTrue(reviewManager.reviewExists("jean.dupont@email.fr", 1, LocalDate.parse("2025-05-17")));
    }
}