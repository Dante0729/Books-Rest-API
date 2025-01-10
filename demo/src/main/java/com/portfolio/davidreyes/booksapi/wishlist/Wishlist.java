package com.portfolio.davidreyes.booksapi.wishlist;

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
 * Entity representing a wishlist created by a user.
 * A wishlist can contain multiple books and is associated with a specific user.
 */
@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Wishlist {

    /**
     * Unique identifier for the wishlist.
     * Generated automatically using the IDENTITY strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the wishlist.
     * Must be unique.
     */
    @Column(unique = true)
    private String name;

    /**
     * Many-to-One relationship with the User entity.
     * A wishlist is associated with a specific user.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // Foreign key in the Wishlist table referencing the User table.
    private User user;

    /**
     * Many-to-Many relationship with the Books entity.
     * A wishlist can contain multiple books, and a book can belong to multiple wishlists.
     */
    @ManyToMany
    @JoinTable(
            name = "wishlist_books", // Name of the join table.
            joinColumns = @JoinColumn(name = "wishlist_id"), // Foreign key referencing the Wishlist.
            inverseJoinColumns = @JoinColumn(name = "book_id") // Foreign key referencing the Books.
    )
    private Set<Books> books = new HashSet<>();


}
