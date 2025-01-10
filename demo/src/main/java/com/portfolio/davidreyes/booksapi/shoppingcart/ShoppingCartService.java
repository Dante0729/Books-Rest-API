package com.portfolio.davidreyes.booksapi.shoppingcart;

import com.portfolio.davidreyes.booksapi.books.Books;
import com.portfolio.davidreyes.booksapi.books.BooksRepository;
import com.portfolio.davidreyes.booksapi.user.User;
import com.portfolio.davidreyes.booksapi.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for managing shopping cart operations.
 * Handles business logic for adding, removing, and retrieving books from a user's shopping cart.
 */
@Service
public class ShoppingCartService {

    private final UserRepository userRepository;
    private final BooksRepository booksRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    /**
     * Constructor to inject dependencies.
     *
     * @param userRepository         Repository for managing users.
     * @param booksRepository        Repository for managing books.
     * @param shoppingCartRepository Repository for managing shopping carts.
     */
    @Autowired
    public ShoppingCartService(UserRepository userRepository, BooksRepository booksRepository, ShoppingCartRepository shoppingCartRepository) {
        this.userRepository = userRepository;
        this.booksRepository = booksRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    /**
     * Adds a book to the user's shopping cart.
     *
     * @param userId ID of the user.
     * @param bookId ID of the book to add.
     */
    @Transactional
    public void addBookToUserShoppingCart(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));

        Books book = booksRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book with ID " + bookId + " not found"));


        user.addBookToShoppingCart(book);


        userRepository.save(user);
    }

    /**
     * Calculates the subtotal of all books in the user's shopping cart.
     *
     * @param userId ID of the user.
     * @return The total price of all books in the shopping cart.
     */
    public double calculateSubtotalForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));

        // Calculate the total price of all books in the user's shopping cart
        return user.getShoppingCart().getBooks().stream()
                .mapToDouble(Books::getPrice)
                .sum();
    }

    /**
     * Retrieves all books in the user's shopping cart.
     *
     * @param userId ID of the user.
     * @return A list of books in the user's shopping cart.
     * @throws IllegalStateException if the shopping cart is empty or does not exist.
     */
    public List<Books> getBooksByShoppingCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with ID " + userId + " does not exist"));

        ShoppingCart shoppingCart = user.getShoppingCart();
        if (shoppingCart == null || shoppingCart.getBooks().isEmpty()) {
            throw new IllegalStateException("This shopping cart is empty or does not exist");
        }

        return new ArrayList<>(shoppingCart.getBooks());
    }

    /**
     * Deletes a book from the user's shopping cart.
     *
     * @param userId ID of the user.
     * @param bookId ID of the book to remove.
     */
    @Transactional
    public void deleteBookFromUserShoppingCart(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));

        Books book = booksRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book with ID " + bookId + " not found"));


        user.deleteBookFromShoppingCart(book);


        userRepository.save(user);
    }
}
