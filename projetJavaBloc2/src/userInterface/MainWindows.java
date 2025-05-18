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

    private JMenuBar menuBar;
    private JMenu customerMenu, reservationMenu, businessMenu, searchMenu, reviewMenu;
    private JMenuItem customerRegistrationMenuItem, listingCustomerMenuItem, updateCustomerMenuItem, deleteCustomerMenuItem, newReservationMenuItem, reservationInvoiceMenuItem, averageRatingReviewsHotelMenuItem, searchReviewsMenuItem, updateReviewsMenuItem, addReviewsMenuItem, deleteReviewsMenuItem, listingReviewsMenuItem, customerReservationsDisplayMenuItem, searchBedroomMenuItem;

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
        searchMenu = new JMenu("Recherches");
        reviewMenu = new JMenu("Avis");

        menuBar.add(customerMenu);
        menuBar.add(reviewMenu);
        menuBar.add(reservationMenu);
        menuBar.add(businessMenu);
        menuBar.add(searchMenu);

        customerRegistrationMenuItem = new JMenuItem("Inscription");
        listingCustomerMenuItem = new JMenuItem("Listing clients");
        updateCustomerMenuItem = new JMenuItem("Modifier clients");
        deleteCustomerMenuItem = new JMenuItem("Supprimer client");
        newReservationMenuItem = new JMenuItem("Réserver");
        reservationInvoiceMenuItem = new JMenuItem("Facture réservation");
        averageRatingReviewsHotelMenuItem = new JMenuItem("Moyenne étoiles d'avis d'hotels");
        searchReviewsMenuItem = new JMenuItem("Informations sur les avis non anonymes");
        updateReviewsMenuItem = new JMenuItem("Modifier un avis");
        addReviewsMenuItem = new JMenuItem("Ajouter un avis");
        deleteReviewsMenuItem = new JMenuItem("Supprimer un avis");
        listingReviewsMenuItem = new JMenuItem("Listing sur les avis");
        customerReservationsDisplayMenuItem = new JMenuItem("Rechercher les réservations clients");
        searchBedroomMenuItem = new JMenuItem("Rechercher les chambres par type");

        customerMenu.add(customerRegistrationMenuItem);
        customerMenu.add(listingCustomerMenuItem);
        customerMenu.add(updateCustomerMenuItem);
        customerMenu.add(deleteCustomerMenuItem);
        reservationMenu.add(newReservationMenuItem);
        businessMenu.add(reservationInvoiceMenuItem);
        businessMenu.add(averageRatingReviewsHotelMenuItem);
        searchMenu.add(searchReviewsMenuItem);
        searchMenu.add(customerReservationsDisplayMenuItem);
        searchMenu.add(searchBedroomMenuItem);
        reviewMenu.add(updateReviewsMenuItem);
        reviewMenu.add(addReviewsMenuItem);
        reviewMenu.add(deleteReviewsMenuItem);
        reviewMenu.add(listingReviewsMenuItem);

        customerRegistrationMenuItem.addActionListener(new AddCustomerActionListener());
        listingCustomerMenuItem.addActionListener(new ListingCustomerActionListener());
        updateCustomerMenuItem.addActionListener(new UpdateCustomerActionListener());
        deleteCustomerMenuItem.addActionListener(new DeleteCustomerActionListener());
        newReservationMenuItem.addActionListener(new NewReservationActionListener());
        reservationInvoiceMenuItem.addActionListener(new ReservationInvoiceActionListener());
        averageRatingReviewsHotelMenuItem.addActionListener(new AverageRatingReviewsHotelActionListener());
        searchReviewsMenuItem.addActionListener(new SearchReviewsActionListener());
        customerReservationsDisplayMenuItem.addActionListener(new CustomerReservationsDisplayActionListener());
        updateReviewsMenuItem.addActionListener(new UpdateReviewActionListener());
        addReviewsMenuItem.addActionListener(new AddReviewActionListener());
        deleteReviewsMenuItem.addActionListener(new DeleteReviewActionListener());
        listingReviewsMenuItem.addActionListener(new ListingReviewActionListener());
        searchBedroomMenuItem.addActionListener(new SearchBedroomActionListener());
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
                showError(ex);
            }
        }
    }

    public class SearchReviewsActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                SearchReviewsPanel searchReviewsPanel = new SearchReviewsPanel(appControllers);
                searchReviewsPanel.setMainWindows(MainWindows.this);
                showPanel(searchReviewsPanel);
            } catch (Exception ex) {
                showError(ex);
            }
        }
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
    }


    private class UpdateReviewActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                UpdateReviewPanel updateReviewPanel = new UpdateReviewPanel(appControllers);
                updateReviewPanel.setMainWindows(MainWindows.this);
                showPanel(updateReviewPanel);
            } catch (Exception exception) {
                showError(exception);
            }

        }
    }

    private class AddReviewActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                ReviewForm reviewForm = new ReviewForm(appControllers, null, null, null);
                reviewForm.setMainWindows(MainWindows.this);
                showPanel(reviewForm);
            } catch (Exception exception) {
                showError(exception);
            }
        }
    }

    private class DeleteReviewActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                DeleteReview deleteReview = new DeleteReview(appControllers);
                deleteReview.setMainWindows(MainWindows.this);
                showPanel(deleteReview);
            } catch (Exception exception) {
                showError(exception);
            }
        }
    }

    private class ListingReviewActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                AllReviewsModel allReviewsModel = new AllReviewsModel(appControllers.getReviewController().getAllReviews());
                JTable table = new JTable(allReviewsModel);
                JScrollPane scrollPane = new JScrollPane(table);
                frameContainer.removeAll();
                frameContainer.add(scrollPane);
                frameContainer.revalidate();
                frameContainer.repaint();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(MainWindows.this, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }

        }
    }


    private class CustomerReservationsDisplayActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                CustomerReservationsDisplay customerReservationsDisplay = new CustomerReservationsDisplay(appControllers);
                customerReservationsDisplay.setMainWindows(MainWindows.this);
                showPanel(customerReservationsDisplay);
            } catch (Exception exception) {
                showError(exception);
            }
        }
    }

    private class SearchBedroomActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                SearchBedroomPanel searchBedroomPanel  = new SearchBedroomPanel(appControllers);
                showPanel(searchBedroomPanel);
            } catch (Exception exception){
                showError(exception);
            }
        }
    }

}
