package com.portfolio.davidreyes.booksapi.books;

import com.portfolio.davidreyes.booksapi.author.AuthorRepository;
import com.portfolio.davidreyes.booksapi.author.Author;
import com.portfolio.davidreyes.booksapi.rating.RatingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.portfolio.davidreyes.booksapi.rating.Rating;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service layer for managing book-related operations.
 */
@Service
public class BooksService {
    private static final Logger log = LoggerFactory.getLogger(BooksService.class);
    private final BooksRepository booksRepository;
    private final AuthorRepository authorRepository;
    private final RatingRepository ratingRepository;

    /**
     * Constructor to inject necessary dependencies.
     */
    @Autowired
    public BooksService(BooksRepository booksRepository, AuthorRepository authorRepository, RatingRepository ratingRepository) {
        this.booksRepository = booksRepository;
        this.authorRepository = authorRepository;
        this.ratingRepository = ratingRepository;
    }

    /**
     * Adds a list of new books after validating their ISBNs.
     *
     * @param booksList List of books to add.
     * @throws IllegalStateException if an ISBN is already taken.
     */
    public void addNewBooks(List<Books> booksList) {
        for (Books book : booksList) {
            Optional<Books> bookOptional = booksRepository.findBookByIsbn(book.getIsbn());
            if (bookOptional.isPresent()) {
                throw new IllegalStateException("ISBN " + book.getIsbn() + " is already taken");
            }
            validateISBN(book.getIsbn());
            booksRepository.save(book);
        }
    }

    /**
     * Validates the format of an ISBN (10 or 13 digits).
     *
     * @param isbn ISBN to validate.
     * @throws IllegalArgumentException if the ISBN is invalid.
     */
    private void validateISBN(Long isbn) {
        String isbnStr = isbn.toString();
        if (isbnStr.length() != 10 && isbnStr.length() != 13) {
            throw new IllegalArgumentException("Invalid ISBN length. ISBN must be 10 or 13 digits long.");
        }

        if (isbnStr.length() == 10) {
            validateISBN10(isbnStr);
        } else {
            validateISBN13(isbnStr);
        }
    }

