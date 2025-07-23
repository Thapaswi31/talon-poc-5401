package com.app.config;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Configuration class for setting up a singleton, thread-safe RestTemplate
 * specifically configured for Talon.One Integration API communication.
 * <p>
 * This configuration injects the Talon.One API key securely from application properties,
 * attaches it as a Bearer token in the Authorization header for all outgoing requests,
 * and logs essential request details (HTTP method and URL) without exposing sensitive data.
 * </p>
 *
 * <p>
 * Properties should be defined in <code>application.properties</code> or <code>application.yml</code>:
 * <pre>
 * talonone.api-key=YOUR_API_KEY
 * talonone.base-url=https://YOUR_SUBDOMAIN.talon.one/integration/v1
 * </pre>
 * </p>
 *
 * @author Senior Automation Engineer
 */
@Configuration
public class RestTemplateConfig {

    private static final Logger logger = LoggerFactory.getLogger("TalonOneRestTemplate");

    /**
     * Defines a singleton, thread-safe RestTemplate bean for Talon.One Integration API.
     * The RestTemplate is configured with an interceptor that logs request details
     * and attaches the Authorization header with the API key.
     *
     * @param talonOneProperties Injected TalonOneProperties containing API key and base URL.
     * @return Configured RestTemplate instance.
     */
    @Bean
    @Primary
    public RestTemplate talonOneRestTemplate(TalonOneProperties talonOneProperties) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(
                Collections.singletonList(new TalonOneAuthLoggingInterceptor(talonOneProperties))
        );
        return restTemplate;
    }

    /**
     * ClientHttpRequestInterceptor implementation for logging and authentication.
     * Logs HTTP method and URI, and attaches the Authorization header using the API key.
     */
    private static class TalonOneAuthLoggingInterceptor implements ClientHttpRequestInterceptor {

        private final TalonOneProperties talonOneProperties;

        public TalonOneAuthLoggingInterceptor(TalonOneProperties talonOneProperties) {
            this.talonOneProperties = talonOneProperties;
        }

        @Override
        public org.springframework.http.client.ClientHttpResponse intercept(
                org.springframework.http.HttpRequest request,
                byte[] body,
                org.springframework.http.client.ClientHttpRequestExecution execution
        ) throws java.io.IOException {
            // Log HTTP method and URI (do not log headers or body)
            logger.info("Talon.One API Request: {} {}", request.getMethod(), request.getURI());

            // Attach Authorization header securely
            request.getHeaders().setBearerAuth(talonOneProperties.getApiKey());

            return execution.execute(request, body);
        }
    }
}

/**
 * Configuration properties for Talon.One Integration API.
 * <p>
 * Binds properties with prefix <code>talonone</code> from application properties.
 * </p>
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "talonone")
class TalonOneProperties {

    /**
     * Talon.One Integration API key (injected securely from application properties).
     */
    private String apiKey;

    /**
     * Talon.One Integration API base URL.
     */
    private String baseUrl;
}
