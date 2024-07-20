package com.shivam.ecommerce.user_auth_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Represents the response body for a successful user sign-in.
 * Contains information such as the JWT token and user details.
 */
@Data
@AllArgsConstructor
public class SigninResponse {
    private String jwtToken; // The JWT token issued for the authenticated user.

    private String username; // The username of the authenticated user.
    private List<String> roles; // The roles assigned to the user.
}
