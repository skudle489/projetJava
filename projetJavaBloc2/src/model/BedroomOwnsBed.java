package model;

public class BedroomOwnsBed {
    private int bedroom;
    private String bed;
    private int hotel;

    public BedroomOwnsBed(int bedroom, String bed, int hotel) {
        this.bedroom = bedroom;
        this.bed = bed;
        this.hotel = hotel;
    }

    public int getBedroom() {
        return bedroom;
    }

    public String getBed() {
        return bed;
    }
}
