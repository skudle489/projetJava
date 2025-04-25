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

public class CustomerDBDAO {
    private static Connection connection;

    public CustomerDBDAO() throws SQLException {
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
        preparedStatement.setString(10, customer.getLocality().getCity());
        preparedStatement.setString(11, customer.getLocality().getPostalCode());
        preparedStatement.setString(12, customer.getLocality().getCountry().getIso());

        preparedStatement.executeUpdate();

    }


    public Customer getCustomer(String mailAdress) throws SQLException, LocalityCreationException, CountryCreationException, CustomerCreationException {
        String sqlInstruction = "select * from customer where mail_adress = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
        preparedStatement.setString(1, mailAdress);

        ResultSet data = preparedStatement.executeQuery();
        data.next();

        String firstName = data.getString("first_name");
        String lastName = data.getString("last_name");
        String phone = data.getString("phone");
        String street = data.getString("street");
        int streetNumber = data.getInt("street_number");
        java.sql.Date birthDate = data.getDate("birthdate");

        int day = birthDate.toLocalDate().getDayOfMonth();
        int month = birthDate.toLocalDate().getMonthValue();
        int year = birthDate.toLocalDate().getYear();

        boolean isVegan = data.getBoolean("is_vegan");
        String secondaryPhone = data.getString("secondary_phone");
        String city = data.getString("city");
        String postalCode = data.getString("postal_code");
        String iso = data.getString("country");


        String sqlCountryNameInstruction = "select name from country where iso = ?";
        preparedStatement = connection.prepareStatement(sqlCountryNameInstruction);
        preparedStatement.setString(1, iso);

        ResultSet countryName = preparedStatement.executeQuery();
        countryName.next();

        Country country = new Country(iso, countryName.getString("name"));
        Locality locality = new Locality(city, country, postalCode);

        return new Customer(mailAdress, firstName, lastName, phone, street, streetNumber, day, month, year, isVegan, locality, secondaryPhone);
    }


}
