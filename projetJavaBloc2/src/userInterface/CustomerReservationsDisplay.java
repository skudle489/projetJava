package userInterface;
import exceptions.GetAllCustomersException;
import exceptions.ReservationException;
import model.Customer;
import model.ReservationInvoiceModel;
import utils.AppControllers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomerReservationsDisplay extends JPanel {
    private JPanel mainPanel;
    private JLabel mailAddressLabel;
    private JList<String> mailAddressJList;
    private MainWindows mainWindows;
    private AppControllers appControllers;
    private JButton validationButton;

    private JTable reservationsTable;
    private DefaultTableModel tableModel;


    public CustomerReservationsDisplay(AppControllers appControllers) throws GetAllCustomersException {
        this.appControllers = appControllers;
        setUpUI();
    }

    public void setMainWindows(MainWindows mainWindows) {
        this.mainWindows = mainWindows;
    }

    public void setUpUI() throws GetAllCustomersException {
        setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 3));

        mailAddressLabel = new JLabel("Addresse mail");
        mailAddressJList = new JList<>();
        validationButton = new JButton("Valider");
        validationButton.addActionListener(new ValidationListener());
        loadCustomersInJList();

        mainPanel.add(mailAddressLabel);
        mainPanel.add(new JScrollPane(mailAddressJList));
        mainPanel.add(validationButton);

        String[] columns = {"Nom hotel", "Date début réservation", "Date de fin de réservation", "Nombre de personnes", "Taille de la chambre (m2)", "Prix par jour"};
        tableModel = new DefaultTableModel(columns, 0);
        reservationsTable = new JTable(tableModel);

        reservationsTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        reservationsTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        reservationsTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        reservationsTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        reservationsTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        reservationsTable.getColumnModel().getColumn(5).setPreferredWidth(100);

        JScrollPane tableScrollPane = new JScrollPane(reservationsTable);

        add(mainPanel, BorderLayout.CENTER);
        add(tableScrollPane, BorderLayout.SOUTH);

    }


    public void loadCustomersInJList() throws GetAllCustomersException {
        ArrayList<Customer> customers = appControllers.getCustomerController().getAllCustomers();
        DefaultListModel<String> model = new DefaultListModel<>();

        for (Customer customer : customers) {
            model.addElement(customer.getMailAdress());
        }

        mailAddressJList.setModel(model);
    }


    private void loadReservationsForCustomer(String email) throws ReservationException {
        tableModel.setRowCount(0);

        ArrayList<ReservationInvoiceModel> reservationInvoiceModels = appControllers.getReservationController().reservationInvoice(email);

        for (ReservationInvoiceModel reservationInvoiceModel : reservationInvoiceModels) {
            Object[] row = {reservationInvoiceModel.getHotelName(), reservationInvoiceModel.getStartDateReservation(), reservationInvoiceModel.getEndDateReservation(), reservationInvoiceModel.getNbOfPeopleInBedroom() , reservationInvoiceModel.getBedroomSize(), reservationInvoiceModel.getCostPerDay()};
            tableModel.addRow(row);
        }
    }


    private class ValidationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (mailAddressJList.getSelectedValue() != null) {
                    loadReservationsForCustomer(mailAddressJList.getSelectedValue());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainPanel, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}


