package com.shivam.ecommerce.user_auth_service.config;

import com.shivam.ecommerce.user_auth_service.jwt.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class for the application.
 * <p>
 * This class configures the security settings for the application, including the HTTP request authorization rules,
 * CSRF protection, custom authentication provider, and the JWT filter.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private final ApplicationConfig applicationConfig;

    @Autowired
    private final AuthTokenFilter authTokenFilter;

    /**
     * Configures the security filter chain for the application.
     * <p>
     * This method sets up the HTTP security settings for the application, including:
     * - Authorization rules: Permitting access to sign up and sign in endpoints while requiring authentication for other requests.
     * - Disabling CSRF protection as it's not required for this setup.
     * - Setting a custom authentication provider from the ApplicationConfig.
     * - Adding a JWT filter before the default UsernamePasswordAuthenticationFilter.
     *
     * @param http The HttpSecurity object to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        // Configure authorization rules
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/api/v1/auth/signup/**", "/api/v1/auth/signin/**").permitAll() // Permit all requests to signup and signin endpoints
                .anyRequest().authenticated()); // Require authentication for all other requests

        // Disable CSRF protection as it is not required for this application
        http.csrf(AbstractHttpConfigurer::disable);

        // Set the custom authentication provider
        http.authenticationProvider(applicationConfig.authenticationProvider());

        // Add the JWT filter before the UsernamePasswordAuthenticationFilter
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // Build and return the SecurityFilterChain
        return http.build();
    }
}
