package com.portfolio.davidreyes.booksapi.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing Comment entities.
 * Extends JpaRepository to provide basic CRUD operations.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Finds all comments associated with a specific book.
     *
     * @param bookId ID of the book for which comments are to be retrieved.
     * @return List of comments associated with the specified book.
     */
    List<Comment> findByBookId(Long bookId);
}
