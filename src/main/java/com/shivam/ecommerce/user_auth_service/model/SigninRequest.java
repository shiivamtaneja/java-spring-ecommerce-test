package com.shivam.ecommerce.user_auth_service.model;

import lombok.Data;

/**
 * Represents the request body for user sign-in.
 * Contains the credentials needed for authentication.
 */
@Data
public class SigninRequest {
    private String username;
    private String password;
}
