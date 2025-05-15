package business;

import dataAccess.CustomerDBDAO;
import exceptions.*;
import model.Customer;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;


public class CustomerManager {
    private CustomerDBDAO customerDBDAO;

    public CustomerManager() {
        try {
            customerDBDAO = new CustomerDBDAO();
        } catch (Exception e) {
            throw new DataAccessException("Erreur de base de donnée");
        }
    }

    public void addCustomer(Customer customer) throws AddCustomerException {
        customerDBDAO.addCustomer(customer);
    }

    public ArrayList<Customer> getAllCustomers() throws GetAllCustomersException {
        return customerDBDAO.getAllCustomers();
    }

    public Customer getCustomer(String mailAddress) throws GetCustomerException {
        return customerDBDAO.getCustomer(mailAddress);
    }

    public void updateCustomer(Customer customer) throws UpdateCustomerException {
        customerDBDAO.updateCustomer(customer);
    }

    public void deleteCustomer(String mailAddress) throws DeleteCustomerException {
        customerDBDAO.deleteCustomer(mailAddress);
    }
}
