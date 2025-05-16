package userInterface;

import exceptions.*;
import model.*;
import utils.IntegerUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationPanel extends JPanel {
    private JPanel mainPanel, startReservationDatePanel, showDetailsAndSearchDatesButtonPanel, endReservationDatePanel;

    private JLabel mailAddressLabel, hotel, bedroom, availableDatesLabel;
    private JComboBox mailAddressComboBox, hotelComboBox, bedroomComboBox;
    private MainWindows mainWindows;
    private JLabel startDateLabel, endDateLabel;
    private JComboBox startReservationDateComboBox;
    private JComboBox endReservationDateComboBox;
    private JButton showDetailsButton, searchAvailableDatesButton, confirmReservationButton;

    public ReservationPanel() {
        initializeComponents();
    }

    public void initializeComponents(){
        mainPanel = new JPanel();

        mailAddressLabel = new JLabel("Addresse mail");
        hotel = new JLabel("Hotel");
        bedroom = new JLabel("Chambre");
        mailAddressComboBox = new JComboBox();
        hotelComboBox = new JComboBox();
        bedroomComboBox = new JComboBox();

        showDetailsAndSearchDatesButtonPanel = new JPanel();
        showDetailsAndSearchDatesButtonPanel.setLayout(new GridLayout(1, 2));
        showDetailsButton = new JButton("Afficher informations");
        searchAvailableDatesButton = new JButton("Chercher les dates libres");
        showDetailsAndSearchDatesButtonPanel.add(showDetailsButton);
        showDetailsAndSearchDatesButtonPanel.add(searchAvailableDatesButton);



        startReservationDatePanel = new JPanel();

        startDateLabel = new JLabel("Date de début de la réservation");

        endDateLabel = new JLabel("Réserve jusqu'au ");


        LocalDate today = LocalDate.now();
        LocalDate threeMonthsLater = today.plusMonths(3);

        startReservationDateComboBox = new JComboBox();

        for (LocalDate date = today; date.isBefore(threeMonthsLater); date = date.plusDays(1)) {
            startReservationDateComboBox.addItem(date);
        }




        startReservationDatePanel.setLayout(new GridLayout(1, 2));
        startReservationDatePanel.add(startDateLabel);
        startReservationDatePanel.add(startReservationDateComboBox);


        endReservationDatePanel = new JPanel(new GridLayout(1, 2));
        endReservationDateComboBox = new JComboBox();
        endReservationDatePanel.add(endDateLabel);
        endReservationDatePanel.add(endReservationDateComboBox);


        availableDatesLabel = new JLabel("Dates libres");

        confirmReservationButton = new JButton("Réserver");



        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(mailAddressLabel);
        mainPanel.add(mailAddressComboBox);
        mainPanel.add(hotel);
        mainPanel.add(hotelComboBox);
        mainPanel.add(bedroom);
        mainPanel.add(bedroomComboBox);
        mainPanel.add(showDetailsAndSearchDatesButtonPanel);
        mainPanel.add(startReservationDatePanel);
        mainPanel.add(availableDatesLabel);
        mainPanel.add(endReservationDatePanel);
        mainPanel.add(confirmReservationButton);

        hotelComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateBedroomsForSelectedHotel();
                }
            }
        });

        showDetailsButton.addActionListener(new ShowDetailsButtonActionListener());
        confirmReservationButton.addActionListener(new ConfirmReservationButtonActionListener());

        searchAvailableDatesButton.addActionListener(new SearchAvaialbleDatesButtonListener());

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

    public void setMainWindows(MainWindows mainWindows){
        this.mainWindows = mainWindows;
        loadDataInComboBox();
    }

    public void loadReservationDates() throws IsRoomReservedException {
        endReservationDateComboBox.removeAllItems();
        LocalDate selectedDate = (LocalDate) startReservationDateComboBox.getSelectedItem();
        Bedroom selectedBedroom = (Bedroom) bedroomComboBox.getSelectedItem();
        Hotel selectedHotel = (Hotel) hotelComboBox.getSelectedItem();
        ArrayList<LocalDate> availableDates = mainWindows.getReservationController().getAvailableDatesFrom(selectedBedroom.getBedroomNumber(), selectedHotel.getId(), selectedDate);

        for (LocalDate availableDate : availableDates) {
            endReservationDateComboBox.addItem(availableDate);
        }
    }


    private class SearchAvaialbleDatesButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                loadReservationDates();

            } catch (IsRoomReservedException exception){
                JOptionPane.showMessageDialog(mainPanel, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class ShowDetailsButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Bedroom selectedBedroom = (Bedroom) bedroomComboBox.getSelectedItem();
            if (selectedBedroom != null) {
                StringBuilder description = new StringBuilder(selectedBedroom.getFullDescription());

                try {
                    ArrayList<Amenity> amenities = mainWindows.getBedroomOwnsAmenityController().getAllAmenitiesFromBedroom(selectedBedroom.getBedroomNumber(), selectedBedroom.getHotel());
                    description.append("Accomodités : ");

                    if (amenities != null) {
                        for (Amenity amenity : amenities) {
                            description.append(amenity.getLabel()).append(", ");
                        }

                        description.setLength(description.length() - 2);

                    }

                    description.append("\n");

                    ArrayList<BedroomOwnsBed> bedroomOwnsBeds = mainWindows.getBedroomOwnsBedController().getAllBedsFromBedroom(selectedBedroom.getBedroomNumber(), selectedBedroom.getHotel());

                    description.append("Type de lit(s) : ");

                    if (bedroomOwnsBeds != null) {
                        for (BedroomOwnsBed bed : bedroomOwnsBeds) {
                            description.append(bed.getBed()).append(", ");
                        }

                        description.setLength(description.length() - 2);
                    }

                    description.append("\n");



                    JTextArea textArea = new JTextArea(description.toString());

                    textArea.setEditable(false);

                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(400, 300));

                    JOptionPane.showMessageDialog(null, scrollPane, "Description complète de la chambre", JOptionPane.INFORMATION_MESSAGE);

                } catch (GetAllAmenitiesFromBedroomException | GetAllBedsFromBedroomException exception){
                    JOptionPane.showMessageDialog(mainPanel, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class ConfirmReservationButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                LocalDate startDate = (LocalDate) startReservationDateComboBox.getSelectedItem();
                LocalDate endDate = (LocalDate) endReservationDateComboBox.getSelectedItem();
                Customer customer = (Customer) mailAddressComboBox.getSelectedItem();
                String mailAddress = customer.getMailAdress();
                Bedroom bedroom = (Bedroom) bedroomComboBox.getSelectedItem();
                int bedroomNumber = bedroom.getBedroomNumber();
                Hotel hotel = (Hotel) hotelComboBox.getSelectedItem();
                int hotelId = hotel.getId();
                mainWindows.getReservationController().addReservation(new Reservation(startDate, endDate, mailAddress, bedroomNumber, hotelId));
                JOptionPane.showMessageDialog(null, "Réservation réussie !");
                loadReservationDates();
                mainWindows.revalidate();
                mainWindows.repaint();


            } catch (ReservationException | ReservationCreationException | CustomerCreationException exception){
                JOptionPane.showMessageDialog(mainPanel, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (IsRoomReservedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


}
