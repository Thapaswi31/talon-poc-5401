package talonone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

/**
 * TalonOneClient is a reusable, centralized client for interacting with Talon.One's Integration API.
 * <p>
 * It provides methods to update customer profiles, evaluate sessions for rewards, and confirm loyalty points.
 * The client handles HTTP communication, authentication headers, and error handling, and is designed to be
 * injected and reused across the application.
 * </p>
 *
 * <p>
 * <b>Configuration:</b><br>
 * Configure the following properties in your <code>application.properties</code>:
 * <ul>
 *   <li><code>talonone.base-url</code> - The base URL of the Talon.One API (e.g., https://your-company.talon.one)</li>
 *   <li><code>talonone.api-key</code> - The API key for authenticating requests</li>
 * </ul>
 * </p>
 *
 * <p>
 * <b>Usage Example:</b>
 * <pre>
 * &#64;Autowired
 * private TalonOneClient talonOneClient;
 *
 * talonOneClient.updateProfile("user123", profileDto);
 * RewardsResponse response = talonOneClient.evaluateSession(sessionDto);
 * talonOneClient.confirmLoyalty("user123", 99.99);
 * </pre>
 * </p>
 *
 * <p>
 * <b>Note:</b> Replace <code>ProfileDTO</code>, <code>SessionDTO</code>, and <code>RewardsResponse</code>
 * with your actual DTO classes as needed.
 * </p>
 */
@Component
public class TalonOneClient {

    @Value("${talonone.base-url}")
    private String baseUrl;

    @Value("${talonone.api-key}")
    private String apiKey;

    private final WebClient webClient;

    /**
     * Constructs a TalonOneClient with a configured WebClient.
     */
    public TalonOneClient() {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * Updates a user profile in Talon.One.
     *
     * @param userId The unique identifier of the user.
     * @param dto    The profile data to update (ProfileDTO).
     * @throws TalonOneClientException if the API call fails.
     */
    public void updateProfile(String userId, Object dto) {
        String url = String.format("%s/v1/profiles/%s", baseUrl, userId);
        try {
            webClient.put()
                    .uri(url)
                    .bodyValue(dto)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException ex) {
            throw new TalonOneClientException("Failed to update profile: " + ex.getResponseBodyAsString(), ex.getStatusCode(), ex);
        } catch (Exception ex) {
            throw new TalonOneClientException("Failed to update profile: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    /**
     * Evaluates a session for rewards and discounts in Talon.One.
     *
     * @param dto The session data (SessionDTO).
     * @return The rewards response (RewardsResponse).
     * @throws TalonOneClientException if the API call fails.
     */
    public Object evaluateSession(Object dto) {
        String url = String.format("%s/v1/sessions", baseUrl);
        try {
            return webClient.post()
                    .uri(url)
                    .bodyValue(dto)
                    .retrieve()
                    .bodyToMono(Object.class) // Replace Object.class with RewardsResponse.class
                    .block();
        } catch (WebClientResponseException ex) {
            throw new TalonOneClientException("Failed to evaluate session: " + ex.getResponseBodyAsString(), ex.getStatusCode(), ex);
        } catch (Exception ex) {
            throw new TalonOneClientException("Failed to evaluate session: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    /**
     * Confirms the usage of loyalty points for a user in Talon.One.
     *
     * @param userId      The unique identifier of the user.
     * @param totalAmount The total amount for which loyalty points are confirmed.
     * @throws TalonOneClientException if the API call fails.
     */
    public void confirmLoyalty(String userId, double totalAmount) {
        String url = String.format("%s/v1/loyalty/%s/confirm", baseUrl, userId);
        try {
            webClient.post()
                    .uri(url)
                    .bodyValue(new LoyaltyConfirmRequest(totalAmount))
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException ex) {
            throw new TalonOneClientException("Failed to confirm loyalty: " + ex.getResponseBodyAsString(), ex.getStatusCode(), ex);
        } catch (Exception ex) {
            throw new TalonOneClientException("Failed to confirm loyalty: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    /**
     * Exception class for TalonOneClient errors.
     */
    public static class TalonOneClientException extends RuntimeException {
        private final HttpStatus status;

        public TalonOneClientException(String message, HttpStatus status, Throwable cause) {
            super(message, cause);
            this.status = status;
        }

        public HttpStatus getStatus() {
            return status;
        }
    }

    /**
     * DTO for loyalty confirmation request.
     * Replace or expand as needed to match Talon.One's API contract.
     */
    public static class LoyaltyConfirmRequest {
        private double totalAmount;

        public LoyaltyConfirmRequest(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }
    }

    // --- DTO Placeholders ---
    // Replace Object with your actual DTO classes as needed.
    // public static class ProfileDTO { ... }
    // public static class SessionDTO { ... }
    // public static class RewardsResponse { ... }
}
