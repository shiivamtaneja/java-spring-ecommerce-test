package com.shivam.ecommerce.user_auth_service.advice;

import com.shivam.ecommerce.user_auth_service.exceptions.ExpiredTokenException;
import com.shivam.ecommerce.user_auth_service.exceptions.NotFoundException;
import com.shivam.ecommerce.user_auth_service.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for handling authentication-related exceptions.
 * This class contains methods to handle specific exceptions and return appropriate
 * error responses to the client.
 */
@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandlerAdvice.class);

    /**
     * Handles ExpiredTokenException thrown when a JWT token has expired.
     *
     * @param e The ExpiredTokenException that was thrown.
     * @return A ResponseEntity containing an ErrorResponse with status 401 (Unauthorized)
     * and the exception message.
     */
    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ErrorResponse> handleExpiredTokenException(ExpiredTokenException e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles NotFoundException thrown by any controller.
     *
     * @param e The NotFoundException that was thrown.
     * @return A ResponseEntity containing an ApiResponse with HTTP status 404 (Not Found)
     * and the exception message.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(NotFoundException e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found!", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles AuthenticationException thrown during authentication failures.
     *
     * @param e The AuthenticationException that was thrown.
     * @return A ResponseEntity containing an ErrorResponse with status 400 (Bad Request)
     * and the exception message.
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(AuthenticationException e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request", e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles generic exceptions thrown by any controller.
     *
     * @param e The Exception that was thrown.
     * @return A ResponseEntity containing an ApiResponse with HTTP status 500 (Internal Server Error)
     * and a generic error message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Exception occurred: {}", e.getMessage());

        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
