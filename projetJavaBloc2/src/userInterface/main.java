package userInterface;

import controller.CustomerController;
import dataAccess.*;
import model.*;

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
