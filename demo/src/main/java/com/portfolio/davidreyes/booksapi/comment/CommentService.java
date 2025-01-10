package com.portfolio.davidreyes.booksapi.comment;

import com.portfolio.davidreyes.booksapi.books.BooksRepository;
import com.portfolio.davidreyes.booksapi.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for managing comments.
 * Handles the business logic for adding and retrieving comments.
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BooksRepository booksRepository;

    /**
     * Adds a new comment for a specific book by a specific user.
     *
     * @param userId  ID of the user adding the comment.
     * @param bookId  ID of the book the comment is associated with.
     * @param comment The content of the comment.
     * @throws IllegalArgumentException if the user or book is not found.
     */
    @Transactional
    public void addComment(Long userId, Long bookId, String comment) {
        // Retrieve the user by their ID
        var user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User not found"));

        // Retrieve the book by its ID
        var book = booksRepository.findById(bookId).orElseThrow(() ->
                new IllegalArgumentException("Book not found"));

        // Create a new comment and associate it with the user and book
        var newComment = new Comment();
        newComment.setUser(user);
        newComment.setBook(book);
        newComment.setComment(comment);

        // Save the comment to the database
        commentRepository.save(newComment);
    }

    /**
     * Retrieves all comments for a specific book.
     *
     * @param bookId ID of the book for which comments are to be retrieved.
     * @return List of comments associated with the specified book.
     */
    public List<Comment> getCommentsByBookId(Long bookId) {
        return commentRepository.findByBookId(bookId);
    }
}
