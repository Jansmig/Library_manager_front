package com.manager.front2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OriginDto {

    private long id;
    private String title;
    private String author;
    private int publishedYear;
    private String isbn;
    private double rating;

    public OriginDto(long id, String title, String author, int publishedYear, String isbn, double rating) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.isbn = isbn;
        this.rating = rating;
    }

    public OriginDto() {
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public double getRating(){
        return rating;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
