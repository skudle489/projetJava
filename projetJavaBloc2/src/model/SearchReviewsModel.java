package model;

import exceptions.SearchReviewModelException;

public class SearchReviewsModel {
    private String reviewComment;
    private String hotelName;
    private int hotelStars;
    private String customerFirstName;
    private String customerLastName;

    public SearchReviewsModel(String reviewComment, String hotelName,  int hotelStars, String customerFirstName, String customerLastName) throws SearchReviewModelException {
        this.reviewComment = reviewComment;
        this.hotelName = hotelName;
        this.hotelStars = hotelStars;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
    }


    public String getReviewComment() {
        return reviewComment;
    }

    public String getHotelName() {
        return hotelName;
    }

    public int getHotelStars() {
        return hotelStars;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }



}

