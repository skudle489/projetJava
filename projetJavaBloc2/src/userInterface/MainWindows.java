package userInterface;

import exceptions.*;
import utils.AppControllers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindows extends JFrame {
    private final AppControllers appControllers;
    private Container frameContainer;

    private Plane plane;

    public MainWindows() {
        super("Plane");
        this.appControllers = new AppControllers();
        this.frameContainer = getContentPane();

        setupUI();
        setupMenuBar();
        showPlaneTemporarily();
    }

    private void setupUI() {
        setBounds(100, 100, 700, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuCustomer = new JMenu("Client");
        JMenu reservationMenu = new JMenu("Reservation");

        menuBar.add(menuCustomer);
        menuBar.add(reservationMenu);

        addMenuItem(menuCustomer, "Inscription", new AddCustomerActionListener());
        addMenuItem(menuCustomer, "Listing clients", new ListingCustomerActionListener());
        addMenuItem(menuCustomer, "Modifier un client", new UpdateCustomerActionListener());
        addMenuItem(menuCustomer, "Supprimer un client", new DeleteCustomerActionListener());
        addMenuItem(reservationMenu, "Nouvelle Reservation", new NewReservationActionListener());
        addMenuItem(reservationMenu, "Facture réservation", new ReservationInvoiceActionListener());
    }

    private void addMenuItem(JMenu menu, String title, ActionListener listener) {
        JMenuItem item = new JMenuItem(title);
        item.addActionListener(listener);
        menu.add(item);
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

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
