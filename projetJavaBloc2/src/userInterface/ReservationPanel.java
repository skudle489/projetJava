package userInterface;

import javax.swing.*;

public class ReservationPanel extends JPanel {
    private JPanel mainPanel;

    private JLabel mailAddressLabel, hotel, bedroom;
    private JComboBox mailAddressComboBox, hotelComboBox, bedroomComboBox;
    private MainWindows mainWindows;

    public ReservationPanel() {
        setUp();
    }

    public void setUp(){
        mainPanel = new JPanel();
        mailAddressLabel = new JLabel("Addresse mail");
        hotel = new JLabel("Hotel");
        bedroom = new JLabel("Bedroom");
        mailAddressComboBox = new JComboBox();
        hotelComboBox = new JComboBox();
        bedroomComboBox = new JComboBox();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(mailAddressLabel);
        mainPanel.add(mailAddressComboBox);
        mainPanel.add(hotel);
        mainPanel.add(hotelComboBox);
        mainPanel.add(bedroom);
        mainPanel.add(bedroomComboBox);
        add(mainPanel);
    }

    public void reservationlistener(MainWindows mainWindows){
        this.mainWindows = mainWindows;
    }
}
