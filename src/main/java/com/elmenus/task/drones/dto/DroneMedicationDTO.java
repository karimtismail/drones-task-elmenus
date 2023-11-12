package com.elmenus.task.drones.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Data Transfer Object (DTO) class for representing the association between a drone and a medication.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroneMedicationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID of the drone associated with the medication.
     */
    @NotNull(message = "Drone ID is required")
    private Long droneId;

    /**
     * ID of the medication associated with the drone.
     */
    @NotNull(message = "Medication ID is required")
    private Long medicationId;

    /**
     * Quantity of the medication associated with the drone.
     */
    private Integer quantity;
}
