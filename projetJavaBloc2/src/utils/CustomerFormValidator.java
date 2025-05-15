package utils;

import exceptions.CustomerCreationException;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerFormValidator {


    public static void validateMailAdress(String mailAddress) throws CustomerCreationException {
        if (mailAddress == null) {
            throw new CustomerCreationException("L'adresse mail ne peut pas etre null.");
        } else {
            if (mailAddress.isEmpty()){
                throw new CustomerCreationException("L'adresse mail ne peut pas etre vide.");
            } else {
                String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                Pattern pattern = Pattern.compile(emailRegex);
                Matcher matcher = pattern.matcher(mailAddress);

                if (!matcher.matches()) {
                    throw new CustomerCreationException("Le format de l'adresse mail n'est pas valide.");
                }
            }
        }
    }


    public static void validateStringValue(String valueName, String value) throws CustomerCreationException {
        if (value == null) {
            throw new CustomerCreationException(valueName +  " ne peut pas etre null");
        } else {
            if (value.isEmpty()) {
                throw new CustomerCreationException(valueName + " ne peut pas etre vide");
            } else {
                String valueRegex = "[A-Za-z\\s'\\-\\.]+";
                Pattern pattern = Pattern.compile(valueRegex);
                Matcher matcher = pattern.matcher(value);

                if (!matcher.matches()) {
                    System.out.println(value);
                    throw new CustomerCreationException(valueName + " doit etre composé(e) seulement de lettres");
                }
            }
        }
    }

    public static void validatePhone(String phone) throws CustomerCreationException {
        if (phone == null) {
            throw new CustomerCreationException("Le numéro du télépohne ne peut pas etre null");
        } else {
            String phoneRegex = "^\\d{10}$";
            Pattern pattern = Pattern.compile(phoneRegex);
            Matcher matcher = pattern.matcher(phone);

            if (!matcher.matches()) {
                throw new CustomerCreationException("Le format de téléphone est invalide. Le numéro doit etre composé de 10 chiffres.");
            }
        }
    }

    public static void validateStreetNumber(String streetNumber) throws CustomerCreationException {
        String streetNumberRegex = "^\\d+$";
        Pattern pattern = Pattern.compile(streetNumberRegex);
        Matcher matcher = pattern.matcher(streetNumber);

        if (!matcher.matches()) {
            throw new CustomerCreationException("Le numéro de la rue ne doit comporter que des chiffres");
        } else {
            if (Integer.parseInt(streetNumber) < 0) {
                throw new CustomerCreationException("Le numéro de la rue ne peut pas etre négatif");
            }
        }
    }

    public static void validateBirthDateAdult(int day, int month, int year) throws CustomerCreationException {
        LocalDate birthDate = LocalDate.of(year, month, day);
        LocalDate today = LocalDate.now();
        if (!birthDate.isBefore(today.minusYears(18))) {
            throw new CustomerCreationException("Vous devez etre majeur pour réserver.");
        }
    }

    public static void validateSecondaryPhone(String secondaryPhone) throws CustomerCreationException {
        if (!secondaryPhone.isEmpty()) {
            validatePhone(secondaryPhone);
        }
    }



}
