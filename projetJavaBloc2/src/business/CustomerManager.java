package business;

import dataAccess.CustomerDBDAO;
import dataAccess.ICustomerDataAccess;
import exceptions.*;
import model.Customer;


import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;


public class CustomerManager {
    private ICustomerDataAccess customerDBDAO;

    public CustomerManager() {
        try {
            customerDBDAO = new CustomerDBDAO();
        } catch (Exception e) {
            throw new DataAccessException("Erreur de base de donnée");
        }
    }

    public void addCustomer(Customer customer) throws AddCustomerException {
        LocalDate birthDate = customer.getBirthDate();
        LocalDate today = LocalDate.now();

        if (Period.between(birthDate, today).getYears() < 18) {
            throw new AddCustomerException("Le client doit être majeur.");
        }

        if (customer.getStreetNumber() < 0){
            throw new AddCustomerException("Le numéro de la rue ne peut etre negatif.");
        }

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

    public boolean customerExists(String mailAddress) {
        try {
            Customer customer = getCustomer(mailAddress);
            return customer != null;
        } catch (GetCustomerException e) {
            return false;
        }
    }
}
