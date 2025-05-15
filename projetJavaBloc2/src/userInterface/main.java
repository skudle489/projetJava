package userInterface;

import controller.CustomerController;
import dataAccess.CountryDBDAO;
import dataAccess.CustomerDBDAO;
import dataAccess.LocalityDBDAO;
import dataAccess.ReviewDBDAO;
import model.Country;
import model.Locality;
import model.Review;

import javax.swing.*;
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
