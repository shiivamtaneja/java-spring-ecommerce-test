package com.shivam.ecommerce.product_catalog_service.model;

/**
 * ErrorResponse represents the structure of an error response sent by the API.
 */
public class ErrorResponse {
    private Integer statusCode;  // HTTP status code indicating the type of error
    private String errorMessage; // Detailed error message from the server
    private String customMessage; // Custom message providing additional context or instructions

    /**
     * Constructs an ErrorResponse with the given status code, error message, and custom message.
     *
     * @param statusCode The HTTP status code indicating the error.
     * @param errorMessage The detailed error message from the server.
     * @param customMessage A custom message providing additional context or instructions.
     */
    public ErrorResponse(Integer statusCode, String errorMessage, String customMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
        this.customMessage = customMessage;
    }

    /**
     * Default constructor for ErrorResponse.
     */
    public ErrorResponse() {
    }

    /**
     * Gets the HTTP status code indicating the type of error.
     *
     * @return The HTTP status code.
     */
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the HTTP status code indicating the type of error.
     *
     * @param statusCode The HTTP status code to set.
     */
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Gets the detailed error message from the server.
     *
     * @return The error message.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the detailed error message from the server.
     *
     * @param errorMessage The error message to set.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Gets the custom message providing additional context or instructions.
     *
     * @return The custom message.
     */
    public String getCustomMessage() {
        return customMessage;
    }

    /**
     * Sets the custom message providing additional context or instructions.
     *
     * @param customMessage The custom message to set.
     */
    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }
}