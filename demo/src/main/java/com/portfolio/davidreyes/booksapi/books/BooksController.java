package com.portfolio.davidreyes.booksapi.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST controller for managing books.
 *
 * This controller provides endpoints for CRUD operations and additional features like:
 * - Fetching books by genre, rating, or top sellers.
 * - Updating book details, prices, or ratings.
 * - Removing duplicates and calculating average ratings.
 *
 * Base URL: /api/v1/book
 */
@RestController
@RequestMapping(path = "api/v1/book")
public class BooksController {

    private static final Logger logger = LoggerFactory.getLogger(BooksController.class);
    private final BooksService bookService;

    /**
     * Constructor for injecting the BooksService dependency.
     *
     * @param bookService Service layer for book management operations.
     */
    @Autowired
    public BooksController(BooksService bookService) {
        this.bookService = bookService;
    }

    /**
     * Retrieves a list of all books.
     *
     * @return List of all books in the system.
     */
    @GetMapping
    public List<Books> getBooks() {
        return bookService.getBooks();
    }

    /**
     * Registers new books in the system.
     *
     * @param books List of books to be added.
     * @return HTTP 200 response if successful.
     */
    @PostMapping
    public ResponseEntity<Void> registerNewBooks(@RequestBody List<Books> books) {
        bookService.addNewBooks(books);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes a book by its ID.
     *
     * @param bookId The ID of the book to delete.
     */
    @DeleteMapping(path = "{bookId}")
    public void deleteBook(@PathVariable("bookId") Long bookId) {
        bookService.deleteBook(bookId);
    }

    /**
     * Updates the details of a book by its ID.
     *
     * @param bookId        The ID of the book to update.
     * @param isbn          Optional updated ISBN.
     * @param bookName      Optional updated book name.
     * @param bookDescription Optional updated description.
     * @param price         Optional updated price.
     * @param authorId      Optional updated author ID.
     * @param genre         Optional updated genre.
     * @param publisher     Optional updated publisher.
     * @param yearPublished Optional updated year published.
     * @param copiesSold    Optional updated copies sold.
     */
    @PutMapping(path = "{bookId}")
    public void updateBook(
            @PathVariable("bookId") Long bookId,
            @RequestParam(required = false) Long isbn,
            @RequestParam(required = false) String bookName,
            @RequestParam(required = false) String bookDescription,
            @RequestParam(required = false) Integer price,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) Integer yearPublished,
            @RequestParam(required = false) Integer copiesSold) {
        bookService.updateBook(bookId, isbn, bookName, bookDescription, price, authorId, genre, publisher, yearPublished, copiesSold);
    }

    /**
     * Retrieves a book by its ISBN.
     *
     * @param isbn The ISBN of the book.
     * @return The book with the given ISBN.
     */
    @GetMapping("/{isbn}")
    public Books getBookByIsbn(@PathVariable Long isbn) {
        return bookService.getBookByIsbn(isbn);
    }

    /**
     * Retrieves books by genre.
     *
     * @param genre The genre of the books.
     * @return A list of books in the specified genre or HTTP 204 if none found.
     */
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Books>> getBooksByGenre(@PathVariable String genre) {
        Optional<List<Books>> optionalBooks = bookService.getBooksByGenre(genre);
        if (optionalBooks.isPresent()) {
            List<Books> books = optionalBooks.get();
            logger.info("Returning books: {}", books);
            return ResponseEntity.ok().body(books);
        } else {
            logger.info("No books found for genre: {}", genre);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * Retrieves the top-selling books.
     *
     * @return A list of top-selling books.
     */
    @GetMapping("/top-sellers")
    public ResponseEntity<List<Books>> getTopSellingBooks() {
        List<Books> books = bookService.getTopSellingBooks();
        return ResponseEntity.ok(books);
    }

    /**
     * Retrieves books by rating.
     *
     * @param rating The minimum rating threshold.
     * @return A list of books with the specified minimum rating.
     */
    @GetMapping("/by-rating/{rating}")
    public ResponseEntity<List<Books>> getBooksByRating(@PathVariable double rating) {
        List<Books> books = bookService.getBooksByRating(rating);
        return ResponseEntity.ok(books);
    }

    /**
     * Updates the rating of a book.
     *
     * @param bookId The ID of the book.
     * @param rating The new rating for the book.
     * @return HTTP 200 response if successful.
     */
    @PutMapping(path = "/{bookId}/rating")
    public ResponseEntity<Void> updateBookRating(@PathVariable("bookId") Long bookId, @RequestParam double rating) {
        bookService.updateBookRating(bookId, rating);
        return ResponseEntity.ok().build();
    }

    /**
     * Updates book prices by applying a discount for books by a specific publisher.
     *
     * @param publisher The publisher of the books.
     * @param discount  The discount percentage to apply.
     * @return HTTP 200 response if successful.
     */
    @PutMapping(path = "/update-price-by-publisher")
    public ResponseEntity<Void> updateBookPricesByPublisher(@RequestParam String publisher, @RequestParam double discount) {
        bookService.updateBookPricesByPublisher(publisher, discount);
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves the average rating for a book.
     *
     * @param bookId The ID of the book.
     * @return The average rating for the book.
     */
    @GetMapping("/{bookId}/average-rating")
    public ResponseEntity<Double> getBookAverageRating(@PathVariable Long bookId) {
        double averageRating = bookService.getAverageRatingForBook(bookId);
        return ResponseEntity.ok(averageRating);
    }

    /**
     * Removes duplicate books from the database.
     *
     * @return A message indicating success.
     */
    @PostMapping("/remove-duplicates")
    public ResponseEntity<String> removeDuplicateBooks() {
        bookService.removeDuplicateBooks();
        return ResponseEntity.ok("Duplicate books removed successfully");
    }
}
