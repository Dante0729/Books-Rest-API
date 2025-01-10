package com.portfolio.davidreyes.booksapi.books;

import com.portfolio.davidreyes.booksapi.author.Author;
import com.portfolio.davidreyes.booksapi.comment.Comment;
import com.portfolio.davidreyes.booksapi.rating.Rating;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a book in the system.
 *
 * A book is associated with an author and can have multiple ratings and comments.
 * It contains metadata such as ISBN, name, description, genre, publisher, and sales data.
 *
 * Annotations:
 * - @Entity: Marks this class as a JPA entity.
 * - @Table(name = "books"): Specifies the table name in the database.
 * - @JsonIdentityInfo: Prevents infinite recursion when serializing relationships.
 * - @JsonIgnoreProperties: Ignores Hibernate-specific properties during serialization.
 */
@Entity
@Table(name = "books")
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Books {

    /**
     * The unique identifier for the book.
     * Uses a sequence generator for ID generation.
     */
    @Id
    @SequenceGenerator(name = "book_sequence", sequenceName = "book_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    private Long id;

    /**
     * The ISBN (International Standard Book Number) of the book.
     * Must be unique for each book.
     */
    @Column(unique = true)
    private Long isbn;

    /**
     * The name of the book.
     */
    private String bookName;

    /**
     * A short description of the book.
     */
    private String bookDescription;

    /**
     * The price of the book in the store.
     */
    private Integer price;

    /**
     * The author of the book.
     * Establishes a many-to-one relationship with the Author entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    /**
     * The genre of the book (e.g., Fiction, Non-Fiction, Science Fiction).
     */
    private String genre;

    /**
     * The publisher of the book.
     */
    private String publisher;

    /**
     * The year the book was published.
     */
    private Integer yearPublished;

    /**
     * The total number of copies sold for the book.
     */
    private Integer copiesSold;

    /**
     * The average rating of the book.
     */
    @Column(name = "rating")
    private double rating;

    /**
     * List of individual ratings given to the book.
     * Establishes a one-to-many relationship with the Rating entity.
     * CascadeType.ALL ensures that related ratings are persisted or removed with the book.
     */
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

    /**
     * List of comments associated with the book.
     * Establishes a one-to-many relationship with the Comment entity.
     * CascadeType.ALL ensures that related comments are persisted or removed with the book.
     */
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    /**
     * Constructs a new Book with the given details.
     *
     * @param isbn           The ISBN of the book.
     * @param bookName       The name of the book.
     * @param bookDescription A short description of the book.
     * @param price          The price of the book.
     * @param author         The author of the book.
     * @param genre          The genre of the book.
     * @param publisher      The publisher of the book.
     * @param yearPublished  The year the book was published.
     * @param copiesSold     The total number of copies sold.
     */
    public Books(Long isbn, String bookName, String bookDescription, Integer price, Author author, String genre, String publisher, Integer yearPublished, Integer copiesSold) {
        this.isbn = isbn;
        this.bookName = bookName;
        this.bookDescription = bookDescription;
        this.price = price;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.yearPublished = yearPublished;
        this.copiesSold = copiesSold;
    }
}
