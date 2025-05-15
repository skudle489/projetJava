package userInterface;

import exceptions.*;
import model.Customer;
import utils.CustomerFormValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UpdateCustomerPanel extends JPanel {

    private JPanel mailAddressPanel;

    private JLabel mailAddressLabel;
    private JComboBox customersComboBox;

    private JButton searchCustomerButton;

    MainWindows updateCustomerListener;

    public UpdateCustomerPanel() {
        setUpUI();
    }

    public void setUpUI(){
        searchCustomerButton = new JButton("Rechercher client");
        searchCustomerButton.addActionListener(new searchCustomerButtonListener());
        mailAddressLabel = new JLabel("Adresse mail");
        customersComboBox = new JComboBox();
        mailAddressPanel = new JPanel();
        mailAddressPanel.setLayout(new GridLayout(1, 3, 2, 2));
        mailAddressPanel.add(mailAddressLabel);
        mailAddressPanel.add(customersComboBox);
        mailAddressPanel.add(searchCustomerButton);

        add(mailAddressPanel, BorderLayout.CENTER);
    }

    public void setUpdateCustomerListener(MainWindows listener){
        this.updateCustomerListener = listener;
        loadAllCustomersInComboBox();
    }

    public void loadAllCustomersInComboBox(){
        try {
            ArrayList<Customer> customers = updateCustomerListener.getCustomerController().getAllCustomers();
            customersComboBox.removeAllItems();
            for (Customer customer : customers) {
                customersComboBox.addItem(customer);
            }
        } catch (GetAllCustomersException exception){
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    private class searchCustomerButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Customer selectedCustomer = (Customer) customersComboBox.getSelectedItem();

                RegistrationForm registrationForm = new RegistrationForm(updateCustomerListener.getCountryController().getAllCountries(), selectedCustomer);
                registrationForm.setRegistrationListener(updateCustomerListener);
                updateCustomerListener.showPanel(registrationForm);

            } catch (GetAllCountryException exception){
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        }
    }

}
