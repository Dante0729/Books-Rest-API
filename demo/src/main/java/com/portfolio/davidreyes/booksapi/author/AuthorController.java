package com.portfolio.davidreyes.booksapi.author;

import com.portfolio.davidreyes.booksapi.books.BooksService;
import com.portfolio.davidreyes.booksapi.books.Books;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * REST controller for managing authors and their related books.
 * Provides endpoints to create, retrieve, and manage authors and their books.
 *
 * Endpoints:
 * - POST /api/v1/authors: Register a new author.
 * - POST /api/v1/authors/register/multiple: Register multiple authors at once.
 * - GET /api/v1/authors/{authorId}/books: Get books by a specific author.
 * - DELETE /api/v1/authors/remove-duplicates: Remove duplicate authors.
 */
@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final BooksService booksService;

    /**
     * Constructor for injecting required services.
     *
     * @param authorService Service for managing authors.
     * @param booksService  Service for managing books.
     */
    @Autowired
    public AuthorController(AuthorService authorService, BooksService booksService) {
        this.authorService = authorService;
        this.booksService = booksService;
    }

    /**
     * Endpoint to register a new author.
     *
     * @param author The author to be registered.
     */
    @PostMapping
    public void registerNewAuthor(@RequestBody Author author) {
        authorService.addAuthor(author);
    }

    /**
     * Endpoint to register multiple authors in a single request.
     *
     * @param authors A list of authors to be registered.
     * @return A ResponseEntity indicating success.
     */
    @PostMapping("/register/multiple")
    public ResponseEntity<Void> registerNewAuthors(@RequestBody List<Author> authors) {
        authorService.addAuthors(authors);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to retrieve books written by a specific author.
     *
     * @param authorId The ID of the author whose books are to be retrieved.
     * @return A ResponseEntity containing a list of books.
     */
    @GetMapping("/{authorId}/books")
    public ResponseEntity<List<Books>> getBooksByAuthor(@PathVariable Long authorId) {
        List<Books> books = booksService.getBooksByAuthorId(authorId);
        return ResponseEntity.ok(books);
    }

    /**
     * Endpoint to remove duplicate authors from the database.
     *
     * @return A ResponseEntity containing a success message.
     */
    @DeleteMapping("/remove-duplicates")
    public ResponseEntity<String> removeDuplicateAuthors() {
        authorService.removeDuplicateAuthors();
        return ResponseEntity.ok("Duplicate authors removed successfully");
    }
}
