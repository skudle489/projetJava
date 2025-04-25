package dataAccess;

import model.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ICustomerDAO {
    void addCustomer(Customer customer) throws SQLException;
    ArrayList<Customer> getCustomers();
    void updateCustomer(Customer customer);
    void deleteCustomer(Customer customer);
}
