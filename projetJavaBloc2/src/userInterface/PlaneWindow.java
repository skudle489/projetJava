package userInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PlaneWindow extends JFrame {
    private Plane plane;
    private Container container;

    public PlaneWindow() {

        super("Plane");
        setBounds(100, 100, 700, 300);
        container = getContentPane();
        plane = new Plane(100, 600, 200, 100);
        container.add(plane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }
}
