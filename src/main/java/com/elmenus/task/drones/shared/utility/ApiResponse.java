package com.elmenus.task.drones.shared.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Utility class representing an API response with data, message, and HTTP status.
 *
 * @param <T> The type of data in the API response.
 */
@Data
@AllArgsConstructor
public class ApiResponse<T> {

    /**
     * The data included in the API response.
     */
    private T data;

    /**
     * A message providing additional information about the API response.
     */
    private String message;

    /**
     * The HTTP status of the API response.
     */
    private HttpStatus status;

    /**
     * Creates a successful API response with the provided data.
     *
     * @param <T>  The type of data in the API response.
     * @param data The data to include in the response.
     * @return An ApiResponse representing a successful response.
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, "Success", HttpStatus.OK);
    }

    /**
     * Creates an API response indicating an error with the provided message and status.
     *
     * @param <T>     The type of data in the API response.
     * @param message The error message providing details about the issue.
     * @param status  The HTTP status indicating the nature of the error.
     * @return An ApiResponse representing an error response.
     */
    public static <T> ApiResponse<T> error(String message, HttpStatus status) {
        return new ApiResponse<>(null, message, status);
    }
}