package com.app.model;

import jakarta.persistence.*;
lombok.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Order entity representing a user's order.
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Foreign key to User
    @Column(nullable = false)
    private Long userId;

    // List of items in the order (stored as a JSON string for simplicity, or use @ElementCollection)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private List<Item> items;

    private double total;

    private double discount;

    @Column(nullable = false)
    private String status; // e.g., PLACED, CANCELLED

    private LocalDateTime createdAt;
}
