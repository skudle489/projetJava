package dataAccess;

import exceptions.ReviewCreationException;
import model.Customer;
import model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReviewDBDAO {
    private static Connection connection;

    public ReviewDBDAO() throws SQLException {
        connection = DatabaseConnection.getInstance();
    }

    public void addReview(Review review) throws SQLException {
        String sqlInstruction = "insert into review values(?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
        preparedStatement.setString(1, review.getComment());

        LocalDate lastVisitDateHotelCountry = review.getLastVisitDateHotelCountry();

        if (lastVisitDateHotelCountry != null) {
            preparedStatement.setDate(2, java.sql.Date.valueOf(lastVisitDateHotelCountry));
        } else {
            preparedStatement.setNull(2, java.sql.Types.DATE);
        }

        LocalDate creationDate = review.getCreationDate();
        preparedStatement.setDate(3, java.sql.Date.valueOf(creationDate));


        preparedStatement.setString(4, review.getCustomer());
        preparedStatement.setInt(5, review.getStar());
        preparedStatement.setBoolean(6, review.getIsAnonymous());
        preparedStatement.setString(7, review.getTitle());
        preparedStatement.setInt(8, review.getHotel());
        preparedStatement.executeUpdate();
    }

    public ArrayList<Review> getAllReviews() throws SQLException, ReviewCreationException {
        String sqlInstruction = "select * from review";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
        ResultSet data = preparedStatement.executeQuery();

        Review review;
        String comment, title, customer;
        int hotel, star;
        boolean isAnonymous;
        LocalDate creationDate, lastVisitDateHotelCountry;
        ArrayList<Review> reviews = new ArrayList<>();

        while (data.next()) {
            comment = data.getString("comment");
            lastVisitDateHotelCountry = data.getDate("last_visit_date_hotel_country").toLocalDate();
            creationDate = data.getDate("creation_date").toLocalDate();
            customer = data.getString("customer");
            star = data.getInt("star");
            isAnonymous = data.getBoolean("is_anonymous");
            title = data.getString("title");
            hotel = data.getInt("hotel");
            review = new Review(comment, hotel, title, isAnonymous, star, customer, creationDate, lastVisitDateHotelCountry);
            reviews.add(review);
        }
        return reviews;
    }


    public void updateReview(Review review) throws SQLException {
        String sqlInstruction = "update review set comment = ?, title = ?, is_anonymous = ?, star = ?, last_visit_date_hotel_country = ? where hotel = ? and customer = ? and creation_date = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
        preparedStatement.setString(1, review.getComment());
        preparedStatement.setString(2, review.getTitle());
        preparedStatement.setBoolean(3, review.getIsAnonymous());
        preparedStatement.setInt(4, review.getStar());

        if (review.getLastVisitDateHotelCountry() != null) {
            preparedStatement.setDate(5, java.sql.Date.valueOf(review.getLastVisitDateHotelCountry()));
        } else {
            preparedStatement.setNull(5, java.sql.Types.DATE);
        }

        preparedStatement.setInt(6, review.getHotel());
        preparedStatement.setString(7, review.getCustomer());
        preparedStatement.setDate(8, java.sql.Date.valueOf(review.getCreationDate()));
        preparedStatement.executeUpdate();
    }

    public void deleteReview(int hotel, String customer, LocalDate creationDate) throws SQLException {
        String sqlInstruction = "delete from review where hotel = ? and customer = ? and creation_date = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
        preparedStatement.setInt(1, hotel);
        preparedStatement.setString(2, customer);
        preparedStatement.setDate(3, java.sql.Date.valueOf(creationDate));
        preparedStatement.executeUpdate();
    }



    public void deleteReviews(String customer) throws SQLException {
        String sqlInstruction = "delete from review where customer = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
        preparedStatement.setString(1, customer);
        preparedStatement.executeUpdate();
    }

}
