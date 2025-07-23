package com.app.service;

import com.app.model.User;
import com.app.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service layer for managing user-related business logic.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Fetches a user by their ID.
     * @param id The ID of the user.
     * @return The User entity.
     * @throws EntityNotFoundException if the user is not found.
     */
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    /**
     * Updates the user's totalOrders and totalSpent statistics.
     * @param id The ID of the user.
     * @param totalOrders The new total orders count.
     * @param totalSpent The new total spent amount.
     * @return The updated User entity.
     */
    @Transactional
    public User updateUserStats(Long id, int totalOrders, double totalSpent) {
        User user = getUserById(id);
        user.setTotalOrders(totalOrders);
        user.setTotalSpent(totalSpent);
        return userRepository.save(user);
    }

    /**
     * Updates user statistics after an order is placed.
     * @param order The order that was placed.
     * @return The updated User entity.
     */
    @Transactional
    public User updateUserStatsAfterOrder(com.app.model.Order order) {
        User user = getUserById(order.getUserId());
        user.setTotalOrders(user.getTotalOrders() + 1);
        user.setTotalSpent(user.getTotalSpent() + order.getTotal());
        return userRepository.save(user);
    }
}
