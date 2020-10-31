package entities;

import consts.Genre;
import entities.identity.IEntity;

import javax.persistence.*;

@Entity
@Table
public class Book implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Genre genre;

    @Column(nullable = false)
    private double integrity;

    public Book() {
        this.integrity = 100;
    }

    public Book(String title, String author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.integrity = 100;
    }

    public String getId() {
        return id;
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
