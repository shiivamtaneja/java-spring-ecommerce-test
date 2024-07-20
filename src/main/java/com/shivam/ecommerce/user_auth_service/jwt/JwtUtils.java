package com.shivam.ecommerce.user_auth_service.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    // Inject the JWT secret key from application properties
    @Value("${JWT_SECRET}")
    private String jwtSecret;

    // Inject the JWT expiration time from application properties
    @Value("${JWT_EXPIRATION_MS}")
    private String jwtExpirationMs;

    /**
     * Extracts the JWT token from the Authorization header of the HTTP request.
     *
     * @param httpRequest The HTTP request containing the Authorization header.
     * @return The JWT token if present, otherwise null.
     */
    public String getJwtFromHeader(HttpServletRequest httpRequest) {
        String bearerToken = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);

        logger.debug("Authorization Header: {}", bearerToken);

        // Check if the Authorization header contains a Bearer token
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Extract the token by removing the "Bearer " prefix
        }

        // Return null if the token is not present or not a Bearer token
        return null;
    }

    /**
     * Generates a JWT token for a given username.
     *
     * @param username The username for which the token is generated.
     * @return The generated JWT token.
     */
    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + Integer.parseInt(jwtExpirationMs))) // Set the token expiration time
                .signWith(key()) // Sign the token with the secret key
                .compact(); // Build the token
    }

    /**
     * Creates the secret key used for signing the JWT token.
     *
     * @return The secret key.
     */
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)); // Decode the secret key from Base64 format
    }

    /**
     * Extracts the username from a given JWT token.
     *
     * @param token The JWT token.
     * @return The username extracted from the token.
     */
    public String getUsernameFromJwtToken(String token) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) key()).build() // Verify the token using the secret key
                .parseSignedClaims(token) // Parse the token claims
                .getPayload().getSubject(); // Extract the subject(username) from the token claims
    }

    /**
     * Validates a given JWT token.
     * <p>
     * This method attempts to parse and verify the JWT token using a secret key.
     * It handles various exceptions that may occur during the validation process.
     *
     * @param token The JWT token to validate.
     * @return True if the token is valid, otherwise false.
     * @throws MalformedJwtException    If the token is malformed.
     * @throws ExpiredJwtException      If the token has expired.
     * @throws UnsupportedJwtException  If the token is not supported.
     * @throws IllegalArgumentException If the token is empty.
     */
    public boolean isJwtValid(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token);

            return true; // Return true if the token is valid
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage()); // Log error for malformed token
            throw e;
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token: {}", e.getMessage()); // Log error for expired token
            throw e;
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token: {}", e.getMessage()); // Log error for unsupported token
            throw e;
        } catch (IllegalArgumentException e) {
            logger.error("Empty JWT token: {}", e.getMessage()); // Log error for empty token
            throw e;
        }
    }
}
