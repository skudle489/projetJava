package userInterface;

import controller.*;
import dataAccess.CountryDBDAO;
import dataAccess.CustomerDBDAO;
import exceptions.*;
import model.Country;
import model.Customer;
import utils.AppControllers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainWindows extends JFrame {
    private final AppControllers appControllers;


    //private CustomerController customerController;

    private Plane plane;


    private JMenuBar menuBar;
    private JMenu menuCustomer, reservationMenu;
    private JMenuItem customerRegistration, listingCustomers, updateCustomer, deleteCustomer, newReservationMenuItem, reservationInvoiceMenuItem;
    private Container frameContainer;

    private RegistrationForm registrationForm;
    private AllCustomersModel allCustomersModel;

    private AddCustomerActionListener addCustomerActionListener;
    private ListingCustomerActionListener listingCustomerActionListener;

    private UpdateCustomerPanel updateCustomerPanel;

    private DeleteCustomerPanel deleteCustomerPanel;
    private ReservationPanel reservationPanel;
    private ReservationInvoicePanel reservationInvoicePanel;


    /*private CountryController countryController;
    private LocalityController localityController;
    private HotelController hotelController;
    private BedroomController bedroomController;
    private BedroomOwnsAmenityController bedroomOwnsAmenityController;
    private BedroomOwnsBedController bedroomOwnsBedController;
    private ReservationController reservationController;*/


    public MainWindows() {
        super("Plane");
        appControllers = new AppControllers();

        /*customerController = new CustomerController();
        countryController = new CountryController();
        localityController = new LocalityController();
        hotelController = new HotelController();
        bedroomController = new BedroomController();
        bedroomOwnsAmenityController = new BedroomOwnsAmenityController();
        bedroomOwnsBedController = new BedroomOwnsBedController();
        reservationController = new ReservationController();*/


        setBounds(100, 100, 700, 300);
        frameContainer = getContentPane();

        plane = new Plane(100, 600, 200, 100);
        frameContainer.add(plane);


        Timer timer = new Timer(6000, e -> {
            frameContainer.remove(plane);
            plane = null;
            frameContainer.repaint();
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

        reservationInvoiceMenuItem = new JMenuItem("Facture réservation");
        reservationInvoiceMenuItem.addActionListener(new ReservationInvoiceActionListener());
        reservationMenu.add(reservationInvoiceMenuItem);


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

    /*public void addCustomer(Customer customer) throws AddCustomerException {
        customerController.addCustomer(customer);
    }

    public void updateCustomer(Customer customer) throws UpdateCustomerException {
        customerController.updateCustomer(customer);
    }

    public LocalityController getLocalityController() {
        return localityController;
    }*/

    public Container getFrameContainer() {
        return frameContainer;
    }

    /*public Customer getCustomer(String mailAddress) throws GetCustomerException {
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

    public HotelController getHotelController(){
        return hotelController;
    }

    public BedroomController getBedroomController(){
        return bedroomController;
    }

    public CustomerController getCustomerController() {
        return customerController;
    }

    public CountryController getCountryController() {
        return countryController;
    }

    public ReservationController getReservationController() {return reservationController;}

    public BedroomOwnsAmenityController getBedroomOwnsAmenityController() {
        return bedroomOwnsAmenityController;
    }

    public BedroomOwnsBedController getBedroomOwnsBedController() {
        return bedroomOwnsBedController;
    }*/

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
                registrationForm = new RegistrationForm(appControllers, null);
                registrationForm.setMainWindows(MainWindows.this);
                frameContainer.add(registrationForm, BorderLayout.CENTER);
                frameContainer.revalidate();
                frameContainer.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    public class ListingCustomerActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                frameContainer.removeAll();
                allCustomersModel = new AllCustomersModel(appControllers.getCustomerController().getAllCustomers());

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
            updateCustomerPanel = new UpdateCustomerPanel(appControllers);
            updateCustomerPanel.setMainWindow(MainWindows.this);
            frameContainer.add(updateCustomerPanel, BorderLayout.CENTER);
            frameContainer.revalidate();
            frameContainer.repaint();
        }
    }

    private class DeleteCustomerActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            frameContainer.removeAll();
            deleteCustomerPanel = new DeleteCustomerPanel(appControllers);
            deleteCustomerPanel.setDeleteCustomerListener(MainWindows.this);
            frameContainer.add(deleteCustomerPanel, BorderLayout.CENTER);
            frameContainer.revalidate();
            frameContainer.repaint();

        }
    }

    public class NewReservationActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            frameContainer.removeAll();
            reservationPanel = new ReservationPanel(appControllers);
            frameContainer.add(reservationPanel, BorderLayout.CENTER);
            reservationPanel.setMainWindows(MainWindows.this);
            frameContainer.revalidate();
            frameContainer.repaint();
        }
    }

    private class ReservationInvoiceActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            frameContainer.removeAll();
            try {
                reservationInvoicePanel = new ReservationInvoicePanel(appControllers);
            } catch (GetAllCustomersException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            frameContainer.add(reservationInvoicePanel, BorderLayout.CENTER);
            frameContainer.revalidate();
            frameContainer.repaint();
        }
    }
}
