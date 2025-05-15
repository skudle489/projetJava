package dataAccess;

import exceptions.*;
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
    private final ReservationDBDAO reservationDBDAO;
    private static Connection connection;

    public CustomerDBDAO() throws SQLException {
        reservationDBDAO = new ReservationDBDAO();
        reviewDBDAO = new ReviewDBDAO();
        connection = DatabaseConnection.getInstance();
    }

    public void addCustomer(Customer customer) throws AddCustomerException {

        try {
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
        } catch (SQLException e) {
            throw new AddCustomerException("Erreur lors de la création du client");
        }


    }


    public ArrayList<Customer> getAllCustomers() throws GetAllCustomersException {

        try {

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
        } catch (SQLException | CustomerCreationException e) {
            throw new GetAllCustomersException("Erreur lors de la récupération des clients" + e.getMessage());
        }
    }

    public void updateCustomer(Customer customer) throws UpdateCustomerException {
        try {
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
        } catch (SQLException e) {
            throw new UpdateCustomerException("Erreur lors de la modification du client");
        }
    }

    public void deleteCustomer(String mailAdress) throws DeleteCustomerException {
        try {
            reviewDBDAO.deleteReviews(mailAdress);
            reservationDBDAO.deleteReservation(mailAdress);
            String sqlInstruction = "delete from customer where mail_adress = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, mailAdress);
            preparedStatement.executeUpdate();
        } catch (SQLException | DeleteReviewException e) {
            throw new DeleteCustomerException("Erreur lors de la suppression du client");
        }
    }

    public Customer getCustomer(String mailAdress) throws GetCustomerException {
        try {
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
            LocalDate birthDate = data.getDate("birthdate").toLocalDate();
            int day = birthDate.getDayOfMonth();
            int month = birthDate.getMonthValue();
            int year = birthDate.getYear();
            boolean isVegan = data.getBoolean("is_vegan");
            String city = data.getString("city");
            String postalCode = data.getString("postal_code");
            String country = data.getString("country");
            String secondaryPhone = data.getString("secondary_phone");

            return new Customer(mailAdress, firstName, lastName, phone, street, streetNumber, day, month, year, isVegan, city, postalCode, country, secondaryPhone);

        } catch (SQLException | CustomerCreationException e){
            throw new GetCustomerException("Erreur lors de la lecture du client" + e.getMessage());
        }

    }


}
