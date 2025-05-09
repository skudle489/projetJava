package dataAccess;

import exceptions.CountryCreationException;
import exceptions.CustomerCreationException;
import exceptions.LocalityCreationException;
import model.Country;
import model.Customer;
import model.Locality;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class CustomerDBDAO implements ICustomerDataAccess {
    private final ReviewDBDAO reviewDBDAO;
    private static Connection connection;

    public CustomerDBDAO() throws SQLException {
        reviewDBDAO = new ReviewDBDAO();
        connection = DatabaseConnection.getInstance();
    }

    public void addCustomer(Customer customer) throws SQLException {
        String sqlInstruction = "insert into customer (mail_adress, first_name, last_name, phone, street, street_number, birthdate," +
                "is_vegan, secondary_phone, city, postal_code, country) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);

        preparedStatement.setString(1, customer.getMailAdress());
        preparedStatement.setString(2, customer.getFirstName());
        preparedStatement.setString(3, customer.getLastName());
        preparedStatement.setString(4, customer.getPhone());
        preparedStatement.setString(5, customer.getStreet());
        preparedStatement.setInt(6, customer.getStreetNumber());
        preparedStatement.setDate(7, java.sql.Date.valueOf(customer.getBirthDate()));
        preparedStatement.setBoolean(8, customer.getIsVegan());
        preparedStatement.setString(9, customer.getSecondaryPhone());
        preparedStatement.setString(10, customer.getCity());
        preparedStatement.setString(11, customer.getPostalCode());
        preparedStatement.setString(12, customer.getCountry());

        preparedStatement.executeUpdate();

    }


    public ArrayList<Customer> getAllCustomers() throws SQLException, CustomerCreationException {
        String sqlInstruction = "select * from customer";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
        ResultSet data = preparedStatement.executeQuery();


        Customer customer;
        String mailAdress;
        String firstName;
        String lastName;
        String phone;
        String street;
        int streetNumber;
        LocalDate birthDate;
        boolean isVegan;
        String secondaryPhone;
        String city;
        String postalCode;
        String iso;

        ArrayList<Customer> customers = new ArrayList<>();



        while (data.next()) {
            mailAdress = data.getString("mail_adress");
            firstName = data.getString("first_name");
            lastName = data.getString("last_name");
            phone = data.getString("phone");
            street = data.getString("street");
            streetNumber = data.getInt("street_number");
            birthDate = data.getDate("birthdate").toLocalDate();


            isVegan = data.getBoolean("is_vegan");
            secondaryPhone = data.getString("secondary_phone");
            city = data.getString("city");
            postalCode = data.getString("postal_code");
            iso = data.getString("country");

            customer = new Customer(mailAdress, firstName, lastName, phone, street, streetNumber, birthDate.getDayOfMonth(), birthDate.getMonthValue(), birthDate.getYear(), isVegan, city, postalCode, iso, secondaryPhone);
            customers.add(customer);
        }
        return customers;
    }

    public void updateCustomer(Customer customer) throws SQLException {
        String sqlInstruction = "update customer set first_name = ?, last_name = ?, phone = ?, street = ?, street_number = ?, birthdate = ?, is_vegan = ?, secondary_phone = ?, city = ?, postal_code = ?, country = ? where mail_adress = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
        preparedStatement.setString(1, customer.getFirstName());
        preparedStatement.setString(2, customer.getLastName());
        preparedStatement.setString(3, customer.getPhone());
        preparedStatement.setString(4, customer.getStreet());
        preparedStatement.setInt(5, customer.getStreetNumber());
        preparedStatement.setDate(6, java.sql.Date.valueOf(customer.getBirthDate()));
        preparedStatement.setBoolean(7, customer.getIsVegan());
        preparedStatement.setString(8, customer.getSecondaryPhone());
        preparedStatement.setString(9, customer.getCity());
        preparedStatement.setString(10, customer.getPostalCode());
        preparedStatement.setString(11, customer.getCountry());
        preparedStatement.setString(12, customer.getMailAdress());
        preparedStatement.executeUpdate();
    }

    public void deleteCustomer(String mailAdress) throws SQLException {
        reviewDBDAO.deleteReviews(mailAdress);
        String sqlInstruction = "delete from customer where mail_adress = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
        preparedStatement.setString(1, mailAdress);
        preparedStatement.executeUpdate();
    }


}
