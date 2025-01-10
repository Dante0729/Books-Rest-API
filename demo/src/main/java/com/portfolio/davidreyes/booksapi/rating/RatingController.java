package com.portfolio.davidreyes.booksapi.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for managing ratings.
 * Provides endpoints for adding ratings to books by users.
 */
@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    /**
     * Adds a new rating for a specific book by a specific user.
     *
     * @param userId ID of the user adding the rating.
     * @param bookId ID of the book being rated.
     * @param rating The rating score (e.g., 1-5).
     * @return ResponseEntity indicating the success of the operation.
     */
    @PostMapping("/add")
    public ResponseEntity<Void> addRating(@RequestParam Long userId, @RequestParam Long bookId, @RequestParam int rating) {
        ratingService.addRating(userId, bookId, rating);
        return ResponseEntity.ok().build();
    }
}
