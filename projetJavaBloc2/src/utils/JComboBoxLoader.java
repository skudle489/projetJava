package utils;

import exceptions.*;
import model.Customer;
import model.Hotel;
import model.Review;

import javax.swing.*;
import java.util.ArrayList;


public class JComboBoxLoader {


    public static void loadAllCustomersInComboBox(JComboBox customersComboBox, AppControllers appControllers) {
        try {
            ArrayList<Customer> customers = appControllers.getCustomerController().getAllCustomers();
            customersComboBox.removeAllItems();
            for (Customer customer : customers) {
                customersComboBox.addItem(customer);
            }
        } catch (GetAllCustomersException exception){
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    public static void loadAllHotelsInComboBox(JComboBox hotelComboBox, AppControllers appControllers){
        try {
            ArrayList<Hotel>hotels = appControllers.getHotelController().getAllHotels();
            hotelComboBox.removeAllItems();
            for (Hotel hotel : hotels) {
                hotelComboBox.addItem(hotel);
            }

        }catch (GetAllHotelsException exception){
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    public static void loadAllReviewsCustomerInComboBox(JComboBox reviewsCustomerComboBox, Customer customer, Hotel hotel, AppControllers appControllers){
        try {
            ArrayList<Review>reviews=appControllers.getReviewController().getAllReviewsByCustomerAndHotel(customer.getMailAdress(), hotel.getId());

            for(Review review : reviews){
                reviewsCustomerComboBox.addItem(review);
            }

        }catch (ReviewException exception){
            JOptionPane.showMessageDialog(null, exception.getMessage());

        }
    }
}
