package userInterface;

import business.ReservationManager;
import controller.CustomerController;
import dataAccess.*;
import model.*;
import model.BedroomOwnsAmenity;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        try {
            MainWindows mainWindows = new MainWindows();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
