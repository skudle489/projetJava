package model;

import java.time.LocalDate;


public class Book {
    private String isbn;
    private Integer pages;
    private String title;
    private LocalDate editionDate;
    private Boolean forAdult;

    public Book(String isbn, Integer pages, String title, LocalDate editionDate, Boolean forAdult) {
        this.isbn = isbn;
        this.pages = pages;
        this.title = title;
        this.editionDate = editionDate;
        this.forAdult = forAdult;
    }

    public Book(String isbn) {
        this(isbn, null, null, null, null);
    }


    public String getIsbn() {
        return isbn;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Integer getPages() {
        return pages;
    }

    public LocalDate getEditionDate() {
        return editionDate;
    }

    public void setEditionDate(LocalDate editionDate) {
        this.editionDate = editionDate;
    }

    public void setForAdult(Boolean forAdult){
        this.forAdult = forAdult;
    }

    public String toString(){
        return "isbn : " + isbn + ", pages : " + pages + ", title : " + title + ", editionDate : " + editionDate + ", for adult : " + forAdult;
    }





}
