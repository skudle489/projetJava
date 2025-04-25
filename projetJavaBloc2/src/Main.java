import dataAccess.CustomerDBDAO;
import dataAccess.DatabaseConnection;
import model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        try {
            Country country = new Country("BE", "Belgique");
            Locality locality = new Locality("Limelette", country, "1342");
            Customer customer = new Customer("thounythea4@gmail.com", "Thouny", "Thea", "0498797605", "Rue de l'Europe", 1, 30, 9, 2003, false, locality, null);
            Hotel hotel = new Hotel(1, "Rue de la brochette", 8, 5, locality, "Briochoc");
            System.out.println(hotel);
            Bed bed = new Bed("King");
            Amenity amenity = new Amenity("Chauffage");
            BedroomType bedroomType = new BedroomType("Familliale");
            Bedroom bedroom = new Bedroom(32, 5, 100, 50, null, true, null, bedroomType, hotel);
            BedroomOwnsAmenity bedroomOwnsAmenity = new BedroomOwnsAmenity(amenity, bedroom);

            Star star = CreateStar.getStar(1);
            Star star2 = CreateStar.getStar(2);
            Star star3 = CreateStar.getStar(3);
            Star star4 = CreateStar.getStar(4);
            Star star5 = CreateStar.getStar(5);
            Star star6 = CreateStar.getStar(5);

            BedroomType bedroomType2 = CreateBedroomType.getBedroomType("Familliale");

            BedroomOwnsBed bedroomOwnsBed = new BedroomOwnsBed(bedroom, bed);
            Review review = new Review("Trop la classe", hotel, "A recommender", true, star, customer, LocalDate.now(), null);

            Country country2 = CreateCountry.getCountry("BE", "Belgique");
            Country country3 = CreateCountry.getCountry("BE", "Belgique");
            System.out.println(country2);
            System.out.println(country3);

            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotel", "root", "root");


            CustomerDBDAO customerDBDAO = new CustomerDBDAO();
            Customer customer1 = customerDBDAO.getCustomer("thounythea4@gmail.com");
            System.out.println(customer1);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
