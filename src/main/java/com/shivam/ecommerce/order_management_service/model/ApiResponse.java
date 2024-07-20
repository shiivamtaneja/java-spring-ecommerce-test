package com.shivam.ecommerce.order_management_service.model;

/**
 * ApiResponse represents a standard structure for API responses.
 *
 * @param <T> The type of data included in the response.
 */
public class ApiResponse<T> {
    private int statusCode; // HTTP status code for the response
    private String message; // Message providing additional information about the response
    private T data; // The actual data returned in the response

    /**
     * Constructs an ApiResponse with the given status code, message, and data.
     *
     * @param statusCode The HTTP status code for the response.
     * @param message    The message providing additional information about the response.
     * @param data       The data to be included in the response.
     */
    public ApiResponse(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    /**
     * Default constructor for ApiResponse.
     */
    public ApiResponse() {
    }

    /**
     * Gets the HTTP status code for the response.
     *
     * @return The HTTP status code.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the HTTP status code for the response.
     *
     * @param statusCode The HTTP status code to set.
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Gets the message providing additional information about the response.
     *
     * @return The message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message providing additional information about the response.
     *
     * @param message The message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the data included in the response.
     *
     * @return The data.
     */
    public T getData() {
        return data;
    }

    /**
     * Sets the data to be included in the response.
     *
     * @param data The data to set.
     */
    public void setData(T data) {
        this.data = data;
    }
}