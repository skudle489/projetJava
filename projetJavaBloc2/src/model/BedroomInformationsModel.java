package model;

public class BedroomInformationsModel {
    private int bedroomNumber;
    private double costPerDay;
    private String hotelName;
    private int streetNumber;
    private String street;
    private String city;
    private String countryName;

    public BedroomInformationsModel(int bedroomNumber, double costPerDay, String hotelName, int streetNumber, String street, String city, String countryName) {
        this.bedroomNumber = bedroomNumber;
        this.costPerDay = costPerDay;
        this.hotelName = hotelName;
        this.streetNumber = streetNumber;
        this.street = street;
        this.city = city;
        this.countryName = countryName;
    }


    public int getBedroomNumber() {
        return bedroomNumber;
    }

    public double getCostPerDay() {
        return costPerDay;
    }

    public String getHotelName() {
        return hotelName;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getCountryName() {
        return countryName;
    }

}