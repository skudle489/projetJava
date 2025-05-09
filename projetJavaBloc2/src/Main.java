import dataAccess.CountryDBDAO;
import dataAccess.CustomerDBDAO;
import dataAccess.DatabaseConnection;
import dataAccess.ReviewDBDAO;
import model.*;
import userInterface.AllCustomersModel;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        try {
            Country country = new Country("BE", "Belgique");
            Locality locality = new Locality("Limelette", "BE", "1342");
            Customer customer = new Customer("thounythea4@gmail.com", "Thouny", "Thea", "0498797605", "Rue de l'Europe", 1, 30, 9, 2003, false, "Spy", "5190", "BE", null);
            Hotel hotel = new Hotel(1, "Rue de la brochette", 8, 5, "Namur", "5190", "BE", "Briochoc");
            Bed bed = new Bed("King");
            Amenity amenity = new Amenity("Chauffage");
            BedroomType bedroomType = new BedroomType("Familliale");
            Bedroom bedroom = new Bedroom(32, 5, 100, 50, null, true, null, "Familliale", 1);
            BedroomOwnsAmenity bedroomOwnsAmenity = new BedroomOwnsAmenity("Chauffage", 32);

            Star star = CreateStar.getStar(5);
            Star star2 = CreateStar.getStar(2);
            Star star3 = CreateStar.getStar(3);
            Star star4 = CreateStar.getStar(4);
            Star star5 = CreateStar.getStar(5);
            Star star6 = CreateStar.getStar(5);

            BedroomType bedroomType2 = CreateBedroomType.getBedroomType("Familliale");

            BedroomOwnsBed bedroomOwnsBed = new BedroomOwnsBed(32, "King");
            Review review = new Review("Trop la classe", 1, "A recommender", true, 4, "thounythea4@gmail.com", LocalDate.now(), null);

            Country country2 = CreateCountry.getCountry("BE", "Belgique");
            Country country3 = CreateCountry.getCountry("BE", "Belgique");


            //Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotel", "root", "root");


            CustomerDBDAO customerDBDAO = new CustomerDBDAO();
            //customerDBDAO.deleteCustomer(customer.getMailAdress());
            //Customer customer1 = customerDBDAO.getCustomer("thounythea4@gmail.com");
            //System.out.println(customer1);

            //customerDBDAO.addCustomer(customer);
            ReviewDBDAO reviewDBDAO = new ReviewDBDAO();
            //reviewDBDAO.addReview(review);



            CountryDBDAO countryDBDAO = new CountryDBDAO();

            ArrayList<Customer> customers = customerDBDAO.getAllCustomers();
            /*for (Customer c : customers) {
                System.out.println(c);
            }*/





            /*System.out.println(hotel);
            System.out.println(country);
            System.out.println(customer);
            System.out.println(hotel);
            System.out.println(review);
            System.out.println(countryDBDAO.getCountry("BE"));*/


            //System.out.println(customerDBDAO2.getAllCustomers());


            AllCustomersModel model = new AllCustomersModel(customers);
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);

            JFrame frame = new JFrame("Liste des clients");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.add(scrollPane);
            frame.setVisible(false);

            //customerDBDAO.deleteCustomer("thounythea4@gmail.com");
            //customerDBDAO.addCustomer(customer);
            //customerDBDAO.updateCustomer(customer);
            //reviewDBDAO.addReview(review);
            //reviewDBDAO.updateReview(review);





            DatabaseConnection.getInstance().close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
