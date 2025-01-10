package com.portfolio.davidreyes.booksapi.shoppingcart;

import com.portfolio.davidreyes.booksapi.books.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing ShoppingCart entities.
 * Extends JpaRepository to provide basic CRUD operations.
 */
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    /**
     * Finds a shopping cart by the user's ID.
     *
     * @param userId ID of the user.
     * @return An Optional containing the ShoppingCart if found, or empty if not found.
     */
    Optional<ShoppingCart> findByUserId(Long userId);

    /**
     * Retrieves all books in a user's shopping cart.
     * Uses a custom JPQL query to join ShoppingCart and Books entities.
     *
     * @param userId ID of the user.
     * @return A list of Books in the specified user's shopping cart.
     */
    @Query("SELECT b FROM ShoppingCart sc JOIN sc.books b WHERE sc.user.id = :userId")
    List<Books> findBooksInUserShoppingCart(@Param("userId") Long userId);
}
