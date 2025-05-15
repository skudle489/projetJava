package userInterface;

import exceptions.*;
import model.Country;
import model.Customer;
import model.Locality;
import utils.CustomerFormValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Objects;


public class RegistrationForm extends JPanel {


    private JPanel formPanel;
    private JPanel birthdayPanel;
    private JPanel comboBoxPanel;
    private JCheckBox isVeganCheckBox;
    private JLabel mailAddressLabel, firstNameLabel, lastName, street, streetNumber, birthday, phone, secondaryPhone, dayLabel, monthLabel, yearLabel, cityLabel, postalCode, country ;
    private JTextField mailAddressTextField, firstNameTextField, lastNameTextField, streetTextField, streetNumberTextField, postalCodeTextField, phoneTextField, secondaryPhoneTextField, cityTextField;
    private JButton buttonValidation;
    private MainWindows registrationListener;

    private JComboBox dayCombox;
    private JComboBox monthCombox;
    private JComboBox yearCombox;
    private JComboBox cityPostalCode;

    private JComboBox countryComboBox;

    Integer[] daysValues;
    Integer[] monthsValues;
    Integer[] yearsValues;

    Customer customer;


    private ArrayList<Country> countries;

    public RegistrationForm(ArrayList<Country> countries, Customer customer) {
        this.customer = customer;

        this.countries = countries;

        setUpUI();

        if (customer != null) {
            setCustomerValue();
        }

    }



    public void setUpUI(){
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(10, 2, 5, 5));


        mailAddressLabel=new JLabel("Adresse mail :");
        formPanel.add(mailAddressLabel);
        mailAddressTextField = new JTextField();
        formPanel.add(mailAddressTextField);

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




        //cityTextField=new JTextField();
        //formPanel.add(cityTextField);

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

        secondaryPhone=new JLabel("Numéro de téléphone secondaire :");
        formPanel.add(secondaryPhone);
        secondaryPhoneTextField=new JTextField();
        formPanel.add(secondaryPhoneTextField);

        isVeganCheckBox=new JCheckBox("Est vegan");
        formPanel.add(isVeganCheckBox);

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

        buttonValidation = new JButton("Valider");
        buttonValidation.addActionListener(new ValidateButtonActionListener());

        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
        add(birthdayPanel, BorderLayout.SOUTH);
        add(buttonValidation, BorderLayout.EAST);


        countryComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Country selectedCountry = (Country) countryComboBox.getSelectedItem();
                    if (selectedCountry != null) {
                        loadLocalitiesForCountry(selectedCountry.getIso());
                    }
                }
            }
        });

    }


    private void loadLocalitiesForCountry(String countryIso) {
        try {

            cityPostalCode.removeAllItems();
            ArrayList<Locality> localities = registrationListener.getLocalityController().getAllLocalityWithCountry(countryIso);

            for (Locality locality : localities) {
                cityPostalCode.addItem(locality);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Impossible de charger les villes : " + e.getMessage());
        }
    }









    public void setCustomerValue(){

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

    }


    public void setRegistrationListener(MainWindows listener) {
        this.registrationListener = listener;

        if (customer != null) {

            for (int i = 0; i < countryComboBox.getItemCount(); i++) {
                Country country = (Country) countryComboBox.getItemAt(i);
                if (Objects.equals(country.getIso(), customer.getCountry())) {
                    countryComboBox.setSelectedItem(country);
                    break;
                }
            }


            loadLocalitiesForCountry(customer.getCountry());


            for (int i = 0; i < cityPostalCode.getItemCount(); i++) {
                Locality locality = (Locality) cityPostalCode.getItemAt(i);
                if (Objects.equals(locality.getCity(), customer.getCity()) && Objects.equals(locality.getPostalCode(), customer.getPostalCode())) {
                    cityPostalCode.setSelectedItem(locality);
                    break;
                }
            }

        } else {
            initializeComboBoxCityPostalCodeDefaultValues();
        }
    }

    public void initializeComboBoxCityPostalCodeDefaultValues() {
        Country selectedCountry = (Country) countryComboBox.getSelectedItem();
        loadLocalitiesForCountry(selectedCountry.getIso());
    }

    private class ValidateButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (registrationListener != null) {

                String mailAdress = mailAddressTextField.getText();
                String firstName = firstNameTextField.getText();
                String lastName = lastNameTextField.getText();
                String phone = phoneTextField.getText();
                String street = streetTextField.getText();
                String streetNumber = streetNumberTextField.getText();
                int iDay = dayCombox.getSelectedIndex();
                int iMonth = monthCombox.getSelectedIndex();
                int iYear = yearCombox.getSelectedIndex();
                boolean isVegan = isVeganCheckBox.isSelected();

                Country selectedCountry = (Country) countryComboBox.getSelectedItem();
                Locality selectedLocality = (Locality) cityPostalCode.getSelectedItem();

                String city = selectedLocality.getCity();
                String postalCode = selectedLocality.getPostalCode();
                Country country = (Country) countryComboBox.getSelectedItem();
                String iso = country.getIso();
                String secondaryPhone = secondaryPhoneTextField.getText();

                try {
                    CustomerFormValidator.validateMailAdress(mailAdress);


                    CustomerFormValidator.validateStringValue("prénom", firstName);
                    CustomerFormValidator.validateStringValue("nom", lastName);
                    CustomerFormValidator.validatePhone(phone);
                    CustomerFormValidator.validateStringValue("rue", street);
                    CustomerFormValidator.validateStreetNumber(streetNumber);
                    CustomerFormValidator.validateBirthDateAdult(daysValues[iDay], monthsValues[iMonth], yearsValues[iYear]);
                    CustomerFormValidator.validateSecondaryPhone(secondaryPhone);

                    Customer customer = new Customer(mailAdress, firstName, lastName, phone, street, Integer.parseInt(streetNumber), daysValues[iDay], monthsValues[iMonth], yearsValues[iYear], isVegan, city, postalCode, iso, secondaryPhone);

                    if (RegistrationForm.this.customer != null) {
                        registrationListener.updateCustomer(customer);
                    } else {
                        if (registrationListener.customerExists(mailAdress)) {
                            throw new GetCustomerException("Client déjà existant");
                        }
                        registrationListener.addCustomer(customer);
                    }


                    registrationListener.onRegistrationValidated();
                } catch (GetCustomerException | UpdateCustomerException | CustomerCreationException |
                         AddCustomerException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        }
    }
}
