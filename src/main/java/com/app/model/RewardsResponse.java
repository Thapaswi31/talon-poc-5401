package com.app.model;

import lombok.*;
import java.util.List;

/**
 * DTO representing the response from Talon.One's reward/discount evaluation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RewardsResponse {

    private double discount;

    private List<String> appliedCampaigns;

    private int loyaltyPointsUsed;

    private int loyaltyPointsEarned;

    // Additional fields as returned by Talon.One can be added here
}
