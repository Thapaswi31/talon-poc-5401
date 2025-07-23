package com.app.service;

import com.app.model.Order;
import com.app.model.OrderRequest;
import com.app.model.CartRequest;
import com.app.model.RewardsResponse;
import com.app.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service layer for handling order-related business logic.
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserService userService;
    private final RewardsService rewardsService;
    private final OrderRepository orderRepository;

    /**
     * Places an order: evaluates rewards, saves the order, updates user stats, and confirms loyalty usage.
     * @param orderRequest The order request containing user and cart information.
     * @return The saved Order entity.
     */
    @Transactional
    public Order saveOrder(OrderRequest orderRequest, RewardsResponse rewardsResponse) {
        // Retrieve user
        Long userId = orderRequest.getUserId();
        com.app.model.User user = userService.getUserById(userId);

        // Apply discount from rewards response
        double discount = rewardsResponse != null ? rewardsResponse.getDiscount() : 0.0;
        double total = orderRequest.getCartRequest().getTotal() - discount;

        // Create and save the order
        Order order = new Order();
        order.setUserId(userId);
        order.setItems(orderRequest.getCartRequest().getItems());
        order.setTotal(total);
        order.setDiscount(discount);
        order.setStatus("PLACED");
        order.setCreatedAt(java.time.LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);

        // Update user statistics
        userService.updateUserStatsAfterOrder(savedOrder);

        // Confirm loyalty points usage
        rewardsService.confirmLoyalty(String.valueOf(userId), total);

        return savedOrder;
    }
}
