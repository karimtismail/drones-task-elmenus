package com.elmenus.task.drones.shared.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private T data;
    private String message;
    private HttpStatus status;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, "Success", HttpStatus.OK);
    }

    public static <T> ApiResponse<T> error(String message, HttpStatus status) {
        return new ApiResponse<>(null, message, status);
    }
}
