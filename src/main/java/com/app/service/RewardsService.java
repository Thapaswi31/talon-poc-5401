package com.app.service;

import com.app.model.CartRequest;
import com.app.model.RewardsResponse;
import com.app.talonone.TalonOneClient;
lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service layer for integrating with Talon.One to manage rewards and discounts.
 */
@Service
@RequiredArgsConstructor
public class RewardsService {

    private final TalonOneClient talonOneClient;

    /**
     * Evaluates rewards and discounts for a given cart by interacting with Talon.One.
     * @param cartRequest The cart request containing items and user information.
     * @return The rewards response with applicable discounts.
     */
    @Transactional(readOnly = true)
    public RewardsResponse evaluateRewards(CartRequest cartRequest) {
        // Update user profile in Talon.One
        talonOneClient.updateProfile(cartRequest.getUserId(), cartRequest);

        // Evaluate the session for discounts and rewards
        RewardsResponse rewardsResponse = talonOneClient.evaluateSession(cartRequest.getUserId(), cartRequest);

        return rewardsResponse;
    }

    /**
     * Confirms the usage of loyalty points for a user after an order is placed.
     * @param userId The ID of the user.
     * @param total The total amount for which loyalty points are confirmed.
     */
    @Transactional
    public void confirmLoyalty(String userId, double total) {
        talonOneClient.confirmLoyalty(userId, total);
    }
}
