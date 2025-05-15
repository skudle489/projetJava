/*package business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/*public class SearchReviews {
    private static Connection connection;

    //creer une classe qui contient les info?
    /*public ??? searchReview (int stars, LocalDate startDate, LocalDate endDate) throws SQLException {
        String sqlInstruction = "SELECT r.comment, h.name, h.stars, c.first_name, c.last_name " +
                "FROM review AS r " +
                "INNER JOIN hotel AS h ON r.hotel_id = h.hotel_id " +
        "INNER JOIN customer AS c ON r.customer_email = c.mail_adress" +
                "WHERE r.stars = ? AND r.creation_date BETWEEN ? AND ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
        preparedStatement.setInt(1, stars);
        preparedStatement.setDate(2, java.sql.Date.valueOf(startDate));
        preparedStatement.setDate(3, java.sql.Date.valueOf(endDate));
        return preparedStatement.executeQuery();*/

    //}
//}
