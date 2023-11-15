package com.elmenus.task.drones.exception;

import com.elmenus.task.drones.shared.utility.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DroneNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleDroneNotFoundException(DroneNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            BatteryLowException.class,
            BatteryHighException.class,
            BatteryEqualException.class,
            WeightExceededException.class,
            DroneStateException.class})
    public ResponseEntity<ApiResponse<Void>> handleCustomExceptions(RuntimeException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

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