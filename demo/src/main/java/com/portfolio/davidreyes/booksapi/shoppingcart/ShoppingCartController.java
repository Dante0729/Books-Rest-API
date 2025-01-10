package com.portfolio.davidreyes.booksapi.shoppingcart;

import com.portfolio.davidreyes.booksapi.books.Books;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing shopping cart operations.
 * Provides endpoints to add, remove, and retrieve books in a user's shopping cart.
 */
@RestController
@RequestMapping(path = "api/v1/shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    /**
     * Constructor to inject the ShoppingCartService.
     *
     * @param shoppingCartService Service handling the business logic for shopping carts.
     */
    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    /**
     * Adds a book to the user's shopping cart.
     *
     * @param userId ID of the user.
     * @param bookId ID of the book to add.
     * @return ResponseEntity indicating success or failure of the operation.
     */
    @PostMapping("/add-to-cart")
    public ResponseEntity<Void> addBookToCart(@RequestParam Long userId, @RequestParam Long bookId) {
        shoppingCartService.addBookToUserShoppingCart(userId, bookId); // Delegates logic to the service layer
        return ResponseEntity.ok().build();
    }

    /**
     * Calculates the subtotal of all books in the user's shopping cart.
     *
     * @param userId ID of the user.
     * @return ResponseEntity containing the subtotal amount.
     */
    @GetMapping("/subtotal/{userId}")
    public ResponseEntity<Double> getCartSubtotal(@PathVariable Long userId) {
        double subtotal = shoppingCartService.calculateSubtotalForUser(userId);
        return ResponseEntity.ok(subtotal);
    }

    /**
     * Retrieves all books in the user's shopping cart.
     *
     * @param userId ID of the user.
     * @return ResponseEntity containing the list of books in the cart or an appropriate HTTP status.
     */
    @GetMapping("/books-byshoppingcart/{userId}")
    public ResponseEntity<List<Books>> getBooksInShoppingCart(@PathVariable Long userId) {
        try {
            List<Books> books = shoppingCartService.getBooksByShoppingCart(userId);
            if (books.isEmpty()) {
                return ResponseEntity.noContent().build(); // Returns 204 No Content if the cart is empty
            }
            return ResponseEntity.ok(books);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null); // Returns 400 Bad Request in case of an error
        }
    }

    /**
     * Deletes a book from the user's shopping cart.
     *
     * @param userId ID of the user.
     * @param bookId ID of the book to remove.
     * @return ResponseEntity indicating success or failure of the operation.
     */
    @PostMapping("/delete-from-cart")
    public ResponseEntity<Void> deleteBookFromCart(@RequestParam Long userId, @RequestParam Long bookId) {
        shoppingCartService.deleteBookFromUserShoppingCart(userId, bookId); // Delegates logic to the service layer
        return ResponseEntity.ok().build();
    }
}
