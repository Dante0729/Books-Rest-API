package com.portfolio.davidreyes.booksapi.author;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

/**
 * Unit test for the AuthorService class.
 * Uses Mockito to mock dependencies and verify interactions.
 */
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository; // Mocked dependency for testing

    @InjectMocks
    private AuthorService authorService; // Class under test

    /**
     * Initializes Mockito annotations before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Opens mocks for usage
    }

    /**
     * Verifies that the addAuthor method in the AuthorService calls the save method
     * in the AuthorRepository exactly once.
     */
    @Test
    void addAuthorShouldCallSaveOnRepository() {
        // Arrange
        Author author = new Author(); // Create a new Author object
        author.setFirstName("John");
        author.setLastName("Doe");
        author.setBiography("Sample biography");
        author.setPublisher("Sample Publisher");

        // Act
        authorService.addAuthor(author); // Call the method being tested

        // Assert
        verify(authorRepository, times(1)).save(any(Author.class)); // Verify interaction with the repository
    }
}
