package dataAccess;

import exceptions.CustomerCreationException;
import model.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ICustomerDataAccess {
    void addCustomer(Customer customer) throws SQLException;
    ArrayList<Customer> getAllCustomers() throws SQLException, CustomerCreationException;
    void updateCustomer(Customer customer) throws SQLException;
    void deleteCustomer(String mailAdress) throws SQLException;
}
