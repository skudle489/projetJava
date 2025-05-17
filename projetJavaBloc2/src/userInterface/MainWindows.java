package userInterface;

import exceptions.*;
import utils.AppControllers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindows extends JFrame {
    private final AppControllers appControllers;
    private final Container frameContainer;

    private Plane plane;

    JMenuBar menuBar;
    JMenu customerMenu, reservationMenu, businessMenu;
    JMenuItem customerRegistrationMenuItem, listingCustomerMenuItem, updateCustomerMenuItem, deleteCustomerMenuItem, newReservationMenuItem, reservationInvoiceMenuItem, averageRatingReviewsHotelMenuItem;

    public MainWindows() {
        super("Plane");
        this.appControllers = new AppControllers();
        this.frameContainer = getContentPane();


        setupUI();
        setupMenuBar();
        showPlaneTemporarily();
        setVisible(true);
    }

    private void setupUI() {
        setBounds(100, 100, 700, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void setupMenuBar() {
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        customerMenu = new JMenu("Client");
        reservationMenu = new JMenu("Reservation");
        businessMenu = new JMenu("Tache métier");

        menuBar.add(customerMenu);
        menuBar.add(reservationMenu);
        menuBar.add(businessMenu);

        customerRegistrationMenuItem = new JMenuItem("Inscription");
        listingCustomerMenuItem = new JMenuItem("Listing clients");
        updateCustomerMenuItem = new JMenuItem("Modifier clients");
        deleteCustomerMenuItem = new JMenuItem("Supprimer client");
        newReservationMenuItem = new JMenuItem("Réserver");
        reservationInvoiceMenuItem = new JMenuItem("Facture réservation");
        averageRatingReviewsHotelMenuItem = new JMenuItem("Moyenne étoiles d'avis d'hotels");

        customerMenu.add(customerRegistrationMenuItem);
        customerMenu.add(listingCustomerMenuItem);
        customerMenu.add(updateCustomerMenuItem);
        customerMenu.add(deleteCustomerMenuItem);
        reservationMenu.add(newReservationMenuItem);
        businessMenu.add(reservationInvoiceMenuItem);
        businessMenu.add(averageRatingReviewsHotelMenuItem);

        customerRegistrationMenuItem.addActionListener(new AddCustomerActionListener());
        listingCustomerMenuItem.addActionListener(new ListingCustomerActionListener());
        updateCustomerMenuItem.addActionListener(new UpdateCustomerActionListener());
        deleteCustomerMenuItem.addActionListener(new DeleteCustomerActionListener());
        newReservationMenuItem.addActionListener(new NewReservationActionListener());
        reservationInvoiceMenuItem.addActionListener(new ReservationInvoiceActionListener());
        averageRatingReviewsHotelMenuItem.addActionListener(new AverageRatingReviewsHotelActionListener());
    }

    public void onRegistrationValidated() {
        frameContainer.removeAll();
        frameContainer.revalidate();
        frameContainer.repaint();
    }

    private void showPlaneTemporarily() {
        this.plane = new Plane(100, 600, 200, 100);
        frameContainer.add(plane);
        Timer timer = new Timer(6000, e -> {
            frameContainer.remove(plane);
            frameContainer.repaint();
            plane = null;
        });
        timer.setRepeats(false);
        timer.start();
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
                RegistrationForm form = new RegistrationForm(appControllers, null);
                form.setMainWindows(MainWindows.this);
                showPanel(form);
            } catch (Exception ex) {
                showError(ex);
            }
        }
    }

    private class ListingCustomerActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                AllCustomersModel model = new AllCustomersModel(appControllers.getCustomerController().getAllCustomers());
                JTable table = new JTable(model);
                JScrollPane scrollPane = new JScrollPane(table);
                frameContainer.removeAll();
                frameContainer.add(scrollPane, BorderLayout.CENTER);
                frameContainer.revalidate();
                frameContainer.repaint();
            } catch (Exception ex) {
                showError(ex);
            }
        }
    }

    private class UpdateCustomerActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            UpdateCustomerPanel panel = new UpdateCustomerPanel(appControllers);
            panel.setMainWindow(MainWindows.this);
            showPanel(panel);
        }
    }

    private class DeleteCustomerActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            DeleteCustomerPanel panel = new DeleteCustomerPanel(appControllers);
            panel.setDeleteCustomerListener(MainWindows.this);
            showPanel(panel);
        }
    }

    private class NewReservationActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ReservationPanel panel = new ReservationPanel(appControllers);
            panel.setMainWindows(MainWindows.this);
            showPanel(panel);
        }
    }

    private class ReservationInvoiceActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                ReservationInvoicePanel panel = new ReservationInvoicePanel(appControllers);
                showPanel(panel);
            } catch (GetAllCustomersException ex) {
                showError(ex);
            }
        }
    }

    public class AverageRatingReviewsHotelActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                HotelReviewsPanel hotelReviewsPanel = new HotelReviewsPanel(appControllers);
                showPanel(hotelReviewsPanel);
            } catch (GetAllHotelsException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
