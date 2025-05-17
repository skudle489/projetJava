package utils;


import exceptions.*;
import model.Customer;
import model.Hotel;
import model.Review;
import userInterface.MainWindows;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

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

    /*public static void loadAllCreationDateInComboBox(JComboBox datesCreationComboBox, MainWindows updateCreationDateListener){
        try {
            ArrayList<Review>reviews=updateCreationDateListener.getReviewController().getAllReviews();
            ArrayList <LocalDate> datesCreation=reviews.stream().map(Review::getCreationDate).collect(Collectors.toCollection(ArrayList::new));;
            datesCreationComboBox.removeAllItems();
            for(LocalDate date : datesCreation){
                datesCreationComboBox.addItem(date);
            }

        }catch (GetAllReviewException exception){
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }*/

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
