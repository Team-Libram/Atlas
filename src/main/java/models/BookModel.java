package models;

import consts.Genre;

public class BookModel {
    private String id;
    private String title;
    private String author;
    private Genre genre;
    private double integrity;

    public BookModel(String id, String title, String author, Genre genre, double integrity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.integrity = integrity;
    }

    public BookModel(String id, String title, String author, Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.integrity = 100;
    }

    public BookModel(String title, String author, Genre genre, double integrity) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.integrity = integrity;
    }

    public BookModel(String title, String author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.integrity = 100;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public double getIntegrity() {
        return integrity;
    }

    public void setIntegrity(double integrity) {
        this.integrity = integrity;
    }
}