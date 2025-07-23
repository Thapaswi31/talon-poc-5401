package com.app.model;

import jakarta.persistence.*;
lombok.*;

/**
 * Item entity representing a product in an order or cart.
 */
@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;

    private String name;

    private int quantity;

    private double price;

    // If needed, add @ManyToOne to Order for bidirectional mapping
    // @ManyToOne
    // @JoinColumn(name = "order_id")
    // private Order order;
}
