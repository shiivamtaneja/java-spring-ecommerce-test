package com.shivam.ecommerce.user_auth_service.config;

import com.shivam.ecommerce.user_auth_service.repository.UserRepository;
import com.shivam.ecommerce.user_auth_service.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for security-related beans in the application.
 * <p>
 * This class configures the authentication manager, user details service, password encoder,
 * and authentication provider used in the Spring Security framework.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepository userRepository;

    /**
     * Provides the AuthenticationManager bean.
     * <p>
     * The AuthenticationManager is responsible for handling authentication requests and
     * authenticating users based on provided credentials.
     *
     * @param authConfig The AuthenticationConfiguration used to build the AuthenticationManager.
     * @return The configured AuthenticationManager bean.
     * @throws Exception If an error occurs while obtaining the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Provides the UserDetailsService bean.
     * <p>
     * UserDetailsService is used to load user-specific data during authentication.
     * This implementation is provided by MyUserDetailsService.
     *
     * @return The configured UserDetailsService bean.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }

    /**
     * Provides the PasswordEncoder bean.
     * <p>
     * PasswordEncoder is used to encode passwords for storage and verify passwords during authentication.
     * BCryptPasswordEncoder is a popular implementation that uses the BCrypt hashing function.
     *
     * @return The configured PasswordEncoder bean.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides the AuthenticationProvider bean.
     * <p>
     * AuthenticationProvider is responsible for authenticating users by delegating to the UserDetailsService
     * and verifying passwords using the PasswordEncoder. DaoAuthenticationProvider is a commonly used
     * implementation that combines these functionalities.
     *
     * @return The configured AuthenticationProvider bean.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }
}
