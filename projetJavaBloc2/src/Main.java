
import userInterface.MainWindows;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            MainWindows mainWindows = new MainWindows();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
