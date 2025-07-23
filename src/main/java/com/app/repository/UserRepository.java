package com.app.repository;

import com.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for User entity.
 * <p>
 * Provides CRUD operations and query methods for User data using Spring Data JPA.
 * This interface should be used for all database interactions related to User entities.
 * </p>
 *
 * <p>
 * Note: Only default query methods provided by JpaRepository are included.
 * No custom queries are defined here.
 * </p>
 */
public interface UserRepository extends JpaRepository<User, Long> {
    // Default CRUD and query methods are sufficient for User entity management.
}
