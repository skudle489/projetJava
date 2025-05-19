package userInterface;

import exceptions.*;
import model.Customer;
import model.Hotel;
import model.Review;
import utils.AppControllers;
import utils.JComboBoxLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.DateTimeException;
import java.time.LocalDate;



public class ReviewForm extends JPanel {
    private JPanel formPanel;
    private JPanel lastVisitDateHotelCountryPanel;
    private JPanel lastVisitDateHotelCountryComboBoxPanel;
    private JPanel dateCreationPanel;
    private JPanel dateCreationComboBoxPanel;
    private JPanel buttonSendPanel;
    private JLabel commentLabel, lastReservationDateLabel, dayLastVisitDateCountryLabel, monthLabel, yearLabel, titleLabel, starsLabel, dateCreationLabel, hotelLabel;
    private JCheckBox isAnonymousCheckBox;
    private JCheckBox lastVisitDateHotelCountryCheckBox;
    private JTextField commentTextField, titleTextField;
    private JComboBox dayComboBoxLastVisitCountry, monthComboBoxLastVisitCountry, yearComboBoxLastVisitCountry, dayComboBoxCreation, monthComboBoxCreation, yearComboBoxCreation;
    private JSpinner starSpinner;
    private JButton buttonValidation;
    private JComboBox customersComboBox;
    private JComboBox hotelsComboBox;
    private MainWindows mainWindows;

    Integer[] daysValues;
    Integer[] monthsValues;
    Integer[] yearsValues;

    private Customer selectedCustomer;
    private Hotel selectedHotel;
    private Review selectedReview;
    private Review correspondingComment;
    private AppControllers appControllers;


    public ReviewForm(AppControllers appControllers, Customer customer, Hotel hotel, Review review) throws GetAllHotelsException {
        this.appControllers = appControllers;
        this.selectedCustomer = customer;
        this.selectedHotel = hotel;
        this.selectedReview = review;
        setUpUI();

        if(customer!=null && hotel!=null && review!=null){
            setReviewValue();
        }
    }

