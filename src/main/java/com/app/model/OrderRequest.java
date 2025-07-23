package com.app.model;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * DTO for placing an order.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @NotNull
    private Long userId;

    @NotNull
    private CartRequest cartRequest;
}
