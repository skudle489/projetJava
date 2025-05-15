package userInterface;

import exceptions.AddCustomerException;
import exceptions.CustomerCreationException;
import model.Customer;

import java.sql.SQLException;

public interface IRegistrationListener {
    void onRegistrationValidated();
    void addCustomer(Customer customer) throws AddCustomerException;
}
