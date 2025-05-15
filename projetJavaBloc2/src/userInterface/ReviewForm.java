package userInterface;

import exceptions.*;
import model.Customer;
import model.Hotel;
import model.Review;
import userInterface.MainWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


public class ReviewForm extends JPanel {
    private JPanel formPanel;
    private JPanel lastReservationPanel;
    private JPanel lastReservationComboBoxPanel;
    private JPanel dateCreationPanel;
    private JPanel dateCreationComboBoxPanel;
    private JLabel commentLabel, lastReservationDateLabel, dayLabel, monthLabel, yearLabel, titleLabel, starsLabel, dateCreationLabel, hotelLabel;
    private JCheckBox isAnonymousCheckBox;
    private JTextField commentTextField, titleTextField;
    private JComboBox dayComboBoxReservation, monthComboBoxReservation, yearComboBoxReservation, dayComboBoxCreation, monthComboBoxCreation, yearComboBoxCreation;
    private JSpinner starSpinner;
    private JButton buttonValidation;
    private CheckBoxListener checkBoxListener;
    private JComboBox customersComboBox;
    private JComboBox hotelsComboBox;
    private MainWindows registrationListener;


    Integer[] daysValues;
    Integer[] monthsValues;
    Integer[] yearsValues;

    public ReviewForm(ArrayList<Customer> customers, ArrayList<Hotel>hotels) {

        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(7, 2, 2, 2));

        hotelLabel= new JLabel("Hotel :");
        formPanel.add(hotelLabel);

        hotelsComboBox=new JComboBox();
        for(Hotel hotel: hotels) {
            hotelsComboBox.addItem(this.getIdHotel(hotel));
        }
        formPanel.add(hotelsComboBox);

        isAnonymousCheckBox = new JCheckBox("Anonymous");
        formPanel.add(isAnonymousCheckBox);
        checkBoxListener = new CheckBoxListener();
        isAnonymousCheckBox.addItemListener(checkBoxListener);


        customersComboBox = new JComboBox();
        for (Customer customer : customers){
            customersComboBox.addItem(this.getEmailCustomer(customer));
        }
        formPanel.add(customersComboBox);


        titleLabel = new JLabel("Titre du commentaire : ");
        formPanel.add(titleLabel);
        titleTextField = new JTextField();
        formPanel.add(titleTextField);

        commentLabel = new JLabel("Comment : ");
        formPanel.add(commentLabel);
        commentTextField = new JTextField();
        formPanel.add(commentTextField);

        starsLabel=new JLabel("Nombres d'étoiles");
        formPanel.add(starsLabel);

        Integer starsValues[]= {1,2,3,4,5};
        starSpinner=new JSpinner(new SpinnerListModel(starsValues));

        formPanel.add(starSpinner);

        lastReservationPanel= new JPanel();
        lastReservationPanel.setLayout(new GridLayout(3, 2, 2, 2));
        formPanel.add(lastReservationPanel);
        lastReservationDateLabel = new JLabel("Date de dernière réservation : ");
        lastReservationPanel.add(lastReservationDateLabel);

        lastReservationComboBoxPanel = new JPanel();
        lastReservationComboBoxPanel.setLayout(new GridLayout(3, 2, 2, 2));
        lastReservationPanel.add(lastReservationComboBoxPanel);

        daysValues = new Integer[31];
        int iValue=1;
        while(iValue <= daysValues.length){
            daysValues[iValue-1] = iValue;
            iValue++;
        }

        dayLabel=new JLabel("Jour : ");
        lastReservationComboBoxPanel.add(dayLabel);

        dayComboBoxReservation=new JComboBox(daysValues);
        lastReservationComboBoxPanel.add(dayComboBoxReservation);

        monthsValues = new Integer[12];
        iValue=1;
        while(iValue <= monthsValues.length){
            monthsValues[iValue-1] = iValue;
            iValue++;
        }
        monthLabel=new JLabel("Mois :");
        lastReservationComboBoxPanel.add(monthLabel);

