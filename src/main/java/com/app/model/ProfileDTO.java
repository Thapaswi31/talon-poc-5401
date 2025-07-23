package com.app.model;

import lombok.*;

/**
 * DTO representing the user profile sent to Talon.One.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDTO {

    private String userId;

    private String email;

    private String name;

    private int loyaltyPoints;

    // Additional custom attributes for Talon.One profile enrichment
}
