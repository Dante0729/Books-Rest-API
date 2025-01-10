package com.portfolio.davidreyes.booksapi.wishlist;

import com.portfolio.davidreyes.booksapi.books.Books;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing wishlists.
 * Provides endpoints to create wishlists, add books, retrieve books, and move books to the shopping cart.
 */
@RestController
@RequestMapping("/api/v1/wishlists")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    /**
     * Creates a new wishlist for a specific user.
     *
     * @param wishlistRequest The request object containing the wishlist name and user ID.
     * @return ResponseEntity indicating the success of the operation.
     */
    @PostMapping
    public ResponseEntity<Void> createWishlist(@RequestBody WishlistRequest wishlistRequest) {
        wishlistService.createWishlist(wishlistRequest.getName(), wishlistRequest.getUserId());
        return ResponseEntity.ok().build();
    }

    /**
     * Adds a book to an existing wishlist.
     *
     * @param wishlistId The ID of the wishlist.
     * @param bookId     The ID of the book to add.
     * @return ResponseEntity indicating the success of the operation.
     */
    @PostMapping("/{wishlistId}/add-book")
    public ResponseEntity<Void> addBookToWishlist(@PathVariable Long wishlistId, @RequestParam Long bookId) {
        wishlistService.addBookToWishlist(wishlistId, bookId);
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves all books in a specified wishlist.
     *
     * @param wishlistId The ID of the wishlist.
     * @return ResponseEntity containing the list of books in the wishlist.
     */
    @GetMapping("/{wishlistId}/books")
    public ResponseEntity<List<Books>> getBooksInWishlist(@PathVariable Long wishlistId) {
        List<Books> books = wishlistService.getBooksInWishlist(wishlistId);
        return ResponseEntity.ok(books);
    }

    /**
     * Removes a book from a wishlist and adds it to the user's shopping cart.
     *
     * @param wishlistId The ID of the wishlist.
     * @param bookId     The ID of the book to move.
     * @return ResponseEntity indicating the success of the operation.
     */
    @PostMapping("/wishlist/{wishlistId}/remove-book/{bookId}/to-cart")
    public ResponseEntity<Void> removeBookFromWishlistAndAddToCart(@PathVariable Long wishlistId, @PathVariable Long bookId) {
        wishlistService.removeBookFromWishlistAndAddToCart(wishlistId, bookId);
        return ResponseEntity.ok().build();
    }
}
