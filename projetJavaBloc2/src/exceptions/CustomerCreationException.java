package exceptions;

import model.Locality;

import java.time.LocalDate;

public class CustomerCreationException extends Exception {
    public CustomerCreationException(String message) {
        super(message);
    }

}
