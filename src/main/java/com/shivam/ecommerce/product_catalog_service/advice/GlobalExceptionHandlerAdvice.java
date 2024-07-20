package com.shivam.ecommerce.product_catalog_service.advice;

import com.shivam.ecommerce.product_catalog_service.exception.NotFoundException;
import com.shivam.ecommerce.product_catalog_service.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandlerAdvice handles exceptions thrown by controllers
 * and provides consistent responses for errors.
 */
@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandlerAdvice.class);

    /**
     * Handles NotFoundException thrown by any controller.
     *
     * @param e The NotFoundException that was thrown.
     * @return A ResponseEntity containing an ApiResponse with HTTP status 404 (Not Found)
     * and the exception message.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(NotFoundException e) {
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(),"Not Found!", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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
