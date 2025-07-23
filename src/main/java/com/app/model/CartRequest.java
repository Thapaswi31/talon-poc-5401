package com.app.model;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * DTO representing the user's cart, used for reward evaluation and order placement.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartRequest {

    @NotNull
    private Long userId;

    @NotNull
    private List<Item> items;

    private double total; // Calculated client-side or server-side before reward evaluation

    // Additional fields for Talon.One (e.g., sessionId, couponCodes) can be added here
}
