package com.portfolio.davidreyes.booksapi.comment;

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
 * Entity representing a comment made by a user on a book.
 */
@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Comment {

    /**
     * Unique identifier for the comment.
     * Generated automatically using the IDENTITY strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The content of the comment provided by the user.
     */
    private String comment;

    /**
     * Many-to-one relationship between comments and users.
     * Maps each comment to a specific user.
     */
    @ManyToOne
    @JoinColumn(name = "user_id") // Maps the foreign key for the user
    private User user;

    /**
     * Many-to-one relationship between comments and books.
     * Maps each comment to a specific book.
     */
    @ManyToOne
    @JoinColumn(name = "book_id") // Maps the foreign key for the book
    private Books book;

    /**
     * The date and time the comment was made.
     * Default value is the current date and time.
     */
    private LocalDateTime dateCommented = LocalDateTime.now();
}
