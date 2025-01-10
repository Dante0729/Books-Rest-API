package com.portfolio.davidreyes.booksapi.creditcard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing CreditCard entities.
 * Extends JpaRepository to provide basic CRUD operations.
 */
@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

}
