package model;

import exceptions.ReviewCreationException;

import java.time.LocalDate;

public class Review {
    private String comment;
    private int hotel;
    private String title;
    private boolean isAnonymous;
    private int star;
    private String customer;
    private LocalDate lastVisitDateHotelCountry;
    private final LocalDate creationDate;


    public Review(String comment, int hotel, String title, boolean isAnonymous, int star, String customer, LocalDate creationDate, LocalDate lastVisitDateHotelCountry) throws ReviewCreationException {
        setComment(comment);
        this.hotel = hotel;
        setTitle(title);
        this.isAnonymous = isAnonymous;
        this.star = star;
        this.customer = customer;
        this.creationDate = creationDate;
        setLastVisitDateHotelCountry(lastVisitDateHotelCountry);
    }

    public Review(int hotel, Boolean isAnonymous, int star, String customer, LocalDate creationDate) throws ReviewCreationException {
        this(null, hotel, null, isAnonymous, star, customer, creationDate, null);
    }

    public void setComment(String comment) throws ReviewCreationException {
        if (comment == null || comment.isEmpty()){
            this.comment = null;
        } else {
            this.comment = comment;
        }
    }

    public void setTitle(String title) throws ReviewCreationException {
        if (title == null || title.isEmpty()){
            this.title = null;
        } else {
            this.title = title;
        }
    }

    public void setLastVisitDateHotelCountry(LocalDate lastVisitDateHotelCountry) throws ReviewCreationException {
        if (lastVisitDateHotelCountry == null){
            this.lastVisitDateHotelCountry = null;
        } else {
            if (lastVisitDateHotelCountry.isAfter(LocalDate.now())){
                throw new ReviewCreationException("La date de dernière visite du pays de l'hotel doit etre dans le passé");
            } else {
                this.lastVisitDateHotelCountry = lastVisitDateHotelCountry;
            }
        }
    }




    public String getComment() {
        return comment;
    }

    public int getHotel() {
        return hotel;
    }

    public String getTitle() {
        return title;
    }

    public boolean getIsAnonymous() {
        return isAnonymous;
    }

    public int getStar() {
        return star;
    }

    public String getCustomer() {
        return customer;
    }

    public LocalDate getLastVisitDateHotelCountry() {
        return lastVisitDateHotelCountry;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String toString() {
        String description = "";
        if (title == null || title.isEmpty()){
            description += star;
        } else {
            description += title + " | " + star;
        }
        description += " étoile(s) crée le " + creationDate;
        return description;
    }


}
