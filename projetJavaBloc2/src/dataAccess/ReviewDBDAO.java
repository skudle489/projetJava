package dataAccess;

import exceptions.*;
import model.Customer;
import model.Review;
import model.SearchReviewsModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class ReviewDBDAO implements IReviewDataAccess {
    private static Connection connection;

    public ReviewDBDAO() throws DataAccessException {
        connection = DatabaseConnection.getInstance();
    }

    public void addReview(Review review) throws AddReviewException {
        try {
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
        } catch (SQLException e) {
            throw new AddReviewException("Erreur lors de l'ajout d'un avis " + e.getMessage());
        }
    }

    public ArrayList<Review> getAllReviews() throws GetAllReviewException {
        try {

            String sqlInstruction = "select * from review";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();

            Review review;
            String comment, title, customer;
            int hotel, star;
            boolean isAnonymous;
            java.sql.Date creationSQLDate, lastVisitSQLDateHotelCountry;
            LocalDate lastVisitDateHotelCountry;
            ArrayList<Review> reviews = new ArrayList<>();

            while (data.next()) {
                comment = data.getString("comment");
                lastVisitSQLDateHotelCountry = data.getDate("last_visit_date_hotel_country");
                creationSQLDate = data.getDate("creation_date");
                customer = data.getString("customer");
                star = data.getInt("star");
                isAnonymous = data.getBoolean("is_anonymous");
                title = data.getString("title");
                hotel = data.getInt("hotel");

                if (lastVisitSQLDateHotelCountry != null) {
                    lastVisitDateHotelCountry = lastVisitSQLDateHotelCountry.toLocalDate();
                } else {
                    lastVisitDateHotelCountry = null;
                }



                review = new Review(comment, hotel, title, isAnonymous, star, customer, creationSQLDate.toLocalDate(), lastVisitDateHotelCountry);
                reviews.add(review);
            }
            return reviews;
        } catch (SQLException | ReviewCreationException exception){
            throw new GetAllReviewException("Erreur lors de la lecture de tous les avis");
        }
    }

    public ArrayList<Review> getAllReviewsByHotel(int hotel) throws GetAllReviewException {

        try {
            String sqlInstruction = "select * from review where hotel = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, hotel);
            ResultSet data = preparedStatement.executeQuery();
            String comment;
            String title;
            boolean isAnonymous;
            int star;
            String customer;
            LocalDate lastVisitDateHotelCountry;
            LocalDate creationDate;
            ArrayList<Review> reviews = new ArrayList<>();
            while (data.next()) {
                comment = data.getString("comment");
                title = data.getString("title");
                isAnonymous = data.getBoolean("is_anonymous");
                star = data.getInt("star");
                customer = data.getString("customer");

                java.sql.Date lastVisitSqlDate = data.getDate("last_visit_date_hotel_country");
                lastVisitDateHotelCountry = (lastVisitSqlDate != null) ? lastVisitSqlDate.toLocalDate() : null;

                java.sql.Date creationSqlDate = data.getDate("creation_date");
                creationDate = (creationSqlDate != null) ? creationSqlDate.toLocalDate() : null;

                reviews.add(new Review(comment, hotel, title, isAnonymous, star, customer, creationDate, lastVisitDateHotelCountry));
            }
            return reviews;
        } catch (SQLException | ReviewCreationException exception){
            throw new GetAllReviewException("Erreur lors de la lectures de tous les avis de l'hotel " + exception.getMessage());
        }
    }


    public void updateReview(Review review) throws UpdateReviewException {

        try {

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
        } catch (SQLException e) {
            throw new UpdateReviewException("Erreur lors de la mise à jour de l'avis");
        }
    }

    public void deleteReview(int hotel, String customer, LocalDate creationDate) throws ReviewException {
        try {
            String sqlInstruction = "delete from review where hotel = ? and customer = ? and creation_date = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, hotel);
            preparedStatement.setString(2, customer);
            preparedStatement.setDate(3, java.sql.Date.valueOf(creationDate));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ReviewException("Erreur lors de la suppression d'un avis " + e.getMessage());
        }

    }



    public void deleteReviews(String customer) throws DeleteReviewException {
        try {
            String sqlInstruction = "delete from review where customer = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, customer);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new DeleteReviewException("Erreur lors de la supression de l'avis client");
        }
    }


    public ArrayList<SearchReviewsModel> searchReviewsByRatingAndDates(int starRating, LocalDate startDate, LocalDate endDate) throws ReviewCreationException {
        try {
            String sqlInstruction = "select r.comment, h.name, h.stars, c.first_name, c.last_name from review as r inner join hotel as h on r.hotel = h.id inner join customer as c on r.customer = c.mail_adress where r.star = ? and r.is_anonymous = false and r.creation_date between ? and ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, starRating);
            preparedStatement.setDate(2, java.sql.Date.valueOf(startDate));
            preparedStatement.setDate(3, java.sql.Date.valueOf(endDate));
            ResultSet data = preparedStatement.executeQuery();
            ArrayList<SearchReviewsModel> searchReviewsModels = new ArrayList<>();

            String comment;
            String hotelName;
            int hotelStars;
            String customerFirstName;
            String customerLastName;



            while (data.next()){
                comment = data.getString("comment");
                if (comment == null) {
                    comment = "/";
                }
                hotelName = data.getString("name");
                customerFirstName = data.getString("first_name");
                customerLastName = data.getString("last_name");
                hotelStars = data.getInt("stars");


                searchReviewsModels.add(new SearchReviewsModel(comment, hotelName, hotelStars, customerFirstName, customerLastName));
            }

            return searchReviewsModels;
        } catch (SQLException | SearchReviewModelException e) {
            throw new ReviewCreationException("Erreur lors de la recherches des avis " + e.getMessage());
        }

    }

    public ArrayList<Review> getAllReviewsByCustomerAndHotel(String customer, int hotel) throws ReviewException {

        try{

            String sqlInstruction = "select * from review where customer = ? and hotel = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, customer);
            preparedStatement.setInt(2, hotel);
            ResultSet data = preparedStatement.executeQuery();

            String comment;
            String title;
            boolean isAnonymous;
            int star;
            LocalDate creationDate;
            java.sql.Date lastVisitSQLDateHotelCountry;
            LocalDate lastVisitDateHotelCountry;

            ArrayList<Review> reviews = new ArrayList<>();

            while (data.next()) {
                lastVisitDateHotelCountry = null;
                comment = data.getString("comment");
                title = data.getString("title");
                isAnonymous = data.getBoolean("is_anonymous");
                star = data.getInt("star");
                creationDate = data.getDate("creation_date").toLocalDate();
                lastVisitSQLDateHotelCountry = data.getDate("last_visit_date_hotel_country");
                if (lastVisitSQLDateHotelCountry != null){
                    lastVisitDateHotelCountry = lastVisitSQLDateHotelCountry.toLocalDate();
                }

                reviews.add(new Review(comment, hotel, title, isAnonymous, star, customer, creationDate, lastVisitDateHotelCountry));
            }
            return reviews;

        }catch (SQLException | ReviewCreationException exception){
            throw new ReviewException("Erreur lors de la lecture de tous les commentaires du client - " + exception.getMessage());
        }

    }

    public boolean reviewExists(String customer, int hotel, LocalDate creationDate) throws ReviewException {

        try {
            String sqlInstruction = "select COUNT(*) from review where customer = ? and hotel = ? and creation_date = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, customer);
            preparedStatement.setInt(2, hotel);
            preparedStatement.setDate(3, java.sql.Date.valueOf(creationDate));
            ResultSet data = preparedStatement.executeQuery();
            data.next();
            return data.getInt(1) > 0;
        } catch (SQLException exception){
            throw new ReviewException("Erreur lors de la vérification de la présence d'un avis dans la base de donnée " + exception.getMessage());
        }
    }


}
