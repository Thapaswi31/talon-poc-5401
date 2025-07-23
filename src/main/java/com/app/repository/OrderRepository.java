package com.app.repository;

import com.app.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Order entity.
 * <p>
 * Provides CRUD operations and query methods for Order data using Spring Data JPA.
 * This interface should be used for all database interactions related to Order entities.
 * </p>
 *
 * <p>
 * Note: Only default query methods provided by JpaRepository are included.
 * No custom queries are defined here.
 * </p>
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Default CRUD and query methods are sufficient for Order entity management.
}
