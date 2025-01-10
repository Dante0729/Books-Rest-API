package com.portfolio.davidreyes.booksapi.author;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Author entities.
 *
 * Extends JpaRepository to provide CRUD operations and additional
 * features such as pagination and sorting for the Author entity.
 *
 * JpaRepository methods available:
 * - save: Save or update an entity.
 * - findById: Retrieve an entity by its ID.
 * - findAll: Retrieve all entities.
 * - deleteById: Delete an entity by its ID.
 * - and more.
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
