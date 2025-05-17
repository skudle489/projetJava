package userInterface;


import dataAccess.ReviewDBDAO;

import javax.swing.*;

public class main {
    public static void main(String[] args) {
        try {
            MainWindows mainWindows = new MainWindows();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
