package dataAccess;

import exceptions.*;
import model.Customer;

import java.util.ArrayList;

public interface ICustomerDataAccess {
    void addCustomer(Customer customer) throws AddCustomerException;
    ArrayList<Customer> getAllCustomers() throws GetAllCustomersException;
    void updateCustomer(Customer customer) throws UpdateCustomerException;
    void deleteCustomer(String mailAdress) throws DeleteCustomerException;
    Customer getCustomer(String mailAdress) throws GetCustomerException;
}
