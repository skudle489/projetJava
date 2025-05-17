package userInterface;


import dataAccess.ReservationDBDAO;
import dataAccess.ReviewDBDAO;
import model.Reservation;

import javax.swing.*;
import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        try {
            MainWindows mainWindows = new MainWindows();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
