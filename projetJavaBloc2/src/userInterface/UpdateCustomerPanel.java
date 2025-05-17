package userInterface;

import exceptions.*;
import model.Customer;
import utils.AppControllers;
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
    private AppControllers appControllers;
    private MainWindows mainWindows;


    public UpdateCustomerPanel(AppControllers appControllers) {
        this.appControllers = appControllers;
        setUpUI();
    }

    public void setMainWindow(MainWindows mainWindows) {
        this.mainWindows = mainWindows;
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
        loadAllCustomersInComboBox();
    }

    public void setMainWindows(MainWindows mainWindows){
        this.mainWindows = mainWindows;
    }


    public void loadAllCustomersInComboBox(){
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

    private class searchCustomerButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Customer selectedCustomer = (Customer) customersComboBox.getSelectedItem();
                RegistrationForm registrationForm = new RegistrationForm(appControllers, selectedCustomer);
                registrationForm.setMainWindows(mainWindows);
                mainWindows.showPanel(registrationForm);

            } catch (Exception exception){
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        }
    }

}
