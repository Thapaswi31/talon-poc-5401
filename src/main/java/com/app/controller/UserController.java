package com.app.controller;

import com.app.model.User;
import com.app.model.OrderRequest;
import com.app.model.Order;
import com.app.model.CartRequest;
import com.app.model.RewardsResponse;
import com.app.service.UserService;
import com.app.service.OrderService;
import com.app.service.RewardsService;
import jakarta.validation.Valid;
lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * UserController handles user-related endpoints.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Fetch user details by ID.
     * GET /users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Update user's totalOrders and totalSpent.
     * PUT /users/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserStats(
            @PathVariable Long id,
            @Valid @RequestBody User updateRequest) {
        // Only allow updating totalOrders and totalSpent
        User updated = userService.updateUserStats(id, updateRequest.getTotalOrders(), updateRequest.getTotalSpent());
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }
}
