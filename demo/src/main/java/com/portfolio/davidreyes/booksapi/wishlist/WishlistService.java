package com.portfolio.davidreyes.booksapi.wishlist;

import com.portfolio.davidreyes.booksapi.books.Books;
import com.portfolio.davidreyes.booksapi.books.BooksRepository;
import com.portfolio.davidreyes.booksapi.shoppingcart.ShoppingCart;
import com.portfolio.davidreyes.booksapi.shoppingcart.ShoppingCartRepository;
import com.portfolio.davidreyes.booksapi.user.User;
import com.portfolio.davidreyes.booksapi.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for managing wishlists.
 * Handles business logic related to wishlist creation, book management, and integration with shopping carts.
 */
@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BooksRepository bookRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    /**
     * Creates a new wishlist for a specified user.
     *
     * @param name   The name of the wishlist.
     * @param userId The ID of the user who owns the wishlist.
     * @throws IllegalArgumentException if the user is not found.
     * @throws IllegalStateException    if a wishlist with the same name already exists for the user.
     */
    @Transactional
    public void createWishlist(String name, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (wishlistRepository.findByNameAndUserId(name, userId).isPresent()) {
            throw new IllegalStateException("Wishlist with name " + name + " already exists for user " + userId);
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setName(name);
        wishlist.setUser(user);
        wishlistRepository.save(wishlist);
    }

    /**
     * Adds a book to an existing wishlist.
     *
     * @param wishlistId The ID of the wishlist.
     * @param bookId     The ID of the book to add.
     * @throws IllegalStateException if the wishlist or book is not found.
     */
    @Transactional
    public void addBookToWishlist(Long wishlistId, Long bookId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new IllegalStateException("Wishlist not found"));
        Books book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException("Book not found"));

        wishlist.getBooks().add(book);
        wishlistRepository.save(wishlist);
    }

    /**
     * Removes a book from a wishlist and adds it to the user's shopping cart.
     *
     * @param wishlistId The ID of the wishlist.
     * @param bookId     The ID of the book to move.
     * @throws IllegalStateException if the wishlist, book, or shopping cart is not found.
     */
    @Transactional
    public void removeBookFromWishlistAndAddToCart(Long wishlistId, Long bookId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new IllegalStateException("Wishlist with ID " + wishlistId + " not found."));
        Books book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException("Book with ID " + bookId + " not found."));

        User user = wishlist.getUser();

        if (!wishlist.getBooks().contains(book)) {
            throw new IllegalStateException("Book not found in the wishlist.");
        }

        wishlist.getBooks().remove(book);
        wishlistRepository.save(wishlist);

        ShoppingCart shoppingCart = user.getShoppingCart();
        shoppingCart.getBooks().add(book);
        shoppingCartRepository.save(shoppingCart);
    }

    /**
     * Retrieves all books in a specified wishlist.
     *
     * @param wishlistId The ID of the wishlist.
     * @return A list of books in the wishlist.
     * @throws IllegalStateException if the wishlist is not found.
     */
    public List<Books> getBooksInWishlist(Long wishlistId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new IllegalStateException("Wishlist with id " + wishlistId + " does not exist"));

        return new ArrayList<>(wishlist.getBooks());
    }

    /**
     * Placeholder method to retrieve a user based on a wishlist or book ID.
     * Implementation may vary based on application structure.
     *
     * @param wishlistId The ID of the wishlist.
     * @param bookId     The ID of the book.
     * @return A User object (currently a placeholder).
     */
    private User getUserByWishlistOrBookId(Long wishlistId, Long bookId) {

        return new User();
    }
}
