package userInterface;

import exceptions.GetAllHotelsException;
import exceptions.GetAllReviewException;
import model.Hotel;
import model.Review;
import utils.AppControllers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HotelReviewsPanel extends JPanel {
    private JPanel mainPanel, allHotelReviews;
    private JLabel hotelsLabel;
    private JComboBox hotelsComboBox;
    private AppControllers appControllers;
    private JLabel averageRatingReviewsHotel;

    public HotelReviewsPanel(AppControllers appControllers) throws GetAllHotelsException {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.appControllers = appControllers;
        setUpUI();
    }

    public void setUpUI() throws GetAllHotelsException {
        mainPanel = new JPanel();
        allHotelReviews = new JPanel();
        allHotelReviews.setLayout(new BoxLayout(allHotelReviews, BoxLayout.Y_AXIS)); // layout vertical

        mainPanel.setLayout(new GridLayout(1, 2));
        hotelsLabel = new JLabel("Hotel ");
        hotelsComboBox = new JComboBox();

        hotelsComboBox.addActionListener(new HotelComboBoxActionListener());
        averageRatingReviewsHotel = new JLabel();
        mainPanel.add(hotelsLabel);
        mainPanel.add(hotelsComboBox);
        add(mainPanel);
        add(averageRatingReviewsHotel);
        JScrollPane scrollPane = new JScrollPane(allHotelReviews);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        allHotelReviews.setAutoscrolls(true);
        allHotelReviews.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);
        loadHotels();
    }

    public void displayAverageStarsReviews(double averageStars){
        averageRatingReviewsHotel.setText("Étoiles moyennes : " + averageStars);
    }

    public void loadHotels() throws GetAllHotelsException {
        ArrayList<Hotel> hotels = appControllers.getHotelController().getAllHotels();
        for(Hotel hotel : hotels){
            hotelsComboBox.addItem(hotel);
        }
    }

    private void displayAllReviews(ArrayList<Review> reviews){
        allHotelReviews.removeAll(); // nettoyer d'abord

        for (Review review : reviews){
            JPanel reviewPanel = new JPanel();
            reviewPanel.setLayout(new GridLayout(7, 2));

            JLabel commentLabel = new JLabel("Commentaire");
            JTextField commentTextField = new JTextField(review.getComment());

            JLabel titleLabel = new JLabel("Titre");
            JTextField titleTextField = new JTextField(review.getTitle());

            JLabel starLabel = new JLabel("Étoile");
            JTextField starTextField = new JTextField(String.valueOf(review.getStar()));

            JLabel customerLabel = new JLabel("Client");
            JTextField customerTextField = new JTextField();
            if (!review.getIsAnonymous()){
                customerTextField.setText(review.getCustomer());
            }

            JLabel lastVisitDateLabel = new JLabel("Date de dernière visite du pays de l'hôtel");
            JTextField lastVisitDateTextField = new JTextField(String.valueOf(review.getLastVisitDateHotelCountry()));

            JLabel creationDateLabel = new JLabel("Date de création de l'avis");
            JTextField creationDateTextField = new JTextField(String.valueOf(review.getCreationDate()));

            reviewPanel.add(commentLabel);
            reviewPanel.add(commentTextField);
            reviewPanel.add(titleLabel);
            reviewPanel.add(titleTextField);
            reviewPanel.add(starLabel);
            reviewPanel.add(starTextField);
            reviewPanel.add(customerLabel);
            reviewPanel.add(customerTextField);
            reviewPanel.add(lastVisitDateLabel);
            reviewPanel.add(lastVisitDateTextField);
            reviewPanel.add(creationDateLabel);
            reviewPanel.add(creationDateTextField);
            reviewPanel.add(new JLabel("======================================================================================================"));
            reviewPanel.add(new JLabel("======================================================================================================"));

            commentTextField.setEditable(false);
            titleTextField.setEditable(false);
            starTextField.setEditable(false);
            customerTextField.setEditable(false);
            lastVisitDateTextField.setEditable(false);
            creationDateTextField.setEditable(false);

            allHotelReviews.add(reviewPanel);
        }

        allHotelReviews.revalidate(); // forcer la mise à jour
        allHotelReviews.repaint();
    }

    private void displayReviews() throws GetAllReviewException {
        Hotel hotel = (Hotel) hotelsComboBox.getSelectedItem();
        ArrayList<Review> reviews = appControllers.getReviewController().getAllReviewsByHotel(hotel.getId());
        displayAllReviews(reviews);
    }

    private class HotelComboBoxActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Hotel hotel = (Hotel) hotelsComboBox.getSelectedItem();
                if (hotel != null){
                    double averageRating = appControllers.getHotelController().getAverageStarsReviewsByHotel(hotel.getId());
                    displayAverageStarsReviews(averageRating);
                    displayReviews();
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez choisir un hôtel.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainPanel, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