    /**
     * Validates the checksum for an ISBN-10.
     */
    private void validateISBN10(String isbn) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int digit = isbn.charAt(i) - '0';
            if (digit < 0 || digit > 9) {
                throw new IllegalArgumentException("Invalid ISBN-10 format.");
            }
            sum += (digit * (10 - i));
        }

        char lastChar = isbn.charAt(9);
        sum += (lastChar == 'X' ? 10 : (lastChar - '0'));

        if (sum % 11 != 0) {
            throw new IllegalArgumentException("Invalid ISBN-10 checksum.");
        }
    }

    /**
     * Validates the checksum for an ISBN-13.
     */
    private void validateISBN13(String isbn) {
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = isbn.charAt(i) - '0';
            sum += digit * ((i % 2 == 0) ? 1 : 3);
        }

        int checksum = 10 - (sum % 10);
        if (checksum == 10) checksum = 0;

        if (checksum != isbn.charAt(12) - '0') {
            throw new IllegalArgumentException("Invalid ISBN-13 checksum.");
        }
    }

    /**
     * Retrieves all books.
     *
     * @return List of all books.
     */
    public List<Books> getBooks() {
        return booksRepository.findAll();
    }

    /**
     * Deletes a book by its ID.
     *
     * @param bookId ID of the book to delete.
     * @throws IllegalStateException if the book does not exist.
     */
    public void deleteBook(Long bookId) {
        boolean exists = booksRepository.existsById(bookId);
        if (!exists) {
            throw new IllegalStateException("book with id " + bookId + " does not exist");
        }
        booksRepository.deleteById(bookId);
    }

    /**
     * Updates book details.
     */
    @Transactional
    public void updateBook(Long bookId, Long isbn, String bookName, String bookDescription, Integer price, Long authorId, String genre, String publisher, Integer yearPublished, Integer copiesSold) {
        Books book = booksRepository.findById(bookId).orElseThrow(() -> new IllegalStateException("book with id " + bookId + " does not exist"));

        if (isbn != null && !Objects.equals(book.getIsbn(), isbn)) book.setIsbn(isbn);
        if (bookName != null && !bookName.isEmpty()) book.setBookName(bookName);
        if (bookDescription != null) book.setBookDescription(bookDescription);
        if (price != null) book.setPrice(price);
        if (genre != null) book.setGenre(genre);
        if (publisher != null) book.setPublisher(publisher);
        if (yearPublished != null) book.setYearPublished(yearPublished);
        if (copiesSold != null) book.setCopiesSold(copiesSold);

        if (authorId != null && (book.getAuthor() == null || !book.getAuthor().getId().equals(authorId))) {
            Author author = authorRepository.findById(authorId).orElseThrow(() -> new IllegalStateException("Author with id " + authorId + " does not exist"));
            book.setAuthor(author);
        }
    }

    /**
     * Retrieves a book by its ISBN.
     */
    public Books getBookByIsbn(Long isbn) {
        return booksRepository.findBookByIsbn(isbn).orElseThrow(() -> new IllegalStateException("Book with ISBN " + isbn + " does not exist"));
    }

    /**
     * Retrieves books by author ID.
     */
    public List<Books> getBooksByAuthorId(Long authorId) {
        authorRepository.findById(authorId).orElseThrow(() -> new IllegalStateException("Author with id " + authorId + " does not exist"));
        return booksRepository.findByAuthorId(authorId);
    }

    /**
     * Retrieves books by genre.
     */
    public Optional<List<Books>> getBooksByGenre(String genre) {
        log.info("Querying books by genre: {}", genre);
        List<Books> booksByGenre = booksRepository.findByGenre(genre);
        if (booksByGenre.isEmpty()) {
            log.warn("No books found for genre: {}", genre);
            return Optional.empty();
        }
        log.info("Found {} books for genre: {}", booksByGenre.size(), genre);
        return Optional.of(booksByGenre);
    }

    /**
     * Retrieves top-selling books.
     */
    public List<Books> getTopSellingBooks() {
        return booksRepository.findTopSellers(PageRequest.of(0, 10));
    }

    /**
     * Updates the rating of a book.
     */
    public void updateBookRating(Long bookId, double rating) {
        Books book = booksRepository.findById(bookId).orElseThrow(() -> new IllegalStateException("Book with ID " + bookId + " does not exist."));
        book.setRating(rating);
        booksRepository.save(book);
    }

    /**
     * Updates book prices by applying a discount based on the publisher.
     */
    public void updateBookPricesByPublisher(String publisher, double discountPercentage) {
        List<Books> booksByPublisher = booksRepository.findByPublisher(publisher);
        if (booksByPublisher.isEmpty()) {
            throw new IllegalStateException("No books found for publisher: " + publisher);
        }

        for (Books book : booksByPublisher) {
            double originalPrice = book.getPrice();
            double discountAmount = originalPrice * (discountPercentage / 100);
            int newPrice = (int) Math.round(originalPrice - discountAmount);
            book.setPrice(newPrice);
        }

        booksRepository.saveAll(booksByPublisher);
    }

    /**
     * Retrieves books with a rating greater than or equal to the specified value.
     */
    public List<Books> getBooksByRating(double rating) {
        return booksRepository.findByRatingGreaterThanEqual(rating);
    }

    /**
     * Calculates the average rating for a book based on its ratings.
     */
    public double getAverageRatingForBook(Long bookId) {
        booksRepository.findById(bookId).orElseThrow(() -> new IllegalStateException("Book with ID " + bookId + " does not exist"));
        return ratingRepository.findByBookId(bookId).stream().mapToInt(Rating::getRating).average().orElse(0.0);
    }

    /**
     * Removes duplicate books based on ISBN.
     */
    public void removeDuplicateBooks() {
        Set<Long> checkedIsbns = new HashSet<>();
        List<Books> allBooks = booksRepository.findAll();

        for (Books book : allBooks) {
            if (!checkedIsbns.add(book.getIsbn())) {
                booksRepository.delete(book);
            }
        }
    }
}
