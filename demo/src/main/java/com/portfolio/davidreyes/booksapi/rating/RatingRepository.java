package com.portfolio.davidreyes.booksapi.rating;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing Rating entities.
 * Extends JpaRepository to provide basic CRUD operations.
 */
public interface RatingRepository extends JpaRepository<Rating, Long> {

    /**
     * Finds all ratings associated with a specific book.
     *
     * @param bookId ID of the book for which ratings are to be retrieved.
     * @return List of ratings associated with the specified book.
     */
    List<Rating> findByBookId(Long bookId);
}
