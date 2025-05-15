package userInterface;

import exceptions.BedroomCreationException;
import exceptions.GetAllCustomersException;
import exceptions.GetAllHotelsException;
import model.Bedroom;
import model.Customer;
import model.Hotel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class ReservationPanel extends JPanel {
    private JPanel mainPanel;

    private JLabel mailAddressLabel, hotel, bedroom;
    private JComboBox mailAddressComboBox, hotelComboBox, bedroomComboBox;
    private MainWindows mainWindows;
    private JButton showDetailsButton;

    public ReservationPanel() {
        setUp();
    }

    public void setUp(){
        mainPanel = new JPanel();
        mailAddressLabel = new JLabel("Addresse mail");
        hotel = new JLabel("Hotel");
        bedroom = new JLabel("Bedroom");
        mailAddressComboBox = new JComboBox();
        hotelComboBox = new JComboBox();
        bedroomComboBox = new JComboBox();
        showDetailsButton = new JButton("Afficher informations");
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(mailAddressLabel);
        mainPanel.add(mailAddressComboBox);
        mainPanel.add(hotel);
        mainPanel.add(hotelComboBox);
        mainPanel.add(bedroom);
        mainPanel.add(bedroomComboBox);
        mainPanel.add(showDetailsButton);

        hotelComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateBedroomsForSelectedHotel();
                }
            }
        });

        showDetailsButton.addActionListener(e -> {
            Bedroom selectedBedroom = (Bedroom) bedroomComboBox.getSelectedItem();
            if (selectedBedroom != null) {
                String description = selectedBedroom.getFullDescription();

                JTextArea textArea = new JTextArea(description);

                textArea.setEditable(false); // Texte non modifiable

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(400, 300));

                JOptionPane.showMessageDialog(null, scrollPane, "Description complète de la chambre", JOptionPane.INFORMATION_MESSAGE);

            }
        });

        add(mainPanel);
    }

    public void loadDataInComboBox(){
        try {
            mailAddressComboBox.removeAllItems();
            hotelComboBox.removeAllItems();
            bedroomComboBox.removeAllItems();

            ArrayList<Customer> customers = mainWindows.getCustomerController().getAllCustomers();
            for (Customer customer : customers) {
                mailAddressComboBox.addItem(customer);
            }

            ArrayList<Hotel> hotels = mainWindows.getHotelController().getAllHotels();
            for (Hotel hotel : hotels) {
                hotelComboBox.addItem(hotel);
            }

            // Appelle la mise à jour des chambres après avoir chargé les hôtels
            updateBedroomsForSelectedHotel();

        } catch (GetAllCustomersException | GetAllHotelsException exception){
            JOptionPane.showMessageDialog(mainPanel, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBedroomsForSelectedHotel() {
        bedroomComboBox.removeAllItems();
        Hotel selectedHotel = (Hotel) hotelComboBox.getSelectedItem();
        if (selectedHotel != null) {
            try {
                ArrayList<Bedroom> bedrooms = mainWindows.getBedroomController().getBedroomsFromHotel(selectedHotel.getId());
                for (Bedroom bedroom : bedrooms) {
                    bedroomComboBox.addItem(bedroom);
                }
            } catch (BedroomCreationException e) {
                JOptionPane.showMessageDialog(mainPanel, e.getMessage(), "Erreur de chargement des chambres", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void reservationlistener(MainWindows mainWindows){
        this.mainWindows = mainWindows;
        loadDataInComboBox();
    }
}
