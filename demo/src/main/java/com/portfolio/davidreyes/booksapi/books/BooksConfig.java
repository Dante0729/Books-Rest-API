package com.portfolio.davidreyes.booksapi.books;

import com.portfolio.davidreyes.booksapi.author.Author;
import com.portfolio.davidreyes.booksapi.author.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class for initializing book and author data.
 *
 * This class uses a Spring Boot `CommandLineRunner` bean to prepopulate the database
 * with sample data for testing or demonstration purposes.
 */
@Configuration
public class BooksConfig {

    @Autowired
    private AuthorRepository authorRepository;

    /**
     * Bean that runs on application startup to populate the database with sample books and authors.
     *
     * @param booksRepository Repository for managing `Books` entities.
     * @return A CommandLineRunner that inserts sample data into the database.
     */
    @Bean
    CommandLineRunner commandLineRunner(BooksRepository booksRepository) {
        return args -> {
            // Create and save sample authors
            Author fitzgerald = authorRepository.save(
                    new Author(
                            "F. Scott",
                            "Fitzgerald",
                            "Short biography of F. Scott Fitzgerald",
                            "Charles Scribner's Sons"
                    )
            );
            Author lee = authorRepository.save(
                    new Author(
                            "Harper",
                            "Lee",
                            "Short biography of Harper Lee",
                            "J. B. Lippincott & Co."
                    )
            );

            // Create sample books associated with authors
            Books book1 = new Books(
                    123456789L,
                    "The Great Gatsby",
                    "A novel set in the Roaring Twenties",
                    20,
                    fitzgerald,
                    "Fiction",
                    "Charles Scribner's Sons",
                    1925,
                    5000000
            );

            Books book2 = new Books(
                    987654321L,
                    "To Kill a Mockingbird",
                    "A novel about innocence and experience, kindness and cruelty",
                    15,
                    lee,
                    "Historical Fiction",
                    "J. B. Lippincott & Co.",
                    1960,
                    3000000
            );

            // Save the sample books to the database
            booksRepository.saveAll(List.of(book1, book2));
        };
    }
}
