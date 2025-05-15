package userInterface;

import controller.LocalityController;
import dataAccess.CountryDBDAO;
import dataAccess.CustomerDBDAO;
import exceptions.*;
import model.Country;
import model.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.CustomerController;
import controller.CountryController;

public class MainWindows extends JFrame implements IRegistrationListener {
    private CustomerController customerController;

    private Plane plane;
    private Container container;


    private JMenuBar menuBar;
    private JMenu menuCustomer, reservationMenu;
    private JMenuItem customerRegistration, listingCustomers, updateCustomer, deleteCustomer, newReservationMenuItem;
    private Container frameContainer;

    private RegistrationForm registrationForm;
    private AllCustomersModel allCustomersModel;

    private AddCustomerActionListener addCustomerActionListener;
    private ListingCustomerActionListener listingCustomerActionListener;

    private UpdateCustomerPanel updateCustomerPanel;

    private DeleteCustomerPanel deleteCustomerPanel;
    private ReservationPanel reservationPanel;


    private CountryController countryController;
    private LocalityController localityController;



    public MainWindows() {
        super("Plane");

        customerController = new CustomerController();
        countryController = new CountryController();
        localityController = new LocalityController();


        setBounds(100, 100, 700, 300);
        container = getContentPane();

        plane = new Plane(100, 600, 200, 100);
        container.add(plane);


        Timer timer = new Timer(6000, e -> {
            container.remove(plane);
            plane = null;
            container.repaint();
        });
        timer.setRepeats(false);
        timer.start();

        frameContainer = getContentPane();

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        menuCustomer = new JMenu("Client");
        menuBar.add(menuCustomer);

        reservationMenu = new JMenu("Reservation");
        menuBar.add(reservationMenu);
        newReservationMenuItem = new JMenuItem("Nouvelle Reservation");
        reservationMenu.add(newReservationMenuItem);
        newReservationMenuItem.addActionListener(new NewReservationActionListener());


        customerRegistration = new JMenuItem("Inscription");
        menuCustomer.add(customerRegistration);
        addCustomerActionListener = new AddCustomerActionListener();
        customerRegistration.addActionListener(addCustomerActionListener);

        listingCustomers = new JMenuItem("Listing clients");
        menuCustomer.add(listingCustomers);
        listingCustomerActionListener = new ListingCustomerActionListener();
        listingCustomers.addActionListener(listingCustomerActionListener);


        updateCustomer = new JMenuItem("Modifier un client");
        menuCustomer.add(updateCustomer);
        UpdateCustomerActionListener updateCustomerActionListener = new UpdateCustomerActionListener();
        updateCustomer.addActionListener(updateCustomerActionListener);


        deleteCustomer = new JMenuItem("Supprimer un client");
        menuCustomer.add(deleteCustomer);
        DeleteCustomerActionListener deleteCustomerActionListener = new DeleteCustomerActionListener();
        deleteCustomer.addActionListener(deleteCustomerActionListener);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public void onRegistrationValidated() {
        frameContainer.removeAll();
        frameContainer.revalidate();
        frameContainer.repaint();
    }

    public void addCustomer(Customer customer) throws AddCustomerException {
        customerController.addCustomer(customer);
    }

    public void updateCustomer(Customer customer) throws UpdateCustomerException {
        customerController.updateCustomer(customer);
    }

    public LocalityController getLocalityController() {
        return localityController;
    }

    public Container getFrameContainer() {
        return frameContainer;
    }

    public Customer getCustomer(String mailAddress) throws GetCustomerException {
        return customerController.getCustomer(mailAddress);
    }


    public boolean customerExists(String mailAddress) {
        try {
            Customer customer = getCustomer(mailAddress);
            return customer != null;
        } catch (GetCustomerException e) {
            return false;
        }
    }

    public CustomerController getCustomerController() {
        return customerController;
    }

    public CountryController getCountryController() {
        return countryController;
    }

    public void showPanel(JPanel panel) {
        frameContainer.removeAll();
        frameContainer.add(panel, BorderLayout.CENTER);
        frameContainer.revalidate();
        frameContainer.repaint();
    }



    private class AddCustomerActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                frameContainer.removeAll();
                registrationForm = new RegistrationForm(countryController.getAllCountries(), null);
                registrationForm.setRegistrationListener(MainWindows.this);
                frameContainer.add(registrationForm, BorderLayout.CENTER);
                frameContainer.revalidate();
                frameContainer.repaint();
            } catch (GetAllCountryException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    public class ListingCustomerActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                frameContainer.removeAll();
                allCustomersModel = new AllCustomersModel(customerController.getAllCustomers());

                JTable table = new JTable(allCustomersModel);
                JScrollPane scrollPane = new JScrollPane(table);

                frameContainer.add(scrollPane, BorderLayout.CENTER);
                frameContainer.revalidate();
                frameContainer.repaint();
            } catch (Exception exception){
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        }
    }

    public class UpdateCustomerActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            frameContainer.removeAll();
            updateCustomerPanel = new UpdateCustomerPanel();
            updateCustomerPanel.setUpdateCustomerListener(MainWindows.this);
            frameContainer.add(updateCustomerPanel, BorderLayout.CENTER);
            frameContainer.revalidate();
            frameContainer.repaint();
        }
    }

    private class DeleteCustomerActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            frameContainer.removeAll();
            deleteCustomerPanel = new DeleteCustomerPanel();
            deleteCustomerPanel.setDeleteCustomerListener(MainWindows.this);
            frameContainer.add(deleteCustomerPanel, BorderLayout.CENTER);
            frameContainer.revalidate();
            frameContainer.repaint();

        }
    }

    public class NewReservationActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            frameContainer.removeAll();
            reservationPanel = new ReservationPanel();
            frameContainer.add(reservationPanel, BorderLayout.CENTER);
            reservationPanel.reservationlistener(MainWindows.this);
            frameContainer.revalidate();
            frameContainer.repaint();
        }
    }

}
