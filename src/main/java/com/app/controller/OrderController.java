package com.app.controller;

import com.app.model.Order;
import com.app.model.OrderRequest;
import com.app.model.RewardsResponse;
import com.app.service.OrderService;
import com.app.service.RewardsService;
import com.app.service.UserService;
import jakarta.validation.Valid;
lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * OrderController handles order-related endpoints.
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final RewardsService rewardsService;
    private final UserService userService;

    /**
     * Place an order, evaluate rewards, save order, and update user.
     * POST /orders
     */
    @PostMapping
    public ResponseEntity<Order> placeOrder(@Valid @RequestBody OrderRequest orderRequest) {
        // Evaluate rewards for the order/cart
        RewardsResponse rewards = rewardsService.evaluateRewards(orderRequest.getCartRequest());

        // Save the order
        Order savedOrder = orderService.saveOrder(orderRequest, rewards);

        // Update user stats (e.g., totalOrders, totalSpent)
        userService.updateUserStatsAfterOrder(savedOrder);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }
}
