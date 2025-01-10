package com.portfolio.davidreyes.booksapi.books;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing `Books` entities.
 *
 * Extends JpaRepository to provide CRUD operations and custom query methods
 * for interacting with the database.
 */
@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {

    /**
     * Finds a book by its ISBN.
     *
     * @param isbn The ISBN of the book.
     * @return An Optional containing the book if found, or empty if not.
     */
    Optional<Books> findBookByIsbn(Long isbn);

    /**
     * Finds books by the author's ID.
     *
     * @param authorId The ID of the author.
     * @return A list of books associated with the specified author.
     */
    List<Books> findByAuthorId(Long authorId);

    /**
     * Finds books by genre.
     *
     * @param genre The genre of the books.
     * @return A list of books in the specified genre.
     */
    List<Books> findByGenre(String genre);

    /**
     * Finds the top-selling books, ordered by copies sold in descending order.
     *
     * @param pageable Pageable object to limit the number of results.
     * @return A list of the top-selling books.
     */
    @Query("SELECT b FROM Books b ORDER BY b.copiesSold DESC")
    List<Books> findTopSellers(Pageable pageable);

    /**
     * Finds books with a rating greater than or equal to the specified value.
     *
     * @param rating The minimum rating threshold.
     * @return A list of books with ratings greater than or equal to the specified value.
     */
    List<Books> findByRatingGreaterThanEqual(double rating);

    /**
     * Finds books by publisher.
     *
     * @param publisher The publisher of the books.
     * @return A list of books published by the specified publisher.
     */
    List<Books> findByPublisher(String publisher);
}