    public void setUpUI(){
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(7, 2, 2, 2));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 0,0,0 ));

        hotelLabel = new JLabel("Hotel :");
        formPanel.add(hotelLabel);

        hotelsComboBox = new JComboBox();
        JComboBoxLoader.loadAllHotelsInComboBox(hotelsComboBox, appControllers);
        formPanel.add(hotelsComboBox);

        isAnonymousCheckBox = new JCheckBox("Souhaite être anonyme");
        formPanel.add(isAnonymousCheckBox);

        customersComboBox = new JComboBox();
        JComboBoxLoader.loadAllCustomersInComboBox(customersComboBox, appControllers);
        formPanel.add(customersComboBox);

        titleLabel = new JLabel("Titre du commentaire :* ");
        formPanel.add(titleLabel);
        titleTextField = new JTextField();
        formPanel.add(titleTextField);

        commentLabel = new JLabel("Commentaire :* ");
        formPanel.add(commentLabel);
        commentTextField = new JTextField();
        formPanel.add(commentTextField);

        starsLabel = new JLabel("Nombres d'étoiles");
        formPanel.add(starsLabel);

        Integer starsValues[] = {1, 2, 3, 4, 5};
        starSpinner = new JSpinner(new SpinnerListModel(starsValues));
        formPanel.add(starSpinner);

        lastVisitDateHotelCountryCheckBox = new JCheckBox("Souhaite informé de sa dernière visite du pays :");
        formPanel.add(lastVisitDateHotelCountryCheckBox);
        lastVisitDateHotelCountryCheckBox.addItemListener(new LastVisitDateHotelCountryCheckBoxListener());

        lastVisitDateHotelCountryPanel = new JPanel(new GridLayout(2, 2, 2, 2));
        formPanel.add(lastVisitDateHotelCountryPanel);

        lastReservationDateLabel = new JLabel("Date de dernière visite du pays : ");
        lastVisitDateHotelCountryPanel.add(lastReservationDateLabel);

        lastVisitDateHotelCountryComboBoxPanel = new JPanel(new GridLayout(3, 2, 1, 1));
        lastVisitDateHotelCountryPanel.add(lastVisitDateHotelCountryComboBoxPanel);

        daysValues = new Integer[31];
        for (int i = 0; i < 31; i++) daysValues[i] = i + 1;

        monthsValues = new Integer[12];
        for (int i = 0; i < 12; i++) monthsValues[i] = i + 1;

        int currentYear = java.time.Year.now().getValue();
        int minYear = currentYear - 120;
        int maxYear = currentYear;
        yearsValues = new Integer[maxYear - minYear + 1];
        for (int i = 0; i < yearsValues.length; i++) yearsValues[i] = maxYear - i;

        dayLastVisitDateCountryLabel = new JLabel("Jour : ");
        dayComboBoxLastVisitCountry = new JComboBox(daysValues);
        dayComboBoxLastVisitCountry.setEnabled(false);
        lastVisitDateHotelCountryComboBoxPanel.add(dayComboBoxLastVisitCountry);

        monthLabel = new JLabel("Mois :");
        monthComboBoxLastVisitCountry = new JComboBox(monthsValues);
        monthComboBoxLastVisitCountry.setEnabled(false);
        lastVisitDateHotelCountryComboBoxPanel.add(monthComboBoxLastVisitCountry);

        yearLabel = new JLabel("Année:");
        yearComboBoxLastVisitCountry = new JComboBox(yearsValues);
        yearComboBoxLastVisitCountry.setEnabled(false);
        lastVisitDateHotelCountryComboBoxPanel.add(yearComboBoxLastVisitCountry);

        dateCreationPanel = new JPanel(new GridLayout(2, 2, 2, 2));
        formPanel.add(dateCreationPanel);

        dateCreationLabel = new JLabel("Date de creation du commentaire :* ");
        dateCreationPanel.add(dateCreationLabel);

        dateCreationComboBoxPanel = new JPanel(new GridLayout(3, 2, 1, 1));
        dateCreationPanel.add(dateCreationComboBoxPanel);

        dayComboBoxCreation = new JComboBox(daysValues);
        dateCreationComboBoxPanel.add(dayLastVisitDateCountryLabel);
        dateCreationComboBoxPanel.add(dayComboBoxCreation);

        monthComboBoxCreation = new JComboBox(monthsValues);
        dateCreationComboBoxPanel.add(monthLabel);
        dateCreationComboBoxPanel.add(monthComboBoxCreation);

        yearComboBoxCreation = new JComboBox(yearsValues);
        dateCreationComboBoxPanel.add(yearLabel);
        dateCreationComboBoxPanel.add(yearComboBoxCreation);

        if (selectedCustomer == null) {
            LocalDate now = LocalDate.now();
            dayComboBoxCreation.setSelectedItem(now.getDayOfMonth());
            monthComboBoxCreation.setSelectedItem(now.getMonthValue());
            yearComboBoxCreation.setSelectedItem(now.getYear());
        }

        dayComboBoxCreation.setEnabled(false);
        monthComboBoxCreation.setEnabled(false);
        yearComboBoxCreation.setEnabled(false);

        buttonSendPanel = new JPanel();
        buttonValidation = new JButton("Valider");
        buttonValidation.addActionListener(new ValidateButtonActionListener());
        buttonValidation.setPreferredSize(new Dimension(160, 18));
        buttonSendPanel.add(buttonValidation);

        lastVisitDateHotelCountryCheckBox.setSelected(false);

        setLayout(new BorderLayout());
        add(new JLabel("<html><body style='width:400px; padding:5px;'>ATTENTION: Par hotel, vous ne pouvez ajouter qu'un avis par jour. Si vous cliquez sur valider, cela modifiera votre avis du jour actuel de l'hôtel et n'ajoutera pas un NOUVEL avis.</body></html>"), BorderLayout.NORTH);
        this.add(formPanel, BorderLayout.CENTER);
        this.add(buttonSendPanel, BorderLayout.SOUTH);
    }

    public void setMainWindows(MainWindows mainWindows) {
        this.mainWindows = mainWindows;
    }

    private class LastVisitDateHotelCountryCheckBoxListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent event) {
            boolean selected = (event.getStateChange() == ItemEvent.SELECTED);
            dayComboBoxLastVisitCountry.setEnabled(selected);
            monthComboBoxLastVisitCountry.setEnabled(selected);
            yearComboBoxLastVisitCountry.setEnabled(selected);
        }
    }

    private class ValidateButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Hotel hotel = (Hotel) hotelsComboBox.getSelectedItem();
                Customer customer = (Customer) customersComboBox.getSelectedItem();

                if (hotel == null || customer == null) {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un hôtel et un client.");
                } else {
                    int idHotel = hotel.getId();
                    String customerEmail = customer.getMailAdress();
                    boolean isAnonymous = isAnonymousCheckBox.isSelected();
                    String title = titleTextField.getText().trim();
                    String comment = commentTextField.getText().trim();
                    int nbStars = Integer.parseInt(starSpinner.getValue().toString());

                    int dayCreationDate = daysValues[dayComboBoxCreation.getSelectedIndex()];
                    int monthCreationDate = monthsValues[monthComboBoxCreation.getSelectedIndex()];
                    int yearCreationDate = yearsValues[yearComboBoxCreation.getSelectedIndex()];
                    LocalDate creationCommentDate = LocalDate.of(yearCreationDate, monthCreationDate, dayCreationDate);

                    LocalDate dateLastVisitCountry;
                    if (lastVisitDateHotelCountryCheckBox.isSelected()) {
                        int dayVisit = daysValues[dayComboBoxLastVisitCountry.getSelectedIndex()];
                        int monthVisit = monthsValues[monthComboBoxLastVisitCountry.getSelectedIndex()];
                        int yearVisit = yearsValues[yearComboBoxLastVisitCountry.getSelectedIndex()];
                        dateLastVisitCountry = LocalDate.of(yearVisit, monthVisit, dayVisit);
                    } else {
                        dateLastVisitCountry = null;
                    }


                    Review review = new Review(comment, idHotel, title, isAnonymous, nbStars, customerEmail, creationCommentDate, dateLastVisitCountry);

                    appControllers.getReviewController().addReview(review);
                    mainWindows.onRegistrationValidated();
                }

            } catch (AddReviewException | ReviewCreationException | UpdateReviewException | ReviewException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } catch (DateTimeException ex) {
                JOptionPane.showMessageDialog(null, "Date invalide. Veuillez vérifier les champs sélectionnés.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Le nombre d'étoiles n'est pas valide.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Une erreur inattendue est survenue : " + ex.getMessage());
            }
        }
    }

    private void setReviewValue() throws GetAllHotelsException {
        String mailAddress = selectedCustomer.getMailAdress();
        for (int i = 0; i < customersComboBox.getItemCount(); i++) {
            Customer customer = (Customer) customersComboBox.getItemAt(i);
            if (mailAddress.equals(customer.getMailAdress())) {
                customersComboBox.setSelectedIndex(i);
                break;
            }
        }

        customersComboBox.setEnabled(false);
        hotelsComboBox.setEnabled(false);
        lastVisitDateHotelCountryCheckBox.setSelected(selectedReview.getLastVisitDateHotelCountry() != null);

        isAnonymousCheckBox.setSelected(selectedReview.getIsAnonymous());

        int hotelId = selectedHotel.getId();
        var allHotels = appControllers.getHotelController().getAllHotels();
        for (int i = 0; i < allHotels.size(); i++) {
            if (hotelId == allHotels.get(i).getId()) {
                hotelsComboBox.setSelectedIndex(i);
                break;
            }
        }

        titleTextField.setText(selectedReview.getTitle());
        commentTextField.setText(selectedReview.getComment());
        starSpinner.setValue(selectedReview.getStar());

        setDateInComboBox(selectedReview.getLastVisitDateHotelCountry(), dayComboBoxLastVisitCountry, monthComboBoxLastVisitCountry, yearComboBoxLastVisitCountry);

        setDateInComboBox(selectedReview.getCreationDate(), dayComboBoxCreation, monthComboBoxCreation, yearComboBoxCreation);
    }

    private void setDateInComboBox(LocalDate date, JComboBox dayCB, JComboBox monthCB, JComboBox yearCB) {
        if (date != null) {
            dayCB.setSelectedItem(date.getDayOfMonth());
            monthCB.setSelectedItem(date.getMonthValue());
            yearCB.setSelectedItem(date.getYear());
        }
    }
}