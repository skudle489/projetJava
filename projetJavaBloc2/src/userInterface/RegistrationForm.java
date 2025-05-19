package userInterface;

import exceptions.*;
import model.Country;
import model.Customer;
import model.Locality;
import utils.AppControllers;
import utils.CustomerFormValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Objects;


public class RegistrationForm extends JPanel {


    private JPanel formPanel;
    private JPanel birthdayPanel;
    private JPanel comboBoxPanel;
    private JPanel southpanel;
    private JPanel buttonPanel;
    private JCheckBox isVeganCheckBox;
    private JLabel mailAddressLabel, firstNameLabel, lastName, street, streetNumber, birthday, phone, secondaryPhone, dayLabel, monthLabel, yearLabel, cityLabel, postalCode, country ;
    private JTextField mailAddressTextField, firstNameTextField, lastNameTextField, streetTextField, streetNumberTextField, postalCodeTextField, phoneTextField, secondaryPhoneTextField, cityTextField;
    private JButton buttonValidation;
    private MainWindows mainWindows;

    private JComboBox dayCombox;
    private JComboBox monthCombox;
    private JComboBox yearCombox;
    private JComboBox cityPostalCode;

    private JComboBox countryComboBox;

    Integer[] daysValues;
    Integer[] monthsValues;
    Integer[] yearsValues;

    Customer customer;

    AppControllers appControllers;


    private ArrayList<Country> countries;

    public RegistrationForm(AppControllers appControllers, Customer customer) throws GetAllCountryException, GetAllLocalityWithCountryException {

        this.appControllers = appControllers;

        this.customer = customer;

        this.countries = appControllers.getCountryController().getAllCountries();

        setUpUI();

        if (customer != null) {
            setCustomerValue();
        } else {
            initializeComboBoxCityPostalCodeDefaultValues();
        }

    }

    public void setMainWindows(MainWindows mainWindows) {
        this.mainWindows = mainWindows;
    }



    public void setUpUI(){

        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(10, 2, 5, 5));


        mailAddressLabel=new JLabel("Adresse mail :");
        formPanel.add(mailAddressLabel);
        mailAddressTextField = new JTextField();
        formPanel.add(mailAddressTextField);

        if (customer != null) {
            mailAddressTextField.setEditable(false);
        }

        firstNameLabel=new JLabel("Prénom :");
        formPanel.add(firstNameLabel);
        firstNameTextField = new JTextField();
        formPanel.add(firstNameTextField);


        lastName=new JLabel("Nom de famille :");
        formPanel.add(lastName);
        lastNameTextField = new JTextField();
        formPanel.add(lastNameTextField);

        country=new JLabel("Pays : ");
        formPanel.add(country);


        countryComboBox=new JComboBox();


        for (Country country : countries){
            countryComboBox.addItem(country);
        }
        formPanel.add(countryComboBox);

        cityLabel=new JLabel("Ville et code postal");
        formPanel.add(cityLabel);
        cityPostalCode = new JComboBox();
        formPanel.add(cityPostalCode);

        street=new JLabel("Rue : ");
        formPanel.add(street);
        streetTextField=new JTextField();
        formPanel.add(streetTextField);

        streetNumber=new JLabel("Numero de Rue :");
        formPanel.add(streetNumber);
        streetNumberTextField=new JTextField();
        formPanel.add(streetNumberTextField);

        phone=new JLabel("Numéro de Téléphone :");
        formPanel.add(phone);
        phoneTextField=new JTextField();
        formPanel.add(phoneTextField);

        secondaryPhone=new JLabel("Numéro de téléphone secondaire* :");
        formPanel.add(secondaryPhone);
        secondaryPhoneTextField=new JTextField();
        formPanel.add(secondaryPhoneTextField);

        isVeganCheckBox=new JCheckBox("Est vegan");
        formPanel.add(isVeganCheckBox);

        southpanel = new JPanel();
        southpanel.setLayout(new BorderLayout());


        birthdayPanel=new JPanel();
        birthdayPanel.setLayout(new GridLayout(1, 2, 2,2));
        birthday=new JLabel("Date de naissance :");
        birthdayPanel.add(birthday);


        comboBoxPanel=new JPanel();
        comboBoxPanel.setLayout(new GridLayout(3, 2, 2, 2));

        birthdayPanel.add(comboBoxPanel);

        daysValues = new Integer[31];
        int iValue=1;
        while(iValue <= daysValues.length){
            daysValues[iValue-1] = iValue;
            iValue++;
        }

        dayLabel=new JLabel("Jour : ");
        comboBoxPanel.add(dayLabel);

        dayCombox=new JComboBox(daysValues);
        comboBoxPanel.add(dayCombox);

        monthsValues = new Integer[12];
        iValue=1;
        while(iValue <= monthsValues.length){
            monthsValues[iValue-1] = iValue;
            iValue++;
        }
        monthLabel=new JLabel("Mois :");
        comboBoxPanel.add(monthLabel);

        monthCombox=new JComboBox(monthsValues);
        comboBoxPanel.add(monthCombox);

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
        comboBoxPanel.add(yearLabel);

        yearCombox=new JComboBox(yearsValues);
        comboBoxPanel.add(yearCombox);

        buttonPanel = new JPanel();

        buttonValidation = new JButton("Valider");
        buttonValidation.addActionListener(new ValidateButtonActionListener());
        buttonValidation.setPreferredSize(new Dimension(150, 25));
        buttonPanel.add(buttonValidation);

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
        add(southpanel, BorderLayout.SOUTH);
        southpanel.add(birthdayPanel, BorderLayout.CENTER);
        southpanel.add(buttonPanel, BorderLayout.SOUTH);


        countryComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Country selectedCountry = (Country) countryComboBox.getSelectedItem();
                    if (selectedCountry != null) {
                        try {
                            loadLocalitiesForCustomerCountry(selectedCountry.getIso());
                        } catch (GetAllLocalityWithCountryException ex) {
                            JOptionPane.showMessageDialog(mainWindows, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

    }

    public void setCustomerLocality(){
        for (int i = 0; i < cityPostalCode.getItemCount(); i++) {
            Locality locality = (Locality) cityPostalCode.getItemAt(i);
            if (Objects.equals(locality.getCity(), customer.getCity()) && Objects.equals(locality.getPostalCode(), customer.getPostalCode())) {
                cityPostalCode.setSelectedItem(locality);
                break;
            }
        }
    }

    public void setCustomerValue() throws GetAllLocalityWithCountryException {

        mailAddressTextField.setText(customer.getMailAdress());
        firstNameTextField.setText(customer.getFirstName());
        lastNameTextField.setText(customer.getLastName());
        streetTextField.setText(customer.getStreet());
        streetNumberTextField.setText(String.valueOf(customer.getStreetNumber()));
        phoneTextField.setText(customer.getPhone());

        int iDay = 0;
        int iMonth = 0;
        int iYear = 0;
        int customerBirthDay = customer.getBirthDate().getDayOfMonth();
        int customerBirthMonth = customer.getBirthDate().getMonthValue();
        int customerBirthYear = customer.getBirthDate().getYear();

        while (iDay < daysValues.length && daysValues[iDay] != customerBirthDay){
            iDay++;
        }

        while (iMonth < monthsValues.length && monthsValues[iMonth] != customerBirthMonth){
            iMonth++;
        }

        while (iYear < yearsValues.length && yearsValues[iYear] != customerBirthYear){
            iYear++;
        }

        dayCombox.setSelectedIndex(iDay);
        monthCombox.setSelectedIndex(iMonth);
        yearCombox.setSelectedIndex(iYear);

        if (customer.getSecondaryPhone() != null){
            secondaryPhoneTextField.setText(customer.getSecondaryPhone());
        }

        if (customer.getIsVegan()) {
            isVeganCheckBox.setSelected(true);
        }

        loadCountryForCustomer();
        loadLocalitiesForCustomerCountry(customer.getCountry());
        setCustomerLocality();
    }

    private void loadLocalitiesForCustomerCountry(String countryIso) throws GetAllLocalityWithCountryException {
        cityPostalCode.removeAllItems();
        ArrayList<Locality> localities = appControllers.getLocalityController().getAllLocalityWithCountry(countryIso);

        for (Locality locality : localities) {
            cityPostalCode.addItem(locality);
        }
    }

    public void loadCountryForCustomer(){
        for (int i = 0; i < countryComboBox.getItemCount(); i++) {
            Country country = (Country) countryComboBox.getItemAt(i);
            if (Objects.equals(country.getIso(), customer.getCountry())) {
                countryComboBox.setSelectedItem(country);
                break;
            }
        }
    }


    public void initializeComboBoxCityPostalCodeDefaultValues() throws GetAllLocalityWithCountryException {
        Country selectedCountry = (Country) countryComboBox.getSelectedItem();
        loadLocalitiesForCustomerCountry(selectedCountry.getIso());
    }

    public void validateForm(String mailAddress, String firstName, String lastName, String phone, String street, String streetNumber, int day, int month, int year, String secondaryPhone) throws CustomerCreationException {
        CustomerFormValidator.validateMailAdress(mailAddress);
        CustomerFormValidator.validateStringValue("prénom", firstName);
        CustomerFormValidator.validateStringValue("nom", lastName);
        CustomerFormValidator.validatePhone(phone);
        CustomerFormValidator.validateStringValue("rue", street);
        CustomerFormValidator.validateStreetNumber(streetNumber);
        CustomerFormValidator.validateBirthDateAdult(day, month, year);
        CustomerFormValidator.validateSecondaryPhone(secondaryPhone);
    }

    private class ValidateButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String mailAddress = mailAddressTextField.getText();
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String phone = phoneTextField.getText();
            String street = streetTextField.getText();
            String streetNumber = streetNumberTextField.getText();
            int iDay = dayCombox.getSelectedIndex();
            int iMonth = monthCombox.getSelectedIndex();
            int iYear = yearCombox.getSelectedIndex();
            boolean isVegan = isVeganCheckBox.isSelected();

            Locality selectedLocality = (Locality) cityPostalCode.getSelectedItem();

            String city = selectedLocality.getCity();
            String postalCode = selectedLocality.getPostalCode();
            Country country = (Country) countryComboBox.getSelectedItem();
            String iso = country.getIso();
            String secondaryPhone = secondaryPhoneTextField.getText();

            try {
                validateForm(mailAddress, firstName, lastName, phone, street, streetNumber, daysValues[iDay], monthsValues[iMonth], yearsValues[iYear], secondaryPhone);
                Customer customer = new Customer(mailAddress, firstName, lastName, phone, street, Integer.parseInt(streetNumber), daysValues[iDay], monthsValues[iMonth], yearsValues[iYear], isVegan, city, postalCode, iso, secondaryPhone);

                if (RegistrationForm.this.customer != null) {
                    appControllers.getCustomerController().updateCustomer(customer);
                } else {
                    if (appControllers.getCustomerController().customerExists(mailAddress)) {
                        throw new CustomerCreationException("Client déjà existant");
                    }
                    appControllers.getCustomerController().addCustomer(customer);
                }
                mainWindows.onRegistrationValidated();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainWindows, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}