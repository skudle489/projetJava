package model;

import exceptions.ReviewCreationException;

import java.time.LocalDate;

public class Review {
    private String comment;
    private Hotel hotel;
    private String title;
    private boolean isAnonymous;
    private Star star;
    private Customer customer;
    private LocalDate lastVisitDateHotelCountry;
    private final LocalDate creationDate;


    public Review(String comment, Hotel hotel, String title, boolean isAnonymous, Star star, Customer customer, LocalDate creationDate, LocalDate lastVisitDateHotelCountry) throws ReviewCreationException {
        setComment(comment);
        this.hotel = hotel;
        setTitle(title);
        this.isAnonymous = isAnonymous;
        this.star = star;
        this.customer = customer;
        this.creationDate = creationDate;
        this.lastVisitDateHotelCountry = lastVisitDateHotelCountry;
    }

    public void setComment(String comment) throws ReviewCreationException {
        if (comment == null){
            this.comment = null;
        } else {
            if (comment.isEmpty()){
                throw new ReviewCreationException("Le commentaire ne peut etre vide");
            } else {
                this.comment = comment;
            }
        }
    }

    public void setTitle(String title) throws ReviewCreationException {
        if (title == null){
            this.title = null;
        } else {
            if (title.isEmpty()){
                throw new ReviewCreationException("Le titre ne peut etre vide");
            } else {
                this.title = title;
            }
        }
    }

}
