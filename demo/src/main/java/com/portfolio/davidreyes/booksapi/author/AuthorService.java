package com.portfolio.davidreyes.booksapi.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service class for managing Author entities.
 *
 * This class contains business logic for managing authors, such as adding
 * new authors, adding multiple authors, and removing duplicate authors.
 *
 * Responsibilities:
 * - Interacts with the AuthorRepository to perform database operations.
 * - Ensures business rules are applied before saving or deleting authors.
 */
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    /**
     * Constructor for injecting the AuthorRepository dependency.
     *
     * @param authorRepository The repository for managing Author entities.
     */
    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * Adds a single author to the database.
     *
     * @param author The Author entity to be added.
     */
    public void addAuthor(Author author) {
        authorRepository.save(author);
    }

    /**
     * Adds multiple authors to the database in a single operation.
     *
     * @param authors A list of Author entities to be added.
     */
    public void addAuthors(List<Author> authors) {
        authorRepository.saveAll(authors);
    }

    /**
     * Removes duplicate authors from the database.
     *
     * A duplicate is defined as an author with the same first name, last name,
     * and publisher. This method iterates through all authors, uses a HashSet
     * to identify duplicates, and deletes them from the database.
     */
    public void removeDuplicateAuthors() {
        // Fetch all authors from the database
        List<Author> allAuthors = authorRepository.findAll();

        // Use a HashSet to track unique authors
        Set<String> uniqueAuthors = new HashSet<>();

        for (Author author : allAuthors) {
            // Create a unique key based on firstName, lastName, and publisher
            String authorKey = author.getFirstName() + "|" + author.getLastName() + "|" + author.getPublisher();

            // If the key already exists, delete the duplicate author
            if (!uniqueAuthors.add(authorKey)) {
                authorRepository.delete(author);
            }
        }
    }
}
