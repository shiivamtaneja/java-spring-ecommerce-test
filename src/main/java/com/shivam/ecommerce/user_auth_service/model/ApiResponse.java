package com.shivam.ecommerce.user_auth_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ApiResponse represents a standard structure for API responses.
 *
 * @param <T> The type of data included in the response.
 */
@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private int statusCode; // HTTP status code for the response
    private String message; // Message providing additional information about the response
    private T data; // The actual data returned in the response

}