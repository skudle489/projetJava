package userInterface;


import exceptions.GetAllCustomersException;
import model.Customer;
import model.Hotel;
import model.Review;
import utils.AppControllers;
import utils.JComboBoxLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateReviewPanel extends JPanel {

    private JPanel identifyingCommentPanel;
    private JPanel sendButtonPanel;
    private JLabel customerEmailLabel, hotelLabel, reviewLabel;
    private JComboBox customersEmailComboBox, hotelsComboBox, reviewsCustomerComboBox;
    private JButton searchCommentButton;


    private MainWindows mainWindows;


    private AppControllers appControllers;

    public UpdateReviewPanel(AppControllers appControllers) {
        this.appControllers = appControllers;
        setUpUI();
    }

    public void setUpUI() {


        identifyingCommentPanel = new JPanel();
        identifyingCommentPanel.setLayout(new GridLayout(3, 2, 2, 2));

        customerEmailLabel = new JLabel("Client recherché :");
        identifyingCommentPanel.add(customerEmailLabel);

        customersEmailComboBox = new JComboBox();
        identifyingCommentPanel.add(customersEmailComboBox);


        hotelLabel = new JLabel("Hotel Recherché :");
        identifyingCommentPanel.add(hotelLabel);

        hotelsComboBox = new JComboBox();
        identifyingCommentPanel.add(hotelsComboBox);

        reviewLabel = new JLabel("Commentaire recherché :");
        identifyingCommentPanel.add(reviewLabel);

        reviewsCustomerComboBox = new JComboBox();
        identifyingCommentPanel.add(reviewsCustomerComboBox);


        sendButtonPanel = new JPanel();
        searchCommentButton = new JButton("Recherché");
        searchCommentButton.setPreferredSize(new Dimension(300, 100));
        sendButtonPanel.add(searchCommentButton);

        searchCommentButton.addActionListener(new SearchReviewButtonListener());

        customersEmailComboBox.addActionListener(new CustomerAndHotelComboBoxListener());
        hotelsComboBox.addActionListener(new CustomerAndHotelComboBoxListener());

        JComboBoxLoader.loadAllCustomersInComboBox(customersEmailComboBox, appControllers);
        JComboBoxLoader.loadAllHotelsInComboBox(hotelsComboBox, appControllers);

        this.setLayout(new GridLayout(2, 1, 50, 50));
        this.add(identifyingCommentPanel);
        this.add(sendButtonPanel);
    }

    public void setMainWindows(MainWindows mainWindows) {
        this.mainWindows = mainWindows;
    }



    private class CustomerAndHotelComboBoxListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            Customer selectedCustomerComboBox = (Customer) customersEmailComboBox.getSelectedItem();
            Hotel selectedHotelComboBox = (Hotel) hotelsComboBox.getSelectedItem();
            reviewsCustomerComboBox.removeAllItems();
            if (selectedCustomerComboBox != null && selectedHotelComboBox != null) {
                JComboBoxLoader.loadAllReviewsCustomerInComboBox(reviewsCustomerComboBox, selectedCustomerComboBox, selectedHotelComboBox, appControllers);
            }
        }
    }

    private class SearchReviewButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Customer selectedCustomer = (Customer) customersEmailComboBox.getSelectedItem();
                Hotel selectedHotel = (Hotel) hotelsComboBox.getSelectedItem();
                Review selectedReview = (Review) reviewsCustomerComboBox.getSelectedItem();

                if (selectedCustomer != null && selectedHotel != null && selectedReview != null) {
                    ReviewForm reviewForm = new ReviewForm(appControllers, selectedCustomer, selectedHotel, selectedReview);
                    reviewForm.setMainWindows(mainWindows);
                    mainWindows.showPanel(reviewForm);
                } else {
                    JOptionPane.showMessageDialog(mainWindows, "Il faut sélectionner un client, hotel et un avis.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainWindows, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
