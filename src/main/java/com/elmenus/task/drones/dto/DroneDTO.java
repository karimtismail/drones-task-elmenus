package com.elmenus.task.drones.dto;


import com.elmenus.task.drones.enums.DroneModel;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Data Transfer Object (DTO) class for representing drone information.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DroneDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Serial number of the drone. Must be unique and adhere to specific formatting rules.
     */
    @Size(max = 100, message = "Serial number must be at most 100 characters")
    @NotBlank(message = "Serial number is required")
    @NotEmpty(message = "Serial number cannot be empty")
    @Pattern(regexp = "^[A-Z0-9_]*$", message = "Serial number must contain only upper case letters, numbers, and '_'")
    private String serialNumber;

    /**
     * Model of the drone.
     */
    @NotNull(message = "Drone model is mandatory field")
    private DroneModel model;

    /**
     * Weight limit of the drone. Must be a non-negative value and not exceed 500 grams.
     */
    @NotEmpty(message = "Weight limit cannot be empty")
    @PositiveOrZero(message = "Weight limit must be a positive or zero value")
    @Max(value = 500, message = "Weight limit cannot exceed 500 grams")
    private Integer weightLimit;

    /**
     * Battery capacity of the drone. Must be a non-negative value and not exceed 100 percent.
     */
    @NotNull(message = "Battery capacity is required")
    @PositiveOrZero(message = "Battery capacity must be a positive or zero value")
    @Max(value = 100, message = "Battery capacity cannot exceed 100 percent")
    private Integer batteryCapacity;

}
