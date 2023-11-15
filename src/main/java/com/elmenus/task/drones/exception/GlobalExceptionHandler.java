package com.elmenus.task.drones.exception;

import com.elmenus.task.drones.shared.utility.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * Global exception handler for handling various types of exceptions across the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the exception when a drone is not found.
     *
     * @param ex The exception indicating that a drone was not found.
     * @return ResponseEntity containing an error response with the exception message and HTTP status.
     */
    @ExceptionHandler(DroneNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleDroneNotFoundException(DroneNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles custom exceptions related to battery, weight, and drone state.
     *
     * @param ex The custom runtime exception.
     * @return ResponseEntity containing an error response with the exception message and HTTP status.
     */
    @ExceptionHandler({
            BatteryLowException.class,
            BatteryHighException.class,
            BatteryEqualException.class,
            WeightExceededException.class,
            DroneStateException.class})
    public ResponseEntity<ApiResponse<Void>> handleCustomExceptions(RuntimeException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles generic exceptions not specifically categorized.
     *
     * @param ex The generic exception.
     * @return ResponseEntity containing an error response with the exception message and HTTP status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles validation exceptions when the request payload does not meet the expected criteria.
     *
     * @param ex The validation exception containing information about validation errors.
     * @return ResponseEntity containing an error response with validation error messages and HTTP status.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String errorMessage = buildValidationErrorMessage(fieldErrors);
        return buildErrorResponse(errorMessage, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiResponse<Void>> buildErrorResponse(String message, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(ApiResponse.error(message, status));
    }

    private String buildValidationErrorMessage(List<FieldError> fieldErrors) {
        StringBuilder message = new StringBuilder("Validation failed for: ");
        for (FieldError error : fieldErrors) {
            message.append(String.format("'%s' with value '%s' (Reason: %s); ",
                    error.getField(),
                    error.getRejectedValue(),
                    error.getDefaultMessage()));
        }
        return message.toString();
    }
}