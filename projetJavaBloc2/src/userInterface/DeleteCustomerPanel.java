package userInterface;

import exceptions.CustomerCreationException;
import exceptions.DeleteCustomerException;
import exceptions.GetAllCustomersException;
import model.Customer;
import utils.AppControllers;
import utils.CustomerFormValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeleteCustomerPanel extends JPanel {
    private JPanel mailAddressPanel;

    private JLabel mailAddressLabel;
    private JComboBox customersComboBox;

    private JButton searchCustomerButton;

    MainWindows updateCustomerListener;
    MainWindows deleteCustomerListener;
    private AppControllers appControllers;

    public DeleteCustomerPanel(AppControllers appControllers) {
        this.appControllers = appControllers;
        setUpUI();
    }

    public void setUpUI(){
        searchCustomerButton = new JButton("Supprimer client");
        searchCustomerButton.addActionListener(new DeleteCustomerListener());
        mailAddressLabel = new JLabel("Adresse mail");
        customersComboBox = new JComboBox();
        mailAddressPanel = new JPanel();
        mailAddressPanel.setLayout(new GridLayout(1, 3, 2, 2));
        mailAddressPanel.add(mailAddressLabel);
        mailAddressPanel.add(customersComboBox);
        mailAddressPanel.add(searchCustomerButton);

        add(mailAddressPanel, BorderLayout.CENTER);
    }

    public void setDeleteCustomerListener(MainWindows deleteCustomerListener) {
        this.deleteCustomerListener = deleteCustomerListener;
        loadAllCustomers();
    }

    public void loadAllCustomers() {
        try {
            ArrayList<Customer> customers = appControllers.getCustomerController().getAllCustomers();
            customersComboBox.removeAllItems();
            for (Customer customer : customers) {
                customersComboBox.addItem(customer);
            }
        } catch (GetAllCustomersException exception){
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    private class DeleteCustomerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Customer selectedCustomer = (Customer) customersComboBox.getSelectedItem();
                if (selectedCustomer != null) {

                    int confirmation = JOptionPane.showConfirmDialog(
                            null,
                            "Voulez-vous vraiment supprimer le client " + selectedCustomer.getFirstName() + " " + selectedCustomer.getLastName() + " ? \nATTENTION: Les avis et les réservations du client seront également supprimés",
                            "Confirmation de suppression",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirmation == JOptionPane.YES_OPTION) {
                        appControllers.getCustomerController().deleteCustomer(selectedCustomer.getMailAdress());

                        loadAllCustomers();

                        deleteCustomerListener.getContentPane().repaint();
                        deleteCustomerListener.getContentPane().revalidate();
                    }
                } else {
                    throw new DeleteCustomerException("Impossible de supprimer un client inexistant");
                }
            } catch (DeleteCustomerException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }

        }
    }
}
