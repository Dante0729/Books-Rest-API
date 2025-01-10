package com.portfolio.davidreyes.booksapi.shoppingcart;

import com.portfolio.davidreyes.booksapi.books.Books;
import com.portfolio.davidreyes.booksapi.books.BooksRepository;
import com.portfolio.davidreyes.booksapi.user.User;
import com.portfolio.davidreyes.booksapi.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ShoppingCartService.
 */
@SpringBootTest
public class ShoppingCartServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BooksRepository booksRepository;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    private User user;
    private Books book;

    /**
     * Sets up mock objects and initializes data for testing.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a mock User
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        // Initialize the User's ShoppingCart
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setBooks(new HashSet<>());

        user.setShoppingCart(shoppingCart);

        // Initialize a mock Book
        book = new Books();
        book.setId(1L);
        book.setPrice(100); // Set a price for testing calculations

        // Mock repository behaviors
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(booksRepository.findById(1L)).thenReturn(Optional.of(book));
    }

    /**
     * Tests that a book is successfully added to the user's shopping cart.
     */
    @Test
    void addBookToUserShoppingCartShouldAddBookSuccessfully() {
        // Act
        shoppingCartService.addBookToUserShoppingCart(1L, 1L);

        // Assert
        verify(userRepository, times(1)).save(user);
        assertEquals(1, user.getShoppingCart().getBooks().size(), "The book should be added to the shopping cart");
    }

    /**
     * Tests that the subtotal calculation for the user's shopping cart is correct.
     */
    @Test
    void calculateSubtotalForUserShouldReturnCorrectSubtotal() {
        // Add a book to the shopping cart
        user.getShoppingCart().getBooks().add(book);

        // Act
        double subtotal = shoppingCartService.calculateSubtotalForUser(1L);

        // Assert
        assertEquals(100.0, subtotal, "Subtotal should match the sum of book prices in the cart.");
    }

    /**
     * Tests that the books in the shopping cart are retrieved correctly.
     */
    @Test
    void getBooksByShoppingCartShouldReturnBooksList() {
        // Add a book to the shopping cart
        user.getShoppingCart().getBooks().add(book);

        // Act
        List<Books> books = shoppingCartService.getBooksByShoppingCart(1L);

        // Assert
        assertEquals(1, books.size(), "The shopping cart should contain one book.");
        assertEquals(book, books.get(0), "The retrieved book should match the one in the cart.");
    }

    /**
     * Tests that a book is successfully removed from the user's shopping cart.
     */
    @Test
    void deleteBookFromUserShoppingCartShouldRemoveBookSuccessfully() {
        // Add a book to the shopping cart
        user.getShoppingCart().getBooks().add(book);

        // Act
        shoppingCartService.deleteBookFromUserShoppingCart(1L, 1L);

        // Assert
        verify(userRepository, times(1)).save(user);
        assertEquals(0, user.getShoppingCart().getBooks().size(), "The book should be removed from the shopping cart.");
    }

    /**
     * Tests that an exception is thrown when a user is not found.
     */
    @Test
    void addBookToUserShoppingCartShouldThrowWhenUserNotFound() {
        // Arrange
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> shoppingCartService.addBookToUserShoppingCart(2L, 1L));
    }

    /**
     * Tests that an exception is thrown when a book is not found.
     */
    @Test
    void addBookToUserShoppingCartShouldThrowWhenBookNotFound() {
        // Arrange
        when(booksRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> shoppingCartService.addBookToUserShoppingCart(1L, 2L));
    }
}
