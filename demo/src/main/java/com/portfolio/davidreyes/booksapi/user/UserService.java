package com.portfolio.davidreyes.booksapi.user;

import com.portfolio.davidreyes.booksapi.books.BooksRepository;
import com.portfolio.davidreyes.booksapi.creditcard.CreditCard;
import com.portfolio.davidreyes.booksapi.creditcard.CreditCardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service layer for managing user-related operations.
 * Handles business logic for user creation, updates, and credit card management.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BooksRepository booksRepository; // Repository for managing books
    private final CreditCardRepository creditCardRepository; // Repository for managing credit cards

    /**
     * Constructor to inject dependencies.
     *
     * @param userRepository    Repository for managing users.
     * @param booksRepository   Repository for managing books.
     */
    @Autowired
    public UserService(UserRepository userRepository, BooksRepository booksRepository, CreditCardRepository creditCardRepository) {
        this.userRepository = userRepository;
        this.booksRepository = booksRepository;
        this.creditCardRepository = creditCardRepository;
    }

    /**
     * Creates a new user and saves it to the database.
     *
     * @param user The user to create.
     */
    public void createUser(User user) {
        userRepository.save(user);
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username The username to search for.
     * @return The user associated with the given username.
     * @throws RuntimeException if the user is not found.
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    /**
     * Updates user details such as username, password, name, and home address.
     *
     * @param currentUsername The current username of the user.
     * @param newUsername     The new username, if applicable.
     * @param newPassword     The new password, if applicable.
     * @param newName         The new name, if applicable.
     * @param newHomeAddress  The new home address, if applicable.
     * @throws IllegalStateException if the new username is already taken or the current user does not exist.
     */
    @Transactional
    public void updateUserDetails(String currentUsername, String newUsername, String newPassword, String newName, String newHomeAddress) {
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new IllegalStateException("User with username: " + currentUsername + " does not exist"));

        // Update username if provided and different
        if (newUsername != null && !newUsername.isEmpty() && !newUsername.equals(user.getUsername())) {
            userRepository.findByUsername(newUsername).ifPresent(u -> {
                throw new IllegalStateException("Username " + newUsername + " is already taken.");
            });
            user.setUsername(newUsername);
        }

        // Update password if provided
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(newPassword);
        }

        // Update name if provided
        if (newName != null && !newName.isEmpty()) {
            user.setName(newName);
        }

        // Update home address if provided
        if (newHomeAddress != null && !newHomeAddress.isEmpty()) {
            user.setHomeAddress(newHomeAddress);
        }

        userRepository.save(user);
    }

    /**
     * Adds a credit card to a user's profile.
     *
     * @param username   The username of the user.
     * @param creditCard The credit card to add.
     * @throws RuntimeException if the user is not found.
     */
    public void addCreditCardToUser(String username, CreditCard creditCard) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        creditCard.setUser(user);
        creditCardRepository.save(creditCard);
    }


}
