package model;

import exceptions.CustomerCreationException;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {
    private String mailAdress;
    private String firstName;
    private String lastName;
    private String phone;
    private String street;
    private int streetNumber;
    private LocalDate birthDate;
    private boolean isVegan;
    private String city;
    private String postalCode;
    private String countryIso;
    private String secondaryPhone;

    public Customer(String mailAdress, String firstName, String lastName, String phone, String street, int streetNumber, int day, int month, int year, boolean isVegan, String city, String postalCode, String countryIso, String secondaryPhone) throws CustomerCreationException {
        setMailAdress(mailAdress);
        setFirstName(firstName);
        setLastName(lastName);
        setPhone(phone);
        setStreet(street);
        setStreetNumber(streetNumber);
        this.city = city;
        this.postalCode = postalCode;
        this.countryIso = countryIso;

        LocalDate birthDate = LocalDate.of(year, month, day);

        setBirthDate(birthDate);
        this.isVegan = isVegan;

        setSecondaryPhone(secondaryPhone);
    }

    public void setMailAdress(String mailAdress) throws CustomerCreationException {

        if (mailAdress == null) {
            throw new CustomerCreationException("L'adresse mail ne peut pas etre null.");
        } else {
            if (mailAdress.isEmpty()){
                throw new CustomerCreationException("L'adresse mail ne peut pas etre vide.");
            } else {
                String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                Pattern pattern = Pattern.compile(emailRegex);
                Matcher matcher = pattern.matcher(mailAdress);

                if (matcher.matches()) {
                    this.mailAdress = mailAdress;
                } else {
                    throw new CustomerCreationException("Le format de l'adresse mail n'est pas valide.");
                }
            }
        }
    }

    public void setFirstName(String firstName) throws CustomerCreationException {
        if (firstName == null) {
            throw new CustomerCreationException("Le prénom ne peut pas etre null");
        } else {
            if (firstName.isEmpty()) {
                throw new CustomerCreationException("Le prénom ne peut pas etre vide");
            } else {
                this.firstName = firstName;
            }
        }
    }

    public void setLastName(String lastName) throws CustomerCreationException {
        if (lastName == null) {
            throw new CustomerCreationException("Le nom de famille ne peut pas etre null");
        } else {
            if (lastName.isEmpty()) {
                throw new CustomerCreationException("Le nom de famille ne peut pas etre vide");
            } else {
                this.lastName = lastName;
            }
        }
    }

    public void setPhone(String phone) throws CustomerCreationException {
        if (phone == null) {
            this.phone = null;
        } else {
            if (phone.isEmpty()) {
                throw new CustomerCreationException("Le numéro de téléphone ne peut pas etre vide");
            } else {
                String phoneRegex = "^\\d{10}$";
                Pattern pattern = Pattern.compile(phoneRegex);
                Matcher matcher = pattern.matcher(phone);

                if (matcher.matches()) {
                    this.phone = phone;
                } else {
                    throw new CustomerCreationException("Le format du numéro de téléphone est invalide. Le numéro doit etre composé de 10 chiffres.");
                }
            }
        }
    }


    public void setStreet(String street) throws CustomerCreationException {
        if (street == null) {
            throw new CustomerCreationException("La rue ne peut pas etre null");
        } else {
            if (street.isEmpty()) {
                throw new CustomerCreationException("La rue ne peut pas etre vide");
            } else {
                this.street = street;
            }
        }
    }

    public void setStreetNumber(int streetNumber) throws CustomerCreationException {
        if (streetNumber < 0) {
            throw new CustomerCreationException("Le numéro de la rue ne peut pas etre négatif");
        } else {
            this.streetNumber = streetNumber;
        }
    }

    public void setBirthDate(LocalDate birthDate) throws CustomerCreationException {
        if (birthDate == null) {
            throw new CustomerCreationException("La date de naissance ne peut pas etre null");
        } else {
            LocalDate today = LocalDate.now();
            if (birthDate.isBefore(today.minusYears(18))) {
                this.birthDate = birthDate;
            } else {
                throw new CustomerCreationException("Vous devez etre majeur pour réserver.");
            }
        }
    }

    public void setSecondaryPhone(String secondaryPhone) throws CustomerCreationException {
        if (secondaryPhone == null || secondaryPhone.isEmpty()) {
            this.secondaryPhone = null;
        } else {
            if (phone == null) {
                throw new CustomerCreationException("Le numéro du téléphone principal est null");
            } else {
                this.secondaryPhone = secondaryPhone;
            }
        }
    }

    public String getMailAdress() {
        return mailAdress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getStreet() {
        return street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getSecondaryPhone() {
        return secondaryPhone;
    }

    public boolean getIsVegan(){
        return isVegan;
    }


    public String getCity(){
        return city;
    }

    public String getPostalCode(){
        return postalCode;
    }

    public String getCountry(){
        return countryIso;
    }

    @Override
    public String toString() {
        return mailAdress;
    }

}
