package userInterface;

import exceptions.DeleteCustomerException;
import exceptions.DeleteReviewException;
import exceptions.ReviewException;
import model.Customer;
import model.Hotel;
import model.Review;
import utils.AppControllers;
import utils.JComboBoxLoader;


import javax.swing.*;
import javax.swing.plaf.ActionMapUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteReview extends JPanel {
    private JPanel identifyingCommentPanel;
    private JPanel deleteButtonPanel;
    private JLabel customerEmailLabel, reviewLabel, hotelLabel;
    private JComboBox customerEmailsComboBox, reviewsComboBox, hotelsComboBox;
    private JButton deleteButton;

    private MainWindows mainWindows;
    private DeleteReviewListener deleteReviewListener;
    private AppControllers appControllers;

    public DeleteReview(AppControllers appControllers) {
        this.appControllers = appControllers;
        setUpUI();
    }


    public void setUpUI() {

        identifyingCommentPanel = new JPanel();
        identifyingCommentPanel.setLayout(new GridLayout(3, 2, 2, 2));

        customerEmailLabel = new JLabel("Client recherché :");
        identifyingCommentPanel.add(customerEmailLabel);

        customerEmailsComboBox = new JComboBox();
        identifyingCommentPanel.add(customerEmailsComboBox);


        hotelLabel = new JLabel("Hotel recherché : ");
        identifyingCommentPanel.add(hotelLabel);

        hotelsComboBox = new JComboBox();
        hotelsComboBox.addActionListener(new CustomerAndHotelComboBoxListener());
        identifyingCommentPanel.add(hotelsComboBox);

        reviewLabel = new JLabel("Avis :");
        identifyingCommentPanel.add(reviewLabel);

        reviewsComboBox = new JComboBox();
        identifyingCommentPanel.add(reviewsComboBox);

        deleteButtonPanel = new JPanel();

        deleteButton = new JButton("Supprimer cet avis");
        deleteButton.setPreferredSize(new Dimension(300, 100));
        deleteButtonPanel.add(deleteButton);
        deleteReviewListener = new DeleteReviewListener();
        deleteButton.addActionListener(deleteReviewListener);

        JComboBoxLoader.loadAllCustomersInComboBox(customerEmailsComboBox, appControllers);
        JComboBoxLoader.loadAllHotelsInComboBox(hotelsComboBox, appControllers);
        customerEmailsComboBox.addActionListener(new CustomerAndHotelComboBoxListener());
        hotelsComboBox.addActionListener(new CustomerAndHotelComboBoxListener());


        setLayout(new GridLayout(2, 1, 50, 50));
        add(identifyingCommentPanel);
        add(deleteButtonPanel);
    }

    public void setMainWindows(MainWindows mainWindows) {
        this.mainWindows = mainWindows;
    }

    private class CustomerAndHotelComboBoxListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            Customer selectedCustomerComboBox = (Customer) customerEmailsComboBox.getSelectedItem();
            Hotel selectedHotelComboBox = (Hotel) hotelsComboBox.getSelectedItem();

            if(selectedCustomerComboBox!= null && selectedHotelComboBox!=null){
                reviewsComboBox.removeAllItems();
                JComboBoxLoader.loadAllReviewsCustomerInComboBox(reviewsComboBox, selectedCustomerComboBox, selectedHotelComboBox, appControllers);
            }

        }
    }


    private class DeleteReviewListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                Customer selectedCustomer = (Customer) customerEmailsComboBox.getSelectedItem();
                Hotel selectedHotelComboBox = (Hotel) hotelsComboBox.getSelectedItem();
                Review selectedReview = (Review) reviewsComboBox.getSelectedItem();
                if (selectedCustomer != null && selectedHotelComboBox != null && selectedReview != null) {
                    int confirmation = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce commentaire ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        appControllers.getReviewController().deleteReview(selectedHotelComboBox.getId(), selectedCustomer.getMailAdress(), selectedReview.getCreationDate());
                        reviewsComboBox.removeAllItems();
                        JComboBoxLoader.loadAllReviewsCustomerInComboBox(reviewsComboBox, selectedCustomer, selectedHotelComboBox, appControllers);
                        mainWindows.revalidate();
                        mainWindows.repaint();
                    }

                }
            } catch(ReviewException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        }
    }
}

