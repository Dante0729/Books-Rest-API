package com.portfolio.davidreyes.booksapi.creditcard;

import com.portfolio.davidreyes.booksapi.user.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a credit card associated with a user.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CreditCard {

    /**
     * Unique identifier for the credit card.
     * Generated automatically using the IDENTITY strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The credit card number.
     * Stored as a String to preserve leading zeros and formatting.
     */
    private String cardNumber;

    /**
     * The expiration date of the credit card (e.g., MM/YY).
     */
    private String expirationDate;

    /**
     * The CVV (Card Verification Value) of the credit card.
     */
    private String cvv;

    /**
     * Many-to-one relationship between credit cards and users.
     * Each credit card is associated with a specific user.
     */
    @ManyToOne
    @JoinColumn(name = "user_id") // Foreign key mapping to the user table
    private User user;
}
