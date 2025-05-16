package controller;
import business.CustomerManager;
import exceptions.*;
import model.Customer;

import java.sql.SQLException;
import java.util.ArrayList;


public class CustomerController {
    private CustomerManager customerManager;

    public CustomerController() {
        customerManager = new CustomerManager();
    }


    public void addCustomer(Customer customer) throws AddCustomerException {
        customerManager.addCustomer(customer);
    }

    public ArrayList<Customer> getAllCustomers() throws GetAllCustomersException {
        return customerManager.getAllCustomers();
    }

    public Customer getCustomer(String mailAddress) throws GetCustomerException {
        return customerManager.getCustomer(mailAddress);
    }

    public void updateCustomer(Customer customer) throws UpdateCustomerException {
        customerManager.updateCustomer(customer);
    }

    public void deleteCustomer(String mailAddress) throws DeleteCustomerException {
        customerManager.deleteCustomer(mailAddress);
    }

    public boolean customerExists(String mailAddress){
        return customerManager.customerExists(mailAddress);
    }

}
