package com.portfolio.davidreyes.booksapi.rating;

import com.portfolio.davidreyes.booksapi.books.Books;
import com.portfolio.davidreyes.booksapi.user.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entity representing a rating given by a user to a book.
 */
@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Rating {

    /**
     * Unique identifier for the rating.
     * Generated automatically using the IDENTITY strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The rating score given by the user.
     * Typically ranges from 1 to 5.
     */
    private int rating;

    /**
     * Many-to-one relationship between ratings and users.
     * Each rating is associated with a specific user.
     */
    @ManyToOne
    @JoinColumn(name = "user_id") // Maps the foreign key for the user
    private User user;

    /**
     * Many-to-one relationship between ratings and books.
     * Each rating is associated with a specific book.
     */
    @ManyToOne
    @JoinColumn(name = "book_id") // Maps the foreign key for the book
    private Books book;

    /**
     * The date and time when the rating was created.
     * Defaults to the current date and time.
     */
    private LocalDateTime dateRated = LocalDateTime.now();

    /**
     * Constructor with parameters for testing or manual instantiation.
     *
     * @param mockBook The book being rated.
     * @param mockUser The user giving the rating.
     * @param rating   The rating score.
     */
    public Rating(Books mockBook, User mockUser, int rating) {
        this.book = mockBook;
        this.user = mockUser;
        this.rating = rating;
        this.dateRated = LocalDateTime.now();
    }

    /**
     * Default no-argument constructor required by JPA.
     */
    public Rating() {
    }

    /**
     * Getter for the rating value.
     *
     * @return The rating score.
     */
    public int getRating() {
        return this.rating;
    }
}
