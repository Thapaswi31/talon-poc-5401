package com.app.model;

import lombok.*;
import java.util.List;

/**
 * DTO representing a session sent to Talon.One for reward evaluation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionDTO {

    private String sessionId;

    private String userId;

    private List<Item> items;

    private double total;

    private List<String> couponCodes;

    // Additional session attributes for Talon.One
}
