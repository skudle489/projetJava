package userInterface;

import exceptions.GetAllAmenitiesFromBedroomException;
import exceptions.GetAllCustomersException;
import exceptions.HotelException;
import exceptions.ReservationException;
import model.Amenity;
import model.BedroomOwnsBed;
import model.Customer;
import model.Reservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ReservationInvoicePanel extends JPanel {
    private JLabel mailAddressJLabel, reservationJLabel;
    private JComboBox mailAddressComboBox, reservationComboBox;
    private MainWindows mainWindows;
    private JPanel mainPanel;
    private JButton reservationInvoiceButton;

    public ReservationInvoicePanel() {
        setUpUI();
    }

    public void setUpUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 2));
        mailAddressJLabel = new JLabel("Addresse mail");
        mailAddressComboBox = new JComboBox();
        reservationInvoiceButton = new JButton("Afficher la facture de la réservation");
        reservationInvoiceButton.addActionListener(new ReservationActionListener());
        mailAddressComboBox.addActionListener(new MailAddressActionListener());

        reservationJLabel = new JLabel("Reservation");
        reservationComboBox = new JComboBox();


        mainPanel.add(mailAddressJLabel);
        mainPanel.add(mailAddressComboBox);
        mainPanel.add(reservationJLabel);
        mainPanel.add(reservationComboBox);

        add(mainPanel);
        add(reservationInvoiceButton);
    }

    public void setMainWindows(MainWindows mainWindows) throws GetAllCustomersException, ReservationException, HotelException {
        this.mainWindows = mainWindows;
        loadCustomersJComboBox();
    }

    public void loadCustomersJComboBox() throws GetAllCustomersException, ReservationException, HotelException {
        ArrayList<Customer> customers = mainWindows.getCustomerController().getAllCustomers();
        for (Customer customer : customers) {
            mailAddressComboBox.addItem(customer);
        }
    }



    private void loadCustomerReservationJComboBox(String mailAddress) throws ReservationException, HotelException {
        reservationComboBox.removeAllItems();
        ArrayList<Reservation> reservations = mainWindows.getReservationController().getAllReservationsCustomer(mailAddress);
        for (Reservation reservation : reservations) {
            reservationComboBox.addItem(reservation);
        }
    }





    private class MailAddressActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Customer customer = (Customer) mailAddressComboBox.getSelectedItem();
            try {
                loadCustomerReservationJComboBox(customer.getMailAdress());
            } catch (ReservationException | HotelException exception) {
                JOptionPane.showMessageDialog(mainPanel, exception.getMessage());
            }
        }
    }

    private class ReservationActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Reservation reservation = (Reservation) reservationComboBox.getSelectedItem();
                String description = mainWindows.getReservationController().getReservationInvoiceCustomer(reservation);

                JTextArea textArea = new JTextArea(description.toString());

                textArea.setEditable(false);

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(400, 300));

                JOptionPane.showMessageDialog(null, scrollPane, "Description complète de la chambre", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception exception) {
                JOptionPane.showMessageDialog(mainPanel, exception.getMessage());
            }
        }
    }
}
