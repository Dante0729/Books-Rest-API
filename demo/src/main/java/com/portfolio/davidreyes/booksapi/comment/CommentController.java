package com.portfolio.davidreyes.booksapi.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing comments related to books.
 * Provides endpoints for adding and retrieving comments.
 */
@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * Adds a new comment for a specific book by a user.
     *
     * @param userId  ID of the user adding the comment.
     * @param bookId  ID of the book the comment is associated with.
     * @param comment The content of the comment.
     * @return ResponseEntity indicating the success of the operation.
     */
    @PostMapping("/add")
    public ResponseEntity<Void> addComment(@RequestParam Long userId, @RequestParam Long bookId, @RequestParam String comment) {
        commentService.addComment(userId, bookId, comment);
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves all comments for a specific book.
     *
     * @param bookId ID of the book for which comments are to be retrieved.
     * @return ResponseEntity containing the list of comments.
     */
    @GetMapping("/by-book/{bookId}")
    public ResponseEntity<List<Comment>> getCommentsByBookId(@PathVariable Long bookId) {
        List<Comment> comments = commentService.getCommentsByBookId(bookId);
        return ResponseEntity.ok(comments);
    }
}
