package com.shivam.ecommerce.user_auth_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ErrorResponse represents the structure of an error response sent by the API.
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
    private Integer statusCode;  // HTTP status code indicating the type of error
    private String errorMessage; // Detailed error message from the server
    private String customMessage; // Custom message providing additional context or instructions
}