        monthComboBoxReservation=new JComboBox(monthsValues);
        lastReservationComboBoxPanel.add(monthComboBoxReservation);

        int currentYear=java.time.Year.now().getValue();
        int minYear=currentYear-120;
        int maxYear=currentYear;

        yearsValues = new Integer[maxYear-minYear + 1];
        iValue=0;
        while(iValue < yearsValues.length){
            yearsValues[iValue]=maxYear-iValue;
            iValue++;
        }

        yearLabel=new JLabel("Année:");
        lastReservationComboBoxPanel.add(yearLabel);

        yearComboBoxReservation=new JComboBox(yearsValues);
        lastReservationComboBoxPanel.add(yearComboBoxReservation);

        dateCreationPanel= new JPanel();
        dateCreationPanel.setLayout(new GridLayout(2, 2, 2, 2));
        formPanel.add(dateCreationPanel);

        dateCreationLabel=new JLabel("Date de creation du commentaire : ");
        dateCreationPanel.add(dateCreationLabel);

        dateCreationComboBoxPanel = new JPanel();
        dateCreationComboBoxPanel.setLayout(new GridLayout(3, 2, 2, 2));
        dateCreationPanel.add(dateCreationComboBoxPanel);



        dayComboBoxCreation=new JComboBox(daysValues);
        dateCreationComboBoxPanel.add(dayLabel);
        dateCreationComboBoxPanel.add(dayComboBoxCreation);

        monthComboBoxCreation=new JComboBox(monthsValues);
        dateCreationComboBoxPanel.add(monthLabel);
        dateCreationComboBoxPanel.add(monthComboBoxCreation);


        yearComboBoxCreation=new JComboBox(yearsValues);
        dateCreationComboBoxPanel.add(yearLabel);
        dateCreationComboBoxPanel.add(yearComboBoxCreation);


        buttonValidation=new JButton("Envoyer");
        buttonValidation.addActionListener(new ValidateButtonActionListener());

        setLayout(new BorderLayout());
        this.add(formPanel, BorderLayout.CENTER);

    }

    public int getIdHotel(Hotel hotel){
        return hotel.getId();
    }

    public String getEmailCustomer(Customer customer){
        return customer.getMailAdress();
    }

    public void setRegistrationListener(MainWindows listener) {
        this.registrationListener = listener;
    }

    private class CheckBoxListener implements ItemListener {
        public void itemStateChanged(ItemEvent event) {
            if(event.getStateChange()== ItemEvent.SELECTED){
                customersComboBox.setEnabled(false);
            }
        }
    }

    private class ValidateButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try{
                if(registrationListener!=null){

                    int id= (int) hotelsComboBox.getSelectedItem();
                    String customerEmail= (String)customersComboBox.getSelectedItem();
                    boolean isAnonymous=isAnonymousCheckBox.isSelected();
                    String title=titleTextField.getText();
                    String comment=commentTextField.getText();
                    int nbStars=Integer.parseInt(starSpinner.getValue().toString());
                    int iDayReservation = dayComboBoxReservation.getSelectedIndex();
                    int iMonthReservation = monthComboBoxReservation.getSelectedIndex();
                    int iYearReservation = yearComboBoxReservation.getSelectedIndex();
                    int iDayCreation = dayComboBoxCreation.getSelectedIndex();
                    int iMonthCreation = monthComboBoxCreation.getSelectedIndex();
                    int iYearCreation = yearComboBoxCreation.getSelectedIndex();


                    LocalDate lastReservationDate=LocalDate.of(yearsValues[iDayReservation], monthsValues[iMonthReservation], daysValues[iYearReservation]);
                    LocalDate creationCommentDate=LocalDate.of(yearsValues[iDayCreation], monthsValues[iMonthCreation], daysValues[iYearCreation]);

                    Review review=new Review(comment,id, title, isAnonymous,nbStars,customerEmail,creationCommentDate,lastReservationDate);

                    registrationListener.addReview(review);
                }

            }catch(AddReviewException | ReviewCreationException ex){
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }




}
