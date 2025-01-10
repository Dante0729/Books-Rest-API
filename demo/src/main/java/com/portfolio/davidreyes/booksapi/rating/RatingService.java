package com.portfolio.davidreyes.booksapi.rating;

import com.portfolio.davidreyes.booksapi.books.Books;
import com.portfolio.davidreyes.booksapi.books.BooksRepository;
import com.portfolio.davidreyes.booksapi.user.User;
import com.portfolio.davidreyes.booksapi.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service layer for managing ratings.
 * Handles the business logic for adding ratings and updating book ratings.
 */
@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BooksRepository booksRepository;

    /**
     * Adds a rating for a specific book by a specific user and updates the book's average rating.
     *
     * @param userId      ID of the user giving the rating.
     * @param bookId      ID of the book being rated.
     * @param ratingValue The rating value (e.g., 1-5).
     * @throws IllegalArgumentException if the user or book is not found.
     */
    @Transactional
    public void addRating(Long userId, Long bookId, int ratingValue) {
        // Fetch the user from the repository
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Fetch the book from the repository
        Books book = booksRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        // Create a new Rating entity
        Rating newRating = new Rating();
        newRating.setUser(user);
        newRating.setBook(book);
        newRating.setRating(ratingValue);

        // Save the new rating to the database
        ratingRepository.save(newRating);

        // Fetch all ratings for the book
        List<Rating> ratings = ratingRepository.findByBookId(bookId);

        // Calculate the average rating for the book
        double averageRating = ratings.stream()
                .mapToInt(Rating::getRating)
                .average()
                .orElse(0.0);

        // Update the book's average rating and save the book entity
        book.setRating(averageRating);
        booksRepository.save(book);
    }
}
