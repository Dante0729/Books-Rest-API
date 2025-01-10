package com.portfolio.davidreyes.booksapi.books;

import com.portfolio.davidreyes.booksapi.author.AuthorRepository;
import com.portfolio.davidreyes.booksapi.rating.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the BooksService class.
 * Uses Mockito for mocking dependencies and verifying interactions.
 */
class BooksServiceTest {

    @Mock
    private BooksRepository booksRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private BooksService booksService;

    /**
     * Initializes Mockito mocks before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Verifies that new books with unique ISBNs are saved.
     */
    @Test
    void addNewBooksShouldSaveBooksWhenIsbnIsNew() {
        // Arrange
        Books newBook = new Books();
        newBook.setIsbn(123456L);
        when(booksRepository.findBookByIsbn(newBook.getIsbn())).thenReturn(Optional.empty());

        // Act
        booksService.addNewBooks(Arrays.asList(newBook));

        // Assert
        verify(booksRepository, times(1)).save(any(Books.class));
    }

    /**
     * Verifies that adding a book with an existing ISBN throws an exception.
     */
    @Test
    void addNewBooksShouldThrowWhenIsbnExists() {
        // Arrange
        Books existingBook = new Books();
        existingBook.setIsbn(123456L);
        when(booksRepository.findBookByIsbn(existingBook.getIsbn())).thenReturn(Optional.of(existingBook));

        Books newBookWithSameIsbn = new Books();
        newBookWithSameIsbn.setIsbn(123456L);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> booksService.addNewBooks(Arrays.asList(newBookWithSameIsbn)));
    }

    /**
     * Verifies that all books are retrieved successfully.
     */
    @Test
    void getBooksShouldReturnAllBooks() {
        // Arrange
        Books book1 = new Books();
        Books book2 = new Books();
        when(booksRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Act
        List<Books> books = booksService.getBooks();

        // Assert
        assertEquals(2, books.size());
        verify(booksRepository, times(1)).findAll();
    }

    /**
     * Verifies that deleting a non-existent book throws an exception.
     */
    @Test
    void deleteBookShouldThrowWhenBookDoesNotExist() {
        // Arrange
        Long bookId = 1L;
        when(booksRepository.existsById(bookId)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> booksService.deleteBook(bookId));
    }

    /**
     * Verifies that book details are updated successfully.
     */
    @Test
    void updateBookShouldUpdateDetails() {
        // Arrange
        Long bookId = 1L;
        Books book = new Books();
        book.setId(bookId);

        when(booksRepository.findById(bookId)).thenReturn(Optional.of(book));

        Long newIsbn = 1234567890L;
        String newBookName = "New Name";

        // Act
        booksService.updateBook(bookId, newIsbn, newBookName, null, null, null, null, null, null, null);

        // Assert
        assertEquals(newIsbn, book.getIsbn());
        assertEquals(newBookName, book.getBookName());
    }

    /**
     * Verifies that books of a specific genre are retrieved successfully.
     */
    @Test
    void getBooksByGenreShouldReturnBooksWhenFound() {
        // Arrange
        String genre = "Fiction";
        Books book1 = new Books(123L, "Book One", "Description One", 20, null, genre, "Publisher One", 2021, 100);
        Books book2 = new Books(456L, "Book Two", "Description Two", 25, null, genre, "Publisher Two", 2022, 150);
        when(booksRepository.findByGenre(genre)).thenReturn(Arrays.asList(book1, book2));

        // Act
        Optional<List<Books>> result = booksService.getBooksByGenre(genre);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(2, result.get().size());
        assertEquals("Book One", result.get().get(0).getBookName());
        assertEquals("Book Two", result.get().get(1).getBookName());
    }

    /**
     * Verifies that an empty list is returned when no books of a specific genre are found.
     */
    @Test
    void getBooksByGenreShouldReturnEmptyWhenNoneFound() {
        // Arrange
        String genre = "Non-Fiction";
        when(booksRepository.findByGenre(genre)).thenReturn(Collections.emptyList());

        // Act
        Optional<List<Books>> result = booksService.getBooksByGenre(genre);

        // Assert
        assertTrue(result.isEmpty());
    }
}
