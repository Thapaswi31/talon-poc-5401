package com.app.model;

import jakarta.persistence.*;
lombok.*;

/**
 * User entity representing an application user.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String name;

    private int totalOrders;

    private double totalSpent;

    private int loyaltyPoints; // For Talon.One loyalty integration

    // Additional profile fields can be added as needed
}
