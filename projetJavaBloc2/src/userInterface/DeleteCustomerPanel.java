package userInterface;

import exceptions.CustomerCreationException;
import exceptions.DeleteCustomerException;
import exceptions.GetAllCustomersException;
import model.Customer;
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

    public DeleteCustomerPanel() {
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
            ArrayList<Customer> customers = deleteCustomerListener.getCustomerController().getAllCustomers();
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
                    deleteCustomerListener.getCustomerController().deleteCustomer(selectedCustomer.getMailAdress());

                    loadAllCustomers();

                    deleteCustomerListener.getFrameContainer().repaint();
                    deleteCustomerListener.getFrameContainer().revalidate();
                } else {
                    throw new DeleteCustomerException("Impossible de supprimer un client inexistant");
                }
            } catch (DeleteCustomerException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }

        }
    }
}
