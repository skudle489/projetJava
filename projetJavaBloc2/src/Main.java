

import dataAccess.ReservationDBDAO;
import dataAccess.ReviewDBDAO;
import model.Reservation;
import model.ReservationInvoiceModel;
import userInterface.MainWindows;

import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            MainWindows mainWindows = new MainWindows();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
