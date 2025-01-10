package com.portfolio.davidreyes.booksapi.wishlist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing Wishlist entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    /**
     * Finds a wishlist by its name and associated user ID.
     *
     * @param name   The name of the wishlist.
     * @param userId The ID of the user who owns the wishlist.
     * @return An Optional containing the Wishlist if found, or empty if not found.
     */
    Optional<Wishlist> findByNameAndUserId(String name, Long userId);
}
