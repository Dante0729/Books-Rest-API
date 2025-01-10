package com.portfolio.davidreyes.booksapi.user;

import com.portfolio.davidreyes.booksapi.books.Books;
import com.portfolio.davidreyes.booksapi.creditcard.CreditCard;
import com.portfolio.davidreyes.booksapi.shoppingcart.ShoppingCart;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a user in the system.
 * Users have a shopping cart, credentials, and associated credit cards.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    /**
     * Unique identifier for the user.
     * Generated automatically using the IDENTITY strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * One-to-one relationship with the ShoppingCart entity.
     * A user has one shopping cart, and it is cascaded for persistence.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ShoppingCart shoppingCart;

    /**
     * The user's unique username.
     * Must be non-null and unique across all users.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * The user's password.
     * Must be non-null.
     */
    @Column(nullable = false)
    private String password;

    /**
     * The user's name.
     */
    private String name;

    /**
     * The user's email address.
     */
    private String emailAddress;

    /**
     * The user's home address.
     */
    private String homeAddress;

    /**
     * One-to-many relationship with the CreditCard entity.
     * A user can have multiple credit cards, and their removal is cascaded.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CreditCard> creditCards = new HashSet<>();

    /**
     * Adds a book to the user's shopping cart.
     * If the shopping cart is null, it creates a new one.
     *
     * @param book The book to add to the shopping cart.
     */
    public void addBookToShoppingCart(Books book) {
        if (this.shoppingCart == null) {
            this.shoppingCart = new ShoppingCart();
            this.shoppingCart.setUser(this);
        }
        this.shoppingCart.addBook(book);
    }

    /**
     * Removes a book from the user's shopping cart.
     *
     * @param book The book to remove from the shopping cart.
     */
    public void deleteBookFromShoppingCart(Books book) {
        if (this.shoppingCart != null) {
            this.shoppingCart.deleteBook(book);
        }
    }
}
