package com.portfolio.davidreyes.booksapi.comment;

import com.portfolio.davidreyes.booksapi.books.Books;
import com.portfolio.davidreyes.booksapi.books.BooksRepository;
import com.portfolio.davidreyes.booksapi.user.User;
import com.portfolio.davidreyes.booksapi.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the CommentService class.
 * Validates the behavior of adding comments and retrieving comments by book ID.
 */
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BooksRepository booksRepository;

    @InjectMocks
    private CommentService commentService;

    /**
     * Initializes Mockito mocks before each test case.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Verifies that a comment is saved when both the user and the book exist.
     */
    @Test
    void addCommentShouldSaveCommentWhenUserAndBookExist() {
        // Arrange
        Long userId = 1L;
        Long bookId = 1L;
        String commentText = "Great book!";

        User mockUser = new User();
        mockUser.setId(userId);

        Books mockBook = new Books();
        mockBook.setId(bookId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(booksRepository.findById(bookId)).thenReturn(Optional.of(mockBook));

        // Act
        commentService.addComment(userId, bookId, commentText);

        // Assert
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    /**
     * Verifies that comments for a specific book are retrieved correctly.
     */
    @Test
    void getCommentsByBookIdShouldReturnComments() {
        // Arrange
        Long bookId = 1L;
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        List<Comment> mockComments = List.of(comment1, comment2);

        when(commentRepository.findByBookId(bookId)).thenReturn(mockComments);

        // Act
        List<Comment> comments = commentService.getCommentsByBookId(bookId);

        // Assert
        assertEquals(2, comments.size());
        verify(commentRepository, times(1)).findByBookId(bookId);
    }

    /**
     * Verifies that adding a comment throws an exception when the user is not found.
     */
    @Test
    void addCommentShouldThrowWhenUserNotFound() {
        // Arrange
        Long userId = 1L;
        Long bookId = 1L;
        String commentText = "Great book!";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> commentService.addComment(userId, bookId, commentText));
    }

    /**
     * Verifies that adding a comment throws an exception when the book is not found.
     */
    @Test
    void addCommentShouldThrowWhenBookNotFound() {
        // Arrange
        Long userId = 1L;
        Long bookId = 1L;
        String commentText = "Great book!";
        User mockUser = new User();
        mockUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(booksRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> commentService.addComment(userId, bookId, commentText));
    }
}
