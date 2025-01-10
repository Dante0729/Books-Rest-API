package com.portfolio.davidreyes.booksapi.shoppingcart;

import com.portfolio.davidreyes.booksapi.books.Books;
import com.portfolio.davidreyes.booksapi.user.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a shopping cart associated with a user.
 * Contains a list of books that the user intends to purchase.
 */
@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ShoppingCart {

    /**
     * Unique identifier for the shopping cart.
     * Generated automatically using the AUTO strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * One-to-one relationship with the User entity.
     * Each user has one shopping cart.
     */
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * Many-to-many relationship with the Books entity.
     * Represents the books added to the shopping cart.
     * Uses a join table "cart_books" to map the relationship.
     */
    @ManyToMany
    @JoinTable(
            name = "cart_books", // Name of the join table
            joinColumns = @JoinColumn(name = "cart_id"), // Foreign key for the shopping cart
            inverseJoinColumns = @JoinColumn(name = "book_id") // Foreign key for the books
    )
    private Set<Books> books = new HashSet<>();

    /**
     * Adds a book to the shopping cart.
     *
     * @param book The book to add.
     */
    public void addBook(Books book) {
        this.books.add(book);
    }

    /**
     * Removes a book from the shopping cart.
     *
     * @param book The book to remove.
     */
    public void deleteBook(Books book) {
        this.books.remove(book);
    }
}
