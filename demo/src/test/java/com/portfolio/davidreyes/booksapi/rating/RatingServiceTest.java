package com.portfolio.davidreyes.booksapi.rating;

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
 * Unit tests for the RatingService class.
 * Verifies the behavior of adding ratings and updating book ratings.
 */
class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BooksRepository booksRepository;

    @InjectMocks
    private RatingService ratingService;

    /**
     * Initializes Mockito mocks before each test case.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Verifies that adding a rating updates the book's average rating correctly.
     */
    @Test
    void addRatingShouldUpdateBooksAverageRating() {
        // Arrange
        Long userId = 1L;
        Long bookId = 1L;
        int ratingValue = 5;

        // Create mock objects for User and Books
        User mockUser = new User();
        mockUser.setId(userId);
        Books mockBook = new Books();
        mockBook.setId(bookId);

        // Configure mocks to return the mock objects when findById is called
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(booksRepository.findById(bookId)).thenReturn(Optional.of(mockBook));

        // Assume there are already ratings, leading to a list of Rating objects
        List<Rating> existingRatings = List.of(
                new Rating(mockBook, mockUser, 4),
                new Rating(mockBook, mockUser, 3)
        );

        // Mock the repository to return existing ratings
        when(ratingRepository.findByBookId(bookId)).thenReturn(existingRatings);

        // Act
        ratingService.addRating(userId, bookId, ratingValue);

        // Assert
        // Verify that the new rating was saved
        verify(ratingRepository, times(1)).save(any(Rating.class));

        // Calculate expected average rating
        double expectedAverageRating = (4 + 3 + 5) / 3.0;

        // Verify that the book's rating was updated
        assertEquals(expectedAverageRating, mockBook.getRating(), 0.001); // Allow for small rounding errors
        verify(booksRepository, times(1)).save(mockBook);
    }

    /**
     * Verifies that adding a rating throws an exception when the book is not found.
     */
    @Test
    void addRatingShouldThrowWhenBookNotFound() {
        // Arrange
        Long userId = 1L;
        Long bookId = 1L;
        int ratingValue = 5;

        when(booksRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> ratingService.addRating(userId, bookId, ratingValue));
    }

    /**
     * Verifies that adding a rating throws an exception when the user is not found.
     */
    @Test
    void addRatingShouldThrowWhenUserNotFound() {
        // Arrange
        Long userId = 1L;
        Long bookId = 1L;
        int ratingValue = 5;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> ratingService.addRating(userId, bookId, ratingValue));
    }
}